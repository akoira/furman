package by.dak.swing;

import java.beans.PropertyChangeListener;

/**
 * User: akoyro
 * Date: 23.08.2009
 * Time: 15:44:14
 */
public interface PropertyChangeInterface
{
    public void addPropertyChangeListener(PropertyChangeListener listener);

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);

    public void removePropertyChangeListener(PropertyChangeListener listener);

    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener);
}
