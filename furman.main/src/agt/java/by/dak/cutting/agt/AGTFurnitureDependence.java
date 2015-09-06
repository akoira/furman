package by.dak.cutting.agt;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.afacade.swing.AFillingTab;
import by.dak.cutting.swing.ItemSelector;
import by.dak.cutting.swing.table.PopupEditor;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.BoardDef;
import by.dak.persistence.entities.OrderFurniture;
import by.dak.persistence.entities.types.FurnitureCode;
import by.dak.persistence.entities.types.FurnitureType;
import by.dak.swing.ActionsPanel;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * > > Уплотнитель.
 * > > 1.Паз 8 мм.:
 * > > вставляется панель(8мм) без уплотнителя.
 * > > втавляется стекло с уплотнителем С-05.
 * > > 2.Паз 4 мм.:
 * > > вставляется только стекло без уплотнителя.
 * > > 3.Паз 10 мм.:
 * > > вставляется ДСП(10мм) без уплотнителя.
 * > > вставляется стекло с уплотнителем С-2.
 * > > Клей.
 * > > Используется во всех случаях,когда заполнением является панель(8мм) либо
 * > > ДСП(10мм).
 * > > А также с профилем 1052-исключение(заполнением является стекло).
 */
public class AGTFurnitureDependence
{

    private AGTFacade facade;

    public void fill()
    {
    }

    public AGTFacade getFacade()
    {
        return facade;
    }

    public void setFacade(AGTFacade facade)
    {
        this.facade = facade;
    }

    public FurnitureType getDowelType()
    {
        if (facade.getProfileType() == null)
        {
            return null;
        }
        return (FurnitureType) FacadeContext.getFurnitureTypeLinkFacade().findBy(facade.getProfileType(), AGTFurnitureType.agtdowel.name());
    }


    public FurnitureCode getDowelCode()
    {
        if (facade.getProfileColor() == null)
        {
            return null;
        }
        return (FurnitureCode) FacadeContext.getFurnitureCodeLinkFacade().findBy(facade.getProfileColor(), AGTFurnitureType.agtdowel.name());
    }


    public FurnitureCode getGlueCode()
    {
        if (facade.getProfileColor() == null)
        {
            return null;
        }
        switch (getWidthGrooveSize())
        {
            case size10:
            case size8:
                OrderFurniture furniture = facade.getFilling();
                if (furniture != null && furniture.getBoardDef() != null)
                {
                    if (furniture.getBoardDef().getThickness() == WidthGrooveSize.size8.getSize() ||
                            furniture.getBoardDef().getThickness() == WidthGrooveSize.size10.getSize())
                    {
                        return (FurnitureCode) FacadeContext.getFurnitureCodeLinkFacade().findBy(facade.getProfileColor(), AGTFurnitureType.agtglue.name());
                    }
                }
                break;
        }
        return null;
    }

    public FurnitureType getGlueType()
    {
        if (facade.getProfileType() == null)
        {
            return null;
        }
        switch (getWidthGrooveSize())
        {
            case size10:
            case size8:
                OrderFurniture furniture = facade.getFilling();
                if (furniture != null && furniture.getBoardDef() != null)
                {
                    if (furniture.getBoardDef().getThickness() == WidthGrooveSize.size8.getSize() ||
                            furniture.getBoardDef().getThickness() == WidthGrooveSize.size10.getSize())
                    {
                        return (FurnitureType) FacadeContext.getFurnitureTypeLinkFacade().findBy(facade.getProfileType(), AGTFurnitureType.agtglue.name());
                    }
                }
                break;
        }
        return null;

    }

    public FurnitureCode getRubberCode()
    {
        if (facade.getProfileColor() == null)
        {
            return null;
        }
        switch (getWidthGrooveSize())
        {
            case size10:
            case size8:
                OrderFurniture furniture = facade.getFilling();
                if (furniture != null && furniture.getBoardDef() != null)
                {
                    if (furniture.getBoardDef().getThickness() == WidthGrooveSize.size4.getSize())
                    {
                        return (FurnitureCode) FacadeContext.getFurnitureCodeLinkFacade().findBy(facade.getProfileColor(), AGTFurnitureType.agtrubber.name());
                    }
                }
                break;
        }
        return null;

    }


    public FurnitureType getRubberType()
    {
        if (facade.getProfileType() == null)
        {
            return null;
        }

        switch (getWidthGrooveSize())
        {
            case size10:
            case size8:
                OrderFurniture furniture = facade.getFilling();
                if (furniture != null && furniture.getBoardDef() != null)
                {
                    if (furniture.getBoardDef().getThickness() == WidthGrooveSize.size4.getSize())
                    {
                        return (FurnitureType) FacadeContext.getFurnitureTypeLinkFacade().findBy(facade.getProfileType(), AGTFurnitureType.agtrubber.name());
                    }
                }
                break;
        }
        return null;
    }

//\    public List<FurnitureType> getRubberTypes()
//\    {
//        SearchFilter filter = SearchFilter.instanceUnbound();
//        switch (getWidthGrooveSize())
//        {
//
//            case size10:
//                OrderFurniture furniture = facade.getFilling();
//                if (furniture != null && furniture.getBoardDef() != null)
//                {
//                    if (furniture.getBoardDef().getThickness() == WidthGrooveSize.size4.getSize())
//                    {
//                        facade.getRubber().setFurnitureCode();
//                        facade.getRubber().setFurnitureType(null);
//                    }
//                }
//                break;
//            case size8:
//                break;
//            case size4:
//                break;
//            case undefined:
//                break;
//        }
//
//    }

    public List<BoardDef> getFillingTypes()
    {
        SearchFilter filter = SearchFilter.instanceUnbound();
        switch (getWidthGrooveSize())
        {
//            > > вставляется ДСП(10мм) без уплотнителя.
//            > > вставляется стекло с уплотнителем С-2.
            case size10:
                filter.or(Restrictions.eq(BoardDef.PROPERTY_thickness, (long) WidthGrooveSize.size10.getSize()),
                        Restrictions.eq(BoardDef.PROPERTY_thickness, (long) WidthGrooveSize.size4.getSize()));
                break;
//            > > вставляется панель(8мм) без уплотнителя.
//            > > втавляется стекло с уплотнителем С-05.
            case size8:
                filter.or(Restrictions.eq(BoardDef.PROPERTY_thickness, (long) WidthGrooveSize.size8.getSize()),
                        Restrictions.eq(BoardDef.PROPERTY_thickness, (long) WidthGrooveSize.size4.getSize()));
                break;
//            > > 2.Паз 4 мм.:
//            > > вставляется только стекло без уплотнителя.
            case size4:
                filter.eq(BoardDef.PROPERTY_thickness, (long) WidthGrooveSize.size4.getSize());
                break;
// все если размер не известен
            case undefined:
                break;
            default:
                throw new IllegalArgumentException();
        }
        return FacadeContext.getBoardDefFacade().loadAll(filter);
    }


    private WidthGrooveSize getWidthGrooveSize()
    {
        Double widthGroove = -1d;
        if (facade.getProfileType() != null)
        {
            widthGroove = facade.getProfileType().getWidthGroove();
            if (facade.getProfileColor() != null &&
                    facade.getProfileColor().getWidthGroove() != null &&
                    facade.getProfileColor().getWidthGroove() > 0.0)
            {
                widthGroove = facade.getProfileColor().getWidthGroove();
            }
        }
        return WidthGrooveSize.valueOf(widthGroove);
    }


    public void initFillingPopupEditor(PopupEditor popupEditor)
    {
        if (popupEditor != null)
        {
            AFillingTab fillingTab = (AFillingTab) ((ActionsPanel) popupEditor.getComponentProvider().getPopupComponent()).getContentComponent();
            if (fillingTab != null)
            {
                ItemSelector itemSelector = (ItemSelector) fillingTab.getEditors().get(OrderFurniture.PROPERTY_priceAware);
                if (itemSelector != null)
                {
                    itemSelector.setItems(getFillingTypes());
                }
            }
        }
    }

}
