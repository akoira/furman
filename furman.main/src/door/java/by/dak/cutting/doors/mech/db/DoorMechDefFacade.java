package by.dak.cutting.doors.mech.db;

import by.dak.cutting.doors.mech.DoorMechDef;
import by.dak.cutting.facade.BaseFacade;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface DoorMechDefFacade extends BaseFacade<DoorMechDef>
{
}
