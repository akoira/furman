package by.dak.cutting.doors.mech.db;

import by.dak.cutting.doors.mech.DoorComponent;
import by.dak.cutting.doors.mech.DoorMechDef;
import by.dak.cutting.facade.BaseFacadeImpl;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 26.08.2009
 * Time: 16:12:56
 * To change this template use File | Settings | File Templates.
 */
public class DoorComponentFacadeImpl extends BaseFacadeImpl<DoorComponent> implements DoorComponentFacade
{
    @Override
    public List<DoorComponent> findAllBy(DoorMechDef doorMechDef)
    {
        return ((DoorComponentDao) getDao()).findAllBy(doorMechDef);
    }
}
