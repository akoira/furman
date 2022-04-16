/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */
package by.dak.report.model.impl;

import by.dak.common.swing.ExceptionHandler;
import by.dak.cutting.cut.facade.StripsFacade;
import by.dak.cutting.swing.cut.CuttingModel;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.MainFacade;
import by.dak.persistence.entities.AOrder;
import by.dak.plastic.jasper.data.DSPPlasticCuttedReportDataCreator;
import by.dak.report.ReportType;
import by.dak.report.jasper.DefaultReportCreatorFactory;
import by.dak.report.jasper.JReportData;
import by.dak.report.jasper.ReportGeneratorImpl;
import by.dak.report.jasper.common.data.CommonDataCreator;
import by.dak.report.jasper.common.data.CommonReportData;
import by.dak.report.jasper.cutting.data.CuttedReportDataCreator;
import by.dak.repository.IReportFacade;
import by.dak.utils.CollectionUtils;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author admin
 */
public class ReportModelCreator {
	private static final Logger LOGGER = Logger.getLogger(ReportModelCreator.class);
	private ExecutorService service = Executors.newCachedThreadPool();

	private final IReportFacade<AOrder, JasperPrint> reportFacade;
	private final StripsFacade stripsFacade;
	private final ExceptionHandler exceptionHandler;

	private boolean createReports = true;

	private AOrder order;

	private CuttingModel cuttingModel;
	private CommonReportData commonReportData;

	public ReportModelCreator(AOrder order, MainFacade mainFacade) {
		this.order = order;
		this.createReports = false;
		this.reportFacade = mainFacade.getReportFacade();
		this.stripsFacade = mainFacade.getStripsFacade();
		this.exceptionHandler = mainFacade.getExceptionHandler();
	}

	public ReportModelCreator(AOrder order, CuttingModel cuttingModel, MainFacade mainFacade) {
		this.order = order;
		this.cuttingModel = cuttingModel;
		this.reportFacade = mainFacade.getReportFacade();
		this.stripsFacade = mainFacade.getStripsFacade();
		this.exceptionHandler = mainFacade.getExceptionHandler();
	}

	public ReportsModelImpl create() {
		ReportsModelImpl reportsModel = new ReportsModelImpl();
		reportsModel.setOrder(order);
		reportsModel.setCuttingModel(this.cuttingModel);

		ArrayList<Future> futures = new ArrayList<Future>();
		List<ReportType> types = reportsModel.getReportTypes();

		for (ReportType reportType : types) {
			futures.add(service.submit(fillJasperPrintTo(reportsModel, reportType)));
		}

		CollectionUtils.waitWhileAllDone(futures);
		return reportsModel;
	}

	private Callable fillJasperPrintTo(final ReportsModelImpl reportsModel, final ReportType reportType) {
		Callable<JasperPrint> callable = new Callable<JasperPrint>() {
			@Override
			public JasperPrint call() throws Exception {
				try {
					boolean _createReports;
					JasperPrint report;
					synchronized (reportsModel) {
						report = FacadeContext.<AOrder, JasperPrint>getReportJCRFacade().loadReport(
								order, reportType);
						_createReports = ReportModelCreator.this.createReports;
					}
					if (report == null) {
						//нужно загружать сдесь так как если открывать из архива ордеров всегда загружается
						synchronized (reportsModel) {
							if (reportsModel.getCuttingModel() == null || !reportsModel.getCuttingModel().isStripsLoaded()) {
								reportsModel.setCuttingModel(stripsFacade.loadCuttingModel(order).load());
								ReportModelCreator.this.cuttingModel = reportsModel.getCuttingModel();
							}
						}
						Object reportObject = getReportObjectBy(reportType);
						if (reportObject != null) {
							reportsModel.setReportData(reportType, DefaultReportCreatorFactory.getInstance().createReportDataCreator(reportObject, reportType).create());

							reportsModel.setJasperPrint(reportType, getReportBy(reportType, reportsModel.getReportData(reportType)));
							ReportModelCreator.this.reportFacade.saveReport(order, reportType, reportsModel.getJasperPrint(reportType));
						}
					} else {
						synchronized (reportsModel) {
							reportsModel.setJasperPrint(reportType, report);
						}
					}
					return report;
				} catch (Throwable e) {
					ReportModelCreator.this.exceptionHandler.handle(e);
					LOGGER.error(e);
				}
				return null;
			}
		};
		return callable;
	}

	private Object getReportObjectBy(ReportType reportType) {
		switch (reportType) {
			case common:
			case production_common:
				return getCommonReportData();
			case cutting:
				return new CuttedReportDataCreator(cuttingModel).create();
			case glueing:
			case cutoff:
			case milling:
			case store:
			case zfacade:
			case agtfacade:
				return order;
			//todo модель надо передавать
			case cutting_dsp_plastic:
				return new DSPPlasticCuttedReportDataCreator(FacadeContext.getDSPPlasticStripsFacade().
						loadCuttingModel(order).load()).create();
			default:
				throw new IllegalArgumentException();
		}

	}


	private JasperPrint getReportBy(ReportType reportType, JReportData reportData) {
		switch (reportType) {
			case doorsingle:
			case doorscommon:
			case common:
			case production_common:
			case cutting:
			case glueing:
			case cutoff:
			case milling:
			case store:
			case cutting_dsp_plastic:
				try {
					return ReportGeneratorImpl.getInstance().generate(reportData);
				} catch (JRException e) {
					LOGGER.info(null, e);
				}
				return null;
			case zfacade:
			case agtfacade:
				try {
					JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportData.getJasperReportPath());
					JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, reportData.getAdditionalParams(), FacadeContext.getJDBCConnection());
					return jasperPrint;
				} catch (Throwable e) {
					FacadeContext.getExceptionHandler().handle(e);
				}
				return null;
			default:
				throw new IllegalArgumentException();
		}
	}

	public synchronized CommonReportData getCommonReportData() {
		if (commonReportData == null) {
			//bug9290 remove previous created common data
			if (order != null) {
				FacadeContext.getCommonDataFacade().deleteAll(order);
			}
			commonReportData = new CommonDataCreator(cuttingModel).create();
		}
		return commonReportData;
	}
}
