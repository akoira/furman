package by.dak.cutting.facade;

import by.dak.persistence.entities.DepartmentEntity;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface DepartmentFacade extends BaseFacade<DepartmentEntity>
{
}
