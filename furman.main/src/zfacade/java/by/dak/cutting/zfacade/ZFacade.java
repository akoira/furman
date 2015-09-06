package by.dak.cutting.zfacade;

import by.dak.cutting.afacade.AFacade;
import by.dak.cutting.zfacade.validator.ZFacadeValidator;
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
 * Date: 05.07.2010
 * Time: 15:31:12
 */
@Entity
@DiscriminatorValue(value = "ZFacade")
@DiscriminatorOptions(force = true)
@Validator(validatorClass = ZFacadeValidator.class)
public class ZFacade extends AFacade<ZProfileType, ZProfileColor>
{
    public static final String PROPERTY_angle = "angle";
    public static final String PROPERTY_butt = "butt";


    //уголки
    @Transient
    private FurnitureLink angle = new FurnitureLink(ZFurnitureType.angle.name());

    //петля
    @Transient
    private ZButtLink butt = new ZButtLink();

    public ZFacade()
    {
        setType(OrderItemType.zfacade);
        getFilling().setName(ZFurnitureType.filling.name());
        getRubber().setName(ZFurnitureType.rubber.name());
    }

    public FurnitureLink getAngle()
    {
        return angle;
    }

    public void setAngle(FurnitureLink angle)
    {
        this.angle = angle;
    }

    public ZButtLink getButt()
    {
        return butt;
    }

    public void setButt(ZButtLink butt)
    {
        this.butt = butt;
    }

    @Override
    public List<AOrderDetail> getTransients()
    {
        List<AOrderDetail> links = super.getTransients();
        links.add(getAngle());
        links.add(getButt());
        return links;
    }
}
