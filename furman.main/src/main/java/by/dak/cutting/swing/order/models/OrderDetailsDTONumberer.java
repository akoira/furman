package by.dak.cutting.swing.order.models;

import by.dak.cutting.swing.order.data.OrderDetailsDTO;

/**
 * Created by IntelliJ IDEA.
 * User: akoyro
 * Date: Mar 26, 2009
 * Time: 12:42:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class OrderDetailsDTONumberer
{
    private long number = 0;

    public void number(OrderDetailsDTO orderDetails)
    {
        number++;
        orderDetails.setNumber(number);
        if (orderDetails.getBoardDef().getComposite())
        {
            number++;
            orderDetails.setSecondNumber(number);
        }
    }
}
