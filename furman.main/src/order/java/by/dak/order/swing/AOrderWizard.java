/*
 * To change this template, choose Tools | Templates and open the template in the draw.
 */
package by.dak.order.swing;

import by.dak.cutting.DialogShowers;
import by.dak.cutting.swing.CuttersPanel;
import by.dak.cutting.swing.MainCuttingPanel;
import by.dak.cutting.swing.cut.CuttingModel;
import by.dak.cutting.swing.cut.CuttingModelEvent;
import by.dak.cutting.swing.order.OrderDetailsPanel;
import by.dak.cutting.swing.order.wizard.Pages;
import by.dak.cutting.swing.report.ReportsPanel;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.AOrder;
import by.dak.plastic.controller.DSPPlasticDelegator;
import by.dak.report.model.impl.ReportsModelImpl;
import by.dak.swing.wizard.Step;
import org.jdesktop.application.Application;
import org.netbeans.spi.wizard.*;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static by.dak.cutting.swing.order.wizard.Pages.*;

/**
 * @author admin
 */
public abstract class AOrderWizard<P extends JComponent, O extends AOrder> extends WizardPanelProvider
{
    private OrderCuttingStep cuttingStep = new OrderCuttingStep();

    private OrderReportsStep reportsStep = new OrderReportsStep();

    private OrderItemsStep itemsStep = new OrderItemsStep();

    private O order;

    private IOrderWizardDelegator iOrderWizardDelegator;


    protected abstract Step<P> getInfoStep();

    public O getOrder()
    {
        return order;
    }

    public void setOrder(O order)
    {
        this.order = order;
        ((IOrderStepDelegator) getInfoStep().getView()).setOrder(order);

        ((IOrderStepDelegator) getInfoStep().getView()).setEditable(order.isEditable());
        cuttingStep.getView().setEditable(order.isEditable());
        itemsStep.getView().setEditable(order.isEditable());
        reportsStep.getView().setEditable(order.isEditable());
    }

    public AOrderWizard(String orderName, Class wizardClass)
    {
        super(Application.getInstance().getContext().getResourceMap(wizardClass).getString("title", orderName),
                new String[]
                        {
                                info.name(),
                                items.name(),
                                cutting.name(),
                                reports.name()

                        },
                new String[]
                        {
                                Application.getInstance().getContext().getResourceMap(wizardClass).getString(info.name()),
                                Application.getInstance().getContext().getResourceMap(wizardClass).getString(items.name()),
                                Application.getInstance().getContext().getResourceMap(wizardClass).getString(cutting.name()),
                                Application.getInstance().getContext().getResourceMap(wizardClass).getString(reports.name())
                        });
    }


    @Override
    protected JComponent createPanel(WizardController controller, String id, Map settings)
    {
        return getComponentPageBy(id);
    }

    public Step getComponentPageBy(String id)
    {
        switch (Pages.valueOf(id))
        {
            case info:
                ((IOrderStepDelegator) getInfoStep().getView()).setFocusToFirstComponent();
                return getInfoStep();
            case items:
                return itemsStep;
            case cutting:
                return cuttingStep;
            case reports:
                return reportsStep;
            default:
                throw new IllegalArgumentException(java.util.ResourceBundle.getBundle(
                        "by/dak/cutting/swing/order/resources/NewOrderPanel").getString("Illegal_page_id_") + id);

        }
    }

    private void reloadProperties(boolean load, JComponent jComponent)
    {
        try
        {
            if (load)
            {
                Application.getInstance().getContext().getSessionStorage().restore(jComponent, jComponent.getName() + ".xml");
            }
            else
            {
                Application.getInstance().getContext().getSessionStorage().save(jComponent, jComponent.getName() + ".xml");
            }
        }
        catch (IOException e)
        {
            Logger.getLogger(OrderDetailsPanel.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public OrderItemsStep getItemsStep()
    {
        return itemsStep;
    }


    public WizardObserver getWizardObserver()
    {
        return new WizardObserver()
        {

            @Override
            public void stepsChanged(Wizard wizard)
            {
            }

            @Override
            public void navigabilityChanged(Wizard wizard)
            {
            }

            @Override
            public void selectionChanged(final Wizard wizard)
            {
                if (wizard.getCurrentStep() != null)
                {
                    /**
                     * todo этот Runnable нужен для дого чтобы он запускался после того как текущий компонент будет добавлен в диалог
                     *
                     */
                    Runnable runnable = new Runnable()
                    {

                        @Override
                        public void run()
                        {
                            Step step = getComponentPageBy(wizard.getCurrentStep());
                            reloadProperties(true, step.getView());
                        }
                    };
                    SwingUtilities.invokeLater(runnable);
                }

                //todo логика определению предидущего step должна быть изменена
                if (wizard.getPreviousStep() != null)
                {
                    Step step = getComponentPageBy(wizard.getPreviousStep());
                    if (step.getParent() == null)
                    {
                        step = getComponentPageBy(wizard.getNextStep());
                    }
                    reloadProperties(false, step.getView());
                }
            }
        };
    }

    public IOrderWizardDelegator getiOrderWizardDelegator()
    {
        return iOrderWizardDelegator;
    }

    public void setiOrderWizardDelegator(IOrderWizardDelegator iOrderWizardDelegator)
    {
        this.iOrderWizardDelegator = iOrderWizardDelegator;
    }

    public class OrderItemsStep extends Step<OrderItemsExplorer> implements PropertyChangeListener
    {

        boolean clean = true;

        @Override
        public void propertyChange(PropertyChangeEvent evt)
        {
            if (clean && getView().isEditable())
            {
                FacadeContext.getStripsFacade().deleteAll(order);
                FacadeContext.getReportJCRFacade().removeAll(order);
                clean = false;
            }
        }

        public OrderItemsStep()
        {

            super(new OrderItemsExplorer());
        }

        @Override
        protected boolean validate(Map map, Wizard wizard)
        {
            return getView().validateGui();
        }

        @Override
        protected void proceedNext(Map map, Wizard wizard)
        {
            getView().saveSessionState();
            getView().save();
            getView().removeClearNextStepListener(this);

            SwingWorker<CuttingModel, CuttingModel> swingWorker = new SwingWorker<CuttingModel, CuttingModel>()
            {
                @Override
                protected CuttingModel doInBackground() throws Exception
                {
                    CuttingModel model = FacadeContext.getStripsFacade().loadCuttingModel(getView().getOrder()).load();
                    return model;
                }
            };

            DialogShowers.showWaitDialog(swingWorker, getView());

            try
            {

                CuttingModel cuttingModel = swingWorker.get();

                cuttingModel.addStripsChangedListener(cuttingStep);
                cuttingModel.addRefreshListener(cuttingStep);
                cuttingStep.getView().getCommonCuttersPanel().setCuttingModel(cuttingModel);
                cuttingStep.getView().getCommonCuttersPanel().setOrderBoardDetailFacade(FacadeContext.getOrderFurnitureFacade());
                cuttingStep.getView().getCommonCuttersPanel().setStripsFacade(FacadeContext.getStripsFacade());

                if (!cuttingModel.isStripsLoaded())
                {
                    cuttingStep.getView().getCommonCuttersPanel().generate();
                }


            }
            catch (Throwable e)
            {
                throw new IllegalArgumentException(e);
            }
        }

        @Override
        protected void remainOnPage(Map map, Wizard wizard)
        {
        }

        public void setOrder(AOrder order)
        {
            getView().removeClearNextStepListener(this);
            getView().setOrder(order);
            getView().setEditable(order.isEditable());
            clean = true;
            /**
             * the listener should be add after all events - everything should be loaded
             */
            Runnable runnable = new Runnable()
            {
                public void run()
                {
                    getView().addClearNextStepListener(OrderItemsStep.this);
                }
            };
            SwingUtilities.invokeLater(runnable);
        }
    }

    private class OrderCuttingStep extends Step<MainCuttingPanel> implements PropertyChangeListener
    {
        private DSPPlasticDelegator dspPlasticDelegator = new DSPPlasticDelegator();


        public OrderCuttingStep()
        {
            super(new MainCuttingPanel());
            dspPlasticDelegator.setMainCuttingPanel(getView());
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt)
        {
            if (evt.getPropertyName().equals(CuttingModelEvent.Type.STRIPS_CHANGED.name()) ||
                    evt.getPropertyName().equals(CuttingModelEvent.Type.REFRESH.name()))
            {
                FacadeContext.getReportJCRFacade().removeAll(order);
            }
        }


        @Override
        public WizardPanelNavResult allowBack(String s, Map map, Wizard wizard)
        {
            if (getView().isEditable() && getView().getCommonCuttersPanel().getState() != CuttersPanel.State.STOPED)
            {
                //todo добавить сообщение что cutting не готов
                return WizardPanelNavResult.REMAIN_ON_PAGE;
            }
            itemsStep.setOrder(order);
            return super.allowBack(s, map, wizard);
        }

        @Override
        protected boolean validate(Map map, Wizard wizard)
        {
            return validateCommonCutting() && dspPlasticDelegator.validate();
        }

        private boolean validateCommonCutting()
        {
            return getView().getCommonCuttersPanel().validateModel();
        }

        @Override
        protected void proceedNext(Map map, Wizard wizard)
        {
            AOrder order = getView().getCommonCuttersPanel().getCuttingModel().getOrder();

            ReportsModelImpl reportsModel = new ReportsModelImpl();
            reportsModel.setCuttingModel(getView().getCommonCuttersPanel().getCuttingModel());
            reportsModel.setOrder(order);
            reportsStep.getReportsPanel().setReportsModel(reportsModel);
            Runnable runnable = () -> reportsStep.getReportsPanel().create();

            SwingUtilities.invokeLater(runnable);
        }

        @Override
        protected void remainOnPage(Map map, Wizard wizard)
        {
        }
    }

    private class OrderReportsStep extends Step<ReportsPanel>
    {

        private ReportsPanel reportsPanel = new ReportsPanel();

        public OrderReportsStep()
        {
            super(null);
            setView(reportsPanel);
        }

        public ReportsPanel getReportsPanel()
        {
            return reportsPanel;
        }

        @Override
        protected boolean validate(Map map, Wizard wizard)
        {
            return false;
        }

        @Override
        protected void proceedNext(Map map, Wizard wizard)
        {
        }

        @Override
        protected void remainOnPage(Map map, Wizard wizard)
        {
        }

        @Override
        public WizardPanelNavResult allowBack(String s, Map map, Wizard wizard)
        {
            return WizardPanelNavResult.PROCEED;
        }
    }

    @Override
    protected Object finish(Map settings) throws WizardException
    {
        Object result = super.finish(settings);
        if (iOrderWizardDelegator != null)
        {
            iOrderWizardDelegator.finish(this);
        }
        return result;
    }

    @Override
    public boolean cancel(Map settings)
    {
        boolean result = super.cancel(settings);
        if (result && iOrderWizardDelegator != null)
        {
            iOrderWizardDelegator.finish(this);
        }
        return result;
    }
}


