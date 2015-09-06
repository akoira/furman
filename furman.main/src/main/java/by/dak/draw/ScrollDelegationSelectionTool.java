package by.dak.draw;

import org.jhotdraw.draw.tool.DelegationSelectionTool;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 5.8.2009
 * Time: 10.14.21
 * To change this template use File | Settings | File Templates.
 */
public class ScrollDelegationSelectionTool extends DelegationSelectionTool
{                               // функционирует при mousePressed для Scrolla 
    private MoveScrollHandle scrollHandler = new MoveScrollHandle();;

    public ScrollDelegationSelectionTool()
    {
        super();
    }

    public ScrollDelegationSelectionTool(Collection<Action> drawingActions, Collection<Action> selectionActions)
    {
        super(drawingActions, selectionActions);
    }

    @Override
    public void mouseDragged(MouseEvent evt)
    {
        scrollHandler.setView(getView());
        scrollHandler.moveScroll(evt.getPoint());
        super.mouseDragged(evt);
    }
}
