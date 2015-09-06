/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

/*
 * DesignerTab.java
 *
 * Created on 02.02.2009, 23:49:33
 */

package by.dak.cutting.swing.store.tabs;

import by.dak.cutting.CuttingApp;
import by.dak.cutting.swing.AEntityEditorTab;
import by.dak.cutting.swing.store.Constants;
import by.dak.persistence.entities.DesignerEntity;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

/**
 * Designer tab
 *
 * @author Rudak Alexei
 */
public class DesignerTab extends AEntityEditorTab<DesignerEntity>
{
    ResourceMap resourceMap = Application.getInstance(
            CuttingApp.class).getContext().getResourceMap(DesignerTab.class);

    @Override
    public String[] getVisibleProperties()
    {
        return Constants.DesignerTableVisibleProperties;
    }

    @Override
    public ResourceMap getResourceMap()
    {
        return resourceMap;
    }
}
