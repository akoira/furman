package by.dak.cutting.afacade;

import by.dak.persistence.entities.types.FurnitureCode;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Collections;
import java.util.Map;

@MappedSuperclass
public abstract class AProfileColor extends FurnitureCode
{
    public static final String PROPERTY_offsetLength = "offsetLength";
    public static final String PROPERTY_offsetWidth = "offsetWidth";


    @Column(name = "OFFSET_LENGTH", nullable = false)
    private Double offsetLength = 0d;

    @Column(name = "OFFSET_WIDTH", nullable = false)
    private Double offsetWidth = 0d;

    public Double getOffsetLength()
    {
        return offsetLength;
    }

    public void setOffsetLength(Double offsetLength)
    {
        this.offsetLength = offsetLength;
    }

    public Double getOffsetWidth()
    {
        return offsetWidth;
    }

    public void setOffsetWidth(Double offsetWidth)
    {
        this.offsetWidth = offsetWidth;
    }

    public Map<String, FurnitureCode> getTransients()
    {
        return Collections.EMPTY_MAP;
    }

}
