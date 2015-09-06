package by.dak.cutting.statistics.swing.tree;

import by.dak.cutting.statistics.AStatistics;
import by.dak.cutting.statistics.StatisticFilter;
import by.dak.cutting.statistics.swing.StatisticsPanel;
import by.dak.swing.table.AListUpdater;
import by.dak.swing.tree.ATreeNode;
import org.apache.commons.beanutils.PropertyUtils;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

/**
 * User: akoyro
 * Date: 18.05.2010
 * Time: 17:18:25
 */
public abstract class AStatisticsNode extends ATreeNode
{
    private StatisticFilter filter;

    protected AStatisticsNode(StatisticFilter filter)
    {
        super();
        this.filter = filter;
    }

    protected AStatisticsNode(Object userObject, StatisticFilter filter)
    {
        super(userObject);
        this.filter = filter;
    }

    protected AStatisticsNode(Object userObject, boolean allowsChildren, StatisticFilter filter)
    {
        super(userObject, allowsChildren);
        this.filter = filter;
    }


    public StatisticFilter getFilter()
    {
        return filter;
    }

    public void setFilter(StatisticFilter filter)
    {
        this.filter = filter;
    }


    public static abstract class ListUpdater<U extends AStatistics> extends AListUpdater<U>
    {
        @Override
        public int getCount()
        {
            return getList().size();
        }


        @Override
        public ResourceMap getResourceMap()
        {
            return Application.getInstance().getContext().getResourceMap(StatisticsPanel.class);
        }

        @Override
        public String[] getTotalProperties()
        {
            return new String[]{"total", "totalDealer"};
        }

        @Override
        public Double getTotalValue(String property)
        {
            double value = 0.0;
            for (U item : getList())
            {
                try
                {
                    value += (Double) PropertyUtils.getProperty(item, property);
                }
                catch (Exception e)
                {
                    throw new IllegalArgumentException(e);
                }
            }
            return value;
        }
    }
}
