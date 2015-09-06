package by.dak.cutting.linear;

import by.dak.cutting.cut.base.Dimension;
import by.dak.persistence.entities.Furniture;
import by.dak.persistence.entities.Order;

/**
 * Created by IntelliJ IDEA.
 * User: dkoyro
 * Date: 02.03.11
 * Time: 19:14
 * To change this template use File | Settings | File Templates.
 */

/**
 * хранит остатки фурнитуры
 */
public class RestFurnitureDimension extends Dimension
{
    private Furniture furniture;
    private Order order; //хранит заказ, создавший этот остаток

    public RestFurnitureDimension(int freeLength, int height, Furniture furniture)
    {
        super(freeLength, height);
        this.furniture = furniture;
    }

    public Order getOrder()
    {
        return order;
    }

    public void setOrder(Order order)
    {
        this.order = order;
    }

    public Furniture getFurniture()
    {
        return furniture;
    }
}
