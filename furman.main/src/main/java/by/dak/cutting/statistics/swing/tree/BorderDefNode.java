package by.dak.cutting.statistics.swing.tree;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.statistics.BorderStatistics;
import by.dak.cutting.statistics.StatisticFilter;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.*;
import by.dak.swing.table.AListUpdater;
import by.dak.swing.table.ListUpdaterProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * User: akoyro
 * Date: 18.05.2010
 * Time: 17:28:37
 */
public class BorderDefNode extends AStatisticsNode implements ListUpdaterProvider<BorderStatistics>
{
    private List<TextureEntity> list;
    private List<BorderStatistics> cacheBorderStatistics = new ArrayList<BorderStatistics>();

    protected BorderDefNode(BorderDefEntity userObject, List<TextureEntity> list, StatisticFilter filter)
    {
        super(userObject, filter);
        this.list = list;
    }

    @Override
    protected void initChildren()
    {
        for (TextureEntity textureEntity : list)
        {
            TextureNode textureNode = new TextureNode(textureEntity);
            add(textureNode);
        }
    }


    @Override
    public by.dak.swing.table.ListUpdater<BorderStatistics> getListUpdater()
    {
        AListUpdater<BorderStatistics> listUpdater = new ListUpdater<BorderStatistics>()
        {
            @Override
            public void update()
            {
                if (cacheBorderStatistics.size() == 0)
                {
                    for (final TextureEntity textureEntity : list)
                    {
                        BorderStatistics borderStatistics = new BorderStatistics();
                        borderStatistics.setType((BorderDefEntity) getUserObject());
                        borderStatistics.setCode(textureEntity);

                        SearchFilter searchFilter = getFilter().getSearchFilter("order");
                        searchFilter.eq(Border.PROPERTY_priceAware, borderStatistics.getType());
                        searchFilter.eq(Border.PROPERTY_priced, borderStatistics.getCode());
                        List<Border> borders = FacadeContext.getBorderFacade().loadAll(searchFilter);
                        Double count = 0.0;
                        for (Border border : borders)
                        {
                            count += border.getLength() * border.getAmount();
                        }
                        borderStatistics.setAmount(1l);
                        borderStatistics.setSize(count);
                        borderStatistics.setPrice(FacadeContext.getPriceFacade().findUniqueBy((PriceAware)borderStatistics.getType(),(Priced)borderStatistics.getCode()));
                        cacheBorderStatistics.add(borderStatistics);
                    }
                }
                getList().clear();
                getList().addAll(cacheBorderStatistics);
            }

            @Override
            public String[] getVisibleProperties()
            {
                return new String[]{"code", "size","price.currencyType", "price.price", "price.priceDealer", "total", "totalDealer"};
            }
        };
        listUpdater.setSearchFilter(SearchFilter.instanceUnbound());
        return listUpdater;
    }
}
