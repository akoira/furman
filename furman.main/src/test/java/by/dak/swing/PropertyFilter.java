package by.dak.swing;

import org.hibernate.criterion.Criterion;

import java.beans.PropertyChangeSupport;

/**
 * User: akoyro
 * Date: 23.08.2009
 * Time: 15:39:38
 */
public class PropertyFilter
{
    private Criterion criterion;
    private String property;
    private Object value;

    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    public Criterion getCriterion()
    {
        return criterion;
    }

    public void setCriterion(Criterion criterion)
    {
        Criterion old = this.criterion;
        this.criterion = criterion;
        support.firePropertyChange("criterion", old, criterion);
    }

    public String getProperty()
    {
        return property;
    }

    public void setProperty(String property)
    {
        String old = this.property;
        this.property = property;
        support.firePropertyChange("property", old, property);
    }

    public Object getValue()
    {
        return value;
    }

    public void setValue(Object value)
    {
        Object old = value;
        this.value = value;
        support.firePropertyChange("value", old, value);
    }
}
