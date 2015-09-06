package by.dak.plastic.strips;

import by.dak.persistence.cutting.entities.ABoardStripsEntity;
import org.hibernate.annotations.DiscriminatorOptions;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 27.09.11
 * Time: 9:30
 * To change this template use File | Settings | File Templates.
 */
@Entity
@NamedQueries(value =
        {
                @NamedQuery(
                        name = "deleteAllDSPStripsEntity",
                        query = "delete from DSPPlasticStripsEntity dspse where dspse.order = :order"
                )
        })
@DiscriminatorValue(value = "DSPPlasticDetail")
@DiscriminatorOptions(force = true)
public class DSPPlasticStripsEntity extends ABoardStripsEntity
{
}
