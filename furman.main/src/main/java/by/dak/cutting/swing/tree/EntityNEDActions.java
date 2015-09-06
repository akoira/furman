package by.dak.cutting.swing.tree;

import by.dak.cutting.DialogShowers;
import by.dak.cutting.swing.AEntiryNEDActions;
import by.dak.cutting.swing.MessageDialog;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.PersistenceEntity;

/**
 * User: akoyro
 * Date: 05.04.11
 * Time: 21:30
 */
public class EntityNEDActions<E extends PersistenceEntity> extends AEntiryNEDActions<E>
{
    public EntityNEDActions(Class<E> entityClass)
    {
        setEntityClass(entityClass);
    }

    @Override
    public void openValue()
    {
        if (getSelectedElement() != null)
        {
            showPanel(getSelectedElement());
        }
        else
        {
            MessageDialog.showSimpleMessage(MessageDialog.NO_ROW_SELECTED);
        }
    }

    @Override
    public void newValue()
    {
        E e = FacadeContext.createNewInstance(getEntityClass());
        showPanel(e);
    }

    protected void showPanel(E e)
    {
        EntityEditorPanel panel = new EntityEditorPanel(getEntityClass());
        panel.setValueSave(getValueSave());
        panel.setValue(e);
        DialogShowers.showBy(panel, getRelatedComponent(), true);
    }

}
