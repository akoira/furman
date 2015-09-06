package by.dak.furman.templateimport.swing.nodes;

import by.dak.furman.templateimport.Property;
import by.dak.furman.templateimport.ValueConverter;
import by.dak.furman.templateimport.values.FurnitureItem;

@ValueConverter(converter = FurnitureItemNodeConverter.class)
public class FurnitureItemNode extends AValueNode<FurnitureItem>
{
    public FurnitureItemNode()
    {
        setProperties(Property.valueOf("name", false));
    }
}
