package by.dak.cutting.doors;

import by.dak.cutting.doors.mech.DoorColor;
import by.dak.cutting.doors.mech.DoorMechType;
import by.dak.cutting.doors.profile.ProfileComp;
import by.dak.cutting.doors.profile.ProfileDef;

import java.awt.geom.Rectangle2D;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 10.08.2009
 * Time: 13:58:33
 * To change this template use File | Settings | File Templates.
 */
public class DoorImpl implements Door
{

    private ProfileDef profileDef;

    private ProfileComp leftProfileComp = new ProfileComp();
    private ProfileComp rightProfileComp = new ProfileComp();

    private ProfileComp downProfileComp = new ProfileComp();
    private ProfileComp upProfileComp = new ProfileComp();


    private DoorColor doorColor;
    private DoorMechType doorMechType;
    private Long height;
    private Long width;
    private Long number;


    @Override
    public Long getHeight()
    {
        return height;
    }

    @Override
    public Long getWidth()
    {
        return width;
    }

    public void setHeight(Long height)
    {
        this.height = height;
        update();
    }

    public void setWidth(Long width)
    {
        this.width = width;
    }

    public void setNumber(Long number)
    {
        this.number = number;
    }

    public Long getNumber()
    {
        return number;
    }

    public DoorColor getDoorColor()
    {
        return doorColor;
    }

    public void setDoorColor(DoorColor doorColor)
    {
        this.doorColor = doorColor;
    }

    @Deprecated
    public DoorMechType getDoorMechType()
    {
        return doorMechType;
    }

    @Deprecated
    public void setDoorMechType(DoorMechType doorMechType)
    {
        this.doorMechType = doorMechType;
    }

    public ProfileComp getLeftProfileComp()
    {
        return leftProfileComp;
    }

    public ProfileComp getRightProfileComp()
    {
        return rightProfileComp;
    }

    @Override
    public ProfileDef getProfileDef()
    {
        return profileDef;
    }

    public void setProfileDef(ProfileDef profileDef)
    {
        this.profileDef = profileDef;
        update();
    }

    private void update()
    {
        if (profileDef == null || width == null || height == null)
        {
            return;
        }
        ((ProfileComp) leftProfileComp).setProfileComponentDef(getProfileDef().getLeftProfileCompDef());
        ((ProfileComp) rightProfileComp).setProfileComponentDef(getProfileDef().getRightProfileCompDef());
        ((ProfileComp) upProfileComp).setProfileComponentDef(getProfileDef().getUpProfileCompDef());
        ((ProfileComp) downProfileComp).setProfileComponentDef(getProfileDef().getDownProfileCompDef());

        ((ProfileComp) leftProfileComp).setLength(getHeight());
        ((ProfileComp) rightProfileComp).setLength(getHeight());
        ((ProfileComp) upProfileComp).setLength(getWidth() - getProfileDef().getLeftProfileCompDef().getWidth() - getProfileDef().getRightProfileCompDef().getWidth());
        ((ProfileComp) downProfileComp).setLength(getWidth() - getProfileDef().getLeftProfileCompDef().getWidth() - getProfileDef().getRightProfileCompDef().getWidth());
    }

    public ProfileComp getDownProfileComp()
    {
        return downProfileComp;
    }

    public void setDownProfileComponent(ProfileComp downProfileComp)
    {
        this.downProfileComp = downProfileComp;
    }

    public ProfileComp getUpProfileComponent()
    {
        return upProfileComp;
    }

    public void setUpProfileComponent(ProfileComp upProfileComp)
    {
        this.upProfileComp = upProfileComp;
    }


    public Rectangle2D.Double getBounds()
    {
        return new Rectangle2D.Double(0, 0, getWidth().doubleValue(), getHeight().doubleValue());
    }
}
