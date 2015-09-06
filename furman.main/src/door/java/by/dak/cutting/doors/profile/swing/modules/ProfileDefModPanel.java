package by.dak.cutting.doors.profile.swing.modules;

import by.dak.cutting.CuttingApp;
import by.dak.cutting.doors.profile.ProfileDef;
import by.dak.cutting.doors.profile.ProfileDefValidator;
import by.dak.cutting.doors.profile.swing.ProfileDefPanel;
import by.dak.cutting.swing.DModPanel;
import by.dak.persistence.FacadeContext;
import com.jgoodies.validation.ValidationResultModel;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 7.10.2009
 * Time: 17.03.27
 * To change this template use File | Settings | File Templates.
 */
public class ProfileDefModPanel extends DModPanel<ProfileDef>
{
    private ResourceMap rsMap = Application.getInstance(CuttingApp.class).getContext().getResourceMap(
            ProfileDefModPanel.class);

    private ProfileDefPanel profileDefPanel;

    public ProfileDefModPanel()
    {
        profileDefPanel = new ProfileDefPanel();
        addTabs();
    }

    @Override
    public void save()
    {
        FacadeContext.getProfileDefFacade().saveOrUpdate(profileDefPanel.getValue());
    }

    @Override
    public Boolean validateGUI()
    {
        ProfileDefValidator validationResult = new ProfileDefValidator();
        ValidationResultModel validationResultModel = getWarningList().getModel();
        validationResultModel.setResult(validationResult.validate(getValue()));
        getWarningList().setModel(validationResultModel);

        if (!validationResultModel.hasErrors())
        {
            return true;
        }
        else
            return false;
    }

    @Override
    protected void addTabs()
    {
        addTab(rsMap.getString("title"), profileDefPanel);
    }

}
