package by.dak.cutting.zfacade;

import by.dak.cutting.afacade.AProfileColor;
import by.dak.persistence.convert.FurnitureCode2StringConverter;
import by.dak.persistence.entities.types.FurnitureCode;
import by.dak.persistence.entities.validator.FurnitureCodeValidator;
import by.dak.utils.convert.StringValue;
import org.hibernate.annotations.DiscriminatorOptions;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.HashMap;
import java.util.Map;

@Entity
@DiscriminatorValue(value = "ZProfileColor")
@DiscriminatorOptions(force = true)
@StringValue(converterClass = FurnitureCode2StringConverter.class)
@by.dak.utils.validator.Validator(validatorClass = FurnitureCodeValidator.class)
public class ZProfileColor extends AProfileColor
{

    public static final String PROPERTY_angleCode = "angleCode";
    public static final String PROPERTY_rubberCode = "rubberCode";

    @Transient
    private FurnitureCode angleCode;

    @Transient
    private FurnitureCode rubberCode;


    public FurnitureCode getAngleCode()
    {
        return angleCode;
    }

    public void setAngleCode(FurnitureCode angleCode)
    {
        this.angleCode = angleCode;
    }

    public FurnitureCode getRubberCode()
    {
        return rubberCode;
    }

    public void setRubberCode(FurnitureCode rubberCode)
    {
        this.rubberCode = rubberCode;
    }

    public Map<String, FurnitureCode> getTransients()
    {
        HashMap<String, FurnitureCode> transients = new HashMap<String, FurnitureCode>();
        transients.put(ZFurnitureType.rubber.name(), getRubberCode());
        transients.put(ZFurnitureType.angle.name(), getAngleCode());
        return transients;
    }

}
