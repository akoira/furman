package by.dak.cutting.statistics.swing.tree;

import by.dak.cutting.statistics.StatisticFilter;

/**
 * User: akoyro
 * Date: 24.11.2010
 * Time: 13:28:19
 */
public class RootStaristicsNode extends AStatisticsNode
{
    public RootStaristicsNode(StatisticFilter filter)
    {
        super(filter);
        setUserObject(getResourceMap().getString("RootStaristicsNode.text"));
    }

    @Override
    protected void initChildren()
    {
        add(new MaterialStaristicsNode(getFilter()));
        add(new ServicesStaristicsNode(getFilter()));
    }
}
