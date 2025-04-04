package by.dak.cutting.facade;

import by.dak.persistence.entities.MelamineGluing;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface MelamineGluingFacade extends PriceAwareFacade<MelamineGluing> {
}
