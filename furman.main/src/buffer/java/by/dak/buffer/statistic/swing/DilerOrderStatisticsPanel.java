package by.dak.buffer.statistic.swing;

import by.dak.buffer.statistic.filter.DilerOrderFilter;
import by.dak.buffer.statistic.filter.swing.DilerOrderPeriodFilterPanel;
import by.dak.buffer.statistic.swing.tree.DilerCustomerNodes;
import by.dak.order.swing.IOrderWizardDelegator;
import by.dak.persistence.MainFacade;
import by.dak.swing.AStatisticPanel;
import by.dak.swing.ActionsPanel;
import by.dak.swing.explorer.ExplorerPanel;
import org.jdesktop.application.Application;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 27.11.2010
 * Time: 21:48:55
 * To change this template use File | Settings | File Templates.
 */
public class DilerOrderStatisticsPanel<V> extends AStatisticPanel<V>
{
    private IOrderWizardDelegator orderWizardDelegator;

    public DilerOrderStatisticsPanel(MainFacade mainFacade)
    {
        super(mainFacade);
    }

    public IOrderWizardDelegator getOrderWizardDelegator()
    {
        return orderWizardDelegator;
    }

    public void setOrderWizardDelegator(IOrderWizardDelegator orderWizardDelegator)
    {
        this.orderWizardDelegator = orderWizardDelegator;
    }

    @Override
    protected void init()
    {
        final DilerOrderPeriodFilterPanel periodFilterPanel = new DilerOrderPeriodFilterPanel();
        periodFilterPanel.init();
        periodFilterPanel.setValue(new DilerOrderFilter());
        ActionsPanel<JPanel> actionsPanel = new ActionsPanel<JPanel>(periodFilterPanel,
                Application.getInstance().getContext().getActionMap(periodFilterPanel),
                DilerOrderPeriodFilterPanel.ACTION_applyFilter,
                DilerOrderPeriodFilterPanel.ACTION_resetFilter);
        actionsPanel.init();
        setPanelFilterControl(actionsPanel);
        final ExplorerPanel explorerPanel = new ExplorerPanel();
        DefaultTreeModel treeModel = new DefaultTreeModel(new DefaultMutableTreeNode());
        explorerPanel.getTreePanel().getTree().setModel(treeModel);
        setPanelResult(explorerPanel);

        periodFilterPanel.addPropertyChangeListener(DilerOrderPeriodFilterPanel.ACTION_resetFilter, new PropertyChangeListener()
        {
            @Override
            public void propertyChange(PropertyChangeEvent evt)
            {
                DefaultTreeModel treeModel = new DefaultTreeModel(null);
                explorerPanel.getTreePanel().getTree().setModel(treeModel);
            }
        });

        periodFilterPanel.addPropertyChangeListener(DilerOrderPeriodFilterPanel.ACTION_applyFilter, new PropertyChangeListener()
        {
            @Override
            public void propertyChange(PropertyChangeEvent evt)
            {
                DilerOrderFilter filter = periodFilterPanel.getValue();


                DilerCustomerNodes dilerCustomerNodes = new DilerCustomerNodes(filter);
                dilerCustomerNodes.setOrderWizardDelegator(getOrderWizardDelegator());
                DefaultTreeModel treeModel = new DefaultTreeModel(dilerCustomerNodes);
                explorerPanel.getTreePanel().getTree().setModel(treeModel);

            }
        });
    }
}
