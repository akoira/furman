package by.dak.cutting.zfacade.swing.tree;

import by.dak.cutting.afacade.swing.tree.FurnitureTypeLinkNode;
import by.dak.cutting.def.swing.tree.MaterialTypeNode;
import by.dak.cutting.zfacade.ZFurnitureType;
import by.dak.swing.tree.ATreeNode;

import javax.swing.tree.MutableTreeNode;

/**
 * User: akoyro
 * Date: 07.08.2010
 * Time: 13:31:13
 */

public class ZProfileNode extends ATreeNode
{
    @Override
    protected void initChildren()
    {
        ZFurnitureType[] zFurnitureTypes = ZFurnitureType.values();
        for (ZFurnitureType zFurnitureType : zFurnitureTypes)
        {
            MutableTreeNode node = null;
            switch (zFurnitureType)
            {
                case profile:
                    node = new MaterialTypeNode();
                    node.setUserObject(by.dak.persistence.entities.predefined.MaterialType.zprofile);
                    add(node);
                    break;
                case filling:
                case butt:
                    break;
                case angle:
                case rubber:
                    add(new FurnitureTypeLinkNode(zFurnitureType));
                    break;
            }
        }
    }
}
