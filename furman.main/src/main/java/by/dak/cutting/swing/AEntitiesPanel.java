/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

/*
 * DepartmentsPanel.java
 *
 * Created on 07.12.2008, 13:02:25
 */

package by.dak.cutting.swing;

import by.dak.persistence.entities.PersistenceEntity;
import by.dak.swing.table.AListUpdater;
import by.dak.utils.GenericUtils;

/**
 * @author admin
 */
public abstract class AEntitiesPanel<E extends PersistenceEntity> extends AListTab<E, Object>
{

    protected AEntitiesPanel(String[] visibleProperties)
    {
        ListUpdater listUpdater = new ListUpdater();
        listUpdater.setElementClass(GenericUtils.getParameterClass(this.getClass(), 0));
        listUpdater.setVisibleProperties(visibleProperties);
        listUpdater.setResourceMap(getResourceMap());
        NEDActions nedActions = new NEDActions();
        nedActions.setRelatedComponent(this);
        listUpdater.setNewEditDeleteActions(nedActions);
        getListNaviTable().setListUpdater(listUpdater);
    }

    @Override
    protected void initBindingListeners()
    {
    }

    public class NEDActions extends AEntiryNEDActions<E>
    {

    }


    public class ListUpdater extends AListUpdater<E>
    {
    }
}
