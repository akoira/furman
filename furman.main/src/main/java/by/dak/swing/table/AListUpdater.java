package by.dak.swing.table;

import by.dak.cutting.SearchFilter;
import by.dak.persistence.FacadeContext;
import by.dak.utils.GenericUtils;
import org.apache.commons.lang.StringUtils;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.observablecollections.ObservableCollections;
import org.jdesktop.observablecollections.ObservableList;
import org.jdesktop.observablecollections.ObservableListListener;
import org.jdesktop.swingbinding.JTableBinding;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * User: akoyro
 * Date: 03.12.2009
 * Time: 13:37:54
 */
public abstract class AListUpdater<E> implements ListUpdater<E>
{

    public static final String[] EMTRY_STRING_ARRAY = new String[0];

    private ResourceMap resourceMap = Application.getInstance().getContext().getResourceMap(getClass());


    private Class<E> elementClass;

    private String name;

    private ObservableList<E> list = ObservableCollections.observableList(new ArrayList<E>());

    private SearchFilter searchFilter;

    protected final Map<String, TableCellEditor> editorPropertyMap = new HashMap<String, TableCellEditor>();
    protected final Map<Class, TableCellEditor> editorClassMap = new HashMap<Class, TableCellEditor>();

    protected final Map<String, TableCellRenderer> rendererPropertyMap = new HashMap<String, TableCellRenderer>();
    protected final Map<Class, TableCellRenderer> rendererClassMap = new HashMap<Class, TableCellRenderer>();


    private ListNaviTable<E> listNaviTable;

    private NewEditDeleteActions<E> newEditDeleteActions;


    private String[] editableProperties = EMTRY_STRING_ARRAY;
    private String[] hiddenProperties = EMTRY_STRING_ARRAY;
    private String[] visibleProperties = EMTRY_STRING_ARRAY;


    private ActionMap actionMap;
    private String[] actions;


    @Override
    public NewEditDeleteActions<E> getNewEditDeleteActions()
    {
        return newEditDeleteActions;
    }

    public void setNewEditDeleteActions(NewEditDeleteActions<E> newEditDeleteActions)
    {
        this.newEditDeleteActions = newEditDeleteActions;
    }


    @Override
    public void initAdditionalTableControls(ListNaviTable listNaviTable)
    {
    }

    @Override
    public ResourceMap getResourceMap()
    {
        return resourceMap;
    }

    public void setResourceMap(ResourceMap resourceMap)
    {
        this.resourceMap = resourceMap;
    }


    @Override
    public String[] getEditableProperties()
    {
        return editableProperties;
    }

    @Override
    public String[] getHiddenProperties()
    {
        return hiddenProperties;
    }

    public void setEditableProperties(String[] editableProperties)
    {
        this.editableProperties = editableProperties;
    }

    public void setHiddenProperties(String[] hiddenProperties)
    {
        this.hiddenProperties = hiddenProperties;
    }

    public void loadProperties()
    {
        try
        {
            State state = (State) Application.getInstance().getContext().getLocalStorage().load(getName() + ".properties.xml");
            if (state != null)
            {
                String[] hiddenProperties = StringUtils.split(state.getHiddenProperties(), ',');
                setHiddenProperties(hiddenProperties);
            }
        }
        catch (Throwable e)
        {
            Logger.getLogger(getClass().getSimpleName()).log(Level.WARNING, e.getLocalizedMessage(), e);
        }
    }

    public void saveProperties()
    {
        try
        {
            State state = new State();
            state.setHiddenProperties(StringUtils.join(getHiddenProperties(), ','));
            Application.getInstance().getContext().getLocalStorage().save(state, getName() + ".properties.xml");
        }
        catch (Throwable e)
        {
            Logger.getLogger(getClass().getSimpleName()).log(Level.WARNING, e.getLocalizedMessage(), e);
        }
    }


    public List<E> getList()
    {
        return list;
    }

    @Override
    public void update()
    {
        getList().clear();
        getList().addAll(FacadeContext.getFacadeBy(getElementClass()).loadAll(getSearchFilter()));
    }

    @Override
    public int getCount()
    {
        return FacadeContext.getFacadeBy(getElementClass()).getCount(getSearchFilter());
    }

    @Override
    public String getName()
    {
        if (name == null)
        {
            name = new StringBuffer("AListUpdater").append(".").append(getElementClass().getSimpleName()).toString();
        }
        return name;
    }


    @Override
    public TableCellEditor getTableCellEditor(String propertyName)
    {
        return editorPropertyMap.get(propertyName);
    }

    @Override
    public TableCellEditor getTableCellEditor(Class propertyClass)
    {
        return editorClassMap.get(propertyClass);
    }

    @Override
    public TableCellRenderer getTableCellRenderer(String propertyName)
    {
        return rendererPropertyMap.get(propertyName);
    }

    @Override
    public TableCellRenderer getTableCellRenderer(Class propertyClass)
    {
        return rendererClassMap.get(propertyClass);
    }

    public void adjustFilter()
    {

    }

    @Override
    public E addNew()
    {
        return null;
    }

    @Override
    public E delete(E e)
    {
        return null;
    }

    @Override
    public E save(E e)
    {
        return null;
    }

    public void addObservableListListener(ObservableListListener listener)
    {
        list.addObservableListListener(listener);
    }

    public void removeObservableListListener(ObservableListListener listener)
    {
        list.removeObservableListListener(listener);
    }


    public SearchFilter getSearchFilter()
    {
        if (searchFilter == null)
        {
            searchFilter = new SearchFilter();
            adjustFilter();
        }
        return searchFilter;
    }

    public void setSearchFilter(SearchFilter searchFilter)
    {
        this.searchFilter = searchFilter;
    }

    @Override
    public String[] getTotalProperties()
    {
        return null;
    }

    @Override
    public Double getTotalValue(String property)
    {
        return null;
    }

    //introduce for access parent component form ListUpdater (for exemple: editor shows frame - the variable is used as related component)
    public ListNaviTable<E> getListNaviTable()
    {
        return listNaviTable;
    }

    public void setListNaviTable(ListNaviTable<E> listNaviTable)
    {
        this.listNaviTable = listNaviTable;
        if (getNewEditDeleteActions() != null)
        {
            getNewEditDeleteActions().setRelatedComponent(listNaviTable);
        }
    }

    public void setElementClass(Class<E> elementClass)
    {
        this.elementClass = elementClass;
    }

    public Class<E> getElementClass()
    {
        if (elementClass == null)
        {
            elementClass = GenericUtils.getParameterClass(getClass(), 0);
        }
        return elementClass;
    }

    public String[] getVisibleProperties()
    {
        return visibleProperties;
    }

    public void setVisibleProperties(String[] visibleProperties)
    {
        this.visibleProperties = visibleProperties;
    }

    @Override
    public void adjustTableColumn(TableColumn tableColumn)
    {
    }

    @Override
    public void adjustColumnBinding(JTableBinding.ColumnBinding columnBinding)
    {
    }

    public void setActionMap(ActionMap actionMap)
    {
        this.actionMap = actionMap;
    }

    @Override
    public ActionMap getActionMap()
    {
        return actionMap;
    }

    public void setActions(String... actions)
    {
        this.actions = actions;
    }

    @Override
    public String[] getActions()
    {
        return actions;
    }


    public static class State
    {
        private String hiddenProperties;

        public String getHiddenProperties()
        {
            return hiddenProperties;
        }

        public void setHiddenProperties(String hiddenProperties)
        {
            this.hiddenProperties = hiddenProperties;
        }
    }
}
