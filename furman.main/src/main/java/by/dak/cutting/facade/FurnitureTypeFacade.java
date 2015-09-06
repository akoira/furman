package by.dak.cutting.facade;

import by.dak.persistence.entities.types.FurnitureType;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface FurnitureTypeFacade extends PriceAwareFacade<FurnitureType>
{
    /**
     * Возвражает список всех детей для кейфорда.
     *
     * @param keyword
     * @return
     */
    public List<FurnitureType> findChildTypesBy(String keyword);
}
