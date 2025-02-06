package by.dak.cutting.facade;

import by.dak.persistence.entities.AOrder;
import by.dak.persistence.entities.ServiceLink;
import by.dak.persistence.entities.predefined.OrderItemType;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ServiceLinkFacade extends AOrderDetailFacade<ServiceLink> {
    List<ServiceLink> loadAllBy(AOrder order, List<OrderItemType> types);

    List<ServiceLink> loadAllBy(AOrder order, List<OrderItemType> types, String discriminator);

}
