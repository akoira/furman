/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package by.dak.cutting.swing.store.modules;

import by.dak.cutting.swing.store.tabs.ProviderTab;
import by.dak.persistence.entities.Provider;

/**
 * @author Oleg Birulia
 */
public class MProviderPanel extends AEntityEditorPanel<Provider>
{
    private ProviderTab providerTab;

    @Override
    protected void addTabs()
    {
        providerTab = new ProviderTab();
        addTab(getResourceMap().getString("tab.providerTab.title"), providerTab);
    }
}
