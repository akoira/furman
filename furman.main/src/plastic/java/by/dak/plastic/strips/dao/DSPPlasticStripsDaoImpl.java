package by.dak.plastic.strips.dao;

import by.dak.persistence.cutting.dao.impl.ABoardStripsDaoImpl;
import by.dak.persistence.entities.AOrder;
import by.dak.plastic.strips.DSPPlasticStripsEntity;
import org.hibernate.Query;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 27.09.11
 * Time: 10:07
 * To change this template use File | Settings | File Templates.
 */
public class DSPPlasticStripsDaoImpl extends ABoardStripsDaoImpl<DSPPlasticStripsEntity> implements DSPPlasticStripsDao
{
    @Override
    public void deleteAll(AOrder order)
    {
        Query q = getSession().getNamedQuery("deleteAllDSPStripsEntity");
        q.setEntity("order", order);
        q.executeUpdate();
    }
}
