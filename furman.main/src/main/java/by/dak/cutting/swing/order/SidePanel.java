package by.dak.cutting.swing.order;

import by.dak.cutting.swing.order.data.OrderDetailsDTO;
import by.dak.cutting.swing.order.popup.CommonSideMenu;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;
import com.jidesoft.swing.JideButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SidePanel<E> extends JPanel implements ViewControl<OrderDetailsDTO, OrderDetailsDTO>
{

    private JButton button;
    private CommonSideMenu popupMenu;
    private OrderDetailsDTO data;

    public SidePanel(CommonSideMenu popupMenu)
    {
        this.popupMenu = popupMenu;
        initComponents();
    }

    public JPanel buildView()
    {
        FormLayout layout = new FormLayout("25dlu:grow", "10dlu:grow");
        DefaultFormBuilder builder = new DefaultFormBuilder(layout);
        builder.append(button);
        return builder.getPanel();
    }

    public JComponent getFocusedComponent()
    {
        return button;
    }

    private void initComponents()
    {
        button = new JideButton(new SideCanvas(2, 2));
//       button.setRolloverEnabled(false);
        popupMenu.setComponentToRefresh(button);
        button.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                showPopup();
            }
        });
    }

    private void showPopup()
    {
        Insets insents = button.getInsets();
        insents.set(insents.top, insents.left, 0, insents.right);
        popupMenu.showPopup(insents, button);//show(button, 0, button.getHeight());
    }

    public OrderDetailsDTO getData()
    {
        return this.data;
    }

    public void setData(OrderDetailsDTO data)
    {
        this.data = data;
        popupMenu.setData(this.data);
    }

    public OrderDetailsDTO getSelectedItem()
    {
        return data;
    }
}
