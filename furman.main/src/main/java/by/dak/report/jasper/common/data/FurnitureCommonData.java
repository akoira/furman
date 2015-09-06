package by.dak.report.jasper.common.data;

import by.dak.persistence.entities.FurnitureLink;
import by.dak.persistence.entities.predefined.Unit;
import by.dak.utils.MathUtils;
import by.dak.utils.convert.StringValueAnnotationProcessor;
import org.apache.commons.lang.StringUtils;

import java.text.DecimalFormat;

/**
 * User: akoyro
 * Date: 14.08.2010
 * Time: 21:38:53
 */

//@Entity
//@DiscriminatorValue(value = "FurnitureCommonData")
//@DiscriminatorOptions(force = true)
public class FurnitureCommonData extends CommonData
{
    //    @Column(nullable = false)
    private String size = "";

    //    @Column(nullable = false)
    private double sizeAsDouble = 0d;

    //    @Column(nullable = false)
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
        assert obj != null && obj instanceof FurnitureCommonData;
        FurnitureCommonData data = (FurnitureCommonData) obj;
        return getName().equals(data.getName()) && getService().equals(data.getService()) && getSize() == data.getSize();
    }

    public CommonData cloneForDialer()
    {
        FurnitureCommonData commonData = new FurnitureCommonData();

        fillCloneForDialer(commonData);

        commonData.size = size;

        commonData.sizeAsDouble = sizeAsDouble;

        commonData.unit = unit;
        return commonData;
    }

    public static FurnitureCommonData valueOf(FurnitureLink link)
    {
        FurnitureCommonData commonData = new FurnitureCommonData();
        commonData.setService(StringValueAnnotationProcessor.getProcessor().convert(link.getFurnitureType()));
        commonData.setName(StringValueAnnotationProcessor.getProcessor().convert(link.getFurnitureCode()));
        return commonData;
    }


}
