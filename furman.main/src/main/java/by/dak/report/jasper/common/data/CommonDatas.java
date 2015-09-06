package by.dak.report.jasper.common.data;

import by.dak.persistence.entities.AOrder;
import by.dak.persistence.entities.Order;

import java.util.ArrayList;
import java.util.Collection;

/**
 * User: akoyro
 * Date: 05.11.2009
 * Time: 9:33:42
 */
public class CommonDatas<E extends CommonData> extends ArrayList<E>
{

    private CommonDataType commonDataType;

    private Double commonCost;

    private Double dialerCommonCost;

    private AOrder order;

    public CommonDatas(CommonDataType commonDataType, AOrder order)
    {
        this.commonDataType = commonDataType;
        this.order = order;
    }

    @Override
    public boolean add(E e)
    {
        commonCost = null;
        e.setCommonDataType(getCommonDataType());
        e.setOrder(order);
        return super.add(e);
    }

    @Override
    public boolean addAll(Collection<? extends E> c)
    {
        commonCost = null;
        return super.addAll(c);
    }

    public Double getCommonCost()
    {
        if (commonCost == null)
        {
            commonCost = 0d;
            for (E e : this)
            {
                commonCost += e.getCost();
            }
        }
        return commonCost;
    }

    public Double getDialerCommonCost()
    {
        if (dialerCommonCost == null)
        {
            dialerCommonCost = 0d;
            for (E e : this)
            {
                dialerCommonCost += e.getDialerCost();
            }
        }
        return dialerCommonCost;
    }


    public CommonDataType getCommonDataType()
    {
        return commonDataType;
    }

    public void setCommonDataType(CommonDataType commonDataType)
    {
        this.commonDataType = commonDataType;
    }

    public AOrder getOrder()
    {
        return order;
    }

    public void setOrder(Order order)
    {
        this.order = order;
    }
}
