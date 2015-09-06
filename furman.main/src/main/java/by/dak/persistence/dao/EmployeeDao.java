package by.dak.persistence.dao;

import by.dak.persistence.FinderException;
import by.dak.persistence.entities.DepartmentEntity;
import by.dak.persistence.entities.Employee;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeDao extends GenericDao<Employee>
{
    Employee findEmployeeByName(String name, String surname) throws FinderException;

    List<Employee> findEmployeeByDepartment(DepartmentEntity departmentEntity);
}
