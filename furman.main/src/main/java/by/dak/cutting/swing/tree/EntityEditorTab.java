package by.dak.cutting.swing.tree;

import by.dak.cutting.swing.AEntityEditorTab;
import by.dak.persistence.entities.PersistenceEntity;
import org.apache.commons.lang.StringUtils;
import org.jdesktop.application.Application;

/**
 * User: akoyro
 * Date: 05.04.11
 * Time: 21:20
 */
public class EntityEditorTab<E extends PersistenceEntity> extends AEntityEditorTab<E>
{
    public EntityEditorTab(Class<E> entityClass)
    {
        setValueClass(entityClass);
        setResourceMap(Application.getInstance().getContext().getResourceMap(entityClass));
        setVisibleProperties(StringUtils.split(Application.getInstance().getContext().getResourceMap(entityClass).getString("editor.visible.properties"), ","));
    }
}
