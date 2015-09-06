package by.dak.design.swing;

import by.dak.design.draw.BoardFigureCreationTool;
import by.dak.design.draw.DimensionFigureCreationTool;
import by.dak.design.draw.FrontDesignerDrawing;
import by.dak.design.draw.components.BoardFigure;
import by.dak.design.draw.components.DimensionFigure;
import by.dak.design.draw.handle.FrontDesignerDragTracker;
import by.dak.design.swing.action.BoardCreationAction;
import by.dak.design.swing.action.BoardSelectionController;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.action.ButtonFactory;
import org.jhotdraw.draw.tool.DelegationSelectionTool;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 6/22/11
 * Time: 4:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class FrontDesignerPanel extends ADesignerPanel
{
    private BoardFigureCreationTool boardFigureCreationTool;

    public FrontDesignerPanel()
    {
        initEnv();
    }

    private void initEnv()
    {
        addPropertyChangeListener("drawing", new PropertyChangeListener()
        {
            @Override
            public void propertyChange(PropertyChangeEvent evt)
            {
                new BoardSelectionController(boardFigureCreationTool);
            }
        });
    }

    public FrontDesignerDrawing getFrontDesignerDrawing()
    {
        return (FrontDesignerDrawing) getDrawing();
    }

    @Override
    public void setDrawing(Drawing d)
    {
        super.setDrawing(d);
        firePropertyChange("drawing", null, d);
    }

    @Override
    protected void fillToolBarFigures()
    {
        ButtonFactory.addToolTo(getToolBarFigures(), getDrawingEditor(), new DimensionFigureCreationTool(new DimensionFigure()),
                "edit.createDistanceLine", getResourceBundleUtil());
        getToolBarFigures().add(getBoardCreationButton(getToolBarFigures()));
    }

    @Override
    protected void fillToolBarAtributes()
    {
        super.fillToolBarAtributes();
    }

    @Override
    protected JToolBar getToolBarFigures()
    {
        if (toolBarFigures == null)
        {
            selectionTool = new DelegationSelectionTool();
            selectionTool.setDragTracker(new FrontDesignerDragTracker());
            JToolBar toolBar = new JToolBar();
            ButtonGroup group = new ButtonGroup();
            toolBar.putClientProperty("toolButtonGroup", group);
            toolBarFigures = toolBar;
            defaultSelectionButton = ButtonFactory.addSelectionToolTo(toolBarFigures, getDrawingEditor(), selectionTool);
            fillToolBarFigures();
        }
        return toolBarFigures;
    }

    private JToggleButton getBoardCreationButton(JToolBar toolBarFigures)
    {
        boardFigureCreationTool = new BoardFigureCreationTool(new BoardFigure());

        ButtonGroup group = (ButtonGroup) toolBarFigures.getClientProperty("toolButtonGroup");
        JToggleButton createBoardButton = new JToggleButton();
        createBoardButton.setFocusable(false);
        BoardCreationAction boardCreationAction = new BoardCreationAction(getDrawingEditor(),
                boardFigureCreationTool, defaultSelectionButton);
        getResourceBundleUtil().configureAction(boardCreationAction, "edit.createBoard");
        createBoardButton.setAction(boardCreationAction);
        group.add(createBoardButton);

        return createBoardButton;
    }
}
