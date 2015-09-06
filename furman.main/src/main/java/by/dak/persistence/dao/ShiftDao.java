package by.dak.persistence.dao;

import by.dak.persistence.entities.ShiftEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShiftDao extends GenericDao<ShiftEntity>
{

    List<ShiftEntity> findShiftsByDepartmentId(Long departmentID);
}
