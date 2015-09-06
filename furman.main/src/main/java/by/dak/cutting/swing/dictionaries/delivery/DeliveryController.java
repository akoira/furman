package by.dak.cutting.swing.dictionaries.delivery;


import by.dak.cutting.facade.AStoreElementFacade;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.ReservedSaver;
import by.dak.persistence.entities.AStoreElement;
import by.dak.persistence.entities.Furniture;
import by.dak.persistence.entities.PersistenceEntity;
import by.dak.persistence.entities.predefined.MaterialType;
import by.dak.persistence.entities.predefined.StoreElementStatus;
import by.dak.swing.wizard.DWizardController;
import by.dak.swing.wizard.WizardStep;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: user0
 * Date: 01.02.2010
 * Time: 16:19:06
 * To change this template use File | Settings | File Templates.
 */
public class DeliveryController extends DWizardController<DeliveryWizardPanelProvider, DeliveryModel, DeliveryController.Step>
{
    private ReservedSaver reservedSaver = new ReservedSaver();

    public DeliveryController()
    {
        setProvider(DeliveryWizardPanelProvider.createInstance());
        getProvider().addWizardFinishedListener(new PropertyChangeListener()
        {
            @Override
            public void propertyChange(PropertyChangeEvent evt)
            {
                if (reservedSaver.size() > 1 || reservedSaver.get(0).hasId())
                {
                    ArrayList<PersistenceEntity> list = new ArrayList<PersistenceEntity>(reservedSaver);
                    PersistenceEntity entity = list.get(0);
                    FacadeContext.getFacadeBy(entity.getClass()).save(entity);

                    for (int i = 1; i < list.size(); i++)
                    {
                        AStoreElement element = (AStoreElement) list.get(i);
                        if ((element instanceof Furniture) ? ((Furniture) element).getSize() > 0 : element.getAmount() > 0 &&
                                element.getOrder() == null &&
                                element.getStatus() == StoreElementStatus.exist)
                        {
                            AStoreElementFacade facade = (AStoreElementFacade) FacadeContext.getFacadeBy(element.getClass());
                            AStoreElement newOrdered = facade.cancelOrdered(element);
                            if (element.getAmount() < 1)
                            {
                                reservedSaver.remove(element);
                                if (element.hasId())
                                {
                                    facade.delete(element.getId());
                                }
                            }
                            if (newOrdered != null)
                            {
                                reservedSaver.add(i, newOrdered);
                            }
                        }
                    }
                    reservedSaver.save();
                }
            }
        });
    }

    @Override
    protected void adjustCurrentStep(Step currentStep)
    {
        switch (currentStep)
        {
            case delivery:
                getProvider().getDeliveryPanel().setValue(getModel().getDelivery());
                getProvider().getDeliveryPanel().setEditable(getModel().isEditable());
                getProvider().getDeliveryTypePanel().setEditable(getModel().getDelivery().getMaterialType() == null);
                if (getModel().getDelivery().getMaterialType() == null)
                {
                    getModel().getDelivery().setMaterialType(MaterialType.board);
                }
                break;
            case type:
                getProvider().getDeliveryTypePanel().setMaterialType(getModel().getDelivery().getMaterialType());
                break;
            case order:
                DeliveryListUpdaters.ADeliveryListUpdater listUpdater = null;
                switch (getModel().getDelivery().getMaterialType())
                {
                    case board:
                        listUpdater = DeliveryListUpdaters.createBoardListUpdater(getModel().getDelivery(), reservedSaver);
                        break;
                    case border:
                        listUpdater = DeliveryListUpdaters.createBorderListUpdater(getModel().getDelivery(), reservedSaver);
                        break;
                    case furniture:
                        listUpdater = DeliveryListUpdaters.createFurnitureListUpdater(getModel().getDelivery(), reservedSaver);
                        break;
                    default:
                        throw new IllegalArgumentException();
                }
                listUpdater.loadList();
                getProvider().getOrderPanel().getTableCtrl().setListUpdater(listUpdater);
                getProvider().getOrderPanel().init();
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    protected void adjustLastStep(Step currentStep)
    {
        if (getLastSelectedStep() != null)
        {
            switch (getLastSelectedStep())
            {
                case delivery:
                    reservedSaver.add(getModel().getDelivery());
                    break;
                case type:
                    getModel().getDelivery().setMaterialType(getProvider().getDeliveryTypePanel().getMaterialType());
                    break;
                case order:
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        }
    }

    @Override
    protected String getIdBy(Step step)
    {
        return step.name();
    }

    @Override
    protected Step getStepBy(String id)
    {
        return Step.valueOf(id);
    }


    public static enum Step implements WizardStep
    {
        delivery,
        type,
        order
    }
}
