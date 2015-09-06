package by.dak.cutting.agt;

import by.dak.cutting.afacade.AProfileType;
import by.dak.cutting.agt.validate.AGTTypeValidator;
import by.dak.persistence.convert.FurnitureType2StringConverter;
import by.dak.persistence.entities.predefined.MaterialType;
import by.dak.persistence.entities.predefined.Unit;
import by.dak.persistence.entities.types.FurnitureType;
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
 * Time: 15:51:59
 */
@Entity
@DiscriminatorValue(value = "AGTType")
@DiscriminatorOptions(force = true)

@StringValue(converterClass = FurnitureType2StringConverter.class)
@by.dak.utils.validator.Validator(validatorClass = AGTTypeValidator.class)
public class AGTType extends AProfileType
{

    @Transient
    private FurnitureType dowelType;
    @Transient
    private FurnitureType glueType;

    public AGTType()
    {
        setType(MaterialType.agtprofile);
        setUnit(Unit.linearMetre);
    }

    @Override
    public Map<String, FurnitureType> getTransients()
    {
        HashMap<String, FurnitureType> map = new HashMap<String, FurnitureType>();
        map.put(AGTFurnitureType.agtrubber.name(), getRubberType());
        map.put(AGTFurnitureType.agtdowel.name(), getDowelType());
        map.put(AGTFurnitureType.agtglue.name(), getGlueType());
        return map;
    }

    public FurnitureType getDowelType()
    {
        return dowelType;
    }

    public void setDowelType(FurnitureType dowelType)
    {
        this.dowelType = dowelType;
    }

    public FurnitureType getGlueType()
    {
        return glueType;
    }

    public void setGlueType(FurnitureType glueType)
    {
        this.glueType = glueType;
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

}
