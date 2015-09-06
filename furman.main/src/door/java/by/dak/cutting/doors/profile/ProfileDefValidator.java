package by.dak.cutting.doors.profile;

import by.dak.cutting.doors.profile.swing.ProfileDefPanel;
import by.dak.persistence.FacadeContext;
import by.dak.utils.validator.ValidationUtils;
import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.Validator;


/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 5.10.2009
 * Time: 16.40.20
 * To change this template use File | Settings | File Templates.
 */
public class ProfileDefValidator implements Validator<ProfileDef>
{
    private org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.
            Application.getInstance(by.dak.cutting.CuttingApp.class).getContext().
            getResourceMap(ProfileDefPanel.class);

    @Override
    public ValidationResult validate(ProfileDef profileDef)
    {
        ValidationResult validationResult = new ValidationResult();
        if (ValidationUtils.isEmpty(profileDef.getName()))
        {
            validationResult.addError(resourceMap.getString("validator.nameEmpty.text"));
        }
        ProfileDef pd = FacadeContext.getProfileDefFacade().getProfileDefByName(profileDef.getName());
        if (pd != null && !pd.getId().equals(profileDef.getId()))
        {
            validationResult.addError(resourceMap.getString("validator.nameExists.text"));
        }

        return validationResult;
    }
}
