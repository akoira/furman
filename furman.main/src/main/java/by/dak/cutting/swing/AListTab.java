package by.dak.cutting.swing;

import by.dak.cutting.MessageBox;
import by.dak.swing.table.ListNaviTable;
import by.dak.utils.BindingAdapter;
import by.dak.utils.validator.ValidatorAnnotationProcessor;
import com.jgoodies.validation.ValidationResult;
import org.jdesktop.application.Application;
import org.jdesktop.beansbinding.Binding;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.List;

/**
 * User: akoyro
 * Date: 14.04.2010
 * Time: 14:48:28
 */
public abstract class AListTab<E, V> extends BaseTabPanel<V>
{
    private ListNaviTable<E> listNaviTable;

    public AListTab()
    {
        initComponents();
        initEnvironment();
    }

    protected void initEnvironment()
    {
        ActionMap aMap = Application.getInstance().getContext().getActionMap(AListTab.class, this);
        getListNaviTable().getTable().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "onDelete");
        getListNaviTable().getTable().getActionMap().put("onDelete", aMap.get("onDelete"));
    }

    public ListNaviTable<E> getListNaviTable()
    {
        return listNaviTable;
    }

    private void initComponents()
    {

        listNaviTable = new ListNaviTable<E>();
        listNaviTable.setShowFilterHeader(false);
        listNaviTable.getTable().getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        listNaviTable.getTable().getColumnModel().getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        listNaviTable.getTable().setRowSelectionAllowed(false);
        listNaviTable.getTable().setCellSelectionEnabled(true);
        listNaviTable.getTable().getTableHeader().setReorderingAllowed(false);

        listNaviTable.setName("listNaviTable"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(listNaviTable, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(listNaviTable, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                                .addContainerGap())
        );
    }


    public void init()
    {
        getListNaviTable().init();
        initBindingListeners(); //порядок важен так как валидация должна произойти в самом  конце
        getListNaviTable().getBindingGroup().addBindingListener(new ValidateController());
    }

    protected abstract void initBindingListeners();


    private class ValidateController extends BindingAdapter
    {
        @Override
        public void synced(Binding binding)
        {
            E element = (E) binding.getSourceObject();
            validateElement(element);
        }
    }

    protected void validateElement(E element)
    {
        ValidationResult result = ValidatorAnnotationProcessor.getProcessor().validate(element);
        if (getWarningList() != null)
        {
            getWarningList().getModel().setResult(result);
        }
        if (!result.hasErrors())
        {
            getListNaviTable().getListUpdater().save(element);
        }
        if (!validateAll().hasErrors())
        {
            getListNaviTable().getListUpdater().addNew();
        }
    }


    protected ValidationResult validateAll()
    {
        List<E> elements = getListNaviTable().getListUpdater().getList();
        ValidationResult result = new ValidationResult();
        for (E e : elements)
        {
            result.addAllFrom(ValidatorAnnotationProcessor.getProcessor().validate(e));
        }
        return result;
    }


    @org.jdesktop.application.Action
    public void onDelete()
    {
        int selRow = getListNaviTable().getTable().getSelectedRow();
        //если выброна не пустая строка
        if (selRow > -1)
        {
            E element = getListNaviTable().getListUpdater().getList().get(selRow);
            if (MessageBox.confirmYesNoDeletion())
            {
                getListNaviTable().getListUpdater().delete(element);
            }
        }
    }
}
