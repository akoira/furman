package by.dak.template.swing;

import by.dak.category.Category;
import by.dak.cutting.swing.AListTab;
import by.dak.template.TemplateOrder;
import org.jdesktop.application.Application;

/**
 * User: akoyro
 * Date: 19.03.11
 * Time: 15:51
 */
public class TemplateOrdersTab extends AListTab<TemplateOrder, Category>
{
    public TemplateOrdersTab()
    {
        TemplateOrdersUpdater updater = new TemplateOrdersUpdater()
        {

            @Override
            protected void init(Category category)
            {
                super.init(category);
                setVisibleProperties(new String[]{TemplateOrder.PROPERTY_name, TemplateOrder.PROPERTY_category});
                setHiddenProperties(new String[]{});
                setEditableProperties(new String[]{});
            }

            @Override
            public void adjustFilter()
            {
                getSearchFilter().addAscOrder(TemplateOrder.PROPERTY_category + "." + TemplateOrder.PROPERTY_id);
                getSearchFilter().addAscOrder(TemplateOrder.PROPERTY_name);
            }

            @Override
            public Class<TemplateOrder> getElementClass()
            {
                return TemplateOrder.class;
            }
        };
        updater.setResourceMap(Application.getInstance().getContext().getResourceMap(TemplateOrdersUpdater.class));
        getListNaviTable().setListUpdater(updater);
        getListNaviTable().setSortable(false);
    }

    @Override
    protected void initBindingListeners()
    {
    }
}
