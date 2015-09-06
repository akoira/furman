package by.dak.cutting.doors.mech.db;

import by.dak.cutting.doors.mech.DoorColor;
import by.dak.cutting.doors.mech.DoorMechDef;
import by.dak.cutting.facade.BaseFacade;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface DoorColorFacade extends BaseFacade<DoorColor>
{
    List<DoorColor> findAllBy(DoorMechDef doorMechDef);
}
