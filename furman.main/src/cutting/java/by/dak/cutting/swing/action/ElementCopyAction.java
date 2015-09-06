package by.dak.cutting.swing.action;

import by.dak.cutting.swing.CuttingDrawing;
import by.dak.cutting.swing.ElementFigure;
import by.dak.cutting.swing.FigureClipboard;
import org.jhotdraw.app.action.edit.AbstractSelectionAction;
import org.jhotdraw.draw.DefaultDrawingView;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.util.ResourceBundleUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Set;

/**
 * User: akoyro
 * Date: 20.12.2010
 * Time: 23:48:59
 */
public class ElementCopyAction extends AbstractSelectionAction
{

    public final static String ID = "edit.copy";

    private FigureClipboard figureClipboard;

    /**
     * Creates a new instance which acts on the currently focused component.
     */
    public ElementCopyAction()
    {
        this(null);
    }

    /**
     * Creates a new instance which acts on the specified component.
     *
     * @param target The target of the action. Specify null for the currently
     *               focused component.
     */
    public ElementCopyAction(JComponent target)
    {
        super(target);
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.app.Labels");
        labels.configureAction(this, ID);
    }

    @Override
    public void actionPerformed(ActionEvent evt)
    {
        if (figureClipboard != null)
        {
            JComponent c = target;
            if (c == null && (KeyboardFocusManager.getCurrentKeyboardFocusManager().
                    getPermanentFocusOwner() instanceof JComponent))
            {
                c = (JComponent) KeyboardFocusManager.getCurrentKeyboardFocusManager().
                        getPermanentFocusOwner();
            }
            // Note: copying is allowed for disabled components
            if (c != null && c instanceof DefaultDrawingView)
            {

                Drawing drawing = ((DefaultDrawingView) c).getDrawing();
                Set<Figure> selected = ((DefaultDrawingView) c).getSelectedFigures();
                if (selected.size() == 1)
                {
                    Figure figure = selected.iterator().next();

                    if (figure instanceof ElementFigure &&
                            ((ElementFigure) figure).getParent() == null
                            && drawing instanceof CuttingDrawing)
                    {
                        figureClipboard.exportData((ElementFigure) figure, (CuttingDrawing) drawing);
                    }
                }
            }
        }
    }

    public FigureClipboard getFigureClipboard()
    {
        return figureClipboard;
    }

    public void setFigureClipboard(FigureClipboard figureClipboard)
    {
        this.figureClipboard = figureClipboard;
    }
}