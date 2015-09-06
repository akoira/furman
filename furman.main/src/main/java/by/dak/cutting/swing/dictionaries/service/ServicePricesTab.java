/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

/*
 * ServicePricesTab.java
 *
 * Created on 18.06.2009, 20:54:35
 */

package by.dak.cutting.swing.dictionaries.service;

import by.dak.cutting.swing.dictionaries.AbstractPricesTab;
import by.dak.cutting.swing.renderer.TableEditorsRenders;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.PriceAware;
import by.dak.persistence.entities.PriceEntity;
import by.dak.persistence.entities.Service;
import org.jdesktop.beansbinding.ELProperty;
import org.jdesktop.observablecollections.ObservableList;
import org.jdesktop.observablecollections.ObservableListListener;
import org.jdesktop.swingbinding.JTableBinding;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * @author admin
 */
public class ServicePricesTab extends AbstractPricesTab<Service>
{
    @Override
    protected void initColumnBindings(JTableBinding jTableBinding)
    {
        JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(ELProperty.create("${priceAware}"));
        columnBinding.setColumnName(getResourceMap().getString("table.column.material"));
        columnBinding.setColumnClass(PriceAware.class);
        columnBinding.setEditable(true);
    }

    @Override
    protected void initTableGui()
    {
        final TableEditorsRenders.ItemsComboBox boardDefsComboBox = new TableEditorsRenders.ItemsComboBox();
        getTablePrices().setDefaultEditor(PriceAware.class, new DefaultCellEditor(boardDefsComboBox)
        {
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
            {

                updateComboBox((PriceAware) value);
                return super.getTableCellEditorComponent(table, value, isSelected, row, column);
            }

            private void updateComboBox(PriceAware currentPriceAware)
            {
                List priceAwareList = null;
                switch (getValue().getServiceType().getMaterialType())
                {
                    case border:
                        priceAwareList = FacadeContext.getBorderDefFacade().loadAll();
                        break;
                    case board:
                        priceAwareList = FacadeContext.getBoardDefFacade().loadAll();
                        break;
                    case zprofile:
                        priceAwareList = FacadeContext.getZProfileTypeFacade().loadAll();
                        break;
                    case agtprofile:
                        priceAwareList = FacadeContext.getAGTTypeFacade().loadAll();
                        break;
                    default:
                        throw new IllegalArgumentException();
                }
                for (PriceEntity price : getPrices())
                {
                    PriceAware priceAware = price.getPriceAware();
                    priceAwareList.remove(priceAware);
                }
                if (currentPriceAware != null)
                {
                    priceAwareList.add(currentPriceAware);
                }
                boardDefsComboBox.getItems().clear();
                boardDefsComboBox.getItems().addAll(priceAwareList);
            }

        });

        final ObservableList<PriceEntity> list = (ObservableList<PriceEntity>) getPrices();
        list.addObservableListListener(new ObservableListListener()
        {
            @Override
            public void listElementsAdded(ObservableList list, int index, int length)
            {
                //updateComboBox(null);
            }

            @Override
            public void listElementsRemoved(ObservableList list, int index, List oldElements)
            {
                //updateComboBox(null);
            }

            @Override
            public void listElementReplaced(ObservableList list, int index, Object oldElement)
            {
                //updateComboBox(null);
            }

            @Override
            public void listElementPropertyChanged(ObservableList list, int index)
            {
            }
        });


    }


    @Override
    protected PriceEntity createEmptyPrice()
    {
        PriceEntity price = new PriceEntity();
        price.setPriced(getValue());
        return price;
    }

    @Override
    protected int getFirstPriceColumn()
    {
        return 1;
    }
}
