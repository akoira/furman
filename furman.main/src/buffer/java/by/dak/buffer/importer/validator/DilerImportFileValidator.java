package by.dak.buffer.importer.validator;


import by.dak.buffer.importer.DilerImportFile;
import by.dak.persistence.entities.validator.AResourceValidator;
import com.jgoodies.validation.ValidationResult;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 12.11.2010
 * Time: 17:50:08
 * To change this template use File | Settings | File Templates.
 */
public class DilerImportFileValidator extends AResourceValidator<DilerImportFile>
{
    @Override
    public ValidationResult validate(DilerImportFile dilerImportFile)
    {
        ValidationResult validationResult = new ValidationResult();
        if (dilerImportFile.getSelectedFile() == null)
        {
            validationResult.addError(resourceMap.getString("validator.filePath"));
        }
        if (dilerImportFile.getCustomer() == null)
        {
            validationResult.addError(resourceMap.getString("validator.customer"));
        }
        return validationResult;
    }
}
