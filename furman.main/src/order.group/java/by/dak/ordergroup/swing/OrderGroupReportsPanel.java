package by.dak.ordergroup.swing;

import by.dak.cutting.linear.report.swing.LinearCuttingReportTab;
import by.dak.cutting.swing.store.modules.AEntityEditorPanel;
import by.dak.ordergroup.OrderGroup;

/**
 * User: akoyro
 * Date: 17.01.2011
 * Time: 11:46:42
 */
public class OrderGroupReportsPanel extends AEntityEditorPanel<OrderGroup>
{
    private static final String REPORT_BORDER_PRODUCTION = "border.production";
    private static final String REPORT_BOARD_PRODUCTION = "board.production";
    private static final String REPORT_ORDER_PRODUCTION = "order.production";
    private static final String REPORT_FURNITURE_PRODUCTION = "furniture.production";
    private static final String REPORT_LINEAR_CUTTING = "linear.cutting";

    public OrderGroupReportsPanel()
    {
        setShowOkCancel(false);
    }

    protected void addTabs()
    {
        addTab(REPORT_ORDER_PRODUCTION);
        addTab(REPORT_BOARD_PRODUCTION);
        addTab(REPORT_BORDER_PRODUCTION);
//        addTab(REPORT_FURNITURE_PRODUCTION);
        addLinearCuttingTab(REPORT_LINEAR_CUTTING);
        setShowOkCancel(false);
    }

    private void addLinearCuttingTab(String reportName)
    {
        LinearCuttingReportTab cuttingReportTab = new LinearCuttingReportTab();
        cuttingReportTab.setReportName(reportName);
        addTab(cuttingReportTab.getTitle(), cuttingReportTab);
        setTab(cuttingReportTab);
    }

    private void addTab(String reportName)
    {
        OrderGroupReportTab reportTab = new OrderGroupReportTab();
        reportTab.setReportName(reportName);
        addTab(reportTab.getTitle(), reportTab);
        setTab(reportTab);
    }
}
