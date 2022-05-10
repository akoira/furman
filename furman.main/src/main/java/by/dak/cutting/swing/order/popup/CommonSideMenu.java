package by.dak.cutting.swing.order.popup;

import by.dak.cutting.swing.order.SideCanvas;
import by.dak.cutting.swing.order.data.DTO;
import by.dak.cutting.swing.order.data.OrderDetailsDTO;
import by.dak.persistence.MainFacade;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.table.TableCellEditor;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Map;

public abstract class CommonSideMenu extends AbstractSideMenu implements ItemListener
{
    protected JComponent canvas;
    protected JCheckBox upChBox;
    protected JCheckBox downChBox;
    protected JCheckBox leftChBox;
    protected JCheckBox rightChBox;
    private String UP;
    private String DOWN;
    private String LEFT;
    private String RIGHT;
    protected OrderDetailsDTO data;
    public Map<JCheckBox, JComponent[]> rules;
    protected JLabel upLabel;
    protected JLabel downLabel;
    protected JLabel rightLabel;
    protected JLabel leftLabel;


    public CommonSideMenu(TableCellEditor tableCellEditor)
    {
        super(tableCellEditor);
    }

    protected void initComponents()
    {
        loadBundle();
        upLabel = new JLabel(UP);
        upLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        downLabel = new JLabel(DOWN);
        downLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        rightLabel = new JLabel(RIGHT);
        rightLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        leftLabel = new JLabel(LEFT);
        leftLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        upChBox = new JCheckBox();
        upChBox.setName(UP);
        downChBox = new JCheckBox();
        downChBox.setName(DOWN);
        leftChBox = new JCheckBox();
        leftChBox.setName(LEFT);
        rightChBox = new JCheckBox();
        rightChBox.setName(RIGHT);

        canvas = new SideCanvas();
        upChBox.addItemListener(this);
        downChBox.addItemListener(this);
        leftChBox.addItemListener(this);
        rightChBox.addItemListener(this);
    }

    protected void loadBundle()
    {
        LEFT = getResourceBundle().getString("left");
        RIGHT = getResourceBundle().getString("right");
        UP = getResourceBundle().getString("up");
        DOWN = getResourceBundle().getString("down");
    }


    /**
     * в супер классе зовется при showpopup каждый раз
     * установлен флаг
     */

    protected void buildView()
    {
        FormLayout layout = new FormLayout("35dlu, 2dlu, 35dlu",
                "10dlu, 3dlu, 10dlu, 3dlu, 10dlu, 3dlu, 10dlu, 3dlu, 40dlu, 3dlu, 20 dlu");
        DefaultFormBuilder builder = new DefaultFormBuilder(layout/*, new FormDebugPanel()*/);
        builder.append(upLabel);
        builder.append(upChBox);
        builder.nextRow();
        builder.append(leftLabel);
        builder.append(leftChBox);
        builder.nextRow();
        builder.append(rightLabel);
        builder.append(rightChBox);
        builder.nextRow();
        builder.append(downLabel);
        builder.append(downChBox);
        builder.nextRow();
        builder.append(canvas, 3);
        builder.nextRow();
        builder.append(new JLabel());
        builder.append(getOkButton());
        JPanel panel = builder.getPanel();
        panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        add(panel);
    }

    public void setData(Object data)
    {
        this.data = (OrderDetailsDTO) data;
    }

    public OrderDetailsDTO getData()
    {
        return data;
    }


    public void updateData()
    {
        getDTO().setUp(upChBox.isSelected());
        getDTO().setDown(downChBox.isSelected());
        getDTO().setLeft(leftChBox.isSelected());
        getDTO().setRight(rightChBox.isSelected());
        refreshComponent();
    }

    protected void refreshComponent()
    {
        if (getComponentToRefresh() == null)
            return;
        SideCanvas icon = ((SideCanvas) ((JButton) getComponentToRefresh()).getIcon());
        icon.setDown(getDTO().isDown());
        icon.setUp(getDTO().isUp());
        icon.setLeft(getDTO().isLeft());
        icon.setRight(getDTO().isRight());
        getComponentToRefresh().repaint();
    }

    public void itemStateChanged(ItemEvent e)
    {
        AbstractButton abstractButton =
                (AbstractButton) e.getSource();
        ButtonModel buttonModel = abstractButton.getModel();

        if (buttonModel.isSelected())
        {
            if (abstractButton.getName().equalsIgnoreCase(UP))
            {
                ((SideCanvas) canvas).setUp(true);
            }
            else if (abstractButton.getName().equalsIgnoreCase(DOWN))
            {
                ((SideCanvas) canvas).setDown(true);
            }
            else if (abstractButton.getName().equalsIgnoreCase(RIGHT))
            {
                ((SideCanvas) canvas).setRight(true);
            }
            else if (abstractButton.getName().equalsIgnoreCase(LEFT))
            {
                ((SideCanvas) canvas).setLeft(true);
            }
        }
        else
        {
            if (abstractButton.getName().equalsIgnoreCase(UP))
            {
                ((SideCanvas) canvas).setUp(false);
            }
            else if (abstractButton.getName().equalsIgnoreCase(DOWN))
            {
                ((SideCanvas) canvas).setDown(false);
            }
            else if (abstractButton.getName().equalsIgnoreCase(RIGHT))
            {
                ((SideCanvas) canvas).setRight(false);
            }
            else if (abstractButton.getName().equalsIgnoreCase(LEFT))
            {
                ((SideCanvas) canvas).setLeft(false);
            }
        }
        refreshCompState();
    }

    protected void createRules()
    {
    }

    protected void refreshCompState()
    {
    }

    protected void flushComponentValues()
    {
        upChBox.setSelected(false);
        downChBox.setSelected(false);
        leftChBox.setSelected(false);
        rightChBox.setSelected(false);
        ((SideCanvas) canvas).flushSides();
        refreshCompState();
    }

    protected void mergeCompAndValues()
    {
        upChBox.setSelected(getDTO().isUp());
        downChBox.setSelected(getDTO().isDown());
        leftChBox.setSelected(getDTO().isLeft());
        rightChBox.setSelected(getDTO().isRight());
    }

    protected void beforeVisible()
    {
        updateComponentValues();
    }

    protected void updateComponentValues()
    {
        mergeCompAndValues();
        refreshCompState();
    }

    protected abstract DTO getDTO();

}
