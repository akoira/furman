package by.dak.delivery.swing.tree;

import by.dak.cutting.swing.tree.EntityNode;
import by.dak.persistence.entities.Delivery;
import by.dak.persistence.entities.predefined.MaterialType;
import org.apache.commons.lang.StringUtils;
import org.jdesktop.application.Application;

import javax.swing.*;

/**
 * User: akoyro
 * Date: 06.04.11
 * Time: 12:09
 */
public class DeliveryNode extends EntityNode<Delivery>
{
    public DeliveryNode(MaterialType materialType, Icon icon)
    {
        super(Delivery.class);
        setUserObject(materialType);

        DeliveryListUpdater listUpdater = new DeliveryListUpdater(materialType);
        listUpdater.setVisibleProperties(StringUtils.split(Application.getInstance().getContext().getResourceMap(getEntityClass()).getString("table.visible.properties"), ","));
        listUpdater.setElementClass(getEntityClass());
        listUpdater.setResourceMap(Application.getInstance().getContext().getResourceMap(getEntityClass()));

        DeliveryNEDActions actions = new DeliveryNEDActions();
        listUpdater.setNewEditDeleteActions(actions);

        setListUpdater(listUpdater);

        setClosedIcon(icon);
        setLeafIcon(icon);
        setOpenIcon(icon);
    }

    public MaterialType getMaterialType()
    {
        return (MaterialType) getUserObject();
    }

}
