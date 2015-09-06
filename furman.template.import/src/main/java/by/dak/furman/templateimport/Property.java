package by.dak.furman.templateimport;

/**
 * User: akoyro
 * Date: 9/23/13
 * Time: 9:05 PM
 */
public class Property
{
    private String path;
    private boolean editable;
    private Class propertyClass;

    public boolean isEditable()
    {
        return editable;
    }

    public void setEditable(boolean editable)
    {
        this.editable = editable;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public static Property valueOf(String path, boolean editable)
    {
        Property property = new Property();
        property.setPath(path);
        property.setEditable(editable);
        property.setPropertyClass(String.class);
        return property;
    }

    public static Property valueOf(Class propertyClass, String path, boolean editable)
    {
        Property property = new Property();
        property.setPath(path);
        property.setEditable(editable);
        property.setPropertyClass(propertyClass);
        return property;
    }

    public Class getPropertyClass()
    {
        return propertyClass;
    }

    public void setPropertyClass(Class propertyClass)
    {
        this.propertyClass = propertyClass;
    }
}
