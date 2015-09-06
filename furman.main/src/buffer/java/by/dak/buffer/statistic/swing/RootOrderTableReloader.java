package by.dak.buffer.statistic.swing;

import by.dak.order.swing.AOrderWizard;
import by.dak.order.swing.IOrderWizardDelegator;
import by.dak.persistence.entities.AOrder;
import by.dak.swing.table.ListNaviTable;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 10.10.11
 * Time: 23:10
 * To change this template use File | Settings | File Templates.
 */

/**
 * reloads OrderExplorer
 */
public class RootOrderTableReloader implements IOrderWizardDelegator
{
    private ListNaviTable<AOrder> listNaviTable;

    public RootOrderTableReloader(ListNaviTable<AOrder> listNaviTable)
    {
        this.listNaviTable = listNaviTable;
    }

    @Override
    public void finish(AOrderWizard wizard)
    {
        listNaviTable.reload();
    }

    @Override
    public void cancel(AOrderWizard wizard)
    {
        listNaviTable.reload();
    }
}
