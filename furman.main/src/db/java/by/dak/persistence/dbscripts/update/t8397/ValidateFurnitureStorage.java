package by.dak.persistence.dbscripts.update.t8397;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.SpringConfiguration;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Furniture;
import by.dak.persistence.entities.predefined.StoreElementStatus;

import java.util.List;

/**
 * User: akoyro
 * Date: 03.08.2010
 * Time: 16:10:34
 */
public class ValidateFurnitureStorage
{
    public static void main(String[] args)
    {
        new SpringConfiguration();

        List<Furniture> furnitures = FacadeContext.getFurnitureFacade().findAllByField(Furniture.PROPERTY_status, StoreElementStatus.order);
        for (Furniture orderedFurniture : furnitures)
        {
            adjustOrderedFurniture(orderedFurniture);
        }
    }

    private static void adjustOrderedFurniture(Furniture orderedFurniture)
    {
        SearchFilter filter = SearchFilter.instanceUnbound();
        filter.eq(Furniture.PROPERTY_priceAware, orderedFurniture.getFurnitureType());
        filter.eq(Furniture.PROPERTY_priced, orderedFurniture.getFurnitureCode());
        filter.eq(Furniture.PROPERTY_status,StoreElementStatus.exist);
        filter.addDescOrder(Furniture.PROPERTY_price);

        List<Furniture> existFurnitures = FacadeContext.getFurnitureFacade().loadAll(filter);

        if (existFurnitures.size() < 1)
        {
            return;
        }

        Furniture existFurniture = existFurnitures.get(0);
        double rest = orderedFurniture.getSize() - existFurniture.getSize();

        Furniture usedFurniture = null;

        if (rest < 0)
        {
            existFurniture.setSize(Math.abs(rest));

            usedFurniture = existFurniture.clone();

            usedFurniture.setSize(orderedFurniture.getSize());


            FacadeContext.getFurnitureFacade().save(existFurniture);
            FacadeContext.getFurnitureFacade().delete(orderedFurniture);
        }
        else
        {
            usedFurniture = existFurniture;

            orderedFurniture.setSize(rest);
            if (rest == 0)
                FacadeContext.getFurnitureFacade().delete(orderedFurniture);
            else
                FacadeContext.getFurnitureFacade().save(orderedFurniture);
        }
        usedFurniture.setOrder(orderedFurniture.getOrder());
        usedFurniture.setStatus(StoreElementStatus.used);
        FacadeContext.getFurnitureFacade().save(usedFurniture);

        if (rest > 0)
            adjustOrderedFurniture(orderedFurniture);
    }
}
