package by.dak.cutting.facade.impl;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.facade.BaseFacadeImpl;
import by.dak.cutting.facade.DeliveryFacade;
import by.dak.persistence.entities.Delivery;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: user0
 * Date: 01.02.2010
 * Time: 15:51:27
 * To change this template use File | Settings | File Templates.
 */
public class DeliveryFacadeImpl extends BaseFacadeImpl<Delivery> implements DeliveryFacade
{
    @Override
    public List<Delivery> loadAll(SearchFilter filter)
    {
        filter.addDescOrder("deliveryDate");
        return super.loadAll(filter);
    }
}
