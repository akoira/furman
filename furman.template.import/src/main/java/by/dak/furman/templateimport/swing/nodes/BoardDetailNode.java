package by.dak.furman.templateimport.swing.nodes;

import by.dak.furman.templateimport.Property;
import by.dak.furman.templateimport.values.BoardDetail;

public class BoardDetailNode extends AValueNode<BoardDetail>
{
    public BoardDetailNode()
    {
        setProperties(Property.valueOf("name", false));
    }
}
