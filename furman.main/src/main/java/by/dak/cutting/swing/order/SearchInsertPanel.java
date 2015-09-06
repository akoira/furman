package by.dak.cutting.swing.order;

import by.dak.cutting.swing.DComboBox;
import by.dak.cutting.swing.DPanel;
import by.dak.cutting.swing.order.data.OrderDetailsDTO;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;
import com.jidesoft.swing.JideButton;

import javax.swing.*;
import java.awt.event.ActionEvent;


public class SearchInsertPanel extends DPanel implements ViewControl<OrderDetailsDTO, Object>
{

    protected DComboBox dComboBox;
    protected JButton button;
    protected JDialog dialog;
    protected OrderDetailsDTO data;
    private boolean isCallNew;

    public SearchInsertPanel(boolean isRenderer)
    {
        initComponents();
        if (!isRenderer)
            initEnvironment();
    }

    public JPanel buildView()
    {
        FormLayout layout = new FormLayout("25dlu:grow, 1dlu, 17dlu", "10dlu:grow");
        DefaultFormBuilder builder = new DefaultFormBuilder(layout);
        builder.append(dComboBox);
        builder.append(button);
        return builder.getPanel();
    }


    protected void initComponents()
    {
        dComboBox = new DComboBox();
        button = new JideButton(">>");
    }

    protected void initEnvironment()
    {
        //todo: add to resources
        Action action = new AbstractAction(">>")
        {
            public void actionPerformed(ActionEvent e)
            {
                showDialog();
            }
        };
        button.setAction(action);

    }

    protected void bind()
    {

    }

    protected void showDialog()
    {

    }

    public JComponent getFocusedComponent()
    {
        return dComboBox;
    }


    public Object getSelectedItem()
    {
        return dComboBox.getSelectedItem();
    }

    public void setData(OrderDetailsDTO data)
    {
        this.data = data;
        bind();
    }

    public OrderDetailsDTO getData()
    {
        return data;
    }

    public DComboBox getDComboBox()
    {
        return dComboBox;
    }

    public void setEnable(boolean isEnable)
    {
        button.setEnabled(isEnable);
        dComboBox.setEnabled(isEnable);
    }

    protected boolean isAlreadyContains(Object ob)
    {
        boolean isContains = false;
        for (int i = 0; i < dComboBox.getItemCount(); i++)
        {
            if (dComboBox.getItemAt(i).equals(ob))
            {
                return true;
            }
        }
        return isContains;
    }

    public boolean isCallNew()
    {
        return isCallNew;
    }

    public void setCallNew(boolean callNew)
    {
        isCallNew = callNew;
    }

}
