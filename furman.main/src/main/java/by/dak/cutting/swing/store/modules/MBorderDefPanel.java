/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */
package by.dak.cutting.swing.store.modules;

import by.dak.cutting.swing.store.tabs.BorderDefEntityTab;
import by.dak.persistence.entities.BorderDefEntity;

/**
 * @author Admin
 */
public class MBorderDefPanel extends AEntityEditorPanel<BorderDefEntity>
{
    private BorderDefEntityTab borderTab;


    @Override
    protected void addTabs()
    {
        borderTab = new BorderDefEntityTab(getValue());
        addTab(getResourceMap().getString("MBorderDefPanel.borderDefTab.title"), borderTab);
    }
}
