package by.dak.swing;

import by.dak.cutting.CuttingApp;
import org.apache.commons.beanutils.BeanUtils;
import org.jdesktop.application.Action;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 25.11.2010
 * Time: 19:40:14
 * To change this template use File | Settings | File Templates.
 */
public class FilterPanel<V> extends AValueTab<V>
{
    public static final String ACTION_applyFilter = "applyFilter";
    public static final String ACTION_resetFilter = "resetFilter";
    public static final String ACTION_printFilter = "printFilter";

    @Action
    public void applyFilter()
    {
        firePropertyChange(ACTION_applyFilter, null, getValue());
    }

    @Action
    public void resetFilter()
    {
        String[] properties = getVisibleProperties();
        for (String property : properties)
        {
            try
            {
                BeanUtils.setProperty(getValue(), property, null);
            }
            catch (Throwable e)
            {
                CuttingApp.getApplication().getExceptionHandler().handle(e);
            }
        }
        firePropertyChange(ACTION_resetFilter, null, getValue());
    }

    @Action
    public void printFilter()
    {
        firePropertyChange(ACTION_printFilter, null, getValue());
    }
}
