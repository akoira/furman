package by.dak.cutting.zfacade.swing.tree;

import by.dak.cutting.swing.dictionaries.FurnitureCodeCfgPanel;
import by.dak.cutting.zfacade.ZProfileColor;
import by.dak.cutting.zfacade.ZProfileType;
import by.dak.persistence.entities.types.FurnitureCode;
import by.dak.utils.BindingUtils;
import org.jdesktop.beansbinding.ELProperty;
import org.jdesktop.swingbinding.JTableBinding;

/**
 * User: akoyro
 * Date: 20.07.2010
 * Time: 9:54:27
 */
public class ZProfileCodeCfgPanel extends FurnitureCodeCfgPanel<ZProfileType>
{

    @Override
    protected void initColumnEditors()
    {
        if (getValue().getRubberType() != null)
            initFurnitureCodeEditor(BindingUtils.PREFIX_TABLE_COLUMN_KEY + ZProfileColor.PROPERTY_rubberCode, getValue().getRubberType());
        if (getValue().getAngleType() != null)
            initFurnitureCodeEditor(BindingUtils.PREFIX_TABLE_COLUMN_KEY + ZProfileColor.PROPERTY_angleCode, getValue().getAngleType());
    }

    @Override
    protected void initFurnitureCodeBinding(JTableBinding jTableBinding)
    {
        super.initFurnitureCodeBinding(jTableBinding);

        JTableBinding.ColumnBinding columnBinding;
        columnBinding = jTableBinding.addColumnBinding(ELProperty.create("${priced.offsetLength}"));
        columnBinding.setColumnName(getResourceMap().getString("table.column.offsetLength"));
        columnBinding.setColumnClass(Double.class);
        columnBinding.setEditable(true);

        columnBinding = jTableBinding.addColumnBinding(ELProperty.create("${priced.offsetWidth}"));
        columnBinding.setColumnName(getResourceMap().getString("table.column.offsetWidth"));
        columnBinding.setColumnClass(Double.class);
        columnBinding.setEditable(true);

        columnBinding = jTableBinding.addColumnBinding(ELProperty.create("${priced." + ZProfileColor.PROPERTY_angleCode + "}"));
        columnBinding.setColumnName(getResourceMap().getString("table.column." + ZProfileColor.PROPERTY_angleCode));
        columnBinding.setColumnClass(FurnitureCode.class);
        columnBinding.setEditable(true);

        columnBinding = jTableBinding.addColumnBinding(ELProperty.create("${priced." + ZProfileColor.PROPERTY_rubberCode + "}"));
        columnBinding.setColumnName(getResourceMap().getString("table.column." + ZProfileColor.PROPERTY_rubberCode));
        columnBinding.setColumnClass(FurnitureCode.class);
        columnBinding.setEditable(true);
    }

}
