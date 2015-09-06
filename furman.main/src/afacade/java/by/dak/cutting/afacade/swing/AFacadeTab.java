package by.dak.cutting.afacade.swing;

import by.dak.cutting.afacade.AFacade;
import by.dak.cutting.afacade.facade.AFacadeFacade;
import by.dak.cutting.afacade.facade.AProfileColorFacade;
import by.dak.cutting.afacade.facade.AProfileTypeFacade;
import by.dak.cutting.swing.AListTab;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.AOrder;
import by.dak.utils.BindingAdapter;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.observablecollections.ObservableList;
import org.jdesktop.observablecollections.ObservableListListener;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

/**
 * User: akoyro
 * Date: 19.07.2010
 * Time: 19:42:17
 */
public abstract class AFacadeTab<F extends AFacade> extends AListTab<F, AOrder>
{
    private AFacadeListUpdater<F> listUpdater;
    private AProfileTypeFacade typeFacade;
    private AProfileColorFacade colorFacade;


    @Override
    protected void valueChanged()
    {
        getListUpdater().setOrder(getValue());
    }

    @Override
    protected void initBindingListeners()
    {
        getListNaviTable().getBindingGroup().addBindingListener(new BindingAdapter()
        {
            @Override
            public void synced(Binding binding)
            {
                if (getListUpdater().getSyncedProperties().contains(binding.getName()))
                {
                    F facade = (F) binding.getSourceObject();
                    if (facade.getProfileType() != null)
                    {
                        getListUpdater().fillFurnitureLinks(facade);
                    }
                    if (binding != null && binding.getName() != null && binding.getName().equals(AFacade.PROPERTY_profileType))
                    {
                        facade.setProfileColor(null);
                    }
                }
            }
        });
    }


    @Override
    public void init()
    {
        getListUpdater().setEditorProvider(new TypeCodeTableEditorProvider<F>(getListUpdater().getList(),
                getTypeFacade(),
                getColorFacade()));

        getListNaviTable().setListUpdater(getListUpdater());
        super.init();
        getListNaviTable().getListUpdater().addObservableListListener(new ObservableListListener()
        {
            @Override
            public void listElementsAdded(ObservableList list, int index, int length)
            {
                getListNaviTable().getTable().setRowSelectionInterval(index, index);
                getListNaviTable().getTable().setColumnSelectionInterval(4, 4);
            }

            @Override
            public void listElementsRemoved(ObservableList list, int index, List oldElements)
            {
            }

            @Override
            public void listElementReplaced(ObservableList list, int index, Object oldElement)
            {
            }

            @Override
            public void listElementPropertyChanged(ObservableList list, int index)
            {
            }

        });

        addPropertyChangeListener("value", new PropertyChangeListener()
        {
            @Override
            public void propertyChange(PropertyChangeEvent evt)
            {
                getListNaviTable().reload();
            }
        });
    }

    public AProfileTypeFacade getTypeFacade()
    {
        return typeFacade;
    }

    public void setTypeFacade(AProfileTypeFacade typeFacade)
    {
        this.typeFacade = typeFacade;
    }

    public AProfileColorFacade getColorFacade()
    {
        return colorFacade;
    }

    public void setColorFacade(AProfileColorFacade colorFacade)
    {
        this.colorFacade = colorFacade;
    }

    public AFacadeListUpdater<F> getListUpdater()
    {
        return listUpdater;
    }

    public void setListUpdater(AFacadeListUpdater<F> listUpdater)
    {
        this.listUpdater = listUpdater;
    }

    @Override
    protected void validateElement(F element)
    {
        if (element.getProfileType() != null && element.getProfileColor() != null)
        {
            ((AFacadeFacade<F>) FacadeContext.getFacadeBy(element.getClass())).getCalculator().calculate(element);
            super.validateElement(element);
        }
    }
}
