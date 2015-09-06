package by.dak.delivery.swing.tree;

import by.dak.cutting.swing.tree.EntityListUpdater;
import by.dak.persistence.entities.Delivery;
import by.dak.persistence.entities.predefined.MaterialType;

/**
 * User: akoyro
 * Date: 06.04.11
 * Time: 12:36
 */
public class DeliveryListUpdater extends EntityListUpdater<Delivery>
{
    private MaterialType materialType;

    public DeliveryListUpdater(MaterialType materialType)
    {
        this.materialType = materialType;
    }

    @Override
    public void adjustFilter()
    {
        if (materialType != null)
        {
            getSearchFilter().eq(Delivery.PROPERTY_materialType, materialType);
        }
    }
}
