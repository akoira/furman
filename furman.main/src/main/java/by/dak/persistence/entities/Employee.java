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

import by.dak.persistence.convert.Employee2StringConverter;
import by.dak.persistence.entities.validator.EmployeeValidator;
import by.dak.utils.convert.StringValue;
import by.dak.utils.validator.Validator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity

@Table(name = "EMPLOYEE")
@StringValue(converterClass = Employee2StringConverter.class)
@Validator(validatorClass = EmployeeValidator.class)
public class Employee extends PersistenceEntity
{

    public Employee()
    {
    }

    @Column(name = "NAME", nullable = false, length = 255)
    private String name;

    @Column(name = "SURNAME", nullable = false, length = 255)
    private String surname;

    @Column(name = "PASSWORD", nullable = false, length = 255)
    private String password;

    @Column(name = "USERNAME", nullable = false, length = 255)
    private String userName;


    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, targetEntity = DepartmentEntity.class)
    @JoinColumns({@JoinColumn(name = "FK_DEPARTMENT_ID", referencedColumnName = "ID", nullable = false)})
    private DepartmentEntity department;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY, mappedBy = "employees", targetEntity = ShiftEntity.class)
    private List<ShiftEntity> shiftEntities = new ArrayList<ShiftEntity>();

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setSurname(String surname)
    {
        this.surname = surname;
    }

    public String getSurname()
    {
        return surname;
    }

    public void setDepartment(DepartmentEntity departmentEntity)
    {
        this.department = departmentEntity;
    }

    public DepartmentEntity getDepartment()
    {
        return department;
    }

    public void setShifts(List<ShiftEntity> shiftEntities)
    {
        this.shiftEntities = shiftEntities;
    }

    public List<ShiftEntity> getShifts()
    {
        return shiftEntities;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }
}