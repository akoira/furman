package by.dak.persistence.entities;

import by.dak.persistence.convert.Border2StringConverter;
import by.dak.persistence.entities.validator.BorderValidator;
import by.dak.utils.convert.StringValue;
import by.dak.utils.validator.Validator;
import org.hibernate.annotations.DiscriminatorOptions;

import javax.persistence.*;

/**
 * User: akoyro
 * Date: 18.11.2009
 * Time: 21:13:27
 */
@Entity

@DiscriminatorValue(value = "Border")
@DiscriminatorOptions(force = true)

@Validator(validatorClass = BorderValidator.class)
@StringValue(converterClass = Border2StringConverter.class)

@NamedQueries(value =
        {
                @NamedQuery(name = "deleteBorderByTemplate",
                        query = "delete from Border b where " +
                                "b.order is null and " +
                                "(b.status = :status0 or " +
                                "b.status = :status1) and " +
                                "b.priceAware = :borderDef and " +
                                "b.priced = :texture and " +
                                "b.provider = :provider and " +
                                "b.length = :length"
                )
//        @NamedQuery(name = "countGroupByTemplate",
//                query = "select count(*) from ( " +
//                        "select b.borderDef,b.texture,b.length,b.status,b.order, count(b.id) from Border b " +
//                        "group by b.borderDef,b.texture,b.length,b.status,b.order)"
//        )
//                @NamedQuery(name = "unlinkBoardsByOrder",
//                        query = "update Board b set b.order = :value where b.order = :order")
        }
)


public class Border extends AStoreElement<BorderDefEntity, TextureEntity>
{

    public static final String PROPERTY_borderDef = "borderDef";
    public static final String PROPERTY_texture = "texture";

    @Column(name = "LENGTH", nullable = false)
    private Double length = 100d;


    public Double getLength()
    {
        return length;
    }

    public void setLength(Double length)
    {
        this.length = length;
    }

    public BorderDefEntity getBorderDef()
    {
        return getPriceAware();
    }

    public void setBorderDef(BorderDefEntity borderDef)
    {
        setPriceAware(borderDef);
    }

    public TextureEntity getTexture()
    {
        return getPriced();
    }

    public void setTexture(TextureEntity texture)
    {
        setPriced(texture);
    }

    public Border clone()
    {
        Border border = new Border();
        cloneFilling(border);

        border.setBorderDef(getBorderDef());
        border.setTexture(getTexture());
        border.setLength(getLength());
        return border;
    }
}
