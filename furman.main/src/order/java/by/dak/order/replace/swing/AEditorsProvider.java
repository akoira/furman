package by.dak.order.replace.swing;

import by.dak.cutting.swing.DComboBox;
import by.dak.persistence.entities.PriceAware;
import by.dak.persistence.entities.Priced;
import org.jdesktop.swingx.autocomplete.ComboBoxCellEditor;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Vector;

/**
 * User: akoyro
 * Date: 21.07.2010
 * Time: 11:21:22
 */
public abstract class AEditorsProvider<T extends PriceAware, C extends Priced>
{
    private DComboBox codeComboBox = new DComboBox();
    private DComboBox typeComboBox = new DComboBox();
    private ComboBoxCellEditor typeCellEditor;
    private ComboBoxCellEditor codeCellEditor;

    public AEditorsProvider()
    {
        init();
    }

    protected abstract List<T> getTypes();

    protected abstract T getCurrentTypeBy(int row);

    protected abstract List<C> getCodesBy(T type);


    private void init()
    {
        typeComboBox.setModel(new DefaultComboBoxModel(new Vector(getTypes())));
        codeCellEditor = new ComboBoxCellEditor(codeComboBox)
        {
            @Override
            public Component getTableCellEditorComponent(JTable
                                                                 table, Object value, boolean isSelected, int row, int column)
            {
                T type = getCurrentTypeBy(row);
                if (type != null)
                {
                    codeComboBox.setModel(new DefaultComboBoxModel(new Vector(getCodesBy(type))));
                }
                return super.getTableCellEditorComponent(table, value, isSelected, row, column);
            }
        };
        typeCellEditor = new ComboBoxCellEditor(typeComboBox);
    }

    public ComboBoxCellEditor getTypeCellEditor()
    {
        return typeCellEditor;
    }

    public ComboBoxCellEditor getCodeCellEditor()
    {
        return codeCellEditor;
    }
}
