package by.dak.buffer.statistic.swing.tree;

import by.dak.buffer.entity.DilerOrder;
import by.dak.buffer.statistic.filter.DilerOrderFilter;
import by.dak.buffer.statistic.swing.SetForProductionHelper;
import by.dak.cutting.SearchFilter;
import by.dak.cutting.swing.store.Constants;
import by.dak.order.swing.IOrderWizardDelegator;
import by.dak.persistence.entities.Customer;
import by.dak.swing.table.AListUpdater;
import by.dak.swing.table.ListNaviTable;
import by.dak.swing.table.ListUpdater;
import by.dak.swing.table.ListUpdaterProvider;
import by.dak.swing.tree.ATreeNode;
import by.dak.utils.convert.TimeUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 27.11.2010
 * Time: 22:28:24
 * To change this template use File | Settings | File Templates.
 */
public class DilerCustomerNode extends ATreeNode implements ListUpdaterProvider<DilerOrder>
{
    private Customer customer = null;
    private DilerOrderFilter filter = null;
    private IOrderWizardDelegator orderWizardDelegator;

    public DilerCustomerNode(Customer customer)
    {
        super(customer);
        this.customer = customer;
    }

    public IOrderWizardDelegator getOrderWizardDelegator()
    {
        return orderWizardDelegator;
    }

    public void setOrderWizardDelegator(IOrderWizardDelegator orderWizardDelegator)
    {
        this.orderWizardDelegator = orderWizardDelegator;
    }

    public void setDilerOrderFilter(DilerOrderFilter filter)
    {
        this.filter = filter;
    }

    public DilerOrderFilter getDilerOrderFilter()
    {
        return filter;
    }

    @Override
    protected void initChildren()
    {
    }

    @Override
    public ListUpdater<DilerOrder> getListUpdater()
    {
        AListUpdater<DilerOrder> listUpdater = new AListUpdater<DilerOrder>()
        {
            @Override
            public void adjustFilter()
            {
                SearchFilter searchFilter = new SearchFilter();
                searchFilter.eq(DilerOrder.PROPERTY_customer, customer);
                SearchFilter.DCriterion<Criterion> dCriterion = new SearchFilter.
                        DCriterion<Criterion>("createdDailySheet.date",
                        Restrictions.between("date", TimeUtils.getDayTimestamp(filter.getStart()),
                                TimeUtils.getDayTimestamp(filter.getEnd())));
                searchFilter.addCriterion(dCriterion);

                setSearchFilter(searchFilter);
            }

            @Override
            public String[] getEditableProperties()
            {
                return Constants.DilerOrderTableEditableProperties;
            }

            @Override
            public String[] getVisibleProperties()
            {
                return Constants.DilerOrderTableEditableProperties;
            }

            @Override
            public ResourceMap getResourceMap()
            {
                resourceMap = Application.getInstance().getContext().getResourceMap(DilerCustomerNode.class);
                return resourceMap;
            }

            @Override
            public void initAdditionalTableControls(ListNaviTable listNaviTable)
            {
                SetForProductionHelper productionHelper = new SetForProductionHelper(listNaviTable);
                productionHelper.setCustomer(customer);
                productionHelper.setOrderWizardDelegator(getOrderWizardDelegator());
                productionHelper.init();
            }

            @Override
            public String[] getHiddenProperties()
            {
                return Constants.DilerOrderTableHiddenProperties;
            }

        };
        return listUpdater;
    }


}
