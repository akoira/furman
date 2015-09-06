package by.dak.persistence.dao.impl;

import by.dak.persistence.FinderException;
import by.dak.persistence.dao.EmployeeDao;
import by.dak.persistence.entities.DepartmentEntity;
import by.dak.persistence.entities.Employee;

import java.util.List;

import static org.hibernate.criterion.Restrictions.eq;

/**
 * @author Denis Koyro
 * @version 0.1 09.12.2008
 * @introduced [Furniture constructor | Iteration 1]
 * @since 1.0.0
 */
public class EmployeeDaoImpl extends GenericDaoImpl<Employee> implements EmployeeDao
{

    /**
     * only used in tests
     */
    public Employee findEmployeeByName(String name, String surname) throws FinderException
    {
        List<Employee> employees = findByCriteria(eq("name", name), eq("surname", surname));
        // replace by generic method and
        assert employees.size() == 1;
        if (employees.isEmpty())
        {
            throw new FinderException("Employee with name = " + name + ", surname = " + surname
                    + " was not found.");
        }
        else
        {
            return employees.get(0);
        }
    }

    public List<Employee> findEmployeeByDepartment(DepartmentEntity departmentEntity)
    {
        return findByCriteria(eq("department", departmentEntity));
    }

}
