package by.dak.cutting.swing.store.modules;

import by.dak.cutting.swing.BaseTabPanel;
import by.dak.cutting.swing.DModPanel;
import by.dak.cutting.swing.ValueSave;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.PersistenceEntity;
import by.dak.utils.GenericUtils;

/**
 * User: akoyro
 * Date: 21.11.2009
 * Time: 0:36:58
 */
public class AEntityEditorPanel<E extends PersistenceEntity> extends DModPanel<E>
{
    private BaseTabPanel tab;

    private Class<E> entityClass;

    public AEntityEditorPanel()
    {
        setName(this.getClass().getSimpleName());
        initEnvironment();
        setShowOkCancel(true);

        setValueSave(new ValueSave<E>()
        {
            @Override
            public void save(E value)
            {
                FacadeContext.getFacadeBy(value.getClass()).save(value);
            }
        });
    }

    public void setEntityClass(Class<E> entityClass)
    {
        this.entityClass = entityClass;
    }


    public Class getEntiryClass()
    {
        if (entityClass == null)
        {
            entityClass = GenericUtils.getParameterClass(getClass(), 0);
        }
        return entityClass;
    }


    @Override
    protected void addTabs()
    {
    }

    protected void initEnvironment()
    {
        addTabs();
    }

    @Override
    public void save()
    {
        getValueSave().save(getValue());
    }

    public BaseTabPanel getTab()
    {
        return tab;
    }

    public void setTab(BaseTabPanel tab)
    {
        this.tab = tab;
    }

}