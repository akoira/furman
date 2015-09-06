package by.dak.cutting.doors.mech;

import by.dak.cutting.doors.mech.stringConverter.DoorMechDef2StringConverter;
import by.dak.persistence.entities.PersistenceEntity;
import by.dak.utils.convert.StringValue;

import javax.persistence.*;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 21.08.2009
 * Time: 11:40:26
 * To change this template use File | Settings | File Templates.
 */
@Entity

@Table(name = "DOOR_MECH_DEF")
@StringValue(converterClass = DoorMechDef2StringConverter.class)
public class DoorMechDef extends PersistenceEntity
{
    public DoorMechDef()
    {
    }

    @Column(name = "NAME", nullable = false, length = 255, unique = true)
    private String name;

    @OneToMany(targetEntity = DoorColor.class, fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "doorMechDef")
    private List<DoorColor> doorColor;

    @OneToMany(targetEntity = DoorComponent.class, fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "doorMechDef")
    private List<DoorComponent> doorComponent;

    @OneToMany(targetEntity = DoorMechType.class, fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "doorMechDef")
    private List<DoorMechType> doorMechType;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public List<DoorMechType> getDoorMechType()
    {
        return doorMechType;
    }

    public void setDoorMechType(List<DoorMechType> doorMechType)
    {
        this.doorMechType = doorMechType;
    }

    public List<DoorColor> getDoorColor()
    {
        return doorColor;
    }

    public void setDoorColor(List<DoorColor> doorColor)
    {
        this.doorColor = doorColor;
    }

    public List<DoorComponent> getDoorComponent()
    {
        return doorComponent;
    }

    public void setDoorComponent(List<DoorComponent> doorComponent)
    {
        this.doorComponent = doorComponent;
    }
}
