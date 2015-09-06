package by.dak.cutting.zfacade;

import by.dak.cutting.afacade.AProfileType;
import by.dak.cutting.zfacade.validator.ZProfileTypeValidator;
import by.dak.persistence.convert.FurnitureType2StringConverter;
import by.dak.persistence.entities.predefined.MaterialType;
import by.dak.persistence.entities.predefined.Unit;
import by.dak.persistence.entities.types.FurnitureType;
import by.dak.utils.convert.StringValue;
import by.dak.utils.validator.Validator;
import org.hibernate.annotations.DiscriminatorOptions;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.HashMap;
import java.util.Map;


/**
 * User: akoyro
 * Date: 07.07.2010
 * Time: 9:13:10
 */
@Entity
@DiscriminatorValue(value = "ZProfileType")
@DiscriminatorOptions(force = true)

@StringValue(converterClass = FurnitureType2StringConverter.class)
@Validator(validatorClass = ZProfileTypeValidator.class)
public class ZProfileType extends AProfileType
{
    public ZProfileType()
    {
        setType(MaterialType.zprofile);
        setUnit(Unit.linearMetre);
    }

    @Transient
    private FurnitureType angleType;

    @Transient
    private FurnitureType buttType;


//    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
//    private List<FurnitureTypeLink> links;

//    public List<FurnitureTypeLink> getLinks()
//    {
//        return links;
//    }
//
//    public void setLinks(List<FurnitureTypeLink> links)
//    {
//        this.links = links;
//    }

    public FurnitureType getAngleType()
    {
        return angleType;
    }

    public void setAngleType(FurnitureType angleType)
    {
        this.angleType = angleType;
    }

    @Override
    public Map<String, FurnitureType> getTransients()
    {
        HashMap<String, FurnitureType> transients = new HashMap<String, FurnitureType>();
        transients.put(ZFurnitureType.rubber.name(), getRubberType());
        transients.put(ZFurnitureType.angle.name(), getAngleType());
        transients.put(ZFurnitureType.butt.name(), getButtType());

        return transients;
    }

    public FurnitureType getButtType()
    {
        return buttType;
    }

    public void setButtType(FurnitureType buttType)
    {
        this.buttType = buttType;
    }
}
