package by.dak.cutting.afacade;

import by.dak.persistence.entities.PriceAware;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * Линк на состовные элементы для типа
 * например
 * User: akoyro
 * Date: 05.08.2010
 * Time: 15:06:42
 */

@Entity
@Table(name = "FURNITURE_TYPE_LINK")

@NamedQueries(value =
        {
                @NamedQuery(
                        name = "deleteTypeLinksByParent",
                        query = "delete from FurnitureTypeLink where parent =:parent")
        }
)


public class FurnitureTypeLink extends ALink<PriceAware, PriceAware>
{
}
