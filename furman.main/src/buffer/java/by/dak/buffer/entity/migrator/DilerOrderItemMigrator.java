package by.dak.buffer.entity.migrator;

import by.dak.buffer.entity.DilerOrderItem;
import by.dak.persistence.entities.OrderItem;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 09.11.2010
 * Time: 11:54:21
 * To change this template use File | Settings | File Templates.
 */

/**
 * суть мигратора в том, что при предаче опрделённого DilerOrderItem'a он переводит
 * и возвращает правильный объект, c сохранёнными данными
 */
public abstract class DilerOrderItemMigrator<I extends OrderItem>
{
    public abstract I migrate(DilerOrderItem dilerOrderItem);
}
