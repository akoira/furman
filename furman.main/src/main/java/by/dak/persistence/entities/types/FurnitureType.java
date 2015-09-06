package by.dak.persistence.entities.types;

import by.dak.cutting.afacade.FurnitureTypeLink;
import by.dak.persistence.convert.FurnitureType2StringConverter;
import by.dak.persistence.entities.PriceAware;
import by.dak.persistence.entities.predefined.MaterialType;
import by.dak.persistence.entities.predefined.Unit;
import by.dak.persistence.entities.validator.FurnitureTypeValidator;
import by.dak.utils.convert.StringValue;
import by.dak.utils.validator.Validator;
import org.hibernate.annotations.DiscriminatorOptions;

import javax.persistence.*;
import java.util.List;

/**
 * Описывает типы существующих пренадлежностей (ручка, шуруп, и т.д)
 */

@Entity
@DiscriminatorValue(value = "FurnitureType")
@DiscriminatorOptions(force = true)

@NamedQueries(value =
        {
                @NamedQuery(name = "childTypesByKeyword", query = "select distinct ftl.child from FurnitureTypeLink ftl where ftl.keyword = :keyword")
        })


@StringValue(converterClass = FurnitureType2StringConverter.class)
@Validator(validatorClass = FurnitureTypeValidator.class)
public class FurnitureType extends PriceAware
{
    public static final String PROPERTY_childLinks = "childLinks";

    @OneToMany(mappedBy = "child", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private List<FurnitureTypeLink> childLinks;

    public FurnitureType()
    {
        setType(MaterialType.furniture);
        setUnit(Unit.piece);
    }

    public List<FurnitureTypeLink> getChildLinks()
    {
        return childLinks;
    }

    public void setChildLinks(List<FurnitureTypeLink> childLinks)
    {
        this.childLinks = childLinks;
    }
}
