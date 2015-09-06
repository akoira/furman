package by.dak.delivery.swing.tree;

import by.dak.cutting.swing.tree.EntityNode;
import by.dak.persistence.entities.Delivery;
import by.dak.persistence.entities.predefined.MaterialType;
import org.jdesktop.application.Application;

/**
 * User: akoyro
 * Date: 06.04.11
 * Time: 11:46
 */
public class RootNode extends EntityNode<Delivery>
{
    public RootNode()
    {
        super(Delivery.class);
    }

    @Override
    protected void initChildren()
    {
        DeliveryNode deliveryNode = new DeliveryNode(MaterialType.board,
                Application.getInstance().getContext().getResourceMap(RootNode.class).getIcon("board.delivery.icon"));
        add(deliveryNode);
        deliveryNode = new DeliveryNode(MaterialType.border,
                Application.getInstance().getContext().getResourceMap(RootNode.class).getIcon("border.delivery.icon"));
        add(deliveryNode);
        deliveryNode = new DeliveryNode(MaterialType.furniture,
                Application.getInstance().getContext().getResourceMap(RootNode.class).getIcon("furniture.delivery.icon"));
        add(deliveryNode);
    }
}
