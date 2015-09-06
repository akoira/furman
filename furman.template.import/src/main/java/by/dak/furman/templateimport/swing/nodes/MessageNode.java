package by.dak.furman.templateimport.swing.nodes;

import by.dak.furman.templateimport.Property;
import by.dak.furman.templateimport.ValueConverter;
import by.dak.furman.templateimport.values.Message;

@ValueConverter(converter = MessageNodeConverter.class)
public class MessageNode extends AValueNode<Message>
{
    public MessageNode()
    {
        setProperties(Property.valueOf("message", false));
    }
}
