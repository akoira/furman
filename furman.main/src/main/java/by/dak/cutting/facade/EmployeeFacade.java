package by.dak.cutting.facade;

import by.dak.persistence.entities.Employee;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface EmployeeFacade extends BaseFacade<Employee>
{

}
