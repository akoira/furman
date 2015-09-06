package by.dak.cutting.swing.order;

import by.dak.cutting.swing.order.popup.AbstractSideMenu;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;
import com.jidesoft.swing.JideButton;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * User: akoyro
 * Date: 21.04.2009
 * Time: 17:04:34
 */
public abstract class AbstractPopupPanel<E, Item> extends JPanel implements ViewControl<E, Item>
{
    protected JButton button;
    private TableCellEditor tableCellEditor;

    protected void initComponents()
    {
        button = new JideButton();
        getSideMenu().setComponentToRefresh(button);
        Action action = new AbstractAction("NO")
        {
            public void actionPerformed(ActionEvent e)
            {
                showPopup();
            }
        };
        getButton().setAction(action);
    }


    public JPanel buildView()
    {
        FormLayout layout = new FormLayout("25dlu:grow", "10dlu:grow");
        DefaultFormBuilder builder = new DefaultFormBuilder(layout);
        builder.append(getButton());
        return builder.getPanel();
    }

    public JComponent getFocusedComponent()
    {
        return getButton();
    }

    protected void showPopup()
    {
        Insets insents = getButton().getInsets();
        insents.set(insents.top, insents.left, 0, insents.right);
        getSideMenu().showPopup(insents, button);//show(button, 0, button.getHeight());
    }

    public abstract AbstractSideMenu getSideMenu();


    public JButton getButton()
    {
        return button;
    }

    public TableCellEditor getTableCellEditor()
    {
        return tableCellEditor;
    }


    public void setTableCellEditor(TableCellEditor tableCellEditor)
    {
        this.tableCellEditor = tableCellEditor;
    }
}
