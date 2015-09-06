package by.dak.cutting.zfacade;

import by.dak.cutting.zfacade.converter.ZButtLink2StringConverter;
import by.dak.cutting.zfacade.validator.ZButtLinkValidator;
import by.dak.persistence.entities.FurnitureLink;
import by.dak.persistence.entities.predefined.Side;
import by.dak.utils.convert.StringValue;
import by.dak.utils.validator.Validator;
import org.hibernate.annotations.DiscriminatorOptions;

import javax.persistence.*;

/**
 * User: akoyro
 * Date: 31.07.2010
 * Time: 22:30:41
 */

/**
 * Деталь петля в заказе для ZFacade
 */
@Entity

@DiscriminatorValue(value = "ZButtLink")
@DiscriminatorOptions(force = true)

@StringValue(converterClass = ZButtLink2StringConverter.class)
@Validator(validatorClass = ZButtLinkValidator.class)

public class ZButtLink extends FurnitureLink
{
    public static final String NAME_butt = "butt";

    public static final String PROPERTY_side = "side";

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Side side = Side.up;

    public ZButtLink()
    {
        setName(NAME_butt);
        setSize(2d);
    }


    public Side getSide()
    {
        return side;
    }

    public void setSide(Side side)
    {
        this.side = side;
    }
}
