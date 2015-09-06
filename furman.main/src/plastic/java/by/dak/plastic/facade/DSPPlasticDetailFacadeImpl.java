package by.dak.plastic.facade;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.facade.impl.AOrderBoardDetailFacadeImpl;
import by.dak.cutting.swing.order.data.TextureBoardDefPair;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.AOrder;
import by.dak.persistence.entities.BoardDef;
import by.dak.persistence.entities.PersistenceEntity;
import by.dak.plastic.DSPPlasticDetail;
import by.dak.plastic.dao.DSPPlasticDetailDao;

import java.util.Collections;
import java.util.List;

/**
 * User: akoyro
 * Date: 25.09.11
 * Time: 12:33
 */
public class DSPPlasticDetailFacadeImpl extends AOrderBoardDetailFacadeImpl<DSPPlasticDetail> implements DSPPlasticDetailFacade
{
    private List<Long> plasticBoardDefIds = Collections.EMPTY_LIST;


    public List<Long> getPlasticBoardDefIds()
    {
        return plasticBoardDefIds;
    }


    public void setPlasticBoardDefIds(List<Long> plasticBoardDefIds)
    {
        this.plasticBoardDefIds = plasticBoardDefIds;
    }

    @Override
    public List<BoardDef> getPlasticBoardDefs()
    {
        SearchFilter searchFilter = SearchFilter.instanceUnbound();
        searchFilter.in(PersistenceEntity.PROPERTY_id, plasticBoardDefIds);
        searchFilter.addAscOrder(BoardDef.PROPERTY_name);
        return FacadeContext.getBoardDefFacade().loadAll(searchFilter);
    }

    @Override
    public int getCountBy(AOrder order, TextureBoardDefPair pair)
    {
        return ((DSPPlasticDetailDao) getDao()).getCountBy(order, pair).intValue();
    }

    public boolean hasPlasticDetails(AOrder order)
    {
        return getPlasticBoardDefIds().size() > 0 && FacadeContext.getOrderFurnitureFacade().getCountBy(order, getPlasticBoardDefIds()) > 0;
    }

    public boolean isPlastic(BoardDef boardDef)
    {
        return getPlasticBoardDefIds().contains(boardDef.getId());
    }

}
