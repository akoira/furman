package by.dak.cutting.doors.profile;

import by.dak.cutting.CuttingApp;
import by.dak.cutting.doors.profile.swing.ProfileCompDefPanel;
import by.dak.utils.validator.ValidationUtils;
import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.Validator;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;


/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 17.9.2009
 * Time: 15.58.22
 * To change this template use File | Settings | File Templates.
 */
public class ProfileCompValidator implements Validator<ProfileComp>
{
    ResourceMap resourceMap = Application.getInstance(
            CuttingApp.class).getContext().getResourceMap(ProfileCompDefPanel.class);

    // resourceMap взял из ProfileCompDefPanel
//и errors добавлял эти же
    @Override
    public ValidationResult validate(ProfileComp profileComp)
    {
        ValidationResult validationResult = new ValidationResult();
        if (by.dak.utils.validator.ValidationUtils.isLessThan(1, profileComp.getLength().longValue()))
        {
            validationResult.addError(resourceMap.getString("validator.lengthError.text"));
        }
        if (ValidationUtils.isEmpty(profileComp.getProfileCompDef().getCode()))
        {
            validationResult.addError(resourceMap.getString("validator.codeError.text"));
        }
        if (by.dak.utils.validator.ValidationUtils.isLessThan(1, profileComp.getProfileCompDef().getMatThickness().longValue()))
        {
            validationResult.addError(resourceMap.getString("validator.thicknessError.text"));
        }
        if (by.dak.utils.validator.ValidationUtils.isLessThan(1, profileComp.getProfileCompDef().getWidth().longValue()))
        {
            validationResult.addError(resourceMap.getString("validator.widthError.text"));
        }
        if (by.dak.utils.validator.ValidationUtils.isLessThan(1, profileComp.getProfileCompDef().getLength().longValue()))
        {
            validationResult.addError(resourceMap.getString("validator.lengthError.text"));
        }
        else if (by.dak.utils.validator.ValidationUtils.isLessThan(1, profileComp.getProfileCompDef().getIndent().longValue()))
        {
            validationResult.addError(resourceMap.getString("validator.indentError.text"));
        }

        return validationResult;
    }
}
