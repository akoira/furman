package by.dak.cutting.swing.store.tree;

import by.dak.persistence.entities.predefined.StoreElementStatus;

/**
 *
 */
public class StatusUsedNode extends AStatusNode
{
    protected StatusUsedNode()
    {
        super(StoreElementStatus.used);
    }
}
