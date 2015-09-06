package by.dak.cutting.statistics.swing.tree;

import by.dak.persistence.entities.TextureEntity;
import by.dak.swing.tree.ATreeNode;

/**
 * User: akoyro
 * Date: 18.05.2010
 * Time: 13:56:38
 */
public class TextureNode extends ATreeNode
{
    protected TextureNode(TextureEntity texture)
    {
        super(texture, false);
    }

    @Override
    protected void initChildren()
    {
    }
}
