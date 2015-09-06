package by.dak.cutting.zfacade.swing;

import by.dak.cutting.afacade.swing.AFacadeListUpdater;
import by.dak.cutting.afacade.swing.AFacadeTab;
import by.dak.cutting.zfacade.ZButtLink;
import by.dak.cutting.zfacade.ZFacade;
import by.dak.cutting.zfacade.ZFurnitureType;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.predefined.OrderItemType;
import by.dak.persistence.entities.types.FurnitureCode;
import org.jdesktop.application.ResourceMap;

import javax.swing.table.TableCellEditor;

/**
 * User: akoyro
 * Date: 19.07.2010
 * Time: 19:42:17
 */
public class ZFacadeTab extends AFacadeTab<ZFacade>
{

    @Override
    public void init()
    {
        setColorFacade(FacadeContext.getZProfileColorFacade());
        setTypeFacade(FacadeContext.getZProfileTypeFacade());
        setListUpdater(new ZFacadeListUpdater());
        super.init();
    }


    class ZFacadeListUpdater extends AFacadeListUpdater<ZFacade>
    {

        @Override
        public OrderItemType getOrderItemType()
        {
            return OrderItemType.zfacade;
        }

        @Override
        protected void fillFurnitureLinks(ZFacade facade)
        {
            fillFurnitureLink(facade.getProfileType(), facade.getAngle(), ZFurnitureType.angle.name());
            fillFurnitureLink(facade.getProfileType(), facade.getRubber(), ZFurnitureType.rubber.name());
            fillFurnitureLink(facade.getProfileType(), facade.getButt(), ZFurnitureType.butt.name());


            FurnitureCode furnitureCode = (FurnitureCode) FacadeContext.getFurnitureCodeLinkFacade().findBy(facade.getProfileColor(), ZFurnitureType.angle.name());
            facade.getAngle().setFurnitureCode(furnitureCode);
            furnitureCode = (FurnitureCode) FacadeContext.getFurnitureCodeLinkFacade().findBy(facade.getProfileColor(), ZFurnitureType.rubber.name());
            facade.getRubber().setFurnitureCode(furnitureCode);
        }

        @Override
        public String[] getHiddenProperties()
        {
            return new String[]{
                    ZFacade.PROPERTY_angle,
                    ZFacade.PROPERTY_rubber
            };
        }

        @Override
        public String[] getEditableProperties()
        {
            return new String[]{ZFacade.PROPERTY_number,
                    ZFacade.PROPERTY_name,
                    ZFacade.PROPERTY_profileType,
                    ZFacade.PROPERTY_profileColor,
                    ZFacade.PROPERTY_length,
                    ZFacade.PROPERTY_width,
                    ZFacade.PROPERTY_amount,
                    ZFacade.PROPERTY_filling,
                    ZFacade.PROPERTY_angle,
                    ZFacade.PROPERTY_butt,
                    ZFacade.PROPERTY_rubber

            };
        }

        @Override
        public ResourceMap getResourceMap()
        {
            return ZFacadeTab.this.getResourceMap();
        }


        @Override
        public TableCellEditor getTableCellEditor(Class propertyClass)
        {
            if (propertyClass == ZButtLink.class)
            {
                TableCellEditor editor = super.getTableCellEditor(propertyClass);
                if (editor == null)
                {
                    editor = createPopupEditor(new ZButtLinkTab());
                    editorClassMap.put(propertyClass, editor);
                }
                return editor;
            }
            else
            {
                return super.getTableCellEditor(propertyClass);
            }
        }
    }
}
