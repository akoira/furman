package by.dak.cutting.facade;

import by.dak.persistence.entities.ShiftEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ShiftFacade extends BaseFacade<ShiftEntity>
{
    ShiftEntity findShiftByName(String name);

    List<ShiftEntity> findShiftsByDepartmentId(Long id);
}
