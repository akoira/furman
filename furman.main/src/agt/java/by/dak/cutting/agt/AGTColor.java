package by.dak.cutting.agt;

import by.dak.cutting.afacade.AProfileColor;
import by.dak.cutting.agt.converter.AGTColor2StringConverter;
import by.dak.cutting.agt.validate.AGTColorValidator;
import by.dak.persistence.entities.types.FurnitureCode;
import by.dak.utils.convert.StringValue;
import org.hibernate.annotations.DiscriminatorOptions;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.HashMap;
import java.util.Map;

/**
 * User: akoyro
 * Date: 24.08.2010
 * Time: 15:52:19
 */
@Entity
@DiscriminatorValue(value = "AGTColor")
@DiscriminatorOptions(force = true)
@StringValue(converterClass = AGTColor2StringConverter.class)
@by.dak.utils.validator.Validator(validatorClass = AGTColorValidator.class)
public class AGTColor extends AProfileColor
{
    public static final String PROPERTY_rubberCode = "rubberCode";
    public static final String PROPERTY_glueCode = "glueCode";
    public static final String PROPERTY_dowelCode = "dowelCode";


    @Transient
    private FurnitureCode rubberCode;

    @Transient
    private FurnitureCode glueCode;

    @Transient
    private FurnitureCode dowelCode;


    public FurnitureCode getDowelCode()
    {
        return dowelCode;
    }

    public void setDowelCode(FurnitureCode dowelCode)
    {
        this.dowelCode = dowelCode;
    }


    public void setWidth(Double width)
    {
        setOffsetWidth(width);
    }

    public Double getWidth()
    {
        return getOffsetWidth();
    }

    public void setWidthGroove(Double widthGroove)
    {
        setOffsetLength(widthGroove);
    }

    public Double getWidthGroove()
    {
        return getOffsetLength();
    }

    public FurnitureCode getRubberCode()
    {
        return rubberCode;
    }

    public void setRubberCode(FurnitureCode rubberCode)
    {
        this.rubberCode = rubberCode;
    }

    public FurnitureCode getGlueCode()
    {
        return glueCode;
    }

    public void setGlueCode(FurnitureCode glueCode)
    {
        this.glueCode = glueCode;
    }


    public Map<String, FurnitureCode> getTransients()
    {
        HashMap<String, FurnitureCode> transients = new HashMap<String, FurnitureCode>();
        transients.put(AGTFurnitureType.agtrubber.name(), getRubberCode());
        transients.put(AGTFurnitureType.agtglue.name(), getGlueCode());
        transients.put(AGTFurnitureType.agtdowel.name(), getDowelCode());
        return transients;
    }


}
