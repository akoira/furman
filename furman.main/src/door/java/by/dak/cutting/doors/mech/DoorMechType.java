package by.dak.cutting.doors.mech;

import by.dak.cutting.doors.mech.panels.MechName;
import by.dak.cutting.doors.mech.stringConverter.DoorMechType2StringConverter;
import by.dak.persistence.entities.PersistenceEntity;
import by.dak.utils.convert.StringValue;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 21.08.2009
 * Time: 11:40:41
 * To change this template use File | Settings | File Templates.
 */
@Entity

@Table(name = "DOOR_MECH_TYPE")
@StringValue(converterClass = DoorMechType2StringConverter.class)
public class DoorMechType extends PersistenceEntity implements MechName
{
    public DoorMechType()
    {
    }

    @Column(name = "NAME", nullable = false, length = 255)
    private String name;

    @ManyToOne(targetEntity = DoorMechDef.class)
    @JoinColumns({@JoinColumn(name = "DOOR_MECH_DEF_ID", referencedColumnName = "ID")})
    private DoorMechDef doorMechDef;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public DoorMechDef getDoorMechDef()
    {
        return doorMechDef;
    }

    public void setDoorMechDef(DoorMechDef doorMechDef)
    {
        this.doorMechDef = doorMechDef;
    }
}
