package by.dak.cutting.facade;

import by.dak.cutting.swing.order.data.TextureBoardDefPair;
import by.dak.persistence.entities.*;
import by.dak.persistence.entities.predefined.ServiceType;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface PriceFacade extends BaseFacade<PriceEntity>
{
    List<PriceEntity> findAllBy(Service service);

    PriceEntity findUniqueBy(PriceAware priceAware, Priced priced);

    List<PriceEntity> findAllBy(PriceAware priceAware);

    PriceEntity getPrice(PriceAware boardDef, ServiceType serviceType);

    PriceEntity getPrice(BoardDef boardDef, ServiceType serviceType);

    PriceEntity getPrice(BorderDefEntity borderDefEntity, ServiceType serviceType);

    PriceEntity getPrice(TextureBoardDefPair pair);
}
