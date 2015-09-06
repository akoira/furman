package by.dak.buffer.entity.migrator.item;

import by.dak.buffer.entity.DilerOrderItem;
import by.dak.buffer.entity.migrator.DilerOrderItemMigrator;
import by.dak.persistence.entities.OrderItem;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 09.11.2010
 * Time: 16:09:46
 * To change this template use File | Settings | File Templates.
 */
public class OrderItemMigrator extends DilerOrderItemMigrator<OrderItem>
{
    @Override
    public OrderItem migrate(DilerOrderItem dilerOrderItem)
    {
        OrderItem orderItem = new OrderItem();
        orderItem.setName(dilerOrderItem.getName());
        orderItem.setAmount(dilerOrderItem.getAmount());
        orderItem.setType(dilerOrderItem.getType());
        orderItem.setNumber(dilerOrderItem.getNumber());
        orderItem.setDiscriminator(dilerOrderItem.getDiscriminator());

        return orderItem;
    }

}
