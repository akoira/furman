import by.dak.cutting.SpringConfiguration;
import by.dak.cutting.facade.impl.helper.BoardOrderStatusRefactorer;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Order;
import by.dak.persistence.entities.OrderStatus;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 24.02.2010
 * Time: 22:37:03
 * To change this template use File | Settings | File Templates.
 */
public class TAdjustOrder
{
    public static void main(String[] args)
    {
        SpringConfiguration springConfiguration = new SpringConfiguration();

        List<Order> entities = FacadeContext.getOrderFacade().findAllByField("orderStatus", OrderStatus.production);
        for (Order order : entities)
        {
            new BoardOrderStatusRefactorer(order, FacadeContext.getBoardFacade()).refactor();
        }
    }


}
