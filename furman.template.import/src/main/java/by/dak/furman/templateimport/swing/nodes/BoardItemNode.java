package by.dak.furman.templateimport.swing.nodes;

import by.dak.furman.templateimport.Property;
import by.dak.furman.templateimport.ValueConverter;
import by.dak.furman.templateimport.values.BoardItem;

@ValueConverter(converter = BoardItemNodeConverter.class)
public class BoardItemNode extends AValueNode<BoardItem>
{
    public BoardItemNode()
    {
        setProperties(Property.valueOf("name", false));
    }
}
