package by.dak.cutting.def.swing.tree;

import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.predefined.MaterialType;
import by.dak.swing.tree.ATreeNode;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 10.02.2010
 * Time: 19:25:31
 * To change this template use File | Settings | File Templates.
 */
public class TypesRootNode extends ATreeNode
{
    @Override
    protected void initChildren()
    {
        removeAllChildren();
        MaterialType[] materialTypes = MaterialType.values();
        for (MaterialType type : materialTypes)
        {
            add(FacadeContext.getMaterialTypeNodeFactory().getNodeBy(type));
        }
        add(new ServicesNode());
    }
}
