package by.dak.plastic.dao;

import by.dak.cutting.swing.order.data.TextureBoardDefPair;
import by.dak.persistence.dao.impl.AOrderDetailDaoImpl;
import by.dak.persistence.entities.AOrder;
import by.dak.plastic.DSPPlasticDetail;
import org.hibernate.Query;

import java.util.List;

/**
 * User: akoyro
 * Date: 25.09.11
 * Time: 12:36
 */
public class DSPPlasticDetailDaoImpl extends AOrderDetailDaoImpl<DSPPlasticDetail> implements DSPPlasticDetailDao
{

    @Override
    public List<Object[]> findUniquePairDefText(AOrder order)
    {
        Query q = getSession().getNamedQuery("uniqueDSPPlasticPairsByOrder");
        q.setEntity("order", order);
        return q.list();
    }

    @Override
    public Long getCountBy(AOrder order, TextureBoardDefPair pair)
    {
        Query q = getSession().getNamedQuery("sumDSPPlasticDetails");
        q.setEntity("order", order);
        q.setEntity("boardDef", pair.getBoardDef());
        q.setEntity("texture", pair.getTexture());
        Long count = (Long) q.list().get(0);

        return count;
    }
}
