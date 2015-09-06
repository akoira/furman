package by.dak.plastic.dao;

import by.dak.cutting.swing.order.data.TextureBoardDefPair;
import by.dak.persistence.dao.AOrderBoardDetailDao;
import by.dak.persistence.entities.AOrder;
import by.dak.plastic.DSPPlasticDetail;

/**
 * User: akoyro
 * Date: 25.09.11
 * Time: 12:36
 */
public interface DSPPlasticDetailDao extends AOrderBoardDetailDao<DSPPlasticDetail>
{
    Long getCountBy(AOrder order, TextureBoardDefPair pair);
}
