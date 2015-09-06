/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

/*
 * TextureConfigurePanel.java
 *
 * Created on 03.06.2009, 20:41:34
 */

package by.dak.cutting.swing.dictionaries;

import by.dak.cutting.afacade.AProfileColor;
import by.dak.cutting.afacade.facade.AProfileColorFacade;
import by.dak.cutting.swing.DComboBox;
import by.dak.cutting.swing.renderer.EntityListRenderer;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Manufacturer;
import by.dak.persistence.entities.PriceEntity;
import by.dak.persistence.entities.types.FurnitureCode;
import by.dak.persistence.entities.types.FurnitureType;
import org.jdesktop.beansbinding.ELProperty;
import org.jdesktop.swingbinding.JTableBinding;
import org.jdesktop.swingx.autocomplete.ComboBoxCellEditor;
import org.jdesktop.swingx.table.TableColumnExt;

import javax.swing.*;
import java.util.List;
import java.util.Vector;

/**
 * @author admin
 */
public class FurnitureCodeCfgPanel<F extends FurnitureType> extends APricedCfgPanel<F>
{
    @Override
    protected void initColumnBindings(JTableBinding jTableBinding)
    {
        JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(ELProperty.create("${id}"));
        columnBinding.setColumnName(getResourceMap().getString("table.column.id"));
        columnBinding.setColumnClass(Long.class);
        columnBinding.setEditable(true);

        initFurnitureCodeBinding(jTableBinding);

        columnBinding = jTableBinding.addColumnBinding(ELProperty.create("${priced.manufacturer}"));
        columnBinding.setColumnName(getResourceMap().getString("table.column.manufacturer"));
        columnBinding.setColumnClass(Manufacturer.class);
        columnBinding.setEditable(true);
    }

    protected void initFurnitureCodeBinding(JTableBinding jTableBinding)
    {
        JTableBinding.ColumnBinding columnBinding;
        columnBinding = jTableBinding.addColumnBinding(ELProperty.create("${priced.name}"));
        columnBinding.setColumnName(getResourceMap().getString("table.column.name"));
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(true);

        columnBinding = jTableBinding.addColumnBinding(ELProperty.create("${priced.code}"));
        columnBinding.setColumnName(getResourceMap().getString("table.column.code"));
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(true);
    }

    protected void initFurnitureCodeEditor(String columnKey, FurnitureType furnitureType)
    {
        if (furnitureType == null)
        {
            return;
        }
        TableColumnExt tableColumnExt = getColumnExtBy(columnKey);
        List<FurnitureCode> priceds = FacadeContext.getFurnitureCodeFacade().findBy(furnitureType);
        DComboBox comboBox = new DComboBox();
        comboBox.setModel(new DefaultComboBoxModel(new Vector(priceds)));
        comboBox.setRenderer(new EntityListRenderer());
        tableColumnExt.setCellEditor(new ComboBoxCellEditor(comboBox));
    }


    @Override
    protected String[] getHidenColumns()
    {
        return new String[]{"table.column.id"};
    }

    @Override
    protected int getFirstPriceColumn()
    {
        return 0;
    }


    @Override
    public void setPrices(List<PriceEntity> list)
    {
        for (PriceEntity priceEntity : list)
        {
            if (priceEntity.getPriced() instanceof AProfileColor)
            {
                ((AProfileColorFacade) FacadeContext.getFacadeBy(priceEntity.getPriced().getClass())).fillChildTypes((AProfileColor) priceEntity.getPriced());
            }
        }
        super.setPrices(list);
    }
}