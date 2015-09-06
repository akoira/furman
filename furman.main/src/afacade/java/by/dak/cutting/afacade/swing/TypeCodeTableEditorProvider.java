package by.dak.cutting.afacade.swing;

import by.dak.cutting.afacade.AFacade;
import by.dak.cutting.facade.PriceAwareFacade;
import by.dak.cutting.facade.PricedFacade;
import by.dak.cutting.swing.DComboBox;
import by.dak.persistence.entities.types.FurnitureType;
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
public class TypeCodeTableEditorProvider<F extends AFacade>
{

    private PricedFacade pricedFacade;
    private PriceAwareFacade priceAwareFacade;
    private DComboBox codeComboBox = new DComboBox();
    private DComboBox typeComboBox = new DComboBox();
    private ComboBoxCellEditor typeCellEditor;
    private ComboBoxCellEditor codeCellEditor;
    private List<F> values;

    public TypeCodeTableEditorProvider(List<F> values, PriceAwareFacade priceAwareFacade, PricedFacade pricedFacade)
    {
        this.values = values;
        this.pricedFacade = pricedFacade;
        this.priceAwareFacade = priceAwareFacade;
        init();
    }


    private void init()
    {
        if (priceAwareFacade == null)
            return;
        typeComboBox.setModel(new DefaultComboBoxModel(new Vector(priceAwareFacade.loadAll())));
        codeCellEditor = new ComboBoxCellEditor(codeComboBox)
        {
            @Override
            public Component getTableCellEditorComponent(JTable
                                                                 table, Object value, boolean isSelected, int row, int column)
            {
                F furniture = values.get(row);
                if (furniture != null)
                {
                    FurnitureType type = furniture.getProfileType();
                    if (type != null)
                    {
                        codeComboBox.setModel(new DefaultComboBoxModel(new Vector(pricedFacade.findBy(furniture.getProfileType()))));
                    }
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
