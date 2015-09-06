package by.dak.cutting.afacade.swing;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.afacade.AFacade;
import by.dak.cutting.afacade.AProfileColor;
import by.dak.cutting.afacade.AProfileType;
import by.dak.cutting.afacade.converter.OrderFurniture2StringConverter;
import by.dak.cutting.afacade.facade.AFacadeFacade;
import by.dak.cutting.swing.AEntityEditorTab;
import by.dak.cutting.swing.table.CellContext;
import by.dak.cutting.swing.table.PopupEditor;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.*;
import by.dak.persistence.entities.predefined.OrderItemType;
import by.dak.persistence.entities.types.FurnitureCode;
import by.dak.persistence.entities.types.FurnitureType;
import by.dak.swing.ActionsPanel;
import by.dak.swing.table.AListUpdater;
import by.dak.swing.table.AbstractComponentProvider;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * User: akoyro
 * Date: 06.09.2010
 * Time: 21:07:33
 */
public abstract class AFacadeListUpdater<F extends AFacade> extends AListUpdater<F>
{
    public static final List<String> SYNCED_PROPERTIES = Collections.unmodifiableList(Arrays.asList(new String[]{AFacade.PROPERTY_profileType, AFacade.PROPERTY_profileColor}));

    private AOrder order;

    private TypeCodeTableEditorProvider<F> editorProvider;

    public abstract OrderItemType getOrderItemType();


    protected AFacadeListUpdater()
    {
        setSearchFilter(SearchFilter.instanceUnbound());
    }

    @Override
    public void adjustFilter()
    {
        getSearchFilter().eq(OrderItem.PROPERTY_type, getOrderItemType());
        getSearchFilter().eq(OrderItem.PROPERTY_order, getOrder());
        getSearchFilter().addAscOrder(OrderItem.PROPERTY_number);
    }

    @Override
    public void update()
    {
        getList().clear();
        if (getOrder() == null)
        {
            return;
        }
        List<F> facades = FacadeContext.getFacadeBy(getElementClass()).loadAll(getSearchFilter());

        for (F facade : facades)
        {
            ((AFacadeFacade<AFacade>) FacadeContext.getFacadeBy(getElementClass())).fillTransients(facade);
        }
        getList().addAll(facades);
        addNew();
    }

    @Override
    public String[] getVisibleProperties()
    {
        return getEditableProperties();
    }

    @Override
    public String[] getEditableProperties()
    {
        return new String[]{AFacade.PROPERTY_number,
                AFacade.PROPERTY_name,
                AFacade.PROPERTY_profileType,
                AFacade.PROPERTY_profileColor,
                AFacade.PROPERTY_length,
                AFacade.PROPERTY_width,
                AFacade.PROPERTY_amount,
                AFacade.PROPERTY_filling
        };
    }

    @Override
    public F addNew()
    {
        F element = FacadeContext.createNewInstance(getElementClass());
        element.setNumber((long) getList().size() + 1);
        element.setName(getResourceMap().getString("default.name"));
        element.setAmount(0);
        if (getList().size() > 0)
        {
            F facade = getList().get(getList().size() - 1);
            element.setProfileType(facade.getProfileType());
            element.setProfileColor(facade.getProfileColor());
            fillFurnitureLinks(element);
        }
        element.setOrder(getOrder());
        getList().add(element);
        return element;
    }

    @Override
    public F delete(F facade)
    {
        if (facade.hasId())
        {
            FacadeContext.getFacadeBy(getElementClass()).delete(facade);
        }
        getList().remove(facade);
        return facade;
    }

    @Override
    public F save(F facade)
    {
        facade.setOrder(getOrder());
        FacadeContext.getFacadeBy(getElementClass()).save(facade);
        return facade;
    }

    @Override
    public TableCellRenderer getTableCellRenderer(Class propertyClass)
    {
        TableCellRenderer renderer = super.getTableCellRenderer(propertyClass);
        if (renderer == null)
        {
            if (propertyClass == OrderFurniture.class)
            {
                renderer = new DefaultTableCellRenderer()
                {
                    OrderFurniture2StringConverter converter = new OrderFurniture2StringConverter();

                    @Override
                    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
                    {
                        if (value != null)
                        {
                            value = converter.convert((OrderFurniture) value);
                        }
                        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    }
                };
            }
            if (renderer != null)
            {
                rendererClassMap.put(propertyClass, renderer);
            }
        }
        return renderer;
    }

    @Override
    public TableCellEditor getTableCellEditor(Class propertyClass)
    {
        TableCellEditor editor = super.getTableCellEditor(propertyClass);
        if (editor == null)
        {
            editor = createTableCellEditorBy(propertyClass);
            if (editor != null)
            {
                editorClassMap.put(propertyClass, editor);
            }
        }
        return editor;
    }

    protected TableCellEditor createTableCellEditorBy(Class propertyClass)
    {
        TableCellEditor editor = null;

        if (AProfileType.class.isAssignableFrom(propertyClass))
        {
            editor = getEditorProvider().getTypeCellEditor();
        }
        else if (AProfileColor.class.isAssignableFrom(propertyClass))
        {
            editor = getEditorProvider().getCodeCellEditor();
        }
        else if (propertyClass == OrderFurniture.class)
        {
            editor = createPopupEditor(new AFillingTab());
        }
        else if (propertyClass == FurnitureLink.class)
        {
            editor = createPopupEditor(new FurnitureLinkTab());
        }
        return editor;
    }

    protected <T extends AOrderDetail> PopupEditor createPopupEditor(final AEntityEditorTab<T> linkTab)
    {
        linkTab.init();
        final ActionsPanel<AEntityEditorTab<T>> tab = new ActionsPanel<AEntityEditorTab<T>>();
        AbstractComponentProvider<T> componentProvider = new AbstractComponentProvider<T>()
        {
            @Override
            public JComponent getPopupComponent()
            {
                return tab;
            }

            @Override
            public void setCellContext(CellContext cellContext)
            {
                T furniture = (T) cellContext.getValue();
                tab.getContentComponent().setValue(furniture);
            }

            @Override
            public T getValue()
            {
                return tab.getContentComponent().getValue();
            }
        };
        PopupEditor popupEditor = new PopupEditor(componentProvider)
        {
            @Override
            public boolean stopCellEditing()
            {
                linkTab.getBindingGroup().unbind();
                return super.stopCellEditing();
            }

            @Override
            public void cancelCellEditing()
            {
                linkTab.getBindingGroup().unbind();
                super.cancelCellEditing();
            }
        };


        tab.setContentComponent(linkTab);
        ActionMap map = new ActionMap();
        map.put("ok", componentProvider.getCommitAction());
        map.put("clear", getClearAction(componentProvider));
        tab.setSourceActionMap(map);
        tab.setActions("ok", "clear");
        tab.init();


        return popupEditor;
    }

    private <T extends AOrderDetail> Action getClearAction(final AbstractComponentProvider<T> provider)
    {
        AbstractAction action = new AbstractAction(getResourceMap().getString("action.clear.name"))
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                provider.getValue().clear();
                provider.getCommitAction().actionPerformed(e);
            }
        };
        return action;
    }

    protected abstract void fillFurnitureLinks(F facade);

    protected void fillFurnitureLink(AProfileType profileType, FurnitureLink link, String linkName)
    {
        FurnitureType type = (FurnitureType) FacadeContext.getFurnitureTypeLinkFacade().findBy(profileType, linkName);
        if (type != null)
        {
            link.setFurnitureType(type);
            List<FurnitureCode> codes = FacadeContext.getFurnitureCodeFacade().findBy(type);
            if (codes.size() == 1)
            {
                link.setFurnitureCode(codes.get(0));
            }
            else
            {
                link.setFurnitureCode(null);
            }
        }
        else
        {
            link.setFurnitureType(null);
        }
    }


    public AOrder getOrder()
    {
        return order;
    }

    public void setOrder(AOrder order)
    {
        this.order = order;
        adjustFilter();
    }

    public TypeCodeTableEditorProvider<F> getEditorProvider()
    {
        return editorProvider;
    }

    public void setEditorProvider(TypeCodeTableEditorProvider<F> editorProvider)
    {
        this.editorProvider = editorProvider;
    }

    /**
     * Возвращает список properties при изменение которых должен быть перещитан links
     *
     * @return
     */
    protected List<String> getSyncedProperties()
    {
        return SYNCED_PROPERTIES;
    }


}