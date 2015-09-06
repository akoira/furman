package by.dak.ordergroup.facade;

import by.dak.cutting.facade.BaseFacadeImpl;
import by.dak.ordergroup.OrderGroup;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Dailysheet;
import by.dak.utils.convert.StringValueAnnotationProcessor;

/**
 * Created by IntelliJ IDEA.
 * User: user0
 * Date: 30.03.2010
 * Time: 17:21:25
 * To change this template use File | Settings | File Templates.
 */
public class OrderGroupFacadeImpl extends BaseFacadeImpl<OrderGroup> implements OrderGroupFacade
{
    @Override
    public OrderGroup createOrderGroup()
    {
        OrderGroup orderGroup = new OrderGroup();
        Dailysheet dailysheet = FacadeContext.getDailysheetFacade().loadCurrentDailysheet();

        orderGroup.setDailysheet(dailysheet);
        orderGroup.setName(StringValueAnnotationProcessor.getProcessor().convert(dailysheet));
        save(orderGroup);

        return orderGroup;
    }
}
