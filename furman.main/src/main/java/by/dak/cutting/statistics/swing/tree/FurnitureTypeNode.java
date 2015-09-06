package by.dak.cutting.statistics.swing.tree;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.statistics.FurnitureStatistics;
import by.dak.cutting.statistics.StatisticFilter;
import by.dak.cutting.statistics.swing.StatisticsPanel;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.FurnitureLink;
import by.dak.persistence.entities.PriceAware;
import by.dak.persistence.entities.types.FurnitureCode;
import by.dak.persistence.entities.types.FurnitureType;
import by.dak.swing.table.AListUpdater;
import by.dak.swing.table.ListUpdaterProvider;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

import java.util.ArrayList;
import java.util.List;

/**
 * User: akoyro
 * Date: 18.05.2010
 * Time: 17:28:37
 */
public class FurnitureTypeNode extends AStatisticsNode implements ListUpdaterProvider<FurnitureStatistics>
{
    private List<FurnitureCode> list;
    private List<FurnitureStatistics> cacheStatistics = new ArrayList<FurnitureStatistics>();

    protected FurnitureTypeNode(PriceAware userObject, List<FurnitureCode> list, StatisticFilter filter)
    {
        super(userObject, filter);
        this.list = list;
    }

    @Override
    protected void initChildren()
    {
        for (FurnitureCode furnitureCode : list)
        {
            FurnitureCodeNode node = new FurnitureCodeNode(furnitureCode);
            add(node);
        }
    }


    @Override
    public by.dak.swing.table.ListUpdater<FurnitureStatistics> getListUpdater()
    {
        AListUpdater<FurnitureStatistics> listUpdater = new ListUpdater<FurnitureStatistics>()
        {
            @Override
            public void update()
            {
                if (cacheStatistics.size() == 0)
                {
                    for (final FurnitureCode furnitureCode : list)
                    {
                        FurnitureStatistics statistics = new FurnitureStatistics();
                        statistics.setType((FurnitureType) getUserObject());
                        statistics.setCode(furnitureCode);

                        SearchFilter searchFilter = getFilter().getSearchFilter("orderItem.order");
                        searchFilter.eq(FurnitureLink.PROPERTY_priceAware, statistics.getType());
                        searchFilter.eq(FurnitureLink.PROPERTY_priced, statistics.getCode());
                        List<FurnitureLink> furnitures = FacadeContext.getFurnitureLinkFacade().loadAll(searchFilter);
                        Double count = 0.0;
                        for (FurnitureLink furniture : furnitures)
                        {
                            count += furniture.getSize() * furniture.getAmount();
                        }
                        statistics.setAmount(1l);
                        statistics.setSize(count);
                        statistics.setPrice(FacadeContext.getPriceFacade().findUniqueBy(statistics.getType(), statistics.getCode()));
                        cacheStatistics.add(statistics);
                    }
                }
                getList().clear();
                getList().addAll(cacheStatistics);
            }

            @Override
            public String[] getVisibleProperties()
            {
                return new String[]{"code", "size", "type.unit", "price.currencyType", "price.price", "price.priceDealer", "total", "totalDealer"};
            }

            @Override
            public ResourceMap getResourceMap()
            {
                return Application.getInstance().getContext().getResourceMap(StatisticsPanel.class);
            }
        };
        listUpdater.setSearchFilter(SearchFilter.instanceUnbound());
        return listUpdater;
    }
}