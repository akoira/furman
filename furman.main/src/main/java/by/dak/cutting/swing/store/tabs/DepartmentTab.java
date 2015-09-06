/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

/*
 * DepartmentTab.java
 *
 * Created on 03.02.2009, 10:04:56
 */

package by.dak.cutting.swing.store.tabs;

import by.dak.cutting.swing.AEntityEditorTab;
import by.dak.persistence.entities.DepartmentEntity;

/**
 * Department tab
 *
 * @author Rudak Alexei
 */
public class DepartmentTab extends AEntityEditorTab<DepartmentEntity>
{
    public DepartmentTab()
    {
        setVisibleProperties(new String[]{"name"});
    }
}
