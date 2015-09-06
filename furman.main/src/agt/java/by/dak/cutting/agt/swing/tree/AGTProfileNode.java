package by.dak.cutting.agt.swing.tree;

import by.dak.cutting.afacade.swing.tree.FurnitureTypeLinkNode;
import by.dak.cutting.agt.AGTFurnitureType;
import by.dak.cutting.def.swing.tree.MaterialTypeNode;
import by.dak.persistence.entities.predefined.MaterialType;
import by.dak.swing.tree.ATreeNode;

import javax.swing.tree.MutableTreeNode;

/**
 * User: akoyro
 * Date: 07.08.2010
 * Time: 13:31:13
 */

public class AGTProfileNode extends ATreeNode
{
    @Override
    protected void initChildren()
    {
        AGTFurnitureType[] types = AGTFurnitureType.values();
        for (AGTFurnitureType type : types)
        {
            MutableTreeNode node;
            switch (type)
            {
                case agtprofile:
                    node = new MaterialTypeNode();
                    node.setUserObject(MaterialType.agtprofile);
                    add(node);
                    break;
                case agtdowel:
                case agtrubber:
                case agtglue:
                    add(new FurnitureTypeLinkNode<AGTFurnitureType>(type));
                    break;
                case agtfilling:
                    break;
            }
        }
    }
}
