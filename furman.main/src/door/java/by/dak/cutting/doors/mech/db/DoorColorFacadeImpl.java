package by.dak.cutting.doors.mech.db;

import by.dak.cutting.doors.mech.DoorColor;
import by.dak.cutting.doors.mech.DoorMechDef;
import by.dak.cutting.facade.BaseFacadeImpl;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 26.08.2009
 * Time: 16:11:23
 * To change this template use File | Settings | File Templates.
 */
public class DoorColorFacadeImpl extends BaseFacadeImpl<DoorColor> implements DoorColorFacade
{
    @Override
    public List<DoorColor> findAllBy(DoorMechDef doorMechDef)
    {
        return ((DoorColorDao) getDao()).findAllBy(doorMechDef);
    }
}
