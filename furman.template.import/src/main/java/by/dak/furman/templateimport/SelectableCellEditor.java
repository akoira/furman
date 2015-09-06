package by.dak.furman.templateimport;

import javax.swing.*;

/**
 * User: akoyro
 * Date: 10/7/13
 * Time: 11:19 PM
 */
public class SelectableCellEditor extends DefaultCellEditor
{
    public SelectableCellEditor()
    {
        super(new JCheckBox());
        JCheckBox checkBox = (JCheckBox) getComponent();
        checkBox.setHorizontalAlignment(SwingConstants.CENTER);
    }

}
