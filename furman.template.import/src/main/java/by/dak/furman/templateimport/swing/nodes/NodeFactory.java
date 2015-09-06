package by.dak.furman.templateimport.swing.nodes;

import by.dak.furman.templateimport.Property;
import by.dak.furman.templateimport.values.AValue;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * User: akoyro
 * Date: 9/23/13
 * Time: 9:14 PM
 */
public class NodeFactory
{
    private static final Logger LOGGER = LogManager.getLogger(NodeFactory.class);

    private Map<Class<? extends AValue>, Class<? extends AValueNode>> nodeClasses
            = new HashMap<Class<? extends AValue>, Class<? extends AValueNode>>();


    public <V extends AValue> AValueNode<V> buildNode(V value)
    {
        Class<? extends AValueNode> nodeClass = nodeClasses.get(value.getClass());
        if (nodeClass == null)
            nodeClass = defineClass(value.getClass());
        try
        {
            AValueNode<V> node = nodeClass.getConstructor().newInstance();
            node.setValue(value);
            return node;
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException(StringUtils.EMPTY, e);
        }
    }

    private <V extends AValue> AValueNode<V> createDefaultNode(V value)
    {
        AValueNode<V> node = new AValueNode<V>()
        {
        };
        node.setValue(value);
        node.setProperties(Property.valueOf(String.class, "name", false));
        return node;
    }

    private Class<? extends AValueNode> defineClass(Class<? extends AValue> aClass)
    {
        try
        {
            String name = String.format("by.dak.furman.templateimport.swing.nodes.%sNode", aClass.getSimpleName());
            return (Class<AValueNode>) this.getClass().getClassLoader().loadClass(name);
        }
        catch (Exception e)
        {
            LOGGER.error(StringUtils.EMPTY, e);
            return DefaultNode.class;
        }
    }


    public static class DefaultNode extends AValueNode<AValue>
    {
        public DefaultNode()
        {
            setProperties(Property.valueOf("name", false));
        }
    }
}
