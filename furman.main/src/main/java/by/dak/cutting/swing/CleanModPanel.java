package by.dak.cutting.swing;

import by.dak.cutting.swing.store.tabs.AnnexTab;
import by.dak.cutting.swing.store.tabs.CommentTab;
import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.ValidationResultModel;
import com.jgoodies.validation.view.ValidationComponentUtils;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Base class for dictionary's panel
 *
 * @author Rudak Alexei
 */
public abstract class CleanModPanel extends BasePanel
{

    private AnnexTab anTab;
    private CommentTab cmTab;
    protected Thread internalThread;

    /**
     * Creates new form SVModPanel
     */
    public CleanModPanel()
    {
        initComponents();
        initEnvironment();
    }

    protected void addServiceTabs()
    {
        cmTab = new CommentTab();
        anTab = new AnnexTab();
        tabbedPane.addTab("Comment", cmTab);
        tabbedPane.addTab("Annex", anTab);
    }

    protected void runWork()
    {
    }

    protected void startTabs()
    {
        Runnable r = new Runnable()
        {

            public void run()
            {
                try
                {
                    runWork();
                }
                catch (Exception x)
                {
                    // in case ANY exception slips through
                    x.printStackTrace();
                }
            }
        };
        internalThread = new Thread(r);
        internalThread.start();
    }

    private void initEnvironment()
    {
        //lbHint.setIcon(ValidationResultViewFactory.getInfoIcon());
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addPropertyChangeListener(new FocusChangeHandler());
    }

    public Boolean validateGUI()
    {
        ValidationResult validationResult = new ValidationResult();
        for (int a = 0; a < tabbedPane.getTabCount(); a++)
        {
            BaseTabPanel tp = (BaseTabPanel) tabbedPane.getComponentAt(a);
            if (tp != null)
            {
                validationResult.addAllFrom(tp.validateGui());
            }
        }
        ValidationResultModel validationResultModel = lbWarningList.getModel();
        validationResultModel.setResult(validationResult);
        lbWarningList.setModel(validationResultModel);

        if (!validationResultModel.hasErrors())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private final class FocusChangeHandler
            implements PropertyChangeListener
    {

        public void propertyChange(PropertyChangeEvent evt)
        {
            String propertyName = evt.getPropertyName();
            if ("permanentFocusOwner".equals(propertyName))
            {
                Component focusOwner = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();

                if (focusOwner instanceof JTextField)
                {
                    JTextField field = (JTextField) focusOwner;
                    String focusHint = (String) ValidationComponentUtils.getInputHint(field);
                    //lbHint.setText(focusHint);
                }
                else
                {
                    //lbHint.setText("");
                }
            }
        }
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <draw-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        tabbedPane = new by.dak.cutting.swing.BaseTabCtrl();
        lbWarningList = new by.dak.cutting.swing.WarningList();

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(layout.createSequentialGroup()
                                .addContainerGap()
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                        .add(tabbedPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                                        .add(lbWarningList, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .add(tabbedPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(lbWarningList, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                                .addContainerGap())
        );
    }// </draw-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public by.dak.cutting.swing.WarningList lbWarningList;
    public by.dak.cutting.swing.BaseTabCtrl tabbedPane;
    // End of variables declaration//GEN-END:variables
}
