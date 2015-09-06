package by.dak.cutting.facade.impl;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.facade.AOrderBoardDetailFacade;
import by.dak.cutting.swing.order.data.TextureBoardDefPair;
import by.dak.persistence.dao.AOrderBoardDetailDao;
import by.dak.persistence.entities.*;

import java.util.ArrayList;
import java.util.List;

/**
 * User: akoyro
 * Date: 21.07.2010
 * Time: 19:37:29
 */
public abstract class AOrderBoardDetailFacadeImpl<E extends AOrderBoardDetail> extends AOrderDetailFacadeImpl<E> implements AOrderBoardDetailFacade<E>
{
    public List<TextureBoardDefPair> findUniquePairDefText(AOrder order)
    {
        List<Object[]> pairs = ((AOrderBoardDetailDao) dao).findUniquePairDefText(order);

        ArrayList<TextureBoardDefPair> result = new ArrayList<TextureBoardDefPair>(pairs.size());
        for (Object[] objects : pairs)
        {
            result.add(new TextureBoardDefPair((TextureEntity) objects[0], (BoardDef) objects[1]));

        }
        return result;
    }

    @Override
    public List<E> findBy(AOrder order, TextureBoardDefPair pair)
    {
        SearchFilter searchFilter = SearchFilter.instanceUnbound();
        searchFilter.eq(order, AOrderBoardDetail.PROPERTY_orderItem, OrderItem.PROPERTY_order);
        searchFilter.eq(AOrderBoardDetail.PROPERTY_priceAware, pair.getBoardDef());
        searchFilter.eq(AOrderBoardDetail.PROPERTY_priced, pair.getTexture());
        return loadAll(searchFilter);
    }
}
