package by.dak.cutting.def.swing.tree;

import by.dak.persistence.entities.predefined.MaterialType;
import by.dak.swing.tree.ATreeNode;

import java.util.HashMap;
import java.util.Map;

/**
 * User: akoyro
 * Date: 07.08.2010
 * Time: 13:25:49
 */
public class MaterialTypeNodeFactory
{
    private Map<String, String> nodeClasses = new HashMap<String, String>();

    public ATreeNode getNodeBy(MaterialType materialType)
    {
        return createNodeBy(materialType);
    }

    private ATreeNode createNodeBy(MaterialType type)
    {
        try
        {
            String className = getNodeClasses().get(type.name());
            Class aClass = MaterialTypeNodeFactory.class.getClassLoader().loadClass(className);
            ATreeNode node = (ATreeNode) aClass.getConstructor(null).newInstance(null);
            node.setUserObject(type);
            return node;
        }
        catch (Throwable t)
        {
            throw new IllegalArgumentException(t);
        }
    }


    public Map<String, String> getNodeClasses()
    {
        return nodeClasses;
    }

    public void setNodeClasses(Map<String, String> nodeClasses)
    {
        this.nodeClasses = nodeClasses;
    }
}
