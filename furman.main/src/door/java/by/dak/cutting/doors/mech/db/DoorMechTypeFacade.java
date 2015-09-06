package by.dak.cutting.doors.mech.db;

import by.dak.cutting.doors.mech.DoorMechDef;
import by.dak.cutting.doors.mech.DoorMechType;
import by.dak.cutting.facade.BaseFacade;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface DoorMechTypeFacade extends BaseFacade<DoorMechType>
{
    List<DoorMechType> findAllBy(DoorMechDef doorMechDef);
}
