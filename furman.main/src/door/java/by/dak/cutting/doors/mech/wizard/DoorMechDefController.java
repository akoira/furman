package by.dak.cutting.doors.mech.wizard;

import by.dak.cutting.doors.mech.DoorMechDef;
import by.dak.swing.wizard.DWizardController;
import by.dak.swing.wizard.WizardStep;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 21.08.2009
 * Time: 13:09:33
 * To change this template use File | Settings | File Templates.
 */
public class DoorMechDefController extends DWizardController<DoorMechDefWizardPanelProvider, DoorMechDefModel, DoorMechDefController.Step>
{
    private DoorMechDef doorMechDef;

    public DoorMechDefController(DoorMechDef doorMechDef)
    {
        this.doorMechDef = doorMechDef;
        setProvider(DoorMechDefWizardPanelProvider.createInstance(doorMechDef));
    }

    @Override
    protected void adjustCurrentStep(Step currentStep)
    {
    }

    @Override
    protected void adjustLastStep(Step currentStep)
    {
    }

    @Override
    protected Step getStepBy(String id)
    {
        return Step.valueOf(id);
    }

    @Override
    protected String getIdBy(Step step)
    {
        return step.name();
    }

    public DoorMechDef getDoorMechDef()
    {
        if (getProvider().isCancel())
        {
            return doorMechDef;
        }
        if (doorMechDef == null)
        {
            doorMechDef = new DoorMechDef();
        }
        doorMechDef.setName(getProvider().getNameMechPanel().getPanel().getName());
        doorMechDef.setDoorMechType(getProvider().getMechTypePanel().getPanel().getList());
        doorMechDef.setDoorColor(getProvider().getDoorColorPanel().getPanel().getList());
        doorMechDef.setDoorComponent(getProvider().getDoorComponentPanel().getPanel().getList());
        return doorMechDef;
    }

    public static enum Step implements WizardStep
    {
        DOOR_MECH_NAME,
        DOOR_MECH_TYPE,
        DOOR_COLOR,
        DOOR_COMPONENT
    }
}
