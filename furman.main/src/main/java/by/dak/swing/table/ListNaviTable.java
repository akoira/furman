package by.dak.swing.table;

import by.dak.cutting.DialogShowers;
import by.dak.cutting.SearchFilter;
import by.dak.cutting.swing.NaviTable;
import by.dak.cutting.swing.TableEventDelegator;
import by.dak.cutting.swing.renderer.EntityTableRenderer;
import by.dak.utils.BindingUtils;
import net.coderazzi.filters.gui.IFilterHeaderObserver;
import net.coderazzi.filters.gui.TableFilterHeader;
import net.coderazzi.filters.gui.editor.FilterEditor;
import org.apache.commons.lang.StringUtils;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.swingbinding.JTableBinding;
import org.jdesktop.swingbinding.SwingBindings;
import org.jdesktop.swingx.table.TableColumnExt;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * User: akoyro
 * Date: 26.11.2009
 * Time: 12:03:22
 */
public class ListNaviTable<E> extends NaviTable
{
    private BindingGroup bindingGroup = new BindingGroup();
    private String[] elementProperties;
    private ResourceMap resourceMap;
    private ListUpdater<E> listUpdater;

    private TableEventDelegator eventDelegator;

    private Class<E> elementClass;

    private IFilterHeaderObserver tableFilterHeaderObserver = new TableFilterHeaderObserver();

    private Map<String, JTableBinding.ColumnBinding> columnBindingMap = new HashMap<String, JTableBinding.ColumnBinding>();

    private PopupMenuHelper popupMenuHelper;

    private TableColumnModelExtAdapter tableColumnModelExtAdapter = new TableColumnModelExtAdapter()
    {

        @Override
        public void columnAdded(TableColumnModelEvent e)
        {
            updateHiddenProperties();
        }

        @Override
        public void columnRemoved(TableColumnModelEvent e)
        {
            updateHiddenProperties();
        }
    };


    public ListNaviTable()
    {
        setShowFilterHeader(true);
        popupMenuHelper = new PopupMenuHelper(getTable());
    }

    /**
     * Метод вызывается после того как все установлено
     */
    public void init()
    {
        columnBindingMap.clear();

        getTableFilterHeader().removeHeaderObserver(tableFilterHeaderObserver);

        //should be removed to avoid false invoking
        getTable().getColumnModel().removeColumnModelListener(tableColumnModelExtAdapter);


        getBindingGroup().unbind();
        bindingGroup = new BindingGroup();

        setSearchFilter(new SearchFilter());

        String[] properties = getElementProperties();

        if (getListUpdater() != null)
        {
            setSearchFilter(getListUpdater().getSearchFilter());
            JTableBinding jTableBinding = SwingBindings.createJTableBinding(AutoBinding.UpdateStrategy.READ_WRITE,
                    getListUpdater().getList(), getTable());

            List<String> editableProperties = Arrays.asList(getListUpdater().getEditableProperties());
            for (String prop : properties)
            {
                JTableBinding.ColumnBinding columnBinding = BindingUtils.createColumnBinding(jTableBinding, prop, getElementClass(), getResourceMap(), editableProperties.contains(prop));
                getListUpdater().adjustColumnBinding(columnBinding);
                columnBindingMap.put(prop, columnBinding);
            }
            getBindingGroup().addBinding(jTableBinding);
        }

        initEnvironment();
        if (getListUpdater() != null)
        {
            reload();
            if (getListUpdater().getNewEditDeleteActions() != null)
            {
                getListUpdater().getNewEditDeleteActions().setSelectedElement(null);
            }
            getListUpdater().initAdditionalTableControls(this);
        }


        getBindingGroup().bind();

        initEditorsRenderes();


        if (getListUpdater() != null)
        {
            if (getListUpdater() instanceof AListUpdater)
            {
                ((AListUpdater) getListUpdater()).loadProperties();
            }
            hideColumns(getListUpdater().getHiddenProperties());
            getTable().getColumnModel().addColumnModelListener(tableColumnModelExtAdapter);
        }

        getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener()
        {
            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                if (getListUpdater() != null && getListUpdater().getNewEditDeleteActions() != null)
                {
                    ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                    int index = lsm.getLeadSelectionIndex();
                    if (getListUpdater().getList().size() > index && !e.getValueIsAdjusting())
                    {
                        if (index > -1)
                        {
                            getListUpdater().getNewEditDeleteActions().setSelectedElement(getListUpdater().getList().get(index));
                        }
                        else
                        {
                            getListUpdater().getNewEditDeleteActions().setSelectedElement(null);
                        }
                    }
                }
            }
        });


        initTableFilterHeader();
        getTable().setEventDelegator(getEventDelegator());
    }

    private void hideColumns(String... columns)
    {
        for (String column : columns)
        {
            TableColumnExt tableColumnExt = getTable().getColumnExt(resourceMap.getString(BindingUtils.PREFIX_TABLE_COLUMN_KEY + column));
            if (tableColumnExt != null)
            {
                tableColumnExt.setVisible(false);
            }

        }
    }

    private void updateHiddenProperties()
    {
        if (getListUpdater() != null && getListUpdater() instanceof AListUpdater)
        {
            String[] visibleProperties = getListUpdater().getVisibleProperties();
            List<String> hiddenProperties = new ArrayList<String>();
            for (String visibleProperty : visibleProperties)
            {
                TableColumnExt tableColumnExt = getTable().getColumnExt(resourceMap.getString(BindingUtils.PREFIX_TABLE_COLUMN_KEY + visibleProperty));
                if (!tableColumnExt.isVisible())
                {
                    hiddenProperties.add(visibleProperty);
                }
            }
            String[] result = new String[hiddenProperties.size()];
            hiddenProperties.toArray(result);
            ((AListUpdater) getListUpdater()).setHiddenProperties(result);
            ((AListUpdater) getListUpdater()).saveProperties();
        }
    }


    private void initTableFilterHeader()
    {
        if (getListUpdater() != null &&
                getTable().getColumnCount() == getListUpdater().getVisibleProperties().length &&
                getTableFilterHeader().getTable() != null)
        {
            String[] properties = getListUpdater().getVisibleProperties();
            for (int i = 0; i < properties.length; i++)
            {
                getTableFilterHeader().setTableCellRenderer(i, new EntityTableRenderer());
            }
            getTableFilterHeader().addHeaderObserver(tableFilterHeaderObserver);
        }
    }

    private void initEditorsRenderes()
    {
        if (getListUpdater() != null)
        {
            String[] properties = getElementProperties();
            List<String> editableProperties = Arrays.asList(getListUpdater().getEditableProperties());
            for (String prop : properties)
            {
                if (editableProperties.contains(prop))
                {
                    initEditorBy(prop);
                }
                initRenderer(prop);
                getListUpdater().adjustTableColumn(getTableColumn(prop));
            }
        }
    }

    private void initRenderer(String property)
    {
        Class propClass = columnBindingMap.get(property).getColumnClass();
        TableCellRenderer renderer = getListUpdater().getTableCellRenderer(propClass);
        if (renderer != null)
        {
            getTable().setDefaultRenderer(propClass, renderer);
        }

        TableColumn tableColumn = getTableColumn(property);
        renderer = getListUpdater().getTableCellRenderer(property);
        if (renderer != null)
        {
            tableColumn.setCellRenderer(renderer);
        }
    }

    private void initEditorBy(String property)
    {
        Class propClass = columnBindingMap.get(property).getColumnClass();
        TableCellEditor editor = getListUpdater().getTableCellEditor(propClass);
        if (editor != null)
        {
            getTable().setDefaultEditor(propClass, editor);
        }

        TableColumn tableColumn = getTableColumn(property);
        editor = getListUpdater().getTableCellEditor(property);
        if (editor != null)
        {
            tableColumn.setCellEditor(editor);
        }
    }

    private TableColumn getTableColumn(String property)
    {
        String identifier = resourceMap != null ? resourceMap.getString(BindingUtils.PREFIX_TABLE_COLUMN_KEY + property) : property;
        TableColumn tableColumn = getTable().getColumn(identifier);
        return tableColumn;
    }

    private void initEnvironment()
    {
    }


    public BindingGroup getBindingGroup()
    {
        return bindingGroup;
    }


    protected Class<E> getElementClass()
    {
        if (elementClass == null)
        {
            ParameterizedType parameterizedType =
                    (ParameterizedType) getClass().getGenericSuperclass();
            elementClass = (Class<E>) parameterizedType.getActualTypeArguments()[0];
        }
        return elementClass;
    }

    private void setElementClass(Class<E> elementClass)
    {
        this.elementClass = elementClass;
    }


    public String[] getElementProperties()
    {
        return elementProperties;
    }

    private void setElementProperties(String[] elementProperties)
    {
        this.elementProperties = elementProperties;
    }

    public ResourceMap getResourceMap()
    {
        return resourceMap;
    }

    public void setResourceMap(ResourceMap resourceMap)
    {
        this.resourceMap = resourceMap;
    }

    public ListUpdater<E> getListUpdater()
    {
        return listUpdater;
    }

    public void setListUpdater(ListUpdater<E> listUpdater)
    {
        ListUpdater<E> old = this.listUpdater;
        this.listUpdater = listUpdater;

        setResourceMap(listUpdater != null ? this.listUpdater.getResourceMap() : null);
        setElementClass(listUpdater != null ? this.listUpdater.getElementClass() : null);
        setElementProperties(listUpdater != null ? this.listUpdater.getVisibleProperties() : null);
        if (this.listUpdater instanceof AListUpdater)
        {
            ((AListUpdater) this.listUpdater).setListNaviTable(this);
        }
        if (getListUpdater() != null)
        {
            initTablePopupMenu();
        }
        firePropertyChange("listUpdater", old, listUpdater);
    }


    private void initTablePopupMenu()
    {
        popupMenuHelper.setActionMap(getListUpdater().getActionMap());
        popupMenuHelper.setActions(getListUpdater().getActions());
        popupMenuHelper.init();
    }


    public TableEventDelegator getEventDelegator()
    {
        if (eventDelegator == null)
        {
            eventDelegator = new TableEventDelegator()
            {
                @Override
                public void onMouseClick()
                {
                }

                @Override
                public void onMouseDbClick()
                {
                    if (getListUpdater() != null &&
                            getListUpdater().getNewEditDeleteActions() != null)
                    {
                        getListUpdater().getNewEditDeleteActions().openValue();
                    }
                }

                @Override
                public void onMouseRight()
                {
                }

                @Override
                public void onMouseLeft()
                {
                }

                @Override
                public void onKeyDelete()
                {
                    if (getListUpdater() != null &&
                            getListUpdater().getNewEditDeleteActions() != null)
                    {
                        getListUpdater().getNewEditDeleteActions().deleteValue();
                    }
                }

                @Override
                public void updateGrid()
                {
                    if (getListUpdater() != null)
                    {
                        getListUpdater().update();
                    }
                }
            };
        }
        return eventDelegator;
    }

    /**
     * Load data after change Search filter content
     */
    public void reload()
    {
        SwingWorker swingWorker = new SwingWorker()
        {
            @Override
            protected Object doInBackground() throws Exception
            {
                if (getListUpdater() != null)
                {

                    Runnable runnable = new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            setSearchFilter(getListUpdater().getSearchFilter());
                            getListUpdater().update();
                            setSize(getListUpdater().getCount());
                        }
                    };

                    SwingUtilities.invokeLater(runnable);
                }
                return null;
            }
        };

        DialogShowers.showWaitDialog(swingWorker, this);
    }

    private class TableFilterHeaderObserver implements IFilterHeaderObserver
    {
        @Override
        public void tableFilterEditorCreated(TableFilterHeader header, FilterEditor editor)
        {

        }

        @Override
        public void tableFilterEditorExcluded(TableFilterHeader header, FilterEditor editor)
        {

        }

        @Override
        public void tableFilterUpdated(final TableFilterHeader header, final FilterEditor editor)
        {
            Runnable runnable = new Runnable()
            {
                public void run()
                {
                    int column = editor.getFilterPosition();
                    String[] properties = getElementProperties();
                    if (column < properties.length)
                    {
                        String property = properties[column];
                        fillFilterColumn(header, getListUpdater().getSearchFilter(), property, column);
                    }
                    reload();
                }
            };
            SwingUtilities.invokeLater(runnable);
        }


        private void fillFilterColumn(TableFilterHeader header, SearchFilter searchFilter, String property, int column)
        {
            searchFilter.removeCriterion(property);
            FilterEditor editor = header.getFilterEditor(column);
            if (editor != null &&
                    !isEmpty(editor.getContent()) &&
                    getTable().getModel().getColumnClass(column).isAssignableFrom(editor.getContent().getClass()

                    ))
            {

                searchFilter.eq(property, editor.getContent());
            }
        }

        private boolean isEmpty(Object o)
        {
            return o == null || StringUtils.isEmpty(o.toString());
        }
    }


    @Override
    protected void adjustAdditional(int row, int column, EventObject e)
    {
        if (getListUpdater() != null && getListUpdater().getNewEditDeleteActions() != null)
        {
            getListUpdater().getNewEditDeleteActions().setSelectedElement(getListUpdater().getList().get(row));
        }
    }
}
