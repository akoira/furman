package by.dak.furman.templateimport.swing.nodes;

import by.dak.furman.templateimport.Property;
import by.dak.furman.templateimport.values.FurnitureDetail;

/**
 * User: akoyro
 * Date: 9/22/13
 * Time: 11:36 PM
 */
public class FurnitureDetailNode extends AValueNode<FurnitureDetail>
{
    public FurnitureDetailNode()
    {
        setProperties(Property.valueOf("name", false));
    }
}
