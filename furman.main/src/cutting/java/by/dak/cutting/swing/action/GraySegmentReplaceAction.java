package by.dak.cutting.swing.action;

import by.dak.cutting.DialogShowers;
import by.dak.cutting.swing.TopSegmentPanel;
import by.dak.swing.WindowCallback;
import org.jhotdraw.app.action.edit.AbstractSelectionAction;
import org.jhotdraw.util.ResourceBundleUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * User: akoyro
 * Date: 25.01.2011
 * Time: 12:35:37
 */
public class GraySegmentReplaceAction extends AbstractSelectionAction
{
    public final static String ID = "gray.segment.add";

    public GraySegmentReplaceAction(TopSegmentPanel topSegmentPanel)
    {
        super(topSegmentPanel);
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.app.Labels");
        labels.configureAction(this, ID);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        JComponent component = target;
        if (component == null && (KeyboardFocusManager.getCurrentKeyboardFocusManager().
                getPermanentFocusOwner() instanceof JComponent))
        {
            component = (JComponent) KeyboardFocusManager.getCurrentKeyboardFocusManager().
                    getPermanentFocusOwner();
        }

        if (component instanceof TopSegmentPanel)
        {
            WindowCallback windowCallback = new WindowCallback();
            RestTab restTab = new RestTab((TopSegmentPanel) component, windowCallback);
            restTab.init();
            DialogShowers.showBy(restTab, component, windowCallback, true);
        }
    }
}
