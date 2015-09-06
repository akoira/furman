package by.dak.cutting.swing.store.tabs;

import by.dak.persistence.entities.Furniture;
import by.dak.persistence.entities.PriceAware;
import by.dak.utils.BindingAdapter;
import org.jdesktop.beansbinding.Binding;

/**
 * Created by IntelliJ IDEA.
 * User: user0
 * Date: 29.01.2010
 * Time: 14:43:07
 * To change this template use File | Settings | File Templates.
 */
public class FurnitureTab extends AStoreElementTab<Furniture>
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
                if (valueResult.getValue() instanceof PriceAware)
                {
                    if (getValue().getPriceAware() != null)
                    {
                        getValue().setSize(getValue().getFurnitureType().getDefaultSize());
                        getValue().setUnit(getValue().getFurnitureType().getUnit());
                    }
                }
            }
        });
    }
}
