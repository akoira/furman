package by.dak.cutting.facade.impl.helper;

import by.dak.cutting.facade.FurnitureFacade;
import by.dak.cutting.facade.FurnitureLink2FurnitureLinkFacade;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Furniture;
import by.dak.persistence.entities.FurnitureLink;
import by.dak.persistence.entities.FurnitureLink2FurnitureLink;
import by.dak.persistence.entities.predefined.StoreElementStatus;

import java.util.List;

/**
 * User: akoyro
 * Date: 07.11.2010
 * Time: 13:15:51
 */
public class FurnitureLink2FurnitureLinker
{
    public void link(FurnitureLink furnitureLink, List<Furniture> furnitures)
    {
        for (Furniture furniture : furnitures)
        {
            FurnitureLink2FurnitureLink link = new FurnitureLink2FurnitureLink();
            link.setParent(furnitureLink);
            link.setChild(furniture);
            getFurnitureLink2FurnitureLinkFacade().save(link);
        }
    }

    public void unlink(Furniture sourceFurniture)
    {
        //разлинковать furniture
        List<FurnitureLink2FurnitureLink> furnitureLinks = getFurnitureLink2FurnitureLinkFacade().findAllByField(FurnitureLink2FurnitureLink.PROPERTY_child, sourceFurniture);
        for (FurnitureLink2FurnitureLink furnitureLink : furnitureLinks)
        {
            //ищем залинкованные другие залинкованные использованные futniture и возвращаем их на склад
            List<FurnitureLink2FurnitureLink> furnitures = FacadeContext.getFurnitureLink2FurnitureLinkFacade().findAllByField(FurnitureLink2FurnitureLink.PROPERTY_parent, furnitureLink.getParent());
            for (FurnitureLink2FurnitureLink furniture : furnitures)
            {
                if (!furniture.getId().equals(sourceFurniture.getId()))
                {
                    Furniture f = furniture.getChild();
                    f.setOrder(null);
                    if (f.getStatus() == StoreElementStatus.used)
                    {
                        f.setStatus(StoreElementStatus.exist);
                    }
                    FacadeContext.getFurnitureFacade().save(f);
                }
            }
        }
        // потому что source уже изменился
        FacadeContext.getFurnitureFacade().delete(sourceFurniture.getId());
    }


    public FurnitureLink2FurnitureLinkFacade getFurnitureLink2FurnitureLinkFacade()
    {
        return (FurnitureLink2FurnitureLinkFacade) FacadeContext.getApplicationContext().getBean("furnitureLink2FurnitureLinkFacade");
    }

    public FurnitureFacade getFurnitureFacade()
    {
        return FacadeContext.getFurnitureFacade();
    }
}
