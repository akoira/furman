package by.dak.cutting.swing.dictionaries.delivery;

import by.dak.persistence.entities.AStoreElement;
import by.dak.persistence.entities.Delivery;
import by.dak.persistence.entities.predefined.StoreElementStatus;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: user0
 * Date: 02.02.2010
 * Time: 18:17:56
 * To change this template use File | Settings | File Templates.
 */
public class DeliveryModel
{
    private Delivery delivery;
    private boolean editable;
    private StoreElementStatus status;

    public List<? extends AStoreElement> getStoreElements()
    {
        return storeElements;
    }

    public void setStoreElements(List<? extends AStoreElement> storeElements)
    {
        this.storeElements = storeElements;
    }

    private List<? extends AStoreElement> storeElements;

    public Delivery getDelivery()
    {
        return delivery;
    }

    public void setDelivery(Delivery delivery)
    {
        this.delivery = delivery;
    }

    public boolean isEditable()
    {
        return editable;
    }

    public void setEditable(boolean editable)
    {
        this.editable = editable;
    }

    public StoreElementStatus getStatus()
    {
        return status;
    }

    public void setStatus(StoreElementStatus status)
    {
        this.status = status;
    }
}
