package by.dak.template.swing;

import by.dak.category.Category;
import by.dak.cutting.DialogShowers;
import by.dak.cutting.swing.renderer.TableEditorsRenders;
import by.dak.cutting.swing.report.ReportsPanel;
import by.dak.persistence.FacadeContext;
import by.dak.report.model.ReportsModel;
import by.dak.report.model.impl.ReportModelCreator;
import by.dak.swing.table.AListUpdater;
import by.dak.swing.table.ListNaviTable;
import by.dak.swing.table.NewEditDeleteActions;
import by.dak.template.TemplateOrder;
import by.dak.utils.BindingAdapter;
import by.dak.utils.BindingUtils;
import by.dak.utils.MathUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.swingx.table.TableColumnExt;

import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 * User: akoyro
 * Date: 13.04.11
 * Time: 12:17
 */
public class TemplateOrdersUpdater extends AListUpdater<TemplateOrder>
{
    public static final String[] VISIBLE_PROPERTIES = new String[]{
            TemplateOrder.PROPERTY_name,
            TemplateOrder.PROPERTY_description,
            TemplateOrder.PROPERTY_cost,
            TemplateOrder.PROPERTY_dialerCost,
            TemplateOrder.PROPERTY_saleFactor,
            TemplateOrder.PROPERTY_salePrice,
            TemplateOrder.PROPERTY_fileUuid,
            TemplateOrder.PROPERTY_id,
    };

    public static final String[] EDITABLE_PROPERTIES = new String[]{
            TemplateOrder.PROPERTY_fileUuid,
            TemplateOrder.PROPERTY_id,
            TemplateOrder.PROPERTY_salePrice,
            TemplateOrder.PROPERTY_saleFactor,

    };

    public static final String[] HIDDEN_PROPERTIES = new String[]{
            TemplateOrder.PROPERTY_cost,
            TemplateOrder.PROPERTY_saleFactor,
            TemplateOrder.PROPERTY_salePrice,

    };


    private NewEditDeleteActions<TemplateOrder> actions;
    private Category category;

    public TemplateOrdersUpdater(Category category)
    {
        init(category);
    }

    protected void init(Category category)
    {
        this.category = category;
        actions = new TemplateOrderActions(category);
        TemplateOrderPermission permission = new TemplateOrderPermission();
        setHiddenProperties(permission.getHiddenProperties());
        setVisibleProperties(permission.getVisibleProperties());
        setEditableProperties(permission.getEditableProperties());
        setActions("setForAll");
        setActionMap(Application.getInstance().getContext().getActionMap(TemplateOrdersUpdater.class, this));
    }

    public TemplateOrdersUpdater()
    {
        this(null);
    }

    @Override
    public void initAdditionalTableControls(ListNaviTable listNaviTable)
    {
        BindingAdapter bindingAdapter = new BindingAdapter()
        {
            @Override
            public void synced(Binding binding)
            {
                if (!binding.getSourceValueForTarget().failed() &&
                        //can other object instance on initialization of the binding
                        binding.getSourceObject() instanceof TemplateOrder)
                {
                    updateProperty((TemplateOrder) binding.getSourceObject(), binding.getName());
                }
            }
        };

        listNaviTable.getBindingGroup().addBindingListener(bindingAdapter);
    }

    private void updateProperty(TemplateOrder templateOrder, String property)
    {
        if (TemplateOrder.PROPERTY_saleFactor.equals(property) ||
                TemplateOrder.PROPERTY_salePrice.equals(property))
        {
            //с редактора может прейти null
            if (TemplateOrder.PROPERTY_saleFactor.equals(property) &&
                    templateOrder.getDialerCost() != null &&
                    templateOrder.getSaleFactor() != null)
            {
                templateOrder.setSalePrice(MathUtils.round(templateOrder.getDialerCost() * templateOrder.getSaleFactor(), 4));
            }
            FacadeContext.getTemplateOrderFacade().save(templateOrder);
        }
    }

    @Override
    public void adjustFilter()
    {
        getSearchFilter().eq(TemplateOrder.PROPERTY_category, getCategory());
        getSearchFilter().addAscOrder(TemplateOrder.PROPERTY_name);
    }


    @Override
    public TableCellEditor getTableCellEditor(String propertyName)
    {
        if (propertyName.equals(TemplateOrder.PROPERTY_fileUuid))
        {
            return new ImageViewEditor(this);
        }
        if (propertyName.equals(TemplateOrder.PROPERTY_id))
        {
            return createReportsEditor();
        }
        else
        {
            return super.getTableCellEditor(propertyName);
        }
    }

    @Override
    public TableCellRenderer getTableCellRenderer(String propertyName)
    {
        if (propertyName.equals(TemplateOrder.PROPERTY_fileUuid))
        {
            return new TableEditorsRenders.ButtonEditor(getResourceMap().getString("table.column.fileUuid"), getResourceMap().getIcon("image.icon"));
        }
        if (propertyName.equals(TemplateOrder.PROPERTY_id))
        {
            return new TableEditorsRenders.ButtonEditor(getResourceMap().getString("table.column.id"), getResourceMap().getIcon("reports.icon"));
        }
        else
        {
            return super.getTableCellRenderer(propertyName);
        }
    }

    @Action
    public void setForAll()
    {
        if (getListNaviTable() != null)
        {
            int col = getListNaviTable().getTable().getSelectedColumn();
            final int row = getListNaviTable().getTable().getSelectedRow();

            if (col > -1 && row > -1)
            {
                TableColumnExt tableColumnExt = getListNaviTable().getTable().getColumnExt(col);
                String property = null;
                if (tableColumnExt.getIdentifier().equals(getResourceMap().getString(BindingUtils.PREFIX_TABLE_COLUMN_KEY + TemplateOrder.PROPERTY_saleFactor)))
                {
                    property = TemplateOrder.PROPERTY_saleFactor;
                }
                if (property != null)
                {
                    final int colM = getListNaviTable().getTable().convertColumnIndexToModel(col);
                    final Object value = getListNaviTable().getTable().getModel().getValueAt(row, colM);
//                            SearchFilter searchFilter = SearchFilter.valueOf(getSearchFilter());
//                            List<TemplateOrder> orders = FacadeContext.getTemplateOrderFacade().loadAll(searchFilter);
                    for (int i = row + 1; i < getList().size(); i++)
                    {
                        try
                        {
                            BeanUtils.setProperty(getList().get(i), property, value);
                            updateProperty(getList().get(i), property);
                        }
                        catch (Throwable e)
                        {
                            throw new IllegalArgumentException(e);
                        }
                        //FacadeContext.getTemplateOrderFacade().save(templateOrder);
                    }
                }
            }

        }
    }


    @Override
    public NewEditDeleteActions getNewEditDeleteActions()
    {
        return actions;
    }

    public void setNewEditDeleteActions(NewEditDeleteActions<TemplateOrder> actions)
    {
        this.actions = actions;
    }

    private TableCellEditor createReportsEditor()
    {
        return new TableEditorsRenders.ButtonEditor(getResourceMap().getString("table.column.id"),
                new TableEditorsRenders.EditorCall<Long, Long>()
                {
                    private Long value;

                    @Override
                    public void setValue(Long value)
                    {
                        this.value = value;
                    }

                    @Override
                    public Long getValue()
                    {
                        return value;
                    }

                    @Override
                    public Long call() throws Exception
                    {
                        if (getValue() != null)
                        {
                            TemplateOrder order = FacadeContext.getTemplateOrderFacade().findBy(getValue());
                            order.setOrderItems(FacadeContext.getOrderItemFacade().loadBy(order));
                            ReportModelCreator creator = new ReportModelCreator(order);
                            ReportsModel reportsModel = creator.create();
                            ReportsPanel reportsPanel = new ReportsPanel();
                            reportsPanel.setEditable(false);
                            reportsPanel.setReportsModel(reportsModel);
                            DialogShowers.showBy(reportsPanel, getListNaviTable(), true);
                        }
                        return getValue();
                    }
                }
                , getResourceMap().getIcon("reports.icon"));
    }

    public Category getCategory()
    {
        return category;
    }


    public static class TemplateOrderPermission
    {
        public String[] getHiddenProperties()
        {
            String department = FacadeContext.getEmployee().getDepartment().getName();
            if (department.equals("manager") || department.equals("dialer"))
            {
                return new String[]{
                        TemplateOrder.PROPERTY_saleFactor,
                        TemplateOrder.PROPERTY_salePrice,
                };
            }
            else
            {
                return HIDDEN_PROPERTIES;
            }
        }

        public String[] getVisibleProperties()
        {
            String department = FacadeContext.getEmployee().getDepartment().getName();
            if (department.equals("manager") || department.equals("dialer"))
            {
                return new String[]{
                        TemplateOrder.PROPERTY_name,
                        TemplateOrder.PROPERTY_description,
                        TemplateOrder.PROPERTY_dialerCost,
                        TemplateOrder.PROPERTY_saleFactor,
                        TemplateOrder.PROPERTY_salePrice,
                        TemplateOrder.PROPERTY_fileUuid,
                        TemplateOrder.PROPERTY_id,
                };
            }
            else
            {
                return VISIBLE_PROPERTIES;
            }
        }

        public String[] getEditableProperties()
        {
            String department = FacadeContext.getEmployee().getDepartment().getName();
            if (department.equals("manager") || department.equals("dialer"))
            {
                return new String[]{
                        TemplateOrder.PROPERTY_fileUuid,
                        TemplateOrder.PROPERTY_id,
                };
            }
            else
            {
                return EDITABLE_PROPERTIES;
            }

        }


    }
}
