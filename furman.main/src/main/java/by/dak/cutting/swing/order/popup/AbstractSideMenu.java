package by.dak.cutting.swing.order.popup;

import com.jidesoft.popup.JidePopup;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ResourceBundle;

/**
 * User: akoyro
 * Date: 21.04.2009
 * Time: 23:07:45
 */
public abstract class AbstractSideMenu extends JidePopup
{
    /**
     * Текущее открытая Popup
     */
    public static JidePopup currentPopup = null;

    private TableCellEditor tableCellEditor;
    private ResourceBundle resourceBundle = java.util.ResourceBundle.getBundle("by/dak/cutting/swing/order/resources/OrderDetailsPanel");
    private Action okAction;
    private Action exitAction;
    private JButton okButton;
    private JComponent component;


    public AbstractSideMenu(TableCellEditor tableCellEditor)
    {
        this.tableCellEditor = tableCellEditor;
        okButton = new JButton();
        initComponents();
        initListeners();
        buildView();
        setFocusable(true);
        setDefaultFocusComponent(getOkButton());
        createRules();
    }


    public TableCellEditor getTableCellEditor()
    {
        return tableCellEditor;
    }

    public ResourceBundle getResourceBundle()
    {
        return resourceBundle;
    }

    protected abstract void initComponents();

    protected void initListeners()
    {
        okAction = new AbstractAction("OK")
        {
            public void actionPerformed(ActionEvent e)
            {
                updateData();
                hidePopup();
                getTableCellEditor().stopCellEditing();
            }
        };
        exitAction = new AbstractAction("")
        {
            public void actionPerformed(ActionEvent e)
            {
                hidePopupImmediately(true);
                Component owner = getActualOwner();
                if (owner != null)
                {
                    owner.requestFocus();
                }
                getTableCellEditor().cancelCellEditing();
            }
        };
        getOkButton().setAction(okAction);
        addPopupMenuListener(new PopupMenuListener()
        {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e)
            {
                beforeVisible();
                getOkButton().requestFocus();
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e)
            {

            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e)
            {

            }
        });

        registerKeyboardAction(exitAction, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        registerKeyboardAction(okAction, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public JButton getOkButton()
    {
        return okButton;
    }

    @Override
    public void showPopup(Insets insets, Component owner)
    {
        if (currentPopup != null)
        {
            currentPopup.hidePopupImmediately();
        }
        currentPopup = this;
        super.showPopup(insets, owner);
    }


    protected abstract void buildView();

    protected abstract void updateData();

    protected abstract void createRules();

    protected abstract void refreshCompState();

    protected abstract void flushComponentValues();

    protected abstract void beforeVisible();


    public void setComponentToRefresh(JComponent component)
    {
        this.component = component;
    }

    public JComponent getComponentToRefresh()
    {
        return this.component;
    }

}
