package by.dak.draw;

import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.DrawingView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Collection;

/**
 * User: akoyro
 * Date: 05.10.2009
 * Time: 15:42:37
 */
public class ShowDimentionAction extends AbstractAction
{
    private DrawingEditor editor;

    public ShowDimentionAction(DrawingEditor editor)
    {
        this.editor = editor;
    }

    private boolean isShowDimention = false;

    @Override
    public void actionPerformed(ActionEvent e)
    {
        isShowDimention = !isShowDimention;
        Collection<DrawingView> collection = editor.getDrawingViews();
        for (DrawingView view : collection)
        {
            Drawing drawing = view.getDrawing();
            if (drawing instanceof ShowDimention)
            {
                drawing.willChange();
                ((ShowDimention) drawing).setShowDimention(isShowDimention);
                drawing.changed();
            }
        }
    }

    public boolean isShowDimention()
    {
        return isShowDimention;
    }
}
