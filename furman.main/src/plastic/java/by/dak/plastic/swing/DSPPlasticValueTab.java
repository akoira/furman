package by.dak.plastic.swing;

import by.dak.cutting.swing.ItemSelector;
import by.dak.cutting.swing.order.data.TextureBoardDefPair;
import by.dak.cutting.swing.store.Constants;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.PriceAware;
import by.dak.persistence.entities.Priced;
import by.dak.swing.AValueTab;
import by.dak.utils.BindingAdapter;
import org.jdesktop.beansbinding.Binding;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 25.09.11
 * Time: 10:51
 * To change this template use File | Settings | File Templates.
 */
public class DSPPlasticValueTab extends AValueTab<DSPPlasticValue>
{
    public DSPPlasticValueTab()
    {
        setVisibleProperties(new String[]{
                DSPPlasticValue.PROPERTY_dspPair + '.' + TextureBoardDefPair.PROPERTY_boardDef,
                DSPPlasticValue.PROPERTY_dspPair + '.' + TextureBoardDefPair.PROPERTY_texture,
        });
        setCacheEditorCreator(Constants.getEntityEditorCreators(TextureBoardDefPair.class));
    }

    @Override
    public void init()
    {
        super.init();
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

//                if (binding.getName().equals(DSPPlasticValue.PROPERTY_needSecondSide))
//                {
//                    getEditors().get(DSPPlasticValue.PROPERTY_secondSidePair + '.' + TextureBoardDefPair.PROPERTY_boardDef).setEnabled(getValue().isNeed());
//                    getEditors().get(DSPPlasticValue.PROPERTY_secondSidePair + '.' + TextureBoardDefPair.PROPERTY_texture).setEnabled(getValue().isNeed());
//                }

                if (binding.getName().equals(DSPPlasticValue.PROPERTY_dspPair + '.' + TextureBoardDefPair.PROPERTY_boardDef))
                {
                    List<? extends Priced> priceds = FacadeContext.getMaterialTypeHelper().findPricedsBy((PriceAware) valueResult.getValue());
                    ((ItemSelector) getEditors().get(DSPPlasticValue.PROPERTY_dspPair + '.' + TextureBoardDefPair.PROPERTY_texture)).setItems(priceds);
                }

//                if (binding.getName().equals(DSPPlasticValue.PROPERTY_secondSidePair + '.' + TextureBoardDefPair.PROPERTY_boardDef))
//                {
//                    List<? extends Priced> priceds = FacadeContext.getMaterialTypeHelper().findPricedsBy((PriceAware) valueResult.getValue());
//                    ((ItemSelector) getEditors().get(DSPPlasticValue.PROPERTY_secondSidePair + '.' + TextureBoardDefPair.PROPERTY_texture)).setItems(priceds);
//                }
            }
        });
    }
}
