package by.dak.cutting.afacade;

import by.dak.cutting.SpringConfiguration;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Furniture;
import by.dak.persistence.entities.predefined.StoreElementStatus;

/**
 * Created by IntelliJ IDEA.
 * User: user0
 * Date: 27.01.2010
 * Time: 17:46:14
 * To change this template use File | Settings | File Templates.
 */
public class TFurnitureFacade
{
    public static void main(String[] args)
    {
        new SpringConfiguration();

        Furniture furniture = new Furniture();
        furniture.setSize(10d);
        furniture.setProvider(FacadeContext.getProviderFacade().loadAll().get(0));
        furniture.setStatus(StoreElementStatus.exist);
//        furniture.setOrder(FacadeContext.getOrderFacade().loadAll().get(0));
//        furniture.setCreatedByOrder(FacadeContext.getOrderFacade().loadAll().get(0));
        furniture.setFurnitureCode(FacadeContext.getFurnitureCodeFacade().loadAll().get(0));
        furniture.setFurnitureType(FacadeContext.getFurnitureTypeFacade().loadAll().get(0));

        FacadeContext.getFurnitureFacade().save(furniture);


        Furniture furniture2 = FacadeContext.getFurnitureFacade().loadAll().get(0);

        System.out.println(furniture2.getSize());
        System.out.println(furniture2.getId());
        System.out.println(furniture2.getFurnitureCode().getName());
        System.out.println(furniture2.getFurnitureType().getName());
        System.out.println(furniture2.getStatus().name());
    }
}
