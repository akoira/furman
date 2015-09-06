/**
 * "Visual Paradigm: DO NOT MODIFY THIS FILE!"
 *
 * This is an automatic generated file. It will be regenerated every time you generate persistence class.
 *
 * Modifying its content may cause the program not work, or your work may lost.
 */

/**
 * Licensee: Anonymous License Type: Purchased
 */
package by.dak.persistence.entities;

import by.dak.persistence.convert.Department2StringConverter;
import by.dak.persistence.entities.validator.DepartmentValidator;
import by.dak.utils.convert.StringValue;
import by.dak.utils.validator.Validator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity

@Table(name = "DEPARTMENT")
@StringValue(converterClass = Department2StringConverter.class)
@Validator(validatorClass = DepartmentValidator.class)
public class DepartmentEntity extends PersistenceEntity
{
    public DepartmentEntity(String name)
    {
        this.name = name;
    }

    public DepartmentEntity()
    {
    }

    @Column(name = "NAME", nullable = false, length = 255)
    private String name;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, targetEntity = Employee.class)
    private List<Employee> employees = new ArrayList<Employee>();

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    private void setEmployees(List<Employee> employees)
    {
        this.employees = employees;
    }

    public List<Employee> getEmployees()
    {
        return employees;
    }

    @Transient
    public void addEmployee(Employee employee)
    {
        getEmployees().add(employee);
    }

    @Transient
    public void removeEmployee(Employee employee)
    {
        getEmployees().remove(employee);
    }
}