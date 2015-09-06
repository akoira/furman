package by.dak.additional.facade;

import by.dak.additional.Additional;
import by.dak.cutting.SearchFilter;
import by.dak.cutting.facade.impl.AOrderDetailFacadeImpl;
import by.dak.persistence.entities.OrderItem;

/**
 * User: akoyro
 * Date: 19.06.2010
 * Time: 19:38:06
 */
public class AdditionalFacadeImpl extends AOrderDetailFacadeImpl<Additional> implements AdditionalFacade
{
    public static SearchFilter getSearchFilterBy(OrderItem orderItem)
    {
        SearchFilter filter = SearchFilter.instanceUnbound();
        filter.eq("orderItem", orderItem);
        filter.addAscOrder("type");
        filter.addAscOrder("name");
        return filter;
    }
}
