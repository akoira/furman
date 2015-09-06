package by.dak.furman.templateimport.swing.nodes;

import by.dak.furman.templateimport.Property;
import by.dak.furman.templateimport.ValueConverter;
import by.dak.furman.templateimport.values.TemplateCategory;

@ValueConverter(converter = CategoryNodeConverter.class)
public class TemplateCategoryNode extends AValueNode<TemplateCategory>
{
    public TemplateCategoryNode()
    {
        setProperties(Property.valueOf("name", false), SELECT_PROPERTY);
    }
}
