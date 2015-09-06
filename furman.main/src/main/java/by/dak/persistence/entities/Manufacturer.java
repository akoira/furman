/**
 * "Visual Paradigm: DO NOT MODIFY THIS FILE!"
 *
 * This is an automatic generated file. It will be regenerated every time you generate persistence class.
 *
 * Modifying its content may cause the program not work, or your work may lost.
 */

/**
 * Licensee: Anonymous License Type: Purchased
 */
package by.dak.persistence.entities;

import by.dak.persistence.convert.Manufacturer2StringConverter;
import by.dak.persistence.entities.validator.ManufacturerValidator;
import by.dak.utils.convert.StringValue;
import by.dak.utils.validator.Validator;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity

@Table(name = "MANUFACTURER")
@StringValue(converterClass = Manufacturer2StringConverter.class)
@Validator(validatorClass = ManufacturerValidator.class)
public class Manufacturer extends ProviderManufacturerBase
{
    public Manufacturer()
    {
    }

    public Manufacturer(String shortName, String name, String address, String phoneNumber, String faxNumber,
                        String emailAddress, String officialSite)
    {
        super(shortName, name, address, phoneNumber, faxNumber, emailAddress, officialSite);
    }

    public Object clone()
    {
        Manufacturer entity = new Manufacturer();
        entity.setName(getName());
        entity.setShortName(getShortName());
        entity.setAddress(getAddress());
        entity.setEmailAddress(getEmailAddress());
        entity.setFaxNumber(getFaxNumber());
        entity.setOfficialSite(getOfficialSite());
        entity.setPhoneNumber(getPhoneNumber());

        return entity;
    }
}