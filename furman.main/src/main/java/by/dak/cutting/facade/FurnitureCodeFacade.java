package by.dak.cutting.facade;

import by.dak.persistence.entities.types.FurnitureCode;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface FurnitureCodeFacade extends PricedFacade<FurnitureCode>
{

}
