package by.dak.cutting.doors.mech.db;

import by.dak.cutting.doors.mech.DoorComponent;
import by.dak.cutting.doors.mech.DoorMechDef;
import by.dak.persistence.dao.impl.GenericDaoImpl;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 26.08.2009
 * Time: 16:03:38
 * To change this template use File | Settings | File Templates.
 */
public class DoorComponentDaoImpl extends GenericDaoImpl<DoorComponent> implements DoorComponentDao
{
    @Override
    public List<DoorComponent> findAllBy(DoorMechDef doorMechDef)
    {
        return findByCriteria(Restrictions.eq("doorMechDef", doorMechDef));
    }
}
