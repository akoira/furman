package by.dak.cutting.doors.profile.def.swing.wizard;

import by.dak.cutting.doors.profile.*;
import by.dak.persistence.FacadeContext;
import by.dak.swing.wizard.DWizardController;
import by.dak.swing.wizard.WizardStep;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 28.9.2009
 * Time: 12.22.44
 * To change this template use File | Settings | File Templates.
 */
public class ProfileDefController extends DWizardController<ProfileDefWizardPanelProvider, ProfileDefModel, ProfileDefController.Step>
{
    private ProfileDef profileDef;

    public ProfileDefController(ProfileDef profileDef)
    {
        this.profileDef = profileDef;
        setProvider(ProfileDefWizardPanelProvider.createInstance(profileDef));
    }

    @Override
    protected void adjustCurrentStep(Step currentStep)
    {

        switch (currentStep)
        {
            case PROFILE_DEF:
                break;
            case PROFILE_COMP_DEF_TOP:
                getProvider().getProfileCompDefPanel(currentStep).setValue(getProfileCompDefBy(ProfilePosition.top,
                        null));
                break;
            case PROFILE_COMP_DEF_DOCKING:
                getProvider().getProfileCompDefPanel(currentStep).setValue(getProfileCompDefBy(ProfilePosition.docking,
                        null));
                break;
            case PROFILE_COMP_DEF_DOWN:
                getProvider().getProfileCompDefPanel(currentStep).setValue(getProfileCompDefBy(ProfilePosition.down,
                        FacadeContext.getProfileCompDefFacade().findBy(profileDef, ProfilePosition.top)));
                break;
            case PROFILE_COMP_DEF_LEFT:
                getProvider().getProfileCompDefPanel(currentStep).setValue(getProfileCompDefBy(ProfilePosition.left,
                        FacadeContext.getProfileCompDefFacade().findBy(profileDef, ProfilePosition.right)));
                break;
            case PROFILE_COMP_DEF_RIGHT:
                getProvider().getProfileCompDefPanel(currentStep).setValue(getProfileCompDefBy(ProfilePosition.right,
                        null));
                break;
            default:
                throw new IllegalArgumentException();
        }

    }

    private ProfileCompDef getProfileCompDefBy(ProfilePosition position, ProfileCompDef prevProfileCompDef)
    {     //prevPofileComp - значение предыдущего значения panel  
        ProfileCompDef profileCompDef = FacadeContext.getProfileCompDefFacade().findBy(profileDef, position);
        if (profileCompDef == null)
        {
            profileCompDef = new ProfileCompDef();
            profileCompDef.setProfileDef(profileDef);
            profileCompDef.setPosition(position);
            if (prevProfileCompDef != null)
            {
                profileCompDef.setCode(prevProfileCompDef.getCode());
                profileCompDef.setMatThickness(prevProfileCompDef.getMatThickness());
                profileCompDef.setIndent(prevProfileCompDef.getIndent());
                profileCompDef.setLength(prevProfileCompDef.getLength());
                profileCompDef.setWidth(prevProfileCompDef.getWidth());
            }
        }
        return profileCompDef;
    }

    @Override
    protected void adjustLastStep(Step currentStep)
    {
        if (getLastSelectedStep() != null)
        {
            switch (getLastSelectedStep())
            {
                case PROFILE_DEF:
                    if (!new ProfileDefValidator().validate(getProvider().getProfileDefPanel().getValue()).hasErrors())
                    {//если не заполняем панель и нажимаем prev, чтобы не было null-pointer-exception
                        getProvider().getProfileDefPanel().save();
                    }
                    break;
                case PROFILE_COMP_DEF_TOP:
                    if (!new ProfileCompDefValidator().validate(getProvider().getProfileCompDefPanel(getLastSelectedStep()).getValue()).hasErrors())
                    {
                        getProvider().getProfileCompDefPanel(getLastSelectedStep()).save();

                    }
                    break;
                case PROFILE_COMP_DEF_DOWN:
                    if (!new ProfileCompDefValidator().validate(getProvider().getProfileCompDefPanel(getLastSelectedStep()).getValue()).hasErrors())
                    {
                        getProvider().getProfileCompDefPanel(getLastSelectedStep()).save();
                    }
                    break;
                case PROFILE_COMP_DEF_RIGHT:
                    if (!new ProfileCompDefValidator().validate(getProvider().getProfileCompDefPanel(getLastSelectedStep()).getValue()).hasErrors())
                    {
                        getProvider().getProfileCompDefPanel(getLastSelectedStep()).save();
                    }
                    break;
                case PROFILE_COMP_DEF_LEFT:
                    if (!new ProfileCompDefValidator().validate(getProvider().getProfileCompDefPanel(getLastSelectedStep()).getValue()).hasErrors())
                    {
                        getProvider().getProfileCompDefPanel(getLastSelectedStep()).save();
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Exception");
            }
        }
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
        PROFILE_DEF,
        PROFILE_COMP_DEF_TOP,
        PROFILE_COMP_DEF_DOWN,
        PROFILE_COMP_DEF_RIGHT,
        PROFILE_COMP_DEF_LEFT,
        PROFILE_COMP_DEF_DOCKING
    }
}
