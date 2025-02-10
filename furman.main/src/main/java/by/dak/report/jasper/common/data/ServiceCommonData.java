package by.dak.report.jasper.common.data;

import by.dak.persistence.entities.ServiceLink;
import by.dak.persistence.entities.predefined.Unit;
import by.dak.utils.MathUtils;
import by.dak.utils.convert.StringValueAnnotationProcessor;
import org.apache.commons.lang.StringUtils;

import java.text.DecimalFormat;

public class ServiceCommonData extends CommonData
{
    private String size = "";

    private double sizeAsDouble = 0d;

    private String unit = Unit.piece.name();

    public String getSize()
    {
        if (StringUtils.isEmpty(size))
        {
            return new DecimalFormat("0.00").format(getSizeAsDouble());
        }
        return size;
    }

    public void setSize(String value)
    {
        this.size = value;
    }

    public double getSizeAsDouble()
    {
        return MathUtils.round(sizeAsDouble, 2);
    }

    public void setSizeAsDouble(double size)
    {
        this.sizeAsDouble = size;
    }


    public String getUnit()
    {
        return unit;
    }

    public void setUnit(String unit)
    {
        this.unit = unit;
    }

    @Override
    public Double getDialerCost()
    {
        if (dialerCost == null)
        {
            dialerCost = MathUtils.round(getCount() * getDialerPrice() * getSizeAsDouble(), 2);
        }
        return dialerCost;
    }

    public Double getCost()
    {
        if (cost == null)
        {
            cost = MathUtils.round(getCount() * getPrice() * getSizeAsDouble(), 2);
        }
        return cost;
    }

    @Override
    public boolean equals(Object obj)
    {
        assert obj != null && obj instanceof ServiceCommonData;
        ServiceCommonData data = (ServiceCommonData) obj;
        return getName().equals(data.getName()) && getService().equals(data.getService()) && getSize() == data.getSize();
    }

    public CommonData cloneForDialer()
    {
        ServiceCommonData commonData = new ServiceCommonData();

        fillCloneForDialer(commonData);

        commonData.size = size;

        commonData.sizeAsDouble = sizeAsDouble;

        commonData.unit = unit;
        return commonData;
    }

    public static ServiceCommonData valueOf(ServiceLink link)
    {
        ServiceCommonData commonData = new ServiceCommonData();
        commonData.setService(StringValueAnnotationProcessor.getProcessor().convert(link.getPriceAware()));
        commonData.setName(StringValueAnnotationProcessor.getProcessor().convert(link.getService()));
        return commonData;
    }


}
