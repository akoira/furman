package by.dak.persistence;

import by.dak.cutting.facade.BaseFacade;
import by.dak.persistence.entities.PersistenceEntity;

import java.util.ArrayList;

/**
 * User: akoyro
 * Date: 20.03.2010
 * Time: 23:11:51
 */
public class ReservedSaver extends ArrayList<PersistenceEntity>
{
    /**
     * Добовляется только в сдучае если нет в листе
     *
     * @param persistenceEntity
     * @return
     */
    @Override
    public boolean add(PersistenceEntity persistenceEntity)
    {
        if (!containsInstance(persistenceEntity))
        {
            return super.add(persistenceEntity);
        }
        else
        {
            return false;
        }

    }

    public boolean containsInstance(PersistenceEntity persistenceEntity)
    {
        for (PersistenceEntity persistenceEntity1 : this)
        {
            if (persistenceEntity == persistenceEntity1)
            {
                return true;
            }
        }
        return false;
    }

    public void save()
    {
        for (PersistenceEntity entity : this)
        {
            BaseFacade baseFacade = FacadeContext.getFacadeBy(entity.getClass());
            baseFacade.save(entity);
        }
    }
}
