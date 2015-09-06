package by.dak.cutting.afacade.swing;

import by.dak.cutting.swing.DModPanel;
import by.dak.cutting.swing.order.wizard.ClearNextStepObserver;
import by.dak.persistence.entities.AOrder;
import by.dak.utils.BindingAdapter;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.observablecollections.ObservableList;
import org.jdesktop.observablecollections.ObservableListListener;

import java.beans.PropertyChangeListener;
import java.util.List;

/**
 * User: akoyro
 * Date: 07.07.2010
 * Time: 9:55:19
 */
public abstract class AFacadeModPanel extends DModPanel<AOrder> implements ClearNextStepObserver
{
    private AFacadeTab facadeTab;

    public AFacadeModPanel()
    {
        super();
        addTabs();
    }

    @Override
    protected void addTabs()
    {
        facadeTab = createFacadeTab();
        addTab(facadeTab);
        facadeTab.setWarningList(getWarningList());
        facadeTab.init();
        this.facadeTab.getListNaviTable().getBindingGroup().addBindingListener(new BindingAdapter()
        {
            @Override
            public void synced(Binding binding)
            {
                firePropertyChange(ClearNextStepObserver.PROPERTY_clearNextStep, null, Boolean.TRUE);
            }
        });
        facadeTab.getListUpdater().addObservableListListener(new ObservableListListener()
        {
            @Override
            public void listElementsAdded(ObservableList list, int index, int length)
            {
                firePropertyChange(ClearNextStepObserver.PROPERTY_clearNextStep, null, Boolean.TRUE);
            }

            @Override
            public void listElementsRemoved(ObservableList list, int index, List oldElements)
            {
                firePropertyChange(ClearNextStepObserver.PROPERTY_clearNextStep, null, Boolean.TRUE);
            }

            @Override
            public void listElementReplaced(ObservableList list, int index, Object oldElement)
            {
                firePropertyChange(ClearNextStepObserver.PROPERTY_clearNextStep, null, Boolean.TRUE);
            }

            @Override
            public void listElementPropertyChanged(ObservableList list, int index)
            {
                firePropertyChange(ClearNextStepObserver.PROPERTY_clearNextStep, null, Boolean.TRUE);
            }
        });
    }

    protected abstract AFacadeTab createFacadeTab();


    @Override
    public void addClearNextStepListener(final PropertyChangeListener listener)
    {
        addPropertyChangeListener(ClearNextStepObserver.PROPERTY_clearNextStep, listener);
    }

    @Override
    public void removeClearNextStepListener(PropertyChangeListener listener)
    {
        removePropertyChangeListener(ClearNextStepObserver.PROPERTY_clearNextStep, listener);
    }
}
