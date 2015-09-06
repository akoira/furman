package by.dak.buffer.facade.impl;

import by.dak.buffer.entity.DilerOrder;
import by.dak.buffer.entity.TempOrder;
import by.dak.buffer.facade.DilerOrderFacade;
import by.dak.cutting.facade.BaseFacadeImpl;
import by.dak.persistence.FacadeContext;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 05.11.2010
 * Time: 11:07:55
 * To change this template use File | Settings | File Templates.
 */
public class DilerOrderFacadeImpl extends BaseFacadeImpl<DilerOrder> implements DilerOrderFacade
{
    @Override
    public DilerOrder getDilerOrderFromTempTable()
    {
        DilerOrder dilerOrder = null;
        List<TempOrder> tempOrders = FacadeContext.getTempOrderFacade().loadAll();
        if (tempOrders.size() > 0)
        {
            TempOrder tempOrder = tempOrders.get(0);
            dilerOrder = copyFrom(tempOrder);
        }

        return dilerOrder;
    }

    private DilerOrder copyFrom(TempOrder tempOrder)
    {
        DilerOrder dilerOrder = new DilerOrder();
        dilerOrder.setStatus(tempOrder.getStatus());
        dilerOrder.setCost(tempOrder.getCost());
        dilerOrder.setName(tempOrder.getName());
        dilerOrder.setDescription(tempOrder.getDescription());
        dilerOrder.setReadyDate(tempOrder.getReadyDate());

        return dilerOrder;
    }
}
