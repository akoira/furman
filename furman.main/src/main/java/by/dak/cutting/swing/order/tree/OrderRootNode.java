package by.dak.cutting.swing.order.tree;

import by.dak.swing.tree.ATreeNode;
import by.dak.utils.convert.EntityToStringConverter;
import by.dak.utils.convert.StringValue;

/**
 * Created by IntelliJ IDEA.
 * User: user0
 * Date: 19.02.2010
 * Time: 13:38:22
 * To change this template use File | Settings | File Templates.
 */
@StringValue(converterClass = OrderRootNode.class)
public class OrderRootNode extends ATreeNode implements EntityToStringConverter
{
    public OrderRootNode()
    {
        setUserObject(this);
    }

    @Override
    protected void initChildren()
    {
        removeAllChildren();

        add(new CustomersNode());
    }

    @Override
    public String convert(Object entity)
    {
        return resourceMap.getString("node.name");
    }
}
