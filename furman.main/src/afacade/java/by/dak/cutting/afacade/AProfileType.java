package by.dak.cutting.afacade;

import by.dak.persistence.entities.types.FurnitureType;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.util.Map;

/**
 * User: akoyro
 * Date: 24.08.2010
 * Time: 16:04:41
 */
@MappedSuperclass
public abstract class AProfileType extends FurnitureType
{
    public static final String PREFIX_TYPE_PROPERTY = "Type";

    @Column(name = "OFFSET_LENGTH", nullable = false)
    private Double offsetLength = 0d;

    @Column(name = "OFFSET_WIDTH", nullable = false)
    private Double offsetWidth = 0d;

    @Transient
    private FurnitureType rubberType;


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

    public abstract Map<String, FurnitureType> getTransients();

    public FurnitureType getRubberType()
    {
        return rubberType;
    }

    public void setRubberType(FurnitureType rubberType)
    {
        this.rubberType = rubberType;
    }
}
