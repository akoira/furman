package by.dak.ordergroup.swing.wizard;

import by.dak.cutting.linear.swing.LinearCuttersPanel;
import by.dak.cutting.swing.DModPanel;
import by.dak.ordergroup.swing.OrderGroupReportsPanel;
import by.dak.ordergroup.swing.OrderGroupTab;
import by.dak.ordergroup.swing.OrdersTab;
import by.dak.swing.wizard.DWizardPanelProvider;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;
import org.netbeans.spi.wizard.Wizard;
import org.netbeans.spi.wizard.WizardPage;
import org.netbeans.spi.wizard.WizardPanelNavResult;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class OrderGroupWizardPanelProvider extends DWizardPanelProvider<OrderGroupWizardController.Step>
{
    private static final ResourceMap resourceMap = Application.getInstance().getContext().getResourceMap(OrderGroupWizardPanelProvider.class);

    private OrderGroupTab orderGroupTab = new OrderGroupTab();
    private OrdersTab ordersTab = new OrdersTab();
    private OrderGroupReportsPanel orderGroupReportsPanel = new OrderGroupReportsPanel();
    private LinearCuttersPanel linearCuttersPanel = new LinearCuttersPanel();


    private OrderGroupWizardPanelProvider(String title, String[] steps, String[] descriptions)
    {
        super(title, steps, descriptions);
        orderGroupTab.init();
        ordersTab.init();
    }

    public static OrderGroupWizardPanelProvider createInstance()
    {
        return new OrderGroupWizardPanelProvider(resourceMap.getString("title"),
                new String[]{
                        OrderGroupWizardController.Step.orderGroup.name(),
                        OrderGroupWizardController.Step.orders.name(),
                        OrderGroupWizardController.Step.linearCutting.name(),
                        OrderGroupWizardController.Step.orderGroupReports.name(),
                },
                new String[]{
                        resourceMap.getString(OrderGroupWizardController.Step.orderGroup.name()),
                        resourceMap.getString(OrderGroupWizardController.Step.orders.name()),
                        resourceMap.getString(OrderGroupWizardController.Step.linearCutting.name()),
                        resourceMap.getString(OrderGroupWizardController.Step.orderGroupReports.name()),
                });
    }

    public JPanel getComponentBy(OrderGroupWizardController.Step step)
    {
        switch (step)
        {
            case orderGroup:
                return orderGroupTab;
            case orders:
                return ordersTab;
            case linearCutting:
                return linearCuttersPanel;
            case orderGroupReports:
                return orderGroupReportsPanel;
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    protected WizardPage createWizardPage(OrderGroupWizardController.Step step, final JPanel panel)
    {
        WizardPage wizardPage = new WizardPage(step.name())
        {
            @Override
            public WizardPanelNavResult allowBack(String stepName, Map settings, Wizard wizard)
            {
                return super.allowBack(stepName, settings, wizard);
            }

            @Override
            public WizardPanelNavResult allowFinish(String stepName, Map settings, Wizard wizard)
            {
                if (panel instanceof DModPanel)
                {
                    return ((DModPanel) panel).validateGUI() ? WizardPanelNavResult.PROCEED : WizardPanelNavResult.REMAIN_ON_PAGE;
                }
                return super.allowFinish(stepName, settings, wizard);
            }

            @Override
            public WizardPanelNavResult allowNext(String stepName, Map settings, Wizard wizard)
            {
                if (panel instanceof DModPanel)
                {
                    return ((DModPanel) panel).validateGUI() ? WizardPanelNavResult.PROCEED : WizardPanelNavResult.REMAIN_ON_PAGE;
                }
                else if (panel instanceof LinearCuttersPanel)
                {
                    return ((LinearCuttersPanel) panel).validateModel() ? WizardPanelNavResult.PROCEED : WizardPanelNavResult.REMAIN_ON_PAGE;
                }
                return super.allowNext(stepName, settings, wizard);
            }
        };
        wizardPage.setLayout(new BorderLayout());
        wizardPage.add(panel, BorderLayout.CENTER);
        return wizardPage;
    }

    @Override
    public OrderGroupWizardController.Step valueOf(String id)
    {
        return OrderGroupWizardController.Step.valueOf(id);
    }
}
