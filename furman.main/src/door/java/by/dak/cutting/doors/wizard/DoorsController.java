package by.dak.cutting.doors.wizard;

import by.dak.cutting.doors.Doors;
import by.dak.swing.wizard.DWizardController;
import by.dak.swing.wizard.WizardStep;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 19.08.2009
 * Time: 14:41:24
 * To change this template use File | Settings | File Templates.
 */
public class DoorsController extends DWizardController<DoorsWizardPanelProvider, DoorModel, DoorsController.Step>
{
    private Doors doors;

    public DoorsController(Doors doors)
    {
        if (doors == null)
        {
            this.doors = null;
        }
        else
        {
            this.doors = doors.clone();
        }

        setProvider(DoorsWizardPanelProvider.createInstance(doors));
    }

    @Override
    protected void adjustCurrentStep(Step currentStep)
    {
        switch (currentStep)
        {
            case DOOR_DETAILS:
                switch (getLastSelectedStep())
                {
                    case DOOR_INFO:
                        getProvider().getDoorsDetailPanel().getDoorsDetailPanel().setDoors(getProvider().getDoorsInfoPanel().getDoorsInfoPanel().getDoors());
                        break;
                    case DOOR_DESIGN:
                        getProvider().getDoorsDetailPanel().getDoorsDetailPanel().setDoors(getProvider().getDoorEditPanel().getDoorEditTabbedPanel().getDoors());
                        break;
                }
                break;
            case DOOR_DESIGN:
                getProvider().getDoorEditPanel().getDoorEditTabbedPanel().setDoors(getProvider().getDoorsDetailPanel().getDoorsDetailPanel().getDoors());
                break;
            case DOOR_DETAILED:
                getProvider().getDoorCellEditPanel().getDoorCellEditTabbedPanel().setDoors(getProvider().getDoorEditPanel().getDoorEditTabbedPanel().getDoors());
                break;
        }
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

    public static enum Step implements WizardStep
    {
        DOOR_INFO,
        DOOR_DETAILS,
        DOOR_DESIGN,
        DOOR_DETAILED
    }

    public Doors getDoors()
    {
        if (getProvider().isCancel())
        {
            return doors;
        }
        return getProvider().getDoorCellEditPanel().getDoorCellEditTabbedPanel().getDoors();
    }
}

