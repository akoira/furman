package by.dak.persistence.entities;

import by.dak.cutting.afacade.ALink;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by IntelliJ IDEA.
 * User: user0
 * Date: 11.03.2010
 * Time: 10:54:09
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "FURN_LINK_FURN_LINK")
//@StringValue(converterClass = FurnitureLink2StringConverter.class)
//@Validator(validatorClass = FurnitureLinkValidator.class)

/**
 * Линк описывает связь между деталью заказа и ресурсом на складе
 */
public class FurnitureLink2FurnitureLink extends ALink<FurnitureLink, Furniture>
{
    public FurnitureLink2FurnitureLink()
    {
        setKeyword("FurnitureLink2FurnitureLink");
    }
}
