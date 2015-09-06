package by.dak.cutting.doors;

import by.dak.cutting.doors.mech.DoorMechDef;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 07.08.2009
 * Time: 16:26:32
 * To change this template use File | Settings | File Templates.
 */
public interface Doors
{
    public List<Door> getDoors();

    public Long getWidth();

    public Long getHeigth();

    public DoorMechDef getDoorMechDef();

    public Doors clone();
}
