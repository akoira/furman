/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */
package by.dak.cutting.swing.store.tabs;

import by.dak.cutting.swing.BaseTabPanel;
import by.dak.cutting.swing.renderer.EntityListRenderer;
import by.dak.persistence.entities.PriceAware;
import by.dak.swing.MultiSelectComponent;
import by.dak.swing.SourceTargetConverter;
import org.jdesktop.application.ResourceMap;

import java.awt.*;
import java.util.Comparator;
import java.util.List;

/**
 * @author admin
 */
public abstract class AbstractBoardDefPricesTab<E extends PriceAware> extends BaseTabPanel
{

    private MultiSelectComponent multiSelectComponent;

    private E entity;

    public AbstractBoardDefPricesTab(ResourceMap resourceMap)
    {
        setLayout(new BorderLayout());
        multiSelectComponent = new MultiSelectComponent();
        multiSelectComponent.setSourceRenderer(new EntityListRenderer());
        multiSelectComponent.setTargetRenderer(new EntityListRenderer());
        multiSelectComponent.setResourceMap(resourceMap);
        add(multiSelectComponent, BorderLayout.CENTER);

    }

    public void setComparator(Comparator comparator)
    {
        multiSelectComponent.setComparator(comparator);
    }

    public Comparator getComparator()
    {
        return multiSelectComponent.getComparator();
    }

    public SourceTargetConverter getSourceTargetConverter()
    {
        return multiSelectComponent.getSourceTargetConverter();
    }

    public void setSourceTargetConverter(SourceTargetConverter sourceTargetConverter)
    {
        multiSelectComponent.setSourceTargetConverter(sourceTargetConverter);
    }

    public E getEntiry()
    {
        return entity;
    }

    public void setEntiry(E entity)
    {
        this.entity = entity;
        multiSelectComponent.setElements(provideSourceElements());
        multiSelectComponent.setSelectedElements(provideSelectedTargetElements());
        multiSelectComponent.setComparator(getComparator());
        multiSelectComponent.setSourceTargetConverter(getSourceTargetConverter());
    }

    public MultiSelectComponent getMultiSelectComponent()
    {
        return multiSelectComponent;
    }

    protected abstract List provideSourceElements();

    protected abstract List provideSelectedTargetElements();
}
