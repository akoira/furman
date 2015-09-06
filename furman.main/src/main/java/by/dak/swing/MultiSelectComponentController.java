package by.dak.swing;

import java.beans.PropertyChangeSupport;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * User: akoyro
 * Date: 14.05.2009
 * Time: 10:43:22
 */
public class MultiSelectComponentController
{

    private MultiSelectComponent multiSelectComponent;

    private List elements;

    private SortableListModel sourceModel;

    private SortableListModel targetModel;

    private Comparator comparator;

    private SourceTargetConverter sourceTargetConverter = new SourceTargetConverter()
    {

        @Override
        public Object source(Object target)
        {
            return target;
        }

        @Override
        public Object target(Object source)
        {
            return source;
        }
    };

    public SourceTargetConverter getSourceTargetConverter()
    {
        return sourceTargetConverter;
    }

    public void setSourceTargetConverter(SourceTargetConverter sourceTargetConverter)
    {
        this.sourceTargetConverter = sourceTargetConverter;
    }

    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public MultiSelectComponent getMultiSelectComponent()
    {
        return multiSelectComponent;
    }

    public void setMultiSelectComponent(MultiSelectComponent multiSelectComponent)
    {
        this.multiSelectComponent = multiSelectComponent;
    }

    public List getElements()
    {
        return elements;
    }

    public void setElements(List elements)
    {
        this.elements = elements;

        this.sourceModel = new SortableListModel(getComparator());
        for (Object element : this.elements)
        {
            this.sourceModel.addElement(element);
        }

        this.targetModel = new SortableListModel(getComparator());
        this.multiSelectComponent.getSourceList().setModel(sourceModel);
        this.multiSelectComponent.getTargetList().setModel(targetModel);
    }

    void addSelectedElements(List objects)
    {
        for (Object object : objects)
        {
            this.sourceModel.removeElement(object);
            this.targetModel.addElement(sourceTargetConverter.target(object));
        }
        getPropertyChangeSupport().firePropertyChange("selectedElements", null, elements);
    }

    void addSelectedTargetElements(List objects)
    {
        for (Object object : objects)
        {
            this.sourceModel.removeElement(sourceTargetConverter.source(object));
            this.targetModel.addElement(object);
        }
        getPropertyChangeSupport().firePropertyChange("selectedElements", null, elements);
    }


    void removeSelectedElements(List elements)
    {
        for (Object object : elements)
        {
            this.targetModel.removeElement(object);
            this.sourceModel.addElement(sourceTargetConverter.source(object));
        }
        getPropertyChangeSupport().firePropertyChange("selectedElements", null, elements);
    }

    public List getSelectedElements()
    {
        return Arrays.asList(this.targetModel.getElements());
    }

    public void setSelectedElements(List elements)
    {
        removeSelectedElements(getSelectedElements());
        addSelectedTargetElements(elements);
    }


    public Comparator getComparator()
    {
        return comparator;
    }

    public void setComparator(Comparator comparator)
    {
        this.comparator = comparator;
    }

    public PropertyChangeSupport getPropertyChangeSupport()
    {
        return propertyChangeSupport;
    }
}
