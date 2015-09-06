package by.dak.cutting.swing.order.wizard;

import java.beans.PropertyChangeListener;

/**
 * User: akoyro
 * Date: 22.07.2010
 * Time: 22:54:22
 */
public interface ClearNextStepObserver
{
    public static final String PROPERTY_clearNextStep = "clearNextStep";

    public void addClearNextStepListener(PropertyChangeListener listener);

    public void removeClearNextStepListener(PropertyChangeListener listener);
}
