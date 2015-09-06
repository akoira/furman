package by.dak.ordergroup.swing.wizard;

import by.dak.cutting.CuttingApp;
import by.dak.cutting.DialogShowers;
import by.dak.cutting.linear.LinearCuttingModel;
import by.dak.cutting.linear.LinearCuttingModelCreator;
import by.dak.cutting.linear.swing.LinearCuttersPanel;
import by.dak.ordergroup.OrderGroup;
import by.dak.ordergroup.swing.OrderGroupReportsPanel;
import by.dak.ordergroup.swing.OrderGroupTab;
import by.dak.ordergroup.swing.OrdersTab;
import by.dak.persistence.FacadeContext;
import by.dak.swing.wizard.DWizardController;
import by.dak.swing.wizard.WizardStep;

import javax.swing.*;

/**
 * User: akoyro
 * Date: 17.01.2011
 * Time: 15:39:41
 */
public class OrderGroupWizardController extends DWizardController<OrderGroupWizardPanelProvider, OrderGroup, OrderGroupWizardController.Step>
{
    public OrderGroupWizardController()
    {
        setProvider(OrderGroupWizardPanelProvider.createInstance());
    }

    @Override
    protected void adjustCurrentStep(Step currentStep)
    {
        switch (currentStep)
        {

            case orderGroup:
                ((OrderGroupTab) getProvider().getComponentBy(currentStep)).setValue(null);
                ((OrderGroupTab) getProvider().getComponentBy(currentStep)).setValue(getModel());
                break;
            case orders:
                ((OrdersTab) getProvider().getComponentBy(currentStep)).setValue(null);
                ((OrdersTab) getProvider().getComponentBy(currentStep)).setValue(getModel());
                break;
            case linearCutting:
                initLinearCuttingStep(currentStep);
                break;
            case orderGroupReports:
                ((OrderGroupReportsPanel) getProvider().getComponentBy(currentStep)).setValue(null);
                ((OrderGroupReportsPanel) getProvider().getComponentBy(currentStep)).setValue(getModel());
                break;
            default:
                throw new IllegalArgumentException();
        }

    }

    /**
     * если cuttingModel не сохранена -> создаётся модель -> generate
     *
     * @param linearCuttingStep
     */
    private void initLinearCuttingStep(Step linearCuttingStep)
    {
        //модель не может проверить загружен у неё раскрой или нет
        final boolean[] loaded = {false};
        SwingWorker<LinearCuttingModel, LinearCuttingModel> swingWorker = new SwingWorker<LinearCuttingModel, LinearCuttingModel>()
        {
            @Override
            protected LinearCuttingModel doInBackground() throws Exception
            {
                LinearCuttingModel cuttingModel = FacadeContext.getLinearStripsFacade().loadLinearCuttingModelBy(getModel());
                if (cuttingModel == null)
                {
                    LinearCuttingModelCreator modelCreator = new LinearCuttingModelCreator();
                    cuttingModel = modelCreator.createCuttingModel(getModel());
                    loaded[0] = false;
                }
                else
                {
                    loaded[0] = true;
                }
                return cuttingModel;
            }
        };
        DialogShowers.showWaitDialog(swingWorker, getProvider().getComponentBy(Step.orders));
        try
        {
            LinearCuttingModel cuttingModel = swingWorker.get();
            ((LinearCuttersPanel) getProvider().getComponentBy(linearCuttingStep)).setCuttingModel(cuttingModel);
            if (!loaded[0])
            {
                ((LinearCuttersPanel) getProvider().getComponentBy(linearCuttingStep)).generate();
            }
        }
        catch (Exception e)
        {
            CuttingApp.getApplication().getExceptionHandler().handle(e);
        }

    }

    @Override
    protected void adjustLastStep(Step currentStep)
    {
        switch (currentStep)
        {
            case orderGroup:
                break;
            case orderGroupReports:
                break;
            case linearCutting:
                break;
            case orders:
                FacadeContext.getOrderGroupFacade().save(getModel());
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    protected Step getStepBy(String id)
    {
        return Step.valueOf(id);
    }

    @Override
    protected String getIdBy(Step step)
    {
        return step.name();
    }


    public static enum Step implements WizardStep
    {
        orderGroup,
        orders,
        linearCutting,
        orderGroupReports,
    }

}
