package by.dak.buffer.importer;

import by.dak.buffer.importer.validator.DilerImportFileValidator;
import by.dak.persistence.entities.Customer;
import by.dak.utils.validator.Validator;

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 13.11.2010
 * Time: 22:19:56
 * To change this template use File | Settings | File Templates.
 */
@Validator(validatorClass = DilerImportFileValidator.class)
public class DilerImportFile
{
    private File selectedFile;
    private Customer customer;

    public File getSelectedFile()
    {
        return selectedFile;
    }

    public void setSelectedFile(File selectedFile)
    {
        this.selectedFile = selectedFile;
    }

    public Customer getCustomer()
    {
        return customer;
    }

    public void setCustomer(Customer customer)
    {
        this.customer = customer;
    }
}


