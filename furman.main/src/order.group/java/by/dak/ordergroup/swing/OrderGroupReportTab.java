package by.dak.ordergroup.swing;

import by.dak.cutting.CuttingApp;
import by.dak.cutting.DialogShowers;
import by.dak.cutting.swing.BaseTabPanel;
import by.dak.ordergroup.OrderGroup;
import by.dak.persistence.FacadeContext;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;
import org.apache.commons.io.IOUtils;
import org.springframework.jdbc.support.JdbcUtils;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * User: akoyro
 * Date: 17.01.2011
 * Time: 11:51:21
 */
public class OrderGroupReportTab extends BaseTabPanel<OrderGroup> {
	private JRViewer jrViewer;

	private String reportName;
	private static final String PRODUCTION_DATE = "PRODUCTION_DATE";
	private static final String ORDER_GROUP_ID = "ORDER_GROUP_ID";
	private static final String ORDERS = "ORDERS";


	public OrderGroupReportTab() {
		setLayout(new BorderLayout());
	}

	@Override
	protected void valueChanged() {
		SwingWorker swingWorker = new SwingWorker() {
			@Override
			protected Object doInBackground() throws Exception {
				Runnable runnable = new Runnable() {
					@Override
					public void run() {
						loadReport();
					}
				};
				SwingUtilities.invokeLater(runnable);
				return null;
			}
		};
		DialogShowers.showWaitDialog(swingWorker, this);

	}

	private void loadReport() {
		if (jrViewer != null) {
			remove(jrViewer);
		}

		if (getValue() != null) {
			InputStream stream = null;
			Connection jdbcConnection = null;
			try {
				Map parameters = new HashMap();
				parameters.put(PRODUCTION_DATE, getValue().getDailysheet().getDate());
				parameters.put(ORDER_GROUP_ID, getValue().getId());
				parameters.put(ORDERS, FacadeContext.getOrderFacade().getOrdersStringBy(getValue()));
				String reportPath = getResourceMap().getString(getReportName() + ".report.path");
				stream = DialogShowers.class.getClassLoader().getResourceAsStream(reportPath);
				JasperDesign jasperDesign = JRXmlLoader.load(stream);
				JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
				jdbcConnection = FacadeContext.getJDBCConnection();
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, jdbcConnection);


				if (jasperPrint.getPages() != null && jasperPrint.getPages().size() > 0) {
					jrViewer = new JRViewer(jasperPrint, Locale.getDefault());
					add(jrViewer, BorderLayout.CENTER);
					validate();
					Runnable runnable = new Runnable() {

						@Override
						public void run() {
							jrViewer.setFitPageZoomRatio();
						}
					};
					SwingUtilities.invokeLater(runnable);
				}
			} catch (Exception e) {
				CuttingApp.getApplication().getExceptionHandler().handle(e);
			} finally {
				if (stream != null)
					IOUtils.closeQuietly(stream);
				if (jdbcConnection != null) {
					JdbcUtils.closeConnection(jdbcConnection);
				}
			}
		}
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	@Override
	public String getTitle() {
		return getResourceMap().getString(getReportName() + "." + "panel.title");
	}
}
