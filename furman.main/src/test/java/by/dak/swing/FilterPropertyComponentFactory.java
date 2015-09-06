package by.dak.swing;

/**
 * User: akoyro
 * Date: 23.08.2009
 * Time: 19:56:16
 */
public class FilterPropertyComponentFactory
{
    public FilterPropertyComponent getFilterPropertyComponent(String property)
    {
        FilterPropertyComponent component = new FilterPropertyComponent()
        {
            @Override
            public void bind()
            {

            }
        };
        PropertyFilter propertyFilter = new PropertyFilter();
        propertyFilter.setProperty(property);
        component.setFilterProperty(propertyFilter);
        component.init();
        return component;
    }
}
