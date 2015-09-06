package by.dak.swing.table;

import by.dak.cutting.SearchFilter;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.observablecollections.ObservableListListener;
import org.jdesktop.swingbinding.JTableBinding;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.util.List;

/**
 * Класс предоставляет все необходимое для обнавление и отабраженеи ListNaviTable
 * User: akoyro
 * Date: 26.11.2009
 * Time: 12:24:18
 */
public interface ListUpdater<E>
{
    /**
     * Возвращает уникальное имя этого updater для использования хранения настроек компонента
     *
     * @return
     */
    public String getName();

    public int getCount();

    public void update();

    public String[] getHiddenProperties();

    public String[] getVisibleProperties();

    public String[] getEditableProperties();

    public TableCellEditor getTableCellEditor(String propertyName);

    public TableCellEditor getTableCellEditor(Class propertyClass);

    public TableCellRenderer getTableCellRenderer(String propertyName);

    public TableCellRenderer getTableCellRenderer(Class propertyClass);

    public void adjustTableColumn(TableColumn tableColumn);

    public void adjustColumnBinding(JTableBinding.ColumnBinding columnBinding);

    public Class getElementClass();

    public NewEditDeleteActions getNewEditDeleteActions();

    public void initAdditionalTableControls(ListNaviTable listNaviTable);

    public ResourceMap getResourceMap();

    public List<E> getList();

    public E addNew();

    public E delete(E e);

    public E save(E e);

    public void addObservableListListener(ObservableListListener listener);

    public void removeObservableListListener(ObservableListListener listener);

    public SearchFilter getSearchFilter();

    public String[] getTotalProperties();

    public Double getTotalValue(String property);

    ActionMap getActionMap();

    String[] getActions();
}
