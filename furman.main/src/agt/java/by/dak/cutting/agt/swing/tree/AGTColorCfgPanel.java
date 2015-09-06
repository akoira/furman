package by.dak.cutting.agt.swing.tree;

import by.dak.cutting.agt.AGTColor;
import by.dak.cutting.agt.AGTType;
import by.dak.cutting.swing.dictionaries.FurnitureCodeCfgPanel;
import by.dak.persistence.entities.types.FurnitureCode;
import by.dak.utils.BindingUtils;
import org.jdesktop.beansbinding.ELProperty;
import org.jdesktop.swingbinding.JTableBinding;

/**
 * User: akoyro
 * Date: 20.07.2010
 * Time: 9:54:27
 */
public class AGTColorCfgPanel extends FurnitureCodeCfgPanel<AGTType>
{
    @Override
    protected void initFurnitureCodeBinding(JTableBinding jTableBinding)
    {
        super.initFurnitureCodeBinding(jTableBinding);

        JTableBinding.ColumnBinding columnBinding;

        columnBinding = jTableBinding.addColumnBinding(ELProperty.create("${priced.groupIdentifier}"));
        columnBinding.setColumnName(getResourceMap().getString("table.column.groupIdentifier"));
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(true);

        columnBinding = jTableBinding.addColumnBinding(ELProperty.create("${priced.offsetLength}"));
        columnBinding.setColumnName(getResourceMap().getString("table.column.offsetLength"));
        columnBinding.setColumnClass(Double.class);
        columnBinding.setEditable(true);

        columnBinding = jTableBinding.addColumnBinding(ELProperty.create("${priced.offsetWidth}"));
        columnBinding.setColumnName(getResourceMap().getString("table.column.offsetWidth"));
        columnBinding.setColumnClass(Double.class);
        columnBinding.setEditable(true);

        columnBinding = jTableBinding.addColumnBinding(ELProperty.create("${priced." + AGTColor.PROPERTY_rubberCode + "}"));
        columnBinding.setColumnName(getResourceMap().getString("table.column." + AGTColor.PROPERTY_rubberCode));
        columnBinding.setColumnClass(FurnitureCode.class);
        columnBinding.setEditable(true);

        columnBinding = jTableBinding.addColumnBinding(ELProperty.create("${priced." + AGTColor.PROPERTY_glueCode + "}"));
        columnBinding.setColumnName(getResourceMap().getString("table.column." + AGTColor.PROPERTY_glueCode));
        columnBinding.setColumnClass(FurnitureCode.class);
        columnBinding.setEditable(true);

        columnBinding = jTableBinding.addColumnBinding(ELProperty.create("${priced." + AGTColor.PROPERTY_dowelCode + "}"));
        columnBinding.setColumnName(getResourceMap().getString("table.column." + AGTColor.PROPERTY_dowelCode));
        columnBinding.setColumnClass(FurnitureCode.class);
        columnBinding.setEditable(true);

    }

    @Override
    protected void initColumnEditors()
    {
        if (getValue().getRubberType() != null)
            initFurnitureCodeEditor(BindingUtils.PREFIX_TABLE_COLUMN_KEY + AGTColor.PROPERTY_rubberCode, getValue().getRubberType());
        if (getValue().getGlueType() != null)
            initFurnitureCodeEditor(BindingUtils.PREFIX_TABLE_COLUMN_KEY + AGTColor.PROPERTY_glueCode, getValue().getGlueType());
        if (getValue().getDowelType() != null)
            initFurnitureCodeEditor(BindingUtils.PREFIX_TABLE_COLUMN_KEY + AGTColor.PROPERTY_dowelCode, getValue().getDowelType());
    }

}
