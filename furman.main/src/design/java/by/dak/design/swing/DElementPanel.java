package by.dak.design.swing;

import by.dak.cutting.swing.ItemSelector;
import by.dak.cutting.swing.store.Constants;
import by.dak.cutting.swing.store.tabs.PriceAwareToPricedBinder;
import by.dak.design.draw.components.BoardElement;
import by.dak.swing.AValueTab;
import by.dak.utils.BindingAdapter;
import org.jdesktop.beansbinding.Binding;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 18.08.11
 * Time: 11:23
 * To change this template use File | Settings | File Templates.
 */
public class DElementPanel extends AValueTab<BoardElement>
{
    public static final String[] UNEDITABLE_PROPERTIES = {
            "length",
            "type"
    };

    public DElementPanel()
    {
        setVisibleProperties(new String[]
                {
                        "length",
                        "width",
                        "boardDef",
                        "texture",
                        "overmeasureLength",
                        "overmeasureWidth",
                        "location",
                        "type"
                });
        setCacheEditorCreator(Constants.getEntityEditorCreators(BoardElement.class));
    }

    private void setUneditableProperties()
    {
        for (String property : UNEDITABLE_PROPERTIES)
        {
            getEditors().get(property).setEnabled(false);
        }
    }

    @Override
    public void init()
    {
        super.init();
        setUneditableProperties();

        getBindingGroup().addBindingListener(new PriceAwareToPricedBinder(this));

        getBindingGroup().addBindingListener(new BindingAdapter()
        {
            @Override
            public void synced(Binding binding)
            {
                Binding.ValueResult valueResult = binding.getSourceValueForTarget();
                if (getValue() == null || valueResult.failed())
                {
                    return;
                }
                if (valueResult.getValue() instanceof BoardElement.Location)
                {
                    for (String property : getCacheProperties().getEditableProperties())
                    {
                        Class aClass = getCacheProperties().getPropertyDescriptor(property).getPropertyType();

                        if (BoardElement.Type.class.isAssignableFrom(aClass))
                        {
                            Component component = getEditors().get(property);
                            if (component instanceof ItemSelector)
                            {
                                component.setEnabled(true);
                            }
                        }
                    }
                }
            }
        });

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
                if (valueResult.getValue() instanceof BoardElement.Type)
                {
                    BoardElement.Type type = (BoardElement.Type) valueResult.getValue();
                    if (!type.equals(BoardElement.Type.common))
                    {
                        getValue().setWidth(type.getSize());
                    }
                }
            }
        });
    }
}
