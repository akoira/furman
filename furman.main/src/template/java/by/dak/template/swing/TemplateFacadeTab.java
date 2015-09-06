package by.dak.template.swing;

import by.dak.cutting.afacade.swing.AFacadeListUpdater;
import by.dak.cutting.afacade.swing.AFacadeTab;
import by.dak.persistence.entities.predefined.OrderItemType;
import by.dak.template.TemplateFacade;
import by.dak.utils.validator.ValidatorAnnotationProcessor;
import com.jgoodies.validation.ValidationResult;
import org.jdesktop.application.ResourceMap;

import javax.swing.table.TableCellEditor;
import java.util.Collections;
import java.util.List;

/**
 * User: akoyro
 * Date: 06.09.2010
 * Time: 21:50:45
 */
public class TemplateFacadeTab extends AFacadeTab<TemplateFacade>
{
    public static final List<String> SYNCED_PROPERTIES = Collections.EMPTY_LIST;

    private static final String[] HIDDEN_PROPERTIES = new String[]{};

    private static final String[] EDITABLE_PROPERTIES = new String[]{TemplateFacade.PROPERTY_number,
            TemplateFacade.PROPERTY_name,
            TemplateFacade.PROPERTY_length,
            TemplateFacade.PROPERTY_width,
            TemplateFacade.PROPERTY_amount,
            TemplateFacade.PROPERTY_drilling,
    };

    private static final String[] VISIBLE_PROPERTIES = EDITABLE_PROPERTIES;


    @Override
    public void init()
    {
        setListUpdater(new ListUpdater());
        super.init();
    }

    @Override
    protected void validateElement(TemplateFacade element)
    {
        ValidationResult result = ValidatorAnnotationProcessor.getProcessor().validate(element);
        if (getWarningList() != null)
        {
            getWarningList().getModel().setResult(result);
        }
        if (!result.hasErrors())
        {
            getListNaviTable().getListUpdater().save(element);
        }
        if (!validateAll().hasErrors())
        {
            getListNaviTable().getListUpdater().addNew();
        }
    }

    class ListUpdater extends AFacadeListUpdater<TemplateFacade>
    {
        @Override
        protected List<String> getSyncedProperties()
        {
            return TemplateFacadeTab.SYNCED_PROPERTIES;
        }

        @Override
        public OrderItemType getOrderItemType()
        {
            return OrderItemType.templateFacade;
        }

        @Override
        protected void fillFurnitureLinks(TemplateFacade facade)
        {
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
            return TemplateFacadeTab.this.getResourceMap();
        }

        @Override
        protected TableCellEditor createTableCellEditorBy(Class propertyClass)
        {
            return super.createTableCellEditorBy(propertyClass);
        }
    }

}
