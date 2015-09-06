package by.dak.cutting.swing.store.tree;

import by.dak.swing.tree.ATreeNode;
import by.dak.utils.convert.EntityToStringConverter;
import by.dak.utils.convert.StringValue;

/**
 * Created by IntelliJ IDEA.
 * User: user0
 * Date: 11.02.2010
 * Time: 17:06:59
 * To change this template use File | Settings | File Templates.
 */
@StringValue(converterClass = StoreRootNode.class)
public class StoreRootNode extends ATreeNode implements EntityToStringConverter
{
    public StoreRootNode()
    {
        setUserObject(this);
    }

    @Override
    protected void initChildren()
    {
        removeAllChildren();

        add(new StatusExistNode());
        add(new StatusOrderNode());
        add(new StatusUsedNode());
    }

    @Override
    public String convert(Object entity)
    {
        return resourceMap.getString("node.name");
    }
}
