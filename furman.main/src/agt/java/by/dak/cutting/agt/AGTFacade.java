package by.dak.cutting.agt;

import by.dak.cutting.afacade.AFacade;
import by.dak.cutting.agt.validate.AGTFacadeValidator;
import by.dak.persistence.entities.AOrderDetail;
import by.dak.persistence.entities.FurnitureLink;
import by.dak.persistence.entities.predefined.OrderItemType;
import by.dak.utils.validator.Validator;
import org.hibernate.annotations.DiscriminatorOptions;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.List;

/**
 * User: akoyro
 * Date: 23.08.2010
 * Time: 23:18:32
 */
@Entity
@DiscriminatorValue(value = "AGTFacade")
@DiscriminatorOptions(force = true)
@Validator(validatorClass = AGTFacadeValidator.class)
public class AGTFacade extends AFacade<AGTType, AGTColor>
{
    public static final String PROPERTY_dowel = "dowel";
    public static final String PROPERTY_glue = "glue";

    @Transient
    private FurnitureLink dowel = new FurnitureLink(AGTFurnitureType.agtdowel.name());

    @Transient
    private FurnitureLink glue = new FurnitureLink(AGTFurnitureType.agtglue.name());

    public AGTFacade()
    {
        setType(OrderItemType.agtfacade);
        getFilling().setName(AGTFurnitureType.agtfilling.name());
        getRubber().setName(AGTFurnitureType.agtrubber.name());
    }

    public FurnitureLink getDowel()
    {
        return dowel;
    }

    public void setDowel(FurnitureLink dowel)
    {
        this.dowel = dowel;
    }

    public FurnitureLink getGlue()
    {
        return glue;
    }

    public void setGlue(FurnitureLink glue)
    {
        this.glue = glue;
    }

    @Override
    public List<AOrderDetail> getTransients()
    {
        List list = super.getTransients();
        list.add(getDowel());
        list.add(getGlue());
        return list;
    }
}
