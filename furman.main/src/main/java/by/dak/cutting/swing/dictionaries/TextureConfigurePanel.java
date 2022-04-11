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

import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Manufacturer;
import by.dak.persistence.entities.PriceAware;
import by.dak.persistence.entities.PriceEntity;
import org.jdesktop.beansbinding.ELProperty;
import org.jdesktop.swingbinding.JTableBinding;

import javax.swing.*;
import java.text.NumberFormat;

/**
 * @author admin
 */
public class TextureConfigurePanel extends APricedCfgPanel<PriceAware>
{
    @Override
    protected void initColumnBindings(JTableBinding jTableBinding)
    {
        JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(ELProperty.create("${id}"));
        columnBinding.setColumnName(getResourceMap().getString("table.column.id"));
        columnBinding.setColumnClass(Long.class);
        columnBinding.setEditable(true);

        initTextureBinding(jTableBinding);

        columnBinding = jTableBinding.addColumnBinding(ELProperty.create("${priced.manufacturer}"));
        columnBinding.setColumnName(getResourceMap().getString("table.column.manufacturer"));
        columnBinding.setColumnClass(Manufacturer.class);
        columnBinding.setEditable(true);
    }

    private void initTextureBinding(JTableBinding jTableBinding)
    {
        JTableBinding.ColumnBinding columnBinding;
        columnBinding = jTableBinding.addColumnBinding(ELProperty.create("${priced.name}"));
        columnBinding.setColumnName(getResourceMap().getString("table.column.name"));
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(true);

//        columnBinding = jTableBinding.addColumnBinding(ELProperty.create("${priced.nameEnglish}"));
//        columnBinding.setColumnName(getResourceMap().getString("table.column.nameEnglish"));
//        columnBinding.setColumnClass(String.class);
//        columnBinding.setEditable(true);

        columnBinding = jTableBinding.addColumnBinding(ELProperty.create("${priced.surface}"));
        columnBinding.setColumnName(getResourceMap().getString("table.column.surface"));
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(true);

        columnBinding = jTableBinding.addColumnBinding(ELProperty.create("${priced.code}"));
        columnBinding.setColumnName(getResourceMap().getString("table.column.code"));
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(true);

        columnBinding = jTableBinding.addColumnBinding(ELProperty.create("${priced.groupIdentifier}"));
        columnBinding.setColumnName(getResourceMap().getString("table.column.groupIdentifier"));
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(true);

        columnBinding = jTableBinding.addColumnBinding(ELProperty.create("${priced.rotatable}"));
        columnBinding.setColumnName(getResourceMap().getString("table.column.rotatable"));
        columnBinding.setColumnClass(Boolean.class);
        columnBinding.setEditable(true);

        columnBinding = jTableBinding.addColumnBinding(ELProperty.create("${priced.inSize}"));
        columnBinding.setColumnName(getResourceMap().getString("table.column.isSize"));
        columnBinding.setColumnClass(Boolean.class);
        columnBinding.setEditable(true);
    }

    @Override
    protected String[] getHidenColumns()
    {
        return new String[]{"table.column.id", "table.column.surface"};
    }

    @Override
    protected void initTableGui()
    {
        super.initTableGui();
        final JFormattedTextField field = new JFormattedTextField(NumberFormat.getNumberInstance());
        getTablePrices().setDefaultEditor(Long.class, new DefaultCellEditor(field));
    }

    public PriceEntity createEmptyPrice()
    {
        PriceEntity priceEntity = new PriceEntity();
        priceEntity.setPriced(FacadeContext.getMaterialTypeHelper().newPricedBy(getValue()));
        priceEntity.setPriceAware(getValue());
        return priceEntity;
    }

    @Override
    protected int getFirstPriceColumn()
    {
        return 0;
    }
}
