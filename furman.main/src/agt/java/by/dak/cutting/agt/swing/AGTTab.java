package by.dak.cutting.agt.swing;

import by.dak.cutting.afacade.AFacade;
import by.dak.cutting.afacade.swing.AFacadeListUpdater;
import by.dak.cutting.afacade.swing.AFacadeTab;
import by.dak.cutting.agt.AGTFacade;
import by.dak.cutting.agt.AGTFurnitureDependence;
import by.dak.cutting.agt.AGTFurnitureType;
import by.dak.cutting.swing.table.PopupEditor;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.OrderFurniture;
import by.dak.persistence.entities.predefined.OrderItemType;
import by.dak.swing.table.ATableCellEditorDecorator;
import org.jdesktop.application.ResourceMap;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * User: akoyro
 * Date: 06.09.2010
 * Time: 21:50:45
 */
public class AGTTab extends AFacadeTab<AGTFacade>
{
    @Override
    public void setEditable(boolean editable) {
    }

    @Override
    public void setEnabled(boolean enabled) {
    }

    public static final List<String> SYNCED_PROPERTIES = Collections.unmodifiableList(Arrays.asList(new String[]{AFacade.PROPERTY_profileType, AFacade.PROPERTY_profileColor, AFacade.PROPERTY_filling}));

    private static final String[] HIDDEN_PROPERTIES = new String[]{
            AGTFacade.PROPERTY_dowel,
            AGTFacade.PROPERTY_glue,
            AGTFacade.PROPERTY_rubber

    };

    private static final String[] EDITABLE_PROPERTIES = new String[]{AGTFacade.PROPERTY_number,
            AGTFacade.PROPERTY_name,
            AGTFacade.PROPERTY_profileType,
            AGTFacade.PROPERTY_profileColor,
            AGTFacade.PROPERTY_length,
            AGTFacade.PROPERTY_width,
            AGTFacade.PROPERTY_amount,
            AGTFacade.PROPERTY_filling,
            AGTFacade.PROPERTY_dowel,
            AGTFacade.PROPERTY_glue,
            AGTFacade.PROPERTY_rubber
    };

    private static final String[] VISIBLE_PROPERTIES = EDITABLE_PROPERTIES;


    @Override
    public void init()
    {
        setColorFacade(FacadeContext.getAGTColorFacade());
        setTypeFacade(FacadeContext.getAGTTypeFacade());
        setListUpdater(new AGTListUpdater());
        super.setEditable(false);
        super.setEnabled(false);
        super.init();
    }


    class AGTListUpdater extends AFacadeListUpdater<AGTFacade>
    {
        @Override
        protected List<String> getSyncedProperties()
        {
            return AGTTab.SYNCED_PROPERTIES;
        }

        @Override
        public OrderItemType getOrderItemType()
        {
            return OrderItemType.agtfacade;
        }

        @Override
        protected void fillFurnitureLinks(AGTFacade facade)
        {
            fillFurnitureLink(facade.getProfileType(), facade.getDowel(), AGTFurnitureType.agtdowel.name());

            AGTFurnitureDependence dependence = new AGTFurnitureDependence();
            dependence.setFacade(facade);

            facade.getRubber().setFurnitureType(dependence.getRubberType());
            facade.getRubber().setFurnitureCode(dependence.getRubberCode());

            facade.getGlue().setFurnitureType(dependence.getGlueType());
            facade.getGlue().setFurnitureCode(dependence.getGlueCode());

            facade.getDowel().setFurnitureType(dependence.getDowelType());
            facade.getDowel().setFurnitureCode(dependence.getDowelCode());

        }


        @Override
        public String[] getHiddenProperties()
        {
            return HIDDEN_PROPERTIES;
        }

        @Override
        public String[] getVisibleProperties()
        {
            return VISIBLE_PROPERTIES;
        }

        @Override
        public String[] getEditableProperties()
        {
            return EDITABLE_PROPERTIES;
        }

        @Override
        public ResourceMap getResourceMap()
        {
            return AGTTab.this.getResourceMap();
        }

        @Override
        protected TableCellEditor createTableCellEditorBy(Class propertyClass)
        {
            if (propertyClass == OrderFurniture.class)
            {
                TableCellEditor editor = super.createTableCellEditorBy(propertyClass);
                ATableCellEditorDecorator decorator = new ATableCellEditorDecorator()
                {
                    @Override
                    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
                    {
                        AGTFacade agtFacade = getList().get(row);
                        if (agtFacade != null)
                        {
                            AGTFurnitureDependence dependence = new AGTFurnitureDependence();
                            dependence.setFacade(agtFacade);
                            dependence.initFillingPopupEditor((PopupEditor) getSource());
                        }
                        return super.getTableCellEditorComponent(table, value, isSelected, row, column);
                    }
                };
                decorator.setSource(editor);
                return decorator;
            }
            return super.createTableCellEditorBy(propertyClass);
        }
    }

}
