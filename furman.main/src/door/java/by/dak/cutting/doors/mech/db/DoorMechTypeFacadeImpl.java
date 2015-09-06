package by.dak.cutting.doors.mech.db;

import by.dak.cutting.doors.mech.DoorMechDef;
import by.dak.cutting.doors.mech.DoorMechType;
import by.dak.cutting.facade.BaseFacadeImpl;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 26.08.2009
 * Time: 16:14:37
 * To change this template use File | Settings | File Templates.
 */
public class DoorMechTypeFacadeImpl extends BaseFacadeImpl<DoorMechType> implements DoorMechTypeFacade
{
    @Override
    public List<DoorMechType> findAllBy(DoorMechDef doorMechDef)
    {
        return ((DoorMechTypeDao) getDao()).findAllBy(doorMechDef);
    }
}
