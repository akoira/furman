package by.dak.persistence.entities;

import by.dak.persistence.convert.Provider2StringConverter;
import by.dak.persistence.entities.validator.ProviderValidator;
import by.dak.utils.convert.StringValue;
import by.dak.utils.validator.Validator;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Created by IntelliJ IDEA.
 * User: Oleg Birulia
 * Date: 29.11.2009
 * Time: 11:52:45
 * To change this template use File | Settings | File Templates.
 */


@Entity

@Table(name = "PROVIDER")
@StringValue(converterClass = Provider2StringConverter.class)
@Validator(validatorClass = ProviderValidator.class)
@NamedQueries(value = {
        @NamedQuery(name = "providerSortedByName",
                query = "from Provider p order by p.name")
})
public class Provider extends ProviderManufacturerBase
{
    public Provider()
    {
    }

    public Provider(String shortName, String name, String address, String phoneNumber, String faxNumber,
                    String emailAddress, String officialSite)
    {
        super(shortName, name, address, phoneNumber, faxNumber, emailAddress, officialSite);
    }

}
