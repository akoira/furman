package by.dak.cutting.doors.mech.db;

import by.dak.cutting.doors.mech.DoorColor;
import by.dak.cutting.doors.mech.DoorMechDef;
import by.dak.persistence.dao.GenericDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoorColorDao extends GenericDao<DoorColor>
{
    List<DoorColor> findAllBy(DoorMechDef doorMechDef);
}
