package by.dak.cutting.statistics.swing.tree;

import by.dak.persistence.entities.types.FurnitureCode;
import by.dak.swing.tree.ATreeNode;

/**
 * User: akoyro
 * Date: 18.05.2010
 * Time: 18:17:51
 */
public class FurnitureCodeNode extends ATreeNode
{
    protected FurnitureCodeNode(FurnitureCode userObject)
    {
        super(userObject);
    }

    @Override
    protected void initChildren()
    {
    }
}
