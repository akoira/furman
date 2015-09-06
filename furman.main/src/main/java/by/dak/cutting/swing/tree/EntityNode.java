package by.dak.cutting.swing.tree;

import by.dak.persistence.entities.PersistenceEntity;
import by.dak.swing.table.ListUpdater;
import by.dak.swing.table.ListUpdaterProvider;
import by.dak.swing.tree.ATreeNode;
import org.apache.commons.lang.StringUtils;
import org.jdesktop.application.Application;

/**
 * User: akoyro
 * Date: 05.04.11
 * Time: 20:49
 */
public class EntityNode<E extends PersistenceEntity> extends ATreeNode implements ListUpdaterProvider<E>
{

    private EntityListUpdater<E> listUpdater;


    private Class<E> entityClass;

    public EntityNode(Class<E> entityClass)
    {
        this.entityClass = entityClass;
        setUserObject(getListUpdater().getResourceMap().getString("node.name"));
        setClosedIcon(getListUpdater().getResourceMap().getIcon("node.icon"));
        setLeafIcon(getListUpdater().getResourceMap().getIcon("node.icon"));
        setOpenIcon(getListUpdater().getResourceMap().getIcon("node.icon"));
    }

    public Class<E> getEntityClass()
    {
        return entityClass;
    }

    public void setListUpdater(EntityListUpdater<E> listUpdater)
    {
        this.listUpdater = listUpdater;
    }

    @Override
    public ListUpdater<E> getListUpdater()
    {
        if (listUpdater == null)
        {
            listUpdater = new EntityListUpdater<E>();
            listUpdater.setVisibleProperties(StringUtils.split(Application.getInstance().getContext().getResourceMap(entityClass).getString("table.visible.properties"), ","));
            listUpdater.setElementClass(entityClass);
            EntityNEDActions actions = new EntityNEDActions(entityClass);
            listUpdater.setNewEditDeleteActions(actions);
            listUpdater.setResourceMap(Application.getInstance().getContext().getResourceMap(entityClass));
        }
        return listUpdater;
    }

    @Override
    protected void initChildren()
    {

    }
}
