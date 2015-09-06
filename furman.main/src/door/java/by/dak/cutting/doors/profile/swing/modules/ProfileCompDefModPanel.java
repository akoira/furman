package by.dak.cutting.doors.profile.swing.modules;

import by.dak.cutting.CuttingApp;
import by.dak.cutting.doors.profile.ProfileCompDef;
import by.dak.cutting.doors.profile.ProfileCompDefValidator;
import by.dak.cutting.doors.profile.swing.ProfileCompDefPanel;
import by.dak.cutting.swing.DModPanel;
import by.dak.persistence.FacadeContext;
import com.jgoodies.validation.ValidationResultModel;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 8.10.2009
 * Time: 13.24.07
 * To change this template use File | Settings | File Templates.
 */
public class ProfileCompDefModPanel extends DModPanel<ProfileCompDef>
{
    private ResourceMap rsMap = Application.getInstance(CuttingApp.class).getContext().getResourceMap(
            ProfileCompDefModPanel.class);

    private ProfileCompDefPanel profileCompDefPanel;


    public ProfileCompDefModPanel()
    {
        profileCompDefPanel = new ProfileCompDefPanel();
        addTabs();
    }

    @Override
    protected void addTabs()
    {
        addTab(rsMap.getString("title"), profileCompDefPanel);
    }


    @Override
    public void save()
    {

        FacadeContext.getProfileCompDefFacade().saveOrUpdate(getValue());
    }


    @Override
    public Boolean validateGUI()
    {
        ProfileCompDefValidator profileCompDefValidator = new ProfileCompDefValidator();

        ValidationResultModel validationResultModel = getWarningList().getModel();
        validationResultModel.setResult(profileCompDefValidator.validate(getValue()));
        getWarningList().setModel(validationResultModel);

        if (!validationResultModel.hasErrors())
        {
            return true;
        }
        else
            return false;
    }

}
