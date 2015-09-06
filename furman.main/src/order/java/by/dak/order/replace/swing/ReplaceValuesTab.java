package by.dak.order.replace.swing;

import by.dak.cutting.ACodeTypePair;
import by.dak.cutting.swing.AListTab;
import by.dak.order.replace.ReplaceValue;
import by.dak.persistence.entities.AOrder;
import by.dak.swing.table.AListUpdater;
import by.dak.utils.BindingAdapter;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.beansbinding.Binding;

import javax.swing.table.TableCellEditor;
import java.util.Collections;
import java.util.List;

/**
 * User: akoyro
 * Date: 03.12.11
 * Time: 12:30
 */
public class ReplaceValuesTab extends AListTab<ReplaceValue, AOrder>
{
    private static final String PROPERTY_sourcePair_type = ReplaceValue.PROPERTY_sourcePair + "." + ACodeTypePair.PROPERTY_type;
    private static final String PROPERTY_sourcePair_code = ReplaceValue.PROPERTY_sourcePair + "." + ACodeTypePair.PROPERTY_code;
    private static final String PROPERTY_replacePair_type = ReplaceValue.PROPERTY_replacePair + "." + ACodeTypePair.PROPERTY_type;
    private static final String PROPERTY_replacePair_code = ReplaceValue.PROPERTY_replacePair + "." + ACodeTypePair.PROPERTY_code;



    private List<ReplaceValue<? extends ACodeTypePair, ? extends ACodeTypePair>> values = Collections.EMPTY_LIST;
    private ListUpdater listUpdater = new ListUpdater();


    private AEditorsProvider editorsProvider;


    public List<ReplaceValue<? extends ACodeTypePair, ? extends ACodeTypePair>> getValues()
    {
        return values;
    }

    public void setValues(List<ReplaceValue<? extends ACodeTypePair, ? extends ACodeTypePair>> values)
    {
        this.values = values;
    }

    @Override
    protected void valueChanged()
    {
        getListNaviTable().reload();
    }

    @Override
    public void init()
    {
        getListNaviTable().setListUpdater(getListUpdater());
        super.init();
    }

    @Override
    protected void initBindingListeners()
    {
        getListNaviTable().getBindingGroup().addBindingListener(new BindingAdapter()
        {
            @Override
            public void synced(Binding binding)
            {
                if (binding.getName().equals(PROPERTY_replacePair_type))
                {

                    ReplaceValue replaceValue = (ReplaceValue) binding.getSourceObject();
                    if (replaceValue != null && replaceValue.getReplacePair() !=null)
                    {
                        replaceValue.getReplacePair().setCode(null);
                    }
                }
            }
        });
    }

    public AEditorsProvider getEditorsProvider()
    {
        return editorsProvider;
    }

    public void setEditorsProvider(AEditorsProvider editorsProvider)
    {
        this.editorsProvider = editorsProvider;
    }

    public ListUpdater getListUpdater()
    {
        return listUpdater;
    }

    public class ListUpdater extends AListUpdater<ReplaceValue>
    {
        @Override
        public void update()
        {
            getList().clear();
            if (getValue() != null)
                getList().addAll(values);
        }


        @Override
        public String[] getVisibleProperties()
        {
            return new String[]{PROPERTY_sourcePair_type,
                    PROPERTY_sourcePair_code,
                    PROPERTY_replacePair_type,
                    PROPERTY_replacePair_code};
        }

        @Override
        public ResourceMap getResourceMap()
        {
            return ReplaceValuesTab.this.getResourceMap();
        }

        @Override
        public String[] getEditableProperties()
        {
            return new String[]{PROPERTY_replacePair_type,
                    PROPERTY_replacePair_code};
        }


        @Override
        public TableCellEditor getTableCellEditor(String propertyName)
        {
            if (editorsProvider != null)
            {
                if (propertyName.equals(PROPERTY_replacePair_type))
                {
                    return editorsProvider.getTypeCellEditor();
                }
                else if (propertyName.equals(PROPERTY_replacePair_code))
                {
                    return editorsProvider.getCodeCellEditor();
                }
            }
            return super.getTableCellEditor(propertyName);
        }

        @Override
        public int getCount()
        {
            return values != null ? values.size() : 0;
        }
    }
}
