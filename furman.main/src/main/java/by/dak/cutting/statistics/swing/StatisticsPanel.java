package by.dak.cutting.statistics.swing;

import by.dak.cutting.DialogShowers;
import by.dak.cutting.statistics.StatisticFilter;
import by.dak.cutting.statistics.swing.tree.RootStaristicsNode;
import by.dak.cutting.swing.BaseTable;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Order;
import by.dak.report.jasper.JReportData;
import by.dak.report.jasper.ReportGeneratorImpl;
import by.dak.report.statistics.data.StatisticOrdersReportDataCreator;
import by.dak.report.swing.TableReportPanel;
import by.dak.swing.AStatisticPanel;
import by.dak.swing.ActionsPanel;
import by.dak.swing.table.ListNaviTable;
import net.sf.jasperreports.engine.JasperPrint;
import org.jdesktop.application.Application;
import org.jdesktop.swingx.plaf.UIManagerExt;
import org.jdesktop.swingx.table.ColumnControlButton;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Date;
import java.util.List;

/**
 * User: akoyro
 * Date: 16.05.2010
 * Time: 17:24:33
 */
public class StatisticsPanel<V> extends AStatisticPanel<V>
{
    private static final String PRINT_TABLE_ACTION_COMMAND = ColumnControlButton.COLUMN_CONTROL_MARKER +
            "printTable";

    @Override
    protected void init()
    {
        final PeriodFilterPanel periodFilterPanel = new PeriodFilterPanel();
        periodFilterPanel.init();
        periodFilterPanel.setValue(new StatisticFilter());
        ActionsPanel<JPanel> actionsPanel = new ActionsPanel<JPanel>(periodFilterPanel,
                Application.getInstance().getContext().getActionMap(periodFilterPanel),
                PeriodFilterPanel.ACTION_applyFilter,
                PeriodFilterPanel.ACTION_resetFilter,
                PeriodFilterPanel.ACTION_printFilter);
        actionsPanel.init();
        setPanelFilterControl(actionsPanel);
        final StatisticExplorerPanel explorerPanel = new StatisticExplorerPanel();
        explorerPanel.getOrdersPanel().init();
        DefaultTreeModel treeModel = new DefaultTreeModel(new DefaultMutableTreeNode());
        explorerPanel.getTreePanel().getTree().setModel(treeModel);
        setPanelResult(explorerPanel);
        createPrintTableAction(explorerPanel.getOrdersPanel().getListNaviTable());

        periodFilterPanel.addPropertyChangeListener(PeriodFilterPanel.ACTION_resetFilter, new PropertyChangeListener()
        {
            @Override
            public void propertyChange(PropertyChangeEvent evt)
            {
                DefaultTreeModel treeModel = new DefaultTreeModel(null);
                explorerPanel.getTreePanel().getTree().setModel(treeModel);
                explorerPanel.getOrdersPanel().setValue(null);
            }
        });

        periodFilterPanel.addPropertyChangeListener(PeriodFilterPanel.ACTION_printFilter, new PropertyChangeListener()
        {
            @Override
            public void propertyChange(PropertyChangeEvent evt)
            {
                StatisticFilter filter = periodFilterPanel.getValue();
                Date start = (filter != null && filter.getStart() != null) ? new Date(filter.getStart().getTime()) : null;
                Date end = (filter != null && filter.getEnd() != null) ? new Date(filter.getEnd().getTime()) : null;
                if (filter.getType() != null && start != null && end != null)
                {
                    DialogShowers.showStatisticsJasperViewer(filter.getType(), start, end);
                }
            }
        });

        periodFilterPanel.addPropertyChangeListener(PeriodFilterPanel.ACTION_applyFilter, new PropertyChangeListener()
        {
            @Override
            public void propertyChange(PropertyChangeEvent evt)
            {
                StatisticFilter filter = periodFilterPanel.getValue();


                RootStaristicsNode rootStaristicsNode = new RootStaristicsNode(filter);
                DefaultTreeModel treeModel = new DefaultTreeModel(rootStaristicsNode);
                explorerPanel.getTreePanel().getTree().setModel(treeModel);
                explorerPanel.getOrdersPanel().setValue(null);
                explorerPanel.getOrdersPanel().setValue(filter);
            }
        });
    }

    private void createPrintTableAction(final ListNaviTable<Order> listNaviTable)
    {
        AbstractAction printAction = new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                printTable(listNaviTable.getListUpdater().getList());
            }
        };
        printAction.putValue(Action.NAME, UIManagerExt.getString(BaseTable.UIPREFIX +
                PRINT_TABLE_ACTION_COMMAND, getLocale()));
        listNaviTable.getTable().getActionMap().put(PRINT_TABLE_ACTION_COMMAND, printAction);
    }

    public void printTable(List<Order> orders)
    {
        StatisticOrdersReportDataCreator reportDataCreator = new StatisticOrdersReportDataCreator();
        reportDataCreator.setOrders(orders);
        try
        {
            JReportData reportData = reportDataCreator.create();
            JasperPrint jasperPrint = ReportGeneratorImpl.getInstance().generate(reportData);
            TableReportPanel reportPanel = new TableReportPanel();
            reportPanel.setValue(jasperPrint);
            DialogShowers.showBy(reportPanel, this, false);
        }
        catch (Exception e)
        {
            FacadeContext.getExceptionHandler().handle(e);
        }

    }
}
