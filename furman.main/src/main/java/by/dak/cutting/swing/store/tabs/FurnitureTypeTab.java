package by.dak.cutting.swing.store.tabs;

import by.dak.cutting.swing.AEntityEditorTab;
import by.dak.persistence.entities.predefined.Unit;
import by.dak.persistence.entities.types.FurnitureType;
import by.dak.utils.BindingAdapter;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.PropertyStateEvent;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 06.01.2010
 * Time: 12:29:29
 * To change this template use File | Settings | File Templates.
 */
public class FurnitureTypeTab extends AEntityEditorTab<FurnitureType>
{
    @Override
    public void init()
    {
        super.init();

        getBindingGroup().getBinding("unit").addBindingListener(new BindingAdapter()
        {

            /**
             * при инициализации таба
             * @param binding
             */
            @Override
            public void synced(Binding binding)
            {
                if (getValue() != null)
                {
                    setEnableEditor(getValue().getUnit());
                }
            }

            /**
             * при смене элемента в comboBox
             * @param binding
             * @param event
             */
            @Override
            public void targetChanged(Binding binding, PropertyStateEvent event)
            {
                setEnableEditor(getValue().getUnit());
            }
        });
    }

    /**
     * enable/disable в зависимости от выбранного типа материала
     *
     * @param unit
     */
    private void setEnableEditor(Unit unit)
    {
        switch (unit)
        {
            case squareMetre:
                getEditors().get("cutter").setEnabled(true);
                break;
            case linearMetre:
                getEditors().get("cutter").setEnabled(true);
                break;
            default:
                getEditors().get("cutter").setEnabled(false);
                break;

        }
    }
}
