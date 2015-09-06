/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.swing.store.modules;

import by.dak.cutting.swing.store.tabs.DesignerTab;
import by.dak.persistence.entities.DesignerEntity;

/**
 * @author savage
 */
public class MDesignerPanel extends AEntityEditorPanel<DesignerEntity>
{
    private DesignerTab designerTab;

    @Override
    protected void addTabs()
    {
        designerTab = new DesignerTab();
        addTab(getResourceMap().getString("MDesignerPanel.designerTab.title"), designerTab);
    }
}
