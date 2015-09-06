package by.dak.cutting.doors.mech.db;

import by.dak.cutting.doors.mech.DoorComponent;
import by.dak.cutting.doors.mech.DoorMechDef;
import by.dak.persistence.dao.GenericDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoorComponentDao extends GenericDao<DoorComponent>
{
    List<DoorComponent> findAllBy(DoorMechDef doorMechDef);
}
