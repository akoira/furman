package by.dak.cutting.doors;

import by.dak.cutting.doors.mech.DoorMechDef;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 10.08.2009
 * Time: 14:38:11
 * To change this template use File | Settings | File Templates.
 */
public class DoorsImpl implements Doors
{
    private List<Door> doors;
    private Long height;
    private Long width;
    private DoorMechDef doorMechDef;

    public List<Door> getDoors()
    {
        return doors;
    }

    @Override
    public Long getWidth()
    {
        return width;
    }

    @Override
    public Long getHeigth()
    {
        return height;
    }

    @Override
    public Doors clone()
    {
        if (this == null)
        {
            return null;
        }
        DoorsImpl doors = new DoorsImpl();
        doors = new DoorsImpl();
        List<Door> list = new ArrayList<Door>();
        for (Door door : this.doors)
        {
//            DoorImpl doorimpl = new DoorImpl();
//            doorimpl.setDoorMechType(door.getDoorMechType());
//            doorimpl.setDoorColor(door.getDoorColor());
//            doorimpl.setDoorDrawing(door.getDoorDrawing());
//            doorimpl.setWidth(door.getWidth());
//            doorimpl.setHeight(door.getHeight());
//            doorimpl.setNumber(((DoorImpl) door).getNumber());
//            list.add(doorimpl);
        }
        doors.setDoors(list);
        doors.setHeight(height);
        doors.setWidth(width);
        return doors;
    }

    public void setDoors(List<Door> doors)
    {
        this.doors = doors;
    }

    public void setHeight(Long height)
    {
        this.height = height;
    }

    public void setWidth(Long width)
    {
        this.width = width;
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
