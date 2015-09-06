package by.dak.swing.table;

import by.dak.cutting.swing.DPanel;
import by.dak.cutting.swing.component.MaskTextField;
import by.dak.utils.BindingUtils;

import javax.swing.*;
import java.awt.*;

/**
 * User: akoyro
 * Date: 29.11.2010
 * Time: 16:10:38
 */
public class TotalPanel extends DPanel
{
    private ListUpdater listUpdater;


    public void init()
    {
        this.removeAll();
        this.setLayout(new GridLayout(2,getListUpdater().getTotalProperties().length, 5,5));

        for (int i = 0; i < getListUpdater().getTotalProperties().length; i++)
        {
            String property = getListUpdater().getTotalProperties()[i];
            JLabel jLabel = new JLabel(getListUpdater().getResourceMap().getString(BindingUtils.PREFIX_TABLE_COLUMN_KEY + property));
            jLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            add(jLabel);
            MaskTextField field = new MaskTextField();
            field.setEditable(false);
            field.setValue(getListUpdater().getTotalValue(property));
            add(field);
        }

    }

    public ListUpdater getListUpdater()
    {
        return listUpdater;
    }

    public void setListUpdater(ListUpdater listUpdater)
    {
        this.listUpdater = listUpdater;
    }
}
