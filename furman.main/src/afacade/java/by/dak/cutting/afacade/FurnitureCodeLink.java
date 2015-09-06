package by.dak.cutting.afacade;

import by.dak.persistence.entities.Priced;

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
@Table(name = "FURNITURE_CODE_LINK")

@NamedQueries(value =
        {
                @NamedQuery(
                        name = "deleteCodeLinksByParent",
                        query = "delete from FurnitureCodeLink where parent =:parent")
        }
)
public class FurnitureCodeLink extends ALink<Priced, Priced>
{
}
