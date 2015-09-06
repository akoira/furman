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

import by.dak.persistence.convert.DailySheet2StringConverter;
import by.dak.utils.convert.StringValue;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Entity

@Table(name = "DAILY_SHEET")
@StringValue(converterClass = DailySheet2StringConverter.class)
//@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Dailysheet extends PersistenceEntity
{
    public static final String PROPERTY_date = "date";
    public Dailysheet(Employee employee)
    {
        this.date = new Date(Calendar.getInstance().getTime().getTime());
        this.employee = employee;
    }

    public Dailysheet()
    {
    }

    @Column(name = "DATE", nullable = false)
    private Date date;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, targetEntity = Employee.class)
    @JoinColumns({@JoinColumn(name = "FK_EMPLOYEE_ID", referencedColumnName = "ID")})
    private Employee employee;

    @ManyToMany(targetEntity = ShiftEntity.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "DAILY_SHEET_SHIFT", joinColumns = {@JoinColumn(name = "FK_DAILY_SHEET_ID")}, inverseJoinColumns = {@JoinColumn(name = "FK_SHIFT_ID")})
    private List<ShiftEntity> shiftEntities = new ArrayList<ShiftEntity>();

    public void setDate(Date date)
    {
        this.date = date;
    }

    public Date getDate()
    {
        return date;
    }

    public void setEmployee(Employee value)
    {
        this.employee = value;
    }

    public Employee getEmployee()
    {
        return employee;
    }

    public void setShifts(List<ShiftEntity> shiftEntities)
    {
        this.shiftEntities = shiftEntities;
    }

    public List<ShiftEntity> getShifts()
    {
        return shiftEntities;
    }

    @Transient
    public void addShift(ShiftEntity shiftEntity)
    {
        getShifts().add(shiftEntity);
    }

    @Transient
    public void removeShift(ShiftEntity shiftEntity)
    {
        getShifts().remove(shiftEntity);
    }

    public static Dailysheet valueOf(Dailysheet dailysheet)
    {
        Dailysheet result = new Dailysheet();
        result.date = dailysheet.date;
        result.employee = dailysheet.employee;
        result.shiftEntities = dailysheet.shiftEntities;

        return result;
    }
}