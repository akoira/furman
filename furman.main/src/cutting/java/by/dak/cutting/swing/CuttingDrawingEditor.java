package by.dak.cutting.swing;

import by.dak.cutting.swing.action.ElementCopyAction;
import by.dak.cutting.swing.action.ElementPasteAction;
import org.jhotdraw.draw.DefaultDrawingEditor;
import org.jhotdraw.draw.action.IncreaseHandleDetailLevelAction;
import org.jhotdraw.draw.action.MoveAction;
import org.jhotdraw.draw.action.MoveConstrainedAction;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * User: akoyro
 * Date: 20.12.2010
 * Time: 23:47:38
 */
public class CuttingDrawingEditor extends DefaultDrawingEditor
{
    private ElementCopyAction elementCopyAction;
    private ElementPasteAction elementPasteAction;
    private FigureClipboard figureClipboard;


    public CuttingDrawingEditor()
    {
        addPropertyChangeListener("figureClipboard", new PropertyChangeListener()
        {
            @Override
            public void propertyChange(PropertyChangeEvent evt)
            {
                elementCopyAction.setFigureClipboard(figureClipboard);
                elementPasteAction.setFigureClipboard(figureClipboard);
            }
        });
    }

    protected ActionMap createActionMap()
    {
        ActionMap m = new ActionMap();

        m.put(IncreaseHandleDetailLevelAction.ID, new IncreaseHandleDetailLevelAction(this));

        m.put(MoveAction.East.ID, new MoveAction.East(this));
        m.put(MoveAction.West.ID, new MoveAction.West(this));
        m.put(MoveAction.North.ID, new MoveAction.North(this));
        m.put(MoveAction.South.ID, new MoveAction.South(this));
        m.put(MoveConstrainedAction.East.ID, new MoveConstrainedAction.East(this));
        m.put(MoveConstrainedAction.West.ID, new MoveConstrainedAction.West(this));
        m.put(MoveConstrainedAction.North.ID, new MoveConstrainedAction.North(this));
        m.put(MoveConstrainedAction.South.ID, new MoveConstrainedAction.South(this));

        elementCopyAction = new ElementCopyAction();
        m.put(ElementCopyAction.ID, elementCopyAction);
        elementPasteAction = new ElementPasteAction();
        m.put(ElementPasteAction.ID, elementPasteAction);

        return m;
    }

    public FigureClipboard getFigureClipboard()
    {
        return figureClipboard;
    }

    public void setFigureClipboard(FigureClipboard figureClipboard)
    {
        FigureClipboard old = this.figureClipboard;
        this.figureClipboard = figureClipboard;
        firePropertyChange("figureClipboard", old, figureClipboard);

    }
}
