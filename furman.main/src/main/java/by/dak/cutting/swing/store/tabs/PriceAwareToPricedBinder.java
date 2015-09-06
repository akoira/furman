package by.dak.cutting.swing.store.tabs;

import by.dak.cutting.swing.ItemSelector;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.PriceAware;
import by.dak.persistence.entities.Priced;
import by.dak.swing.AValueTab;
import by.dak.utils.BindingAdapter;
import org.jdesktop.beansbinding.Binding;

import java.awt.*;
import java.util.List;

/**
 * User: akoyro
 * Date: 19.07.2010
 * Time: 21:13:26
 */
public class PriceAwareToPricedBinder extends BindingAdapter
{
    private AValueTab tab;

    public PriceAwareToPricedBinder(AValueTab tab)
    {
        this.tab = tab;
    }

    @Override
    public void synced(Binding binding)
    {
        Binding.ValueResult valueResult = binding.getTargetValueForSource();
        if (tab.getValue() == null || valueResult.failed())
        {
            return;
        }

        //привязка Priced к PriceAware
        if (valueResult.getValue() instanceof PriceAware)
        {
            List<? extends Priced> priceds = FacadeContext.getMaterialTypeHelper().findPricedsBy((PriceAware) valueResult.getValue());

            for (String property : tab.getCacheProperties().getEditableProperties())
            {
                Class aClass = tab.getCacheProperties().getPropertyDescriptor(property).getPropertyType();

                if (Priced.class.isAssignableFrom(aClass))
                {
                    Component component = (Component) tab.getEditors().get(property);
                    if (component instanceof ItemSelector)
                    {
                        ItemSelector selector = (ItemSelector) component;
                        selector.setItems(priceds);
                    }
                }
            }
        }

    }

}
