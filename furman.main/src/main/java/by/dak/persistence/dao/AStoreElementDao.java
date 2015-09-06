package by.dak.persistence.dao;

import by.dak.persistence.entities.AStoreElement;
import by.dak.persistence.entities.Delivery;
import by.dak.persistence.entities.Provider;
import by.dak.persistence.entities.predefined.StoreElementStatus;
import org.springframework.stereotype.Repository;

/**
 * User: akoyro
 * Date: 21.11.2009
 * Time: 23:32:37
 */
@Repository
public interface AStoreElementDao<E extends AStoreElement> extends GenericDao<E>
{
    void updateDeliveryProviderTo(Delivery delivery, Provider provider);

    void updateDeliveryStatusTo(Delivery delivery, StoreElementStatus status);
}
