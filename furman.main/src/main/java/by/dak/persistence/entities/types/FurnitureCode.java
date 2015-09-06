package by.dak.persistence.entities.types;

import by.dak.cutting.afacade.FurnitureCodeLink;
import by.dak.persistence.convert.FurnitureCode2StringConverter;
import by.dak.persistence.entities.Priced;
import by.dak.persistence.entities.validator.FurnitureCodeValidator;
import by.dak.utils.convert.StringValue;
import by.dak.utils.validator.Validator;
import org.hibernate.annotations.DiscriminatorOptions;

import javax.persistence.*;
import java.util.List;

/**
 * Описывает уникальный код пренадлежности от определнного производителя
 */

@Entity
@StringValue(converterClass = FurnitureCode2StringConverter.class)
@Validator(validatorClass = FurnitureCodeValidator.class)
@DiscriminatorValue(value = "FurnitureCode")
@DiscriminatorOptions(force = true)
public class FurnitureCode extends Priced
{
    public static final String PROPERTY_childLinks = "childLinks";

    @OneToMany(mappedBy = "child", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private List<FurnitureCodeLink> childLinks;

    public List<FurnitureCodeLink> getChildLinks()
    {
        return childLinks;
    }

    public void setChildLinks(List<FurnitureCodeLink> childLinks)
    {
        this.childLinks = childLinks;
    }

}
