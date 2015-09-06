/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.doors.profile;

import by.dak.cutting.CuttingApp;
import by.dak.cutting.doors.profile.swing.ProfileCompDefPanel;
import by.dak.utils.validator.ValidationUtils;
import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.Validator;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

/**
 * @author admin
 */
public class ProfileCompDefValidator implements Validator<ProfileCompDef>
{
    private ResourceMap resourceMap = Application.getInstance(
            CuttingApp.class).getContext().getResourceMap(ProfileCompDefPanel.class);

    //errors из ProfileCompDefPanel
    @Override
    public ValidationResult validate(ProfileCompDef profileCompDef)
    {
        ValidationResult validationResult = new ValidationResult();

        if (ValidationUtils.isEmpty(profileCompDef.getCode()))
        {
            validationResult.addError(resourceMap.getString("validator.codeError.text"));
        }
        if (profileCompDef.getMatThickness() == null || by.dak.utils.validator.ValidationUtils.isLessThan(1, profileCompDef.getMatThickness().longValue()))
        {
            validationResult.addError(resourceMap.getString("validator.thicknessError.text"));
        }
        if (profileCompDef.getWidth() == null || by.dak.utils.validator.ValidationUtils.isLessThan(1, profileCompDef.getWidth().longValue()))
        {
            validationResult.addError(resourceMap.getString("validator.widthError.text"));
        }
        if (profileCompDef.getLength() == null || by.dak.utils.validator.ValidationUtils.isLessThan(1, profileCompDef.getLength().longValue()))
        {
            validationResult.addError(resourceMap.getString("validator.lengthError.text"));
        }
        if (profileCompDef.getIndent() == null || by.dak.utils.validator.ValidationUtils.isLessThan(1, profileCompDef.getIndent().longValue()))
        {
            validationResult.addError(resourceMap.getString("validator.indentError.text"));
        }
        return validationResult;
    }
}
