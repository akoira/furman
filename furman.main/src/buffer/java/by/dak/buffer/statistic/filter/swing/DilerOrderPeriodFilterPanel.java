package by.dak.buffer.statistic.filter.swing;

import by.dak.buffer.statistic.filter.DilerOrderFilter;
import by.dak.cutting.statistics.StatisticFilter;
import by.dak.cutting.swing.store.Constants;
import by.dak.swing.FilterPanel;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 25.11.2010
 * Time: 18:21:02
 * To change this template use File | Settings | File Templates.
 */
public class DilerOrderPeriodFilterPanel extends FilterPanel<DilerOrderFilter>
{
    public DilerOrderPeriodFilterPanel()
    {
        setVisibleProperties(Constants.getEntityEditorVisibleProperties(DilerOrderFilter.class));
        setCacheEditorCreator(Constants.getEntityEditorCreators(StatisticFilter.class));
    }

}
