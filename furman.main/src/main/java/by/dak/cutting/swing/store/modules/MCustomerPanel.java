/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.swing.store.modules;

import by.dak.cutting.swing.store.tabs.CustomerTab;
import by.dak.persistence.entities.Customer;

/**
 * @author Admin
 */
public class MCustomerPanel extends AEntityEditorPanel<Customer>
{
    private CustomerTab csTab;

    @Override
    protected void addTabs()
    {
        csTab = new CustomerTab();
        addTab(getResourceMap().getString("MCustomerPanel.customerTab.title"), csTab);
    }
}
