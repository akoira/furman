package by.dak.design.swing;

import by.dak.design.draw.ADesignerDrawing;
import by.dak.design.draw.TopDesignerDrawing;
import by.dak.design.draw.handle.TopDesignerDragTracker;
import org.jhotdraw.draw.action.ButtonFactory;
import org.jhotdraw.draw.tool.DelegationSelectionTool;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 24.08.11
 * Time: 16:32
 * To change this template use File | Settings | File Templates.
 */
public class TopDesignerPanel extends ADesignerPanel
{
    private TopCellChangedListener topCellChangedListener = new TopCellChangedListener();

    public TopDesignerPanel()
    {
        TopDesignerDrawing topDesignerDrawing = new TopDesignerDrawing();
        topDesignerDrawing.addPropertyChangeListener(ADesignerDrawing.PROPERTY_topFigure, topCellChangedListener);
        setDrawing(topDesignerDrawing);
    }

    public TopDesignerDrawing getTopDesignerDrawing()
    {
        return (TopDesignerDrawing) getDrawing();
    }

    @Override
    protected JToolBar getToolBarFigures()
    {
        if (toolBarFigures == null)
        {
            selectionTool = new DelegationSelectionTool();
            selectionTool.setDragTracker(new TopDesignerDragTracker());
            JToolBar toolBar = new JToolBar();
            ButtonGroup group = new ButtonGroup();
            toolBar.putClientProperty("toolButtonGroup", group);
            toolBarFigures = toolBar;
            defaultSelectionButton = ButtonFactory.addSelectionToolTo(toolBarFigures, getDrawingEditor(), selectionTool);
            fillToolBarFigures();
        }
        return toolBarFigures;
    }


    private class TopCellChangedListener implements PropertyChangeListener
    {
        @Override
        public void propertyChange(PropertyChangeEvent evt)
        {
            getDrawingView().getComponent().revalidate();
        }
    }
}
