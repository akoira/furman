package by.dak.template.swing;

import by.dak.category.Category;
import by.dak.cutting.DialogShowers;
import by.dak.cutting.swing.MessageDialog;
import by.dak.cutting.swing.store.helpers.AEntityNEDActions;
import by.dak.order.swing.AOrderWizard;
import by.dak.order.swing.IOrderWizardDelegator;
import by.dak.persistence.FacadeContext;
import by.dak.template.TemplateOrder;

/**
 * User: akoyro
 * Date: 13.04.11
 * Time: 12:19
 */
public class TemplateOrderActions extends AEntityNEDActions<TemplateOrder>
{
    private Category category;

    public TemplateOrderActions(Category category)
    {
        this.category = category;
    }

    @Override
    public void newValue()
    {
        final TemplateOrder order = FacadeContext.getTemplateOrderFacade().initNewOrderEntity("");
        order.setCategory(getCategory());
        final TemplateOrderWizard orderWizard = new TemplateOrderWizard(order.getName());
        orderWizard.setOrder(order);
        orderWizard.setiOrderWizardDelegator(new IOrderWizardDelegator()
        {
            @Override
            public void finish(AOrderWizard wizard)
            {
                setSelectedElement(order);
                firePropertyChange(AEntityNEDActions.PROPERTY_updateGui, null, getSelectedElement());
            }

            @Override
            public void cancel(AOrderWizard wizard)
            {
                if (order.hasId())
                {
                    setSelectedElement(order);
                    firePropertyChange(AEntityNEDActions.PROPERTY_updateGui, null, getSelectedElement());
                }
            }
        });
        DialogShowers.showWizard(orderWizard, orderWizard.getWizardObserver());

    }

    @Override
    public void openValue()
    {
        if (getSelectedElement() != null)
        {
            TemplateOrder order = FacadeContext.getTemplateOrderFacade().findBy(getSelectedElement().getId());
            final TemplateOrderWizard orderWizard = new TemplateOrderWizard(order.getName());
            orderWizard.setOrder(order);
            DialogShowers.showWizard(orderWizard, orderWizard.getWizardObserver());

            orderWizard.setiOrderWizardDelegator(new IOrderWizardDelegator()
            {
                @Override
                public void finish(AOrderWizard wizard)
                {
                    firePropertyChange(AEntityNEDActions.PROPERTY_updateGui, null, getSelectedElement());
                }

                @Override
                public void cancel(AOrderWizard wizard)
                {
                    firePropertyChange(AEntityNEDActions.PROPERTY_updateGui, null, getSelectedElement());
                }
            });

        }
        else
        {
            MessageDialog.showSimpleMessage(MessageDialog.NO_ROW_SELECTED);
        }
    }

    public Category getCategory()
    {
        return category;
    }
}