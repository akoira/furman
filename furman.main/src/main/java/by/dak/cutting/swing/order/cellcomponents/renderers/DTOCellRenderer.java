package by.dak.cutting.swing.order.cellcomponents.renderers;

import by.dak.cutting.swing.order.SideCanvas;
import by.dak.cutting.swing.order.data.DTO;
import by.dak.cutting.swing.order.data.DefaultDTO;
import com.jidesoft.swing.JideButton;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.ArrayList;


/**
 * @depricated todo должен использоватся ButtonCellRenderer из swing пакета
 */

public class DTOCellRenderer implements TableCellRenderer
{

    private java.util.List<JButton> renderers = new ArrayList<JButton>();
    private boolean needIcon;

    public DTOCellRenderer(boolean needIcon)
    {
        this.needIcon = needIcon;
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        JButton rend;
        if (renderers.size() > row)
        {
            if (renderers.get(row) != null)
            {
                rend = renderers.get(row);
            }
            else
            {
                rend = createButton();
                renderers.set(row, rend);
            }
        }
        else
        {
            while (renderers.size() <= row)
            {
                rend = createButton();
                renderers.add(rend);
            }
            rend = renderers.get(row);
        }
        DTO dto = (DTO) value;
        refreshComponent(dto, rend);
        if (rend != null)
        {
            rend.setSelected(hasFocus);
        }
        return rend;
    }

    private JButton createButton()
    {
        if (needIcon)
        {
            SideCanvas icon = new SideCanvas(2, 2);
            JButton button = new JideButton(icon);
            button.setRolloverIcon(icon);
            button.setRolloverSelectedIcon(icon);
            return button;
        }
        else
        {
            return new JideButton("NO");
        }
    }

    protected void refreshComponent(DTO dto, JButton button)
    {
        Icon icon = button.getIcon();
        if (icon == null)
        {
            button.setText(dto == null || StringUtils.isBlank(dto.getPicName()) ? "NO" : "Yes");
        }
        else if (icon instanceof SideCanvas)
        {
            SideCanvas canvas = (SideCanvas) icon;
            if (dto == null)
            {
                //empty dto;
                dto = new DefaultDTO();
            }
            canvas.setDown(dto.isDown());
            canvas.setUp(dto.isUp());
            canvas.setLeft(dto.isLeft());
            canvas.setRight(dto.isRight());
        }
        button.repaint();
    }
}
