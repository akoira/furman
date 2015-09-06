package by.dak.swing.table;

import by.dak.swing.ActionsContext;
import by.dak.utils.GenericUtils;
import org.jdesktop.application.Action;
import org.jdesktop.application.Application;


/**
 * User: akoyro
 * Date: 07.12.2009
 * Time: 19:38:36
 */
public abstract class NewEditDeleteActions<E> extends ActionsContext<E>
{
    public static final String PROPERTY_updateGui = "updateGui";


    private Class<E> entityClass;


    protected NewEditDeleteActions()
    {
        setActionMap(Application.getInstance().getContext().getActionMap(NewEditDeleteActions.class, this));
        setActions(new String[]{"newValue", "openValue", "deleteValue"});
    }

    @Action
    public abstract void newValue();

    @Action
    public abstract void openValue();

    @Action
    public abstract void deleteValue();

    public Class<E> getEntityClass()
    {
        if (entityClass == null)
        {
            entityClass = GenericUtils.getParameterClass(getClass(), 0);
        }
        return entityClass;
    }

    public void setEntityClass(Class<E> entityClass)
    {
        this.entityClass = entityClass;
    }

}
