package by.dak.cutting.swing;

import by.dak.cutting.CuttingApp;
import by.dak.cutting.swing.store.tabs.AnnexTab;
import by.dak.cutting.swing.store.tabs.CommentTab;
import by.dak.swing.*;
import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.ValidationResultModel;
import com.jidesoft.swing.JideSplitPane;
import org.apache.commons.beanutils.BeanUtils;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingListener;
import org.jdesktop.beansbinding.PropertyStateEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.beans.Beans;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


/**
 * Base class for dictionary's panel
 *
 * @author Andrey Koyro
 */
public abstract class DModPanel<V> extends BasePanel implements TabsContainer, Titled, HasValue<V>, IDefaultButtonProvider, IWindowAware
{

    private AnnexTab anTab;
    private CommentTab cmTab;

    private javax.swing.JButton buttonCancel;
    private javax.swing.JButton buttonOk;

    private by.dak.cutting.swing.WarningList lbWarningList;
    private by.dak.cutting.swing.BaseTabCtrl tabbedPane;
    private JideSplitPane splitPane1;

    private ValueSave<V> valueSave;

    private ValueChangedController valueChangedController = new ValueChangedController();
    private ValidationController validationController = new ValidationController();


    private boolean showOkCancel = false;
    private boolean showWarningList = true;

    private V value;

    private Window parentWindow;


    /**
     * Creates new form SVModPanel
     */

    public DModPanel()
    {
        initComponents();
        if (!Beans.isDesignTime())
        {
            initEnvironment();
            setName(this.getClass().getSimpleName());
        }
    }

    public BaseTabCtrl getTabbedPane()
    {
        return tabbedPane;
    }


    @Override
    public void setWindow(Window window)
    {
        this.parentWindow = window;
    }

    protected void addServiceTabs()
    {
        cmTab = new CommentTab();
        anTab = new AnnexTab();
        tabbedPane.addTab("Comment", cmTab);
        tabbedPane.addTab("Annex", anTab);
    }

    protected void addTabs()
    {

    }

    private void initEnvironment()
    {
        //lbHint.setIcon(ValidationResultViewFactory.getInfoIcon());
        /*KeyboardFocusManager.getCurrentKeyboardFocusManager()
                .addPropertyChangeListener(new FocusChangeHandler());*/
        this.setFocusable(true);
        this.addPropertyChangeListener("value", valueChangedController);
        this.setShowOkCancel(false);
        this.addPropertyChangeListener("showWarningList", new ShowWarningListListener());
        this.addPropertyChangeListener("", new ShowWarningListListener());

        this.buttonOk.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (getValueSave() != null && validateGUI())
                {
                    getValueSave().save(getValue());
                    if (parentWindow != null)
                    {
                        parentWindow.dispose();
                    }
                }
            }
        });

        this.buttonCancel.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (parentWindow != null)
                {
                    parentWindow.dispose();
                }
            }
        });

    }

    public Boolean validateGUI()
    {
        final ValidationResult validationResult = new ValidationResult();
        TabIterator<Boolean> tabIterator = new TabIterator<Boolean>(tabbedPane)
        {
            @Override
            protected Boolean iterate(Component tab)
            {
                if (tab instanceof BaseTabPanel)
                {
                    validationResult.addAllFrom(((BaseTabPanel) tab).validateGui());
                }
                return true;
            }
        };
        tabIterator.iterate();

        ValidationResultModel validationResultModel = lbWarningList.getModel();
        validationResultModel.setResult(validationResult);
        lbWarningList.setModel(validationResultModel);

        return !validationResultModel.hasErrors();
    }

    @Deprecated
    public void save()
    {

    }

    public void setValue(V value)
    {
        V old = this.value;
        this.value = value;
        firePropertyChange("value", old, value);
    }

    public V getValue()
    {
        return value;
    }

    public void setEditable(final boolean editable)
    {
        super.setEditable(editable);
        updateEditable(editable);
    }

    private void updateEditable(final boolean editable)
    {
        TabIterator<Boolean> tabIterator = new TabIterator<Boolean>(tabbedPane)
        {
            @Override
            protected Boolean iterate(Component tab)
            {
                try
                {
                    try
                    {
                        BeanUtils.getProperty(tab, "editable");
                        BeanUtils.setProperty(tab, "editable", editable);
                    }
                    catch (Exception e)
                    {
                    }
                }
                catch (Exception e)
                {
                    CuttingApp.getApplication().getExceptionHandler().handle(e);
                }
                return true;
            }
        };
        tabIterator.iterate();
    }

    public ValueSave<V> getValueSave()
    {
        return valueSave;
    }

    public void setValueSave(ValueSave<V> valueSave)
    {
        ValueSave<V> old = this.valueSave;
        this.valueSave = valueSave;
        firePropertyChange("valueSave", old, this.valueSave);
    }

    public boolean isShowWarningList()
    {
        return showWarningList;
    }

    public void setShowWarningList(boolean showWarningList)
    {
        boolean old = this.showWarningList;
        this.showWarningList = showWarningList;
        firePropertyChange("showWarningList", old, showWarningList);
    }

    private final class ShowWarningListListener implements PropertyChangeListener
    {
        @Override
        public void propertyChange(PropertyChangeEvent evt)
        {
            if (isShowWarningList())
            {
                basePanel.remove(tabbedPane);
                basePanel.add(BorderLayout.CENTER, splitPane1);
            }
            else
            {
                basePanel.remove(splitPane1);
                basePanel.add(BorderLayout.CENTER, tabbedPane);
            }
            revalidate();
            repaint();
        }
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        basePanel = new JPanel(new BorderLayout());

        splitPane1 = new JideSplitPane(JideSplitPane.VERTICAL_SPLIT);
        tabbedPane = new by.dak.cutting.swing.BaseTabCtrl();
        lbWarningList = new by.dak.cutting.swing.WarningList();
        buttonCancel = new javax.swing.JButton();
        buttonOk = new javax.swing.JButton();

        splitPane1.addPane(tabbedPane);
        splitPane1.addPane(lbWarningList);
        splitPane1.addHierarchyListener(new HierarchyListener()
        {
            @Override
            public void hierarchyChanged(HierarchyEvent e)
            {
                if ((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) > 1)
                {
                    Runnable runnable = new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            splitPane1.setDividerLocation(0, 600);
                        }
                    };
                    SwingUtilities.invokeLater(runnable);
                }
            }
        });

        basePanel.add(BorderLayout.CENTER, splitPane1);


        buttonCancel.setText("CANCEL");

        buttonOk.setText("OK");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(layout.createSequentialGroup()
                                .add(379, 379, 379)
                                .add(buttonOk)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(buttonCancel)
                                .addContainerGap())
                        .add(basePanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 515, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                                .add(basePanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                        .add(buttonCancel)
                                        .add(buttonOk))
                                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    private JPanel basePanel;
    // End of variables declaration//GEN-END:variables

    public WarningList getWarningList()
    {
        return lbWarningList;
    }

    public void insertTab(String title, Icon icon, Component component, String tip, int index)
    {
        addBindingListener(component);
        tabbedPane.insertTab(title, icon, component, tip, index);
        if (component instanceof BaseTabPanel)
        {
            ((BaseTabPanel) component).setValue(getValue());
        }
    }

    private void addBindingListener(Component component)
    {
        if (component instanceof BaseTabPanel)
        {
            ((BaseTabPanel) component).getBindingGroup().addBindingListener(validationController);
        }
    }

    public void setSelectedIndex(int index)
    {
        tabbedPane.setSelectedIndex(index);
    }

    public void remove(Component component)
    {
        if (component instanceof BaseTabPanel)
        {
            ((BaseTabPanel) component).getBindingGroup().removeBindingListener(validationController);
        }
        tabbedPane.remove(component);
    }

    public void addTab(Component component, String titleKey, Object... args)
    {
        addTab(getResourceMap().getString(titleKey, args), component);
    }

    public void addTab(String title, Component component)
    {
        if (component instanceof AValueTab)
        {
            ((AValueTab) component).init();
        }
        addBindingListener(component);

        tabbedPane.addTab(title, component);
    }

    public void addTab(Component component)
    {
        if (component instanceof Titled)
        {
            addTab(((Titled) component).getTitle(), component);
        }
        else
        {
            addTab(component.getClass().getName(), component);
        }
    }

    protected void updateValueForTab(Component tab)
    {
        if (tab instanceof HasValue)
        {
            ((HasValue) tab).setValue(getValue());
        }
    }


    public class ValueChangedController implements PropertyChangeListener
    {
        @Override
        public void propertyChange(PropertyChangeEvent evt)
        {
            TabIterator<Boolean> tabIterator = new TabIterator<Boolean>(tabbedPane)
            {
                @Override
                protected Boolean iterate(Component tab)
                {
                    updateValueForTab(tab);
                    return true;
                }
            };
            tabIterator.iterate();
        }

    }

    public class ValidationController implements BindingListener
    {

        @Override
        public void bindingBecameBound(Binding binding)
        {
        }

        @Override
        public void bindingBecameUnbound(Binding binding)
        {
        }

        @Override
        public void syncFailed(Binding binding, Binding.SyncFailure failure)
        {
            validateGUI();
        }

        @Override
        public void synced(Binding binding)
        {
            validateGUI();
        }

        @Override
        public void sourceChanged(Binding binding, PropertyStateEvent event)
        {
        }

        @Override
        public void targetChanged(Binding binding, PropertyStateEvent event)
        {
        }
    }


    public boolean isShowOkCancel()
    {
        return showOkCancel;
    }

    public void setShowOkCancel(boolean showOkCancel)
    {
        this.showOkCancel = showOkCancel;
        buttonCancel.setVisible(showOkCancel);
        buttonOk.setVisible(showOkCancel);
    }

    public JButton getDefaultButton()
    {
        return buttonOk;
    }

    protected void setFocusToFirstComponent()
    {
        TabIterator<Boolean> tabIterator = new TabIterator<Boolean>(tabbedPane)
        {
            @Override
            protected Boolean iterate(Component tab)
            {
                if (tab instanceof FocusToFirstComponent)
                {
                    ((FocusToFirstComponent) tab).setFocusToFirstComponent();
                }
                return true;
            }
        };
        tabIterator.iterate();
    }
}
