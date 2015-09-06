package by.dak.cutting.doors;

import by.dak.cutting.doors.mech.DoorColor;
import by.dak.cutting.doors.profile.ProfileComp;
import by.dak.cutting.doors.profile.ProfileDef;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 07.08.2009
 * Time: 16:26:19
 * To change this template use File | Settings | File Templates.
 */
public interface Door
{
    ProfileDef getProfileDef();

    ProfileComp getLeftProfileComp();

    ProfileComp getRightProfileComp();

    ProfileComp getUpProfileComponent();

    ProfileComp getDownProfileComp();

    DoorColor getDoorColor();

    Long getHeight();

    Long getWidth();
}
