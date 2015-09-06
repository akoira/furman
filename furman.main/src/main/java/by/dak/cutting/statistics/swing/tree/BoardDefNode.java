package by.dak.cutting.statistics.swing.tree;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.cut.facade.StripsFacade;
import by.dak.cutting.statistics.BoardStatistics;
import by.dak.cutting.statistics.StatisticFilter;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.BoardDef;
import by.dak.persistence.entities.TextureEntity;
import by.dak.swing.table.AListUpdater;
import by.dak.swing.table.ListUpdaterProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * User: akoyro
 * Date: 18.05.2010
 * Time: 13:52:06
 */
public class BoardDefNode extends AStatisticsNode implements ListUpdaterProvider<BoardStatistics>
{
    private List<TextureEntity> list;
    private List<BoardStatistics> cacheBoardStatisticses = new ArrayList<BoardStatistics>();

    protected BoardDefNode(BoardDef boardDef, List<TextureEntity> list, StatisticFilter filter)
    {
        super(boardDef, filter);
        this.list = list;
    }

    @Override
    protected void initChildren()
    {
        for (TextureEntity texture : list)
        {
            TextureNode textureNode = new TextureNode(texture);
            add(textureNode);
        }
    }

    @Override
    public by.dak.swing.table.ListUpdater<BoardStatistics> getListUpdater()
    {

        AListUpdater<BoardStatistics> listUpdater = new ListUpdater<BoardStatistics>()
        {

            @Override
            public void update()
            {
                if (cacheBoardStatisticses.size() == 0)
                {
                    for (final TextureEntity textureEntity : list)
                    {
                        BoardStatistics boardStatistics = new BoardStatistics();
                        boardStatistics.setType((BoardDef) getUserObject());
                        boardStatistics.setCode(textureEntity);
                        List<StripsFacade.StatisticDTO> list = FacadeContext.getStripsFacade().getStatistics(getFilter(), boardStatistics.getType(), boardStatistics.getCode());
                        assert list.size() > 0;
                        boardStatistics.setAmount(list.get(0).getAmount());
                        boardStatistics.setSize(list.get(0).getSize());
                        boardStatistics.setPrice(FacadeContext.getPriceFacade().findUniqueBy(boardStatistics.getType(), boardStatistics.getCode()));
                        cacheBoardStatisticses.add(boardStatistics);
                    }
                }
                getList().clear();
                getList().addAll(cacheBoardStatisticses);
            }

            @Override
            public String[] getVisibleProperties()
            {
                return new String[]{"code", "size", "type.unit", "price.currencyType", "price.price", "price.priceDealer", "total", "totalDealer"};
            }
        };
        listUpdater.setSearchFilter(SearchFilter.instanceUnbound());
        return listUpdater;
    }


}
