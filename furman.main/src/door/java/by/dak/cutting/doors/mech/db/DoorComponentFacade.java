package by.dak.cutting.doors.mech.db;

import by.dak.cutting.doors.mech.DoorComponent;
import by.dak.cutting.doors.mech.DoorMechDef;
import by.dak.cutting.facade.BaseFacade;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface DoorComponentFacade extends BaseFacade<DoorComponent>
{
    List<DoorComponent> findAllBy(DoorMechDef doorMechDef);
}
