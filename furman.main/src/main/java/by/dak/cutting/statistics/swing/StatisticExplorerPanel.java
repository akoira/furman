package by.dak.cutting.statistics.swing;

import by.dak.swing.explorer.AExplorerPanel;
import net.infonode.docking.RootWindow;
import net.infonode.docking.SplitWindow;
import net.infonode.docking.View;
import net.infonode.docking.util.ViewMap;
import net.infonode.util.Direction;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 12.10.11
 * Time: 17:20
 * To change this template use File | Settings | File Templates.
 */
public class StatisticExplorerPanel extends AExplorerPanel
{
    private OrdersPanel ordersPanel;

    @Override
    protected RootWindow initRootWindow()
    {
        RootWindow rootWindow = super.initRootWindow();
        rootWindow.getWindowBar(Direction.RIGHT).setEnabled(true);
        rootWindow.getWindowBar(Direction.RIGHT).addTab(((ViewMap) rootWindow.getViewSerializer()).getView(2));
        rootWindow.setWindow(new SplitWindow(true, 0.8f, new SplitWindow(true, 0.1f, ((ViewMap) rootWindow.getViewSerializer()).getView(0),
                ((ViewMap) rootWindow.getViewSerializer()).getView(1)), ((ViewMap) rootWindow.getViewSerializer()).getView(2)));

        return rootWindow;
    }

    @Override
    protected void save()
    {
    }

    @Override
    protected boolean validateCurrentContent()
    {
        return true;
    }

    @Override
    protected List<View> createViews()
    {
        List<View> views = super.createViews();
        views.add(createOrdersPanelView());

        return views;
    }

    private View createOrdersPanelView()
    {
        ordersPanel = new OrdersPanel();

        View ordersPanelView = new View(getResourceMap().getString("ordersPanel.title"),
                getResourceMap().getIcon("ordersPanel.icon"), ordersPanel);
        ordersPanelView.getWindowProperties().setCloseEnabled(false);
        ordersPanelView.getWindowProperties().setDockEnabled(true);
        ordersPanelView.getWindowProperties().setUndockEnabled(false);
        ordersPanelView.getWindowProperties().setDragEnabled(true);
        ordersPanelView.getWindowProperties().setMinimizeEnabled(true);
        return ordersPanelView;
    }

    public OrdersPanel getOrdersPanel()
    {
        return ordersPanel;
    }
}
