package by.dak.buffer.facade.impl;

import by.dak.buffer.entity.DilerOrder;
import by.dak.buffer.entity.DilerOrderItem;
import by.dak.buffer.entity.TempOrderItem;
import by.dak.buffer.facade.DilerOrderItemFacade;
import by.dak.cutting.facade.BaseFacadeImpl;
import by.dak.persistence.FacadeContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 05.11.2010
 * Time: 11:19:08
 * To change this template use File | Settings | File Templates.
 */
public class DilerOrderItemFacadeImpl extends BaseFacadeImpl<DilerOrderItem> implements DilerOrderItemFacade
{
    @Override
    public List<DilerOrderItem> getDilerOrderItemsFromTempTable()
    {
        List<TempOrderItem> tempOrderItems = FacadeContext.getTempOrderItemFacade().loadAll();
        return copyFrom(tempOrderItems);
    }

    private List<DilerOrderItem> copyFrom(List<TempOrderItem> tempOrderItems)
    {
        List<DilerOrderItem> dilerOrderItems = new ArrayList<DilerOrderItem>();

        for (TempOrderItem tempOrderItem : tempOrderItems)
        {
            DilerOrderItem dilerOrderItem = new DilerOrderItem();
            dilerOrderItem.setAmount(tempOrderItem.getAmount());
            dilerOrderItem.setDiscriminator(tempOrderItem.getDiscriminator());
            dilerOrderItem.setName(tempOrderItem.getName());
            dilerOrderItem.setType(tempOrderItem.getType());
            dilerOrderItem.setLength(tempOrderItem.getLength());
            dilerOrderItem.setWidth(tempOrderItem.getWidth());
            dilerOrderItem.setOrderItemId(tempOrderItem.getId());

            dilerOrderItems.add(dilerOrderItem);
        }

        return dilerOrderItems;
    }

    @Override
    public List<DilerOrderItem> findBy(DilerOrder dilerOrder)
    {
        return findAllByField(DilerOrderItem.PROPERTY_dilerOrder, dilerOrder);
    }
}
