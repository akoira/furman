package by.dak.plastic;

import by.dak.persistence.convert.OrderFurniture2StringConverter;
import by.dak.persistence.entities.AOrderBoardDetail;
import by.dak.persistence.entities.validator.OrderFurnitureValidator;
import by.dak.utils.convert.StringValue;
import org.hibernate.annotations.DiscriminatorOptions;
import org.hibernate.annotations.Proxy;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * The class represets DSP for palstic details
 * User: akoyro
 * Date: 25.09.11
 * Time: 12:30
 */
@Entity
@Proxy(lazy = false)
@DiscriminatorValue(value = "DSPPlasticDetail")
@DiscriminatorOptions(force = true)
@NamedQueries(value =
        {
                @NamedQuery(name = "uniqueDSPPlasticPairsByOrder",
                        query = "select distinct pd.priced as texture, pd.priceAware as furnitureDef  from DSPPlasticDetail  pd where pd.orderItem.order = :order"),
                @NamedQuery(name = "sumDSPPlasticDetails",
                        query = "select sum(dsp.amount * dsp.orderItem.amount) from DSPPlasticDetail dsp where dsp.orderItem.order = :order and dsp.priced = :texture and dsp.priceAware = :boardDef")

        })
@StringValue(converterClass = OrderFurniture2StringConverter.class)
@by.dak.utils.validator.Validator(validatorClass = OrderFurnitureValidator.class)
public class DSPPlasticDetail extends AOrderBoardDetail
{
    public static DSPPlasticDetail valueOf(DSPPlasticDetail entity)
    {
        DSPPlasticDetail aNew = new DSPPlasticDetail();

        aNew.setPrimary(entity.isPrimary());

        aNew.setNumber(entity.getNumber());
        aNew.setName(entity.getName());

        aNew.setBoardDef(entity.getBoardDef());
        aNew.setComlexBoardDef(entity.getComlexBoardDef());
        aNew.setTexture(entity.getTexture());

        aNew.setLength(entity.getLength());
        aNew.setWidth(entity.getWidth());
        aNew.setAmount(entity.getAmount());
        aNew.setRotatable(entity.isRotatable());


        aNew.setDrilling(entity.getDrilling());
        aNew.setGlueing(entity.getGlueing());
        aNew.setGroove(entity.getGroove());
        aNew.setMilling(entity.getMilling());
        aNew.setAngle45(entity.getAngle45());
        aNew.setCutoff(entity.getCutoff());

        return aNew;
    }
}
