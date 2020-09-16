package by.dak.cutting.swing.struct.tree;

import by.dak.cutting.swing.order.cellcomponents.editors.CheckBoxCellEditor;
import by.dak.cutting.swing.order.cellcomponents.renderers.CheckBoxCellRenderer;
import by.dak.cutting.swing.tree.EntityListUpdater;
import by.dak.cutting.swing.tree.EntityNEDActions;
import by.dak.persistence.entities.Customer;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class CustomerListUpdater extends EntityListUpdater<Customer> {

    public CustomerListUpdater() {
        ResourceMap map = Application.getInstance().getContext().getResourceMap(Customer.class);
        setResourceMap(map);
        setVisibleProperties(map.getString("table.visible.properties").split(","));
        setElementClass(Customer.class);
        EntityNEDActions actions = new EntityNEDActions(Customer.class) {
            @Override
            public void newValue() {
//                super.newValue();
            }
        };
        this.setNewEditDeleteActions(actions);

    }

    @Override
    public TableCellRenderer getTableCellRenderer(String propertyName) {
        switch (propertyName) {
            case "deleted":
                return new CheckBoxCellRenderer();
            default:
                return super.getTableCellRenderer(propertyName);
        }
    }

    @Override
    public TableCellEditor getTableCellEditor(String propertyName) {
        switch (propertyName) {
            case "deleted":
                return new CheckBoxCellEditor();
            default:
                return super.getTableCellEditor(propertyName);
        }
    }
}
