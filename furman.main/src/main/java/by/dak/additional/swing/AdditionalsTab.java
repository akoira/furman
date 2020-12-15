package by.dak.additional.swing;

import by.dak.additional.Additional;
import by.dak.additional.facade.AdditionalFacadeImpl;
import by.dak.cutting.CuttingApp;
import by.dak.cutting.SearchFilter;
import by.dak.cutting.swing.AListTab;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.OrderItem;
import by.dak.swing.table.AListUpdater;
import by.dak.utils.GenericUtils;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

/**
 * User: akoyro
 * Date: 19.06.2010
 * Time: 16:39:47
 */
public class AdditionalsTab extends AListTab<Additional, OrderItem>
{
    @Override
    public void setEditable(boolean editable) {
    }

    @Override
    public void setEnabled(boolean enabled) {
    }

    public AdditionalsTab()
    {
        setResourceMap(Application.getInstance().getContext().getResourceMap(Additional.class));
        setEditable(false);
        setEnabled(false);

    }

    @Override
    public void init()
    {

        AListUpdater<Additional> listUpdater = new AListUpdater<Additional>()
        {

            @Override
            public void update()
            {
                getList().clear();
                getList().addAll(FacadeContext.getFacadeBy(GenericUtils.getParameterClass(this.getClass(), 0)).loadAll(AdditionalFacadeImpl.getSearchFilterBy(getValue())));
                addNew();
            }

            @Override
            public int getCount()
            {
                return getList().size() - 1;
            }

            @Override
            public String[] getVisibleProperties()
            {
                return getEditableProperties();
            }

            @Override
            public String[] getEditableProperties()
            {
                return new String[]{"type", "name", "size", "price", "dialerPrice"};
            }

            @Override
            public ResourceMap getResourceMap()
            {
                return Application.getInstance().getContext().getResourceMap(getElementClass());
            }

            @Override
            public Additional addNew()
            {
                Additional element = null;
                try
                {
                    element = getElementClass().getConstructor(null).newInstance(null);
                    element.setOrderItem(getValue());
                    getList().add(element);
                }
                catch (Exception e)
                {
                    CuttingApp.getApplication().getExceptionHandler().handle(e);
                }
                return element;
            }

            @Override
            public Additional delete(Additional additional)
            {
                if (additional.hasId())
                {
                    FacadeContext.getAdditionalFacade().delete(additional);
                }
                getList().remove(additional);
                return additional;
            }

            @Override
            public Additional save(Additional additional)
            {
                FacadeContext.getAdditionalFacade().save(additional);
                return additional;
            }
        };

        listUpdater.setSearchFilter(SearchFilter.instanceUnbound());
        getListNaviTable().setListUpdater(listUpdater);
        super.init();
    }

    @Override
    protected void initBindingListeners()
    {
    }
}
