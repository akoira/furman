package by.dak.cutting.swing.store.tree;

import by.dak.persistence.entities.predefined.StoreElementStatus;
import by.dak.swing.tree.ATreeNode;

/**
 * Абстрактный node для статуса
 */
public abstract class AStatusNode extends ATreeNode
{
    protected AStatusNode(StoreElementStatus status)
    {
        super(status);
    }

    @Override
    protected void initChildren()
    {
        removeAllChildren();

        add(new BoardsNode((StoreElementStatus) getUserObject()));
        add(new BordersNode((StoreElementStatus) getUserObject()));
        add(new FurnituresNode((StoreElementStatus) getUserObject()));
    }

}
