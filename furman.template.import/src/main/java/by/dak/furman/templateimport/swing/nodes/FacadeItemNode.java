package by.dak.furman.templateimport.swing.nodes;

import by.dak.furman.templateimport.Property;
import by.dak.furman.templateimport.ValueConverter;
import by.dak.furman.templateimport.values.FacadeItem;

@ValueConverter(converter = FacadeItemNodeConverter.class)
public class FacadeItemNode extends AValueNode<FacadeItem>
{
    public FacadeItemNode()
    {
        setProperties(Property.valueOf("name", false));
    }
}
