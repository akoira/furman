package by.dak.cutting.swing.store.tabs;

import by.dak.cutting.swing.AEntityEditorTab;
import by.dak.cutting.swing.ItemSelector;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.AStoreElement;
import by.dak.persistence.entities.PriceAware;
import by.dak.persistence.entities.PriceEntity;
import by.dak.persistence.entities.Priced;
import by.dak.utils.BindingAdapter;
import org.jdesktop.beansbinding.Binding;

import java.awt.*;
import java.util.List;

/**
 * User: akoyro
 * Date: 12.03.2010
 * Time: 16:01:48
 */
public class AStoreElementTab<E extends AStoreElement> extends AEntityEditorTab<E>
{
    @Override
    public void init()
    {
        super.init();
        getBindingGroup().addValueBindingListener(new BindingAdapter()
        {
            @Override
            public void synced(Binding binding)
            {
                Binding.ValueResult valueResult = binding.getTargetValueForSource();
                if (getValue() == null || valueResult.failed())
                {
                    return;
                }

                //вставка цены из прайсов
                if (valueResult.getValue() instanceof PriceAware ||
                        valueResult.getValue() instanceof Priced)
                {
                    if (getValue().getPriceAware() != null && getValue().getPriced() != null)
                    {
                        final PriceEntity price = FacadeContext.getPriceFacade().findUniqueBy(getValue().getPriceAware(), getValue().getPriced());
                        if (price != null) //нельзя проверять на установленное значение - не будет сбрасывать.
                        {
                            getValue().setPrice(price.getPrice());
                        }
                        else
                        {
                            getValue().setPrice(null);
                        }

                    }
                }
            }
        });

        getBindingGroup().addBindingListener(new BindingAdapter()
        {
            @Override
            public void synced(Binding binding)
            {
                Binding.ValueResult valueResult = binding.getTargetValueForSource();
                if (getValue() == null || valueResult.failed())
                {
                    return;
                }

                //привязка Priced к PriceAware
                if (valueResult.getValue() instanceof PriceAware)
                {
                    List<? extends Priced> priceds = FacadeContext.getMaterialTypeHelper().findPricedsBy(getValue().getPriceAware());

                    for (String property : getCacheProperties().getEditableProperties())
                    {
                        Class aClass = getCacheProperties().getPropertyDescriptor(property).getPropertyType();

                        if (Priced.class.isAssignableFrom(aClass))
                        {
                            Component component = getEditors().get(property);
                            if (component instanceof ItemSelector)
                            {
                                ItemSelector selector = (ItemSelector) component;
                                selector.setItems(priceds);
                            }
                        }
                    }
                }

            }
        });
    }

}
