/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

/*
 * CustomerTab.java
 *
 * Created on 02.03.2009, 16:47:51
 */

package by.dak.cutting.swing.store.tabs;

import by.dak.cutting.CuttingApp;
import by.dak.cutting.swing.AEntityEditorTab;
import by.dak.cutting.swing.store.Constants;
import by.dak.persistence.entities.Customer;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

/**
 * CustomerTab tab
 *
 * @author Rudak Alexei
 */

public class CustomerTab extends AEntityEditorTab<Customer>
{
    ResourceMap resourceMap = Application.getInstance(
            CuttingApp.class).getContext().getResourceMap(CustomerTab.class);

    @Override
    public String[] getVisibleProperties()
    {
        return Constants.CustomerTableVisibleProperties;
    }

    @Override
    public ResourceMap getResourceMap()
    {
        return resourceMap;
    }

}
