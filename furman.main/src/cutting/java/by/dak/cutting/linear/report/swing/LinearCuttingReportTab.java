package by.dak.cutting.linear.report.swing;

import by.dak.cutting.CuttingApp;
import by.dak.cutting.DialogShowers;
import by.dak.cutting.linear.LinearCuttingModel;
import by.dak.cutting.linear.report.data.LinearCuttedDataReport;
import by.dak.cutting.linear.report.data.LinearCuttingReportDataCreator;
import by.dak.cutting.linear.report.data.LinearMaterialCuttedData;
import by.dak.cutting.linear.report.data.LinearMaterialCuttedDataCreator;
import by.dak.cutting.swing.BaseTabPanel;
import by.dak.ordergroup.OrderGroup;
import by.dak.persistence.FacadeContext;
import by.dak.report.jasper.JReportData;
import by.dak.report.jasper.ReportGeneratorImpl;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * User: dkoyro
 * Date: 28.04.11
 * Time: 17:22
 * To change this template use File | Settings | File Templates.
 */
public class LinearCuttingReportTab extends BaseTabPanel<OrderGroup>
{
    private JRViewer jrViewer;

    private String reportName;

    public LinearCuttingReportTab()
    {
        setLayout(new BorderLayout());
    }

    @Override
    protected void valueChanged()
    {
        SwingWorker swingWorker = new SwingWorker()
        {
            @Override
            protected Object doInBackground() throws Exception
            {
                Runnable runnable = new Runnable()
                {
                    @Override
                    public void run()
                    {
                        loadReport();
                    }
                };
                SwingUtilities.invokeLater(runnable);
                return null;
            }
        };
        DialogShowers.showWaitDialog(swingWorker, this);

    }

    public void loadReport()
    {
        if (jrViewer != null)
        {
            remove(jrViewer);
        }

        if (getValue() != null)
        {
            try
            {

                JReportData reportData = prepareReportData();
                if (reportData != null)
                {
                    ReportGeneratorImpl reportGenerator = ReportGeneratorImpl.getInstance();
                    JasperPrint jasperPrint = reportGenerator.generate(reportData);

                    if (jasperPrint.getPages() != null && jasperPrint.getPages().size() > 0)
                    {
                        jrViewer = new JRViewer(jasperPrint, Locale.getDefault());
                        add(jrViewer, BorderLayout.CENTER);
                        validate();
                        Runnable runnable = new Runnable()
                        {

                            @Override
                            public void run()
                            {
                                jrViewer.setFitPageZoomRatio();
                            }
                        };
                        SwingUtilities.invokeLater(runnable);
                    }
                }

            }
            catch (Exception e)
            {
                CuttingApp.getApplication().getExceptionHandler().handle(e);
            }
        }
    }

    private JReportData prepareReportData()
    {
        LinearCuttingModel cuttingModel = FacadeContext.getLinearStripsFacade().loadLinearCuttingModelBy(getValue());
        if (cuttingModel != null)
        {
            LinearMaterialCuttedDataCreator materialCuttedDataCreator = new LinearMaterialCuttedDataCreator(cuttingModel);

            java.util.List<LinearMaterialCuttedData> materialCuttedDataList = materialCuttedDataCreator.create();
            LinearCuttedDataReport cuttedDataReport = new LinearCuttedDataReport();
            cuttedDataReport.setOrderGroup(getValue());
            cuttedDataReport.setMaterialCuttedData(materialCuttedDataList);
            LinearCuttingReportDataCreator reportDataCreator = new LinearCuttingReportDataCreator(cuttedDataReport);
            return reportDataCreator.create();
        }
        return null;

    }

    public String getReportName()
    {
        return reportName;
    }

    public void setReportName(String reportName)
    {
        this.reportName = reportName;
    }

    @Override
    public String getTitle()
    {
        return getResourceMap().getString(getReportName() + "." + "panel.title");
    }

}
