/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.swing.store.modules;

import by.dak.cutting.swing.store.tabs.DepartmentTab;
import by.dak.persistence.entities.DepartmentEntity;

/**
 * @author savage
 */
public class MDepartmentPanel extends AEntityEditorPanel<DepartmentEntity>
{
    private DepartmentTab depTab;

    @Override
    protected void addTabs()
    {
        depTab = new DepartmentTab();
        depTab.init();
        getTabbedPane().addTab(depTab.getTitle(), depTab);
        //addServiceTabs();
    }
}
