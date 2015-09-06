package by.dak.cutting.doors.mech.db;

import by.dak.cutting.doors.mech.DoorMechDef;
import by.dak.cutting.doors.mech.DoorMechType;
import by.dak.persistence.dao.GenericDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoorMechTypeDao extends GenericDao<DoorMechType>
{
    List<DoorMechType> findAllBy(DoorMechDef doorMechDef);
}
