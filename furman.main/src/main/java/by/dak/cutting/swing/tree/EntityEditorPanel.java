package by.dak.cutting.swing.tree;

import by.dak.cutting.swing.store.modules.AEntityEditorPanel;
import by.dak.persistence.entities.PersistenceEntity;
import org.jdesktop.application.Application;

/**
 * User: akoyro
 * Date: 05.04.11
 * Time: 21:15
 */
public class EntityEditorPanel<E extends PersistenceEntity> extends AEntityEditorPanel<E>
{
    public EntityEditorPanel(Class<E> entityClass)
    {
        setResourceMap(Application.getInstance().getContext().getResourceMap(entityClass));
        setEntityClass(entityClass);
        setEditable(true);
        setShowOkCancel(true);
        addTabs();
    }

    @Override
    public void initEnvironment()
    {
    }

    @Override
    protected void addTabs()
    {
        EntityEditorTab entityEditorTab = new EntityEditorTab(getEntiryClass());
        addTab(entityEditorTab.getTitle(), entityEditorTab);
    }
}
