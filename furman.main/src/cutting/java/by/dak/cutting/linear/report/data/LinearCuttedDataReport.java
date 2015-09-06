package by.dak.cutting.linear.report.data;

import by.dak.ordergroup.OrderGroup;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: dkoyro
 * Date: 29.04.11
 * Time: 20:57
 * To change this template use File | Settings | File Templates.
 */
public class LinearCuttedDataReport
{
    private OrderGroup orderGroup;
    private List<LinearMaterialCuttedData> materialCuttedData;

    public OrderGroup getOrderGroup()
    {
        return orderGroup;
    }

    public void setOrderGroup(OrderGroup orderGroup)
    {
        this.orderGroup = orderGroup;
    }

    public List<LinearMaterialCuttedData> getMaterialCuttedData()
    {
        return materialCuttedData;
    }

    public void setMaterialCuttedData(List<LinearMaterialCuttedData> materialCuttedData)
    {
        this.materialCuttedData = materialCuttedData;
    }
}
