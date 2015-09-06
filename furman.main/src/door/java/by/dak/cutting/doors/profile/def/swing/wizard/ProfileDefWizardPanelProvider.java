package by.dak.cutting.doors.profile.def.swing.wizard;

import by.dak.cutting.CuttingApp;
import by.dak.cutting.doors.profile.ProfileDef;
import by.dak.cutting.doors.profile.swing.modules.ProfileCompDefModPanel;
import by.dak.cutting.doors.profile.swing.modules.ProfileDefModPanel;
import by.dak.swing.wizard.DWizardPanelProvider;
import org.netbeans.spi.wizard.WizardException;

import javax.swing.*;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 28.9.2009
 * Time: 16.50.45
 * To change this template use File | Settings | File Templates.
 */
public class ProfileDefWizardPanelProvider extends DWizardPanelProvider<ProfileDefController.Step>
{
    //провожу сохранение последнего шага по нажатию finish_button 
    private ProfileDef profileDef;
    private ProfileDefModPanel profileDefModPanel;
    private ProfileCompDefModPanel profileCompDefModPanelTop;
    private ProfileCompDefModPanel profileCompDefModPanelDown;
    private ProfileCompDefModPanel profileCompDefModPanelRight;
    private ProfileCompDefModPanel profileCompDefModPanelLeft;
    private ProfileCompDefModPanel profileCompDefModPanelDocking;
    private boolean isCancel = false;

    protected ProfileDefWizardPanelProvider(String title, String[] steps, String[] descriptions, ProfileDef profileDef)
    {
        super(title, steps, descriptions);
        this.profileDef = profileDef;

    }

    public static ProfileDefWizardPanelProvider createInstance(ProfileDef profileDef)
    {
        return new ProfileDefWizardPanelProvider(CuttingApp.getApplication().getContext().getResourceMap(ProfileDefWizardPanelProvider.class).getString("title"),
                new String[]{
                        ProfileDefController.Step.PROFILE_DEF.name(),
                        ProfileDefController.Step.PROFILE_COMP_DEF_TOP.name(),
                        ProfileDefController.Step.PROFILE_COMP_DEF_DOWN.name(),
                        ProfileDefController.Step.PROFILE_COMP_DEF_RIGHT.name(),
                        ProfileDefController.Step.PROFILE_COMP_DEF_LEFT.name(),
                        ProfileDefController.Step.PROFILE_COMP_DEF_DOCKING.name()
                },
                new String[]{
                        CuttingApp.getApplication().getContext().getResourceMap(ProfileDefWizardPanelProvider.class).getString(ProfileDefController.Step.PROFILE_DEF.name()),
                        CuttingApp.getApplication().getContext().getResourceMap(ProfileDefWizardPanelProvider.class).getString(ProfileDefController.Step.PROFILE_COMP_DEF_TOP.name()),
                        CuttingApp.getApplication().getContext().getResourceMap(ProfileDefWizardPanelProvider.class).getString(ProfileDefController.Step.PROFILE_COMP_DEF_DOWN.name()),
                        CuttingApp.getApplication().getContext().getResourceMap(ProfileDefWizardPanelProvider.class).getString(ProfileDefController.Step.PROFILE_COMP_DEF_RIGHT.name()),
                        CuttingApp.getApplication().getContext().getResourceMap(ProfileDefWizardPanelProvider.class).getString(ProfileDefController.Step.PROFILE_COMP_DEF_LEFT.name()),
                        CuttingApp.getApplication().getContext().getResourceMap(ProfileDefWizardPanelProvider.class).getString(ProfileDefController.Step.PROFILE_COMP_DEF_DOCKING.name())
                },
                profileDef);
    }

    @Override
    public JPanel getComponentBy(ProfileDefController.Step step)
    {
        switch (step)
        {
            case PROFILE_DEF:
                return getProfileDefPanel();
            case PROFILE_COMP_DEF_TOP:
                return getProfileCompDefPanel(step);
            case PROFILE_COMP_DEF_DOWN:
                return getProfileCompDefPanel(step);
            case PROFILE_COMP_DEF_RIGHT:
                return getProfileCompDefPanel(step);
            case PROFILE_COMP_DEF_LEFT:
                return getProfileCompDefPanel(step);
            case PROFILE_COMP_DEF_DOCKING:
                return getProfileCompDefPanel(step);
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public ProfileDefController.Step valueOf(String id)
    {
        return ProfileDefController.Step.valueOf(id);
    }

    @Override
    public boolean cancel(Map map)
    {
        isCancel = true;
        return super.cancel(map);
    }

    public boolean isCancel()
    {
        return isCancel;
    }

    public ProfileDefModPanel getProfileDefPanel()
    {

        if (profileDefModPanel == null)
        {
            profileDefModPanel = new ProfileDefModPanel();
            profileDefModPanel.setValue(profileDef);
        }

        return profileDefModPanel;
    }

    public ProfileCompDefModPanel getProfileCompDefPanel(ProfileDefController.Step step)
    {   //в зависимости от шага создаю нужную панель
        //каждая панель сохраняется и загружается
        // используя одну панель получается билеберда(изменяем свойства одной панели),
        // а при изменении шага, сохраняется панель на том шаге, где создана текущая панель
        switch (step)
        {
            case PROFILE_COMP_DEF_TOP:
            {
                if (profileCompDefModPanelTop == null)
                {
                    profileCompDefModPanelTop = new ProfileCompDefModPanel();
                }
                return profileCompDefModPanelTop;
            }
            case PROFILE_COMP_DEF_DOWN:
            {
                if (profileCompDefModPanelDown == null)
                {
                    profileCompDefModPanelDown = new ProfileCompDefModPanel();
                }
                return profileCompDefModPanelDown;
            }
            case PROFILE_COMP_DEF_RIGHT:
            {
                if (profileCompDefModPanelRight == null)
                {
                    profileCompDefModPanelRight = new ProfileCompDefModPanel();
                }
                return profileCompDefModPanelRight;
            }
            case PROFILE_COMP_DEF_LEFT:
            {
                if (profileCompDefModPanelLeft == null)
                {
                    profileCompDefModPanelLeft = new ProfileCompDefModPanel();
                }
                return profileCompDefModPanelLeft;
            }
            case PROFILE_COMP_DEF_DOCKING:
            {
                if (profileCompDefModPanelDocking == null)
                {
                    profileCompDefModPanelDocking = new ProfileCompDefModPanel();
                }
                return profileCompDefModPanelDocking;
            }
            default:
                throw new IllegalArgumentException("");

        }
    }

    @Override
    protected Object finish(Map map) throws WizardException
    {
        getProfileCompDefPanel(ProfileDefController.Step.PROFILE_COMP_DEF_DOCKING).save();
        return super.finish(map);
    }
}
