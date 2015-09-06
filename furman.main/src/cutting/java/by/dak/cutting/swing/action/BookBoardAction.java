package by.dak.cutting.swing.action;

import by.dak.cutting.cut.guillotine.Segment;
import by.dak.cutting.swing.TopSegmentPanel;
import by.dak.cutting.swing.cut.SheetDimentionItem;
import by.dak.persistence.entities.Board;
import org.jhotdraw.app.action.edit.AbstractSelectionAction;
import org.jhotdraw.util.ResourceBundleUtil;

import java.awt.event.ActionEvent;

/**
 * User: akoyro
 * Date: 2/23/14
 * Time: 1:11 PM
 */
public class BookBoardAction extends AbstractSelectionAction
{
    public final static String ID_UNLOCKED = "book.board";
    public final static String ID_LOCKED = "booked.board";

    private boolean locked;

    public BookBoardAction(TopSegmentPanel topSegmentPanel)
    {
        super(topSegmentPanel);
        setLocked(false);

    }

    public TopSegmentPanel getTopSegmentPanel()
    {
        return (TopSegmentPanel) target;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        boolean value = !isLocked();
        Segment segment = getTopSegmentPanel().getSegment();
        SheetDimentionItem sheetDimentionItem = (SheetDimentionItem) segment.getDimensionItem();
        Board board = sheetDimentionItem.getBoard();
    }

    public void setLocked(boolean b)
    {
        this.locked = b;
        if (this.locked)
        {
            ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.app.Labels");
            labels.configureAction(this, ID_LOCKED);
        }
        else
        {
            ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.app.Labels");
            labels.configureAction(this, ID_UNLOCKED);
        }
    }

    public boolean isLocked()
    {
        return locked;
    }
}
