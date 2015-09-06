package by.dak.cutting.swing.action;

import by.dak.cutting.swing.CuttingDrawing;
import by.dak.cutting.swing.FigureClipboard;
import org.jhotdraw.app.action.edit.AbstractSelectionAction;
import org.jhotdraw.draw.DefaultDrawingView;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.util.ResourceBundleUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * User: akoyro
 * Date: 20.12.2010
 * Time: 23:49:11
 */
public class ElementPasteAction extends AbstractSelectionAction
{

    public final static String ID = "edit.paste";

    private FigureClipboard figureClipboard;


    /**
     * Creates a new instance which acts on the currently focused component.
     */
    public ElementPasteAction()
    {
        this(null);
    }

    /**
     * Creates a new instance which acts on the specified component.
     *
     * @param target The target of the action. Specify null for the currently
     *               focused component.
     */
    public ElementPasteAction(JComponent target)
    {
        super(target);
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.app.Labels");
        labels.configureAction(this, ID);
    }

    @Override
    public void actionPerformed(ActionEvent evt)
    {
        if (figureClipboard == null)
        {
            return;
        }

        JComponent c = target;
        if (c == null && (KeyboardFocusManager.getCurrentKeyboardFocusManager().
                getPermanentFocusOwner() instanceof JComponent))
        {
            c = (JComponent) KeyboardFocusManager.getCurrentKeyboardFocusManager().
                    getPermanentFocusOwner();
        }
        if (c != null && c.isEnabled())
        {
            Drawing drawing = ((DefaultDrawingView) c).getDrawing();
            if (drawing instanceof CuttingDrawing)
            {
//                figureClipboard.importData((CuttingDrawing) drawing);
            }
        }
    }

    @Override
    protected void updateEnabled()
    {
        if (target != null)
        {
            setEnabled(target.isEnabled());
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