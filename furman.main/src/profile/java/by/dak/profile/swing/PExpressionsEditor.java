package by.dak.profile.swing;

import by.dak.cutting.swing.store.EditorCreators;
import by.dak.cutting.swing.store.tabs.PriceAwareToPricedBinder;
import by.dak.profile.PExpressions;
import by.dak.profile.PFurnitureTypeDef;
import by.dak.swing.APropertyEditorCreator;
import by.dak.swing.AValueTab;
import net.infonode.tabbedpanel.TabFactory;
import net.infonode.tabbedpanel.TabbedPanel;

import java.beans.PropertyDescriptor;
import java.util.Collections;
import java.util.HashMap;

/**
 * User: akoyro
 * Date: 01.09.11
 * Time: 14:13
 */
public class PExpressionsEditor extends TabbedPanel
{
    private static final HashMap<Class, APropertyEditorCreator> EDITOR_CREATORS = new HashMap<Class, APropertyEditorCreator>(EditorCreators.EDITOR_CREATORS);

    private PFurnitureTypeDef furnitureTypeDef;
    private AValueTab<PFurnitureTypeDef> furniturePanel;
    private AValueTab<PExpressions> expressionsPanel;

    public PExpressionsEditor()
    {
        furniturePanel = new AValueTab<PFurnitureTypeDef>()
        {

            @Override
            public void init()
            {
                super.init();
                getBindingGroup().addBindingListener(new PriceAwareToPricedBinder(this));
            }

            @Override
            protected APropertyEditorCreator getEditorCreator(PropertyDescriptor descriptor)
            {
                if (descriptor.getName().equals(PFurnitureTypeDef.PROPERTY_usingFormula))
                {
                    return new EditorCreators.ClassEditorCreator<PExpressionField>(PExpressionField.class, "value");
                }
                return super.getEditorCreator(descriptor);
            }
        };
        furniturePanel.setVisibleProperties(PFurnitureTypeDef.PROPERTY_type,
                PFurnitureTypeDef.PROPERTY_code,
                PFurnitureTypeDef.PROPERTY_usingFormula);
        furniturePanel.setCacheEditorCreator(Collections.unmodifiableMap(EDITOR_CREATORS));
        furniturePanel.setValueClass(PFurnitureTypeDef.class);
        furniturePanel.init();


        expressionsPanel = new AValueTab<PExpressions>()
        {
            @Override
            protected APropertyEditorCreator getEditorCreator(PropertyDescriptor descriptor)
            {
                if (descriptor.getName().equals(PExpressions.PROPERTY_countExpression) ||
                        descriptor.getName().equals(PExpressions.PROPERTY_widthExpression) ||
                        descriptor.getName().equals(PExpressions.PROPERTY_lengthExpression)

                        )
                {
                    return new EditorCreators.ClassEditorCreator<PExpressionField>(PExpressionField.class, "fieldExpression.text");
                }
                return super.getEditorCreator(descriptor);
            }
        };
        expressionsPanel.setVisibleProperties(PExpressions.PROPERTY_lengthExpression,
                PExpressions.PROPERTY_widthExpression,
                PExpressions.PROPERTY_countExpression);
        expressionsPanel.setCacheEditorCreator(Collections.unmodifiableMap(EDITOR_CREATORS));
        expressionsPanel.setValueClass(PExpressions.class);
        expressionsPanel.init();


        addTab(TabFactory.createTitledTab(furniturePanel.getTitle(), null, furniturePanel));
        addTab(TabFactory.createTitledTab(expressionsPanel.getTitle(), null, expressionsPanel));
    }


    public PFurnitureTypeDef getFurnitureTypeDef()
    {
        return furnitureTypeDef;
    }

    public void setFurnitureTypeDef(PFurnitureTypeDef furnitureTypeDef)
    {
        this.furnitureTypeDef = furnitureTypeDef;

        furniturePanel.setValue(this.furnitureTypeDef);
        expressionsPanel.setValue(this.furnitureTypeDef.getExpressions());

    }


}
