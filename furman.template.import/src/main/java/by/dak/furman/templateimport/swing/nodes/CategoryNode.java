package by.dak.furman.templateimport.swing.nodes;

import by.dak.furman.templateimport.Property;
import by.dak.furman.templateimport.ValueConverter;
import by.dak.furman.templateimport.values.ACategory;

@ValueConverter(converter = CategoryNodeConverter.class)
public class CategoryNode extends AValueNode<ACategory>
{
    public CategoryNode()
    {
        setProperties(Property.valueOf("name", false), SELECT_PROPERTY);
    }
}
