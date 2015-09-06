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

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@org.hibernate.annotations.Proxy(lazy = false)
@Table(name = "SHIFT")
public class ShiftEntity extends PersistenceEntity
{
    public ShiftEntity(String name, DepartmentEntity departmentEntity)
    {
        this.name = name;
        this.department = departmentEntity;
    }

    public ShiftEntity()
    {
    }

    @Column(name = "NAME", nullable = false, length = 255)
    private String name;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, targetEntity = DepartmentEntity.class)
    @JoinColumns({@JoinColumn(name = "FK_DEPARTMENT_ID", referencedColumnName = "ID")})
    private DepartmentEntity department;

    @ManyToMany(targetEntity = Employee.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "SHIFT_EMPLOYEE", joinColumns = {@JoinColumn(name = "FK_SHIFT_ID")}, inverseJoinColumns = {@JoinColumn(name = "FK_EMPLOYEE_ID")})
    private List<Employee> employees = new ArrayList<Employee>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "shiftEntities", targetEntity = Dailysheet.class)
    private List<Dailysheet> dailysheets = new ArrayList<Dailysheet>();

    public void setName(String value)
    {
        this.name = value;
    }

    public String getName()
    {
        return name;
    }

    public void setDepartment(DepartmentEntity departmentEntity)
    {
        this.department = departmentEntity;
    }

    public DepartmentEntity getDepartment()
    {
        return department;
    }

    public void setEmployees(List<Employee> employees)
    {
        this.employees = employees;
    }

    public List<Employee> getEmployees()
    {
        return employees;
    }

    public void setDailysheets(List<Dailysheet> dailysheets)
    {
        this.dailysheets = dailysheets;
    }

    public List<Dailysheet> getDailysheets()
    {
        return dailysheets;
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