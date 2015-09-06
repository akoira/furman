package by.dak.persistence.entities;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Created by IntelliJ IDEA.
 * User: Oleg Birulia
 * Date: 08.12.2009
 * Time: 16:50:26
 * To change this template use File | Settings | File Templates.
 */
@MappedSuperclass
public abstract class ProviderManufacturerBase extends PersistenceEntity
{
    public static final String PROPERTY_name = "name";


    @Column(name = "NAME", nullable = false, length = 255)
    private String name;

    @Column(name = "SHORT_NAME", nullable = false, length = 255)
    private String shortName;

    @Column(name = "ADDRESS", nullable = true, length = 255)
    private String address;

    @Column(name = "PHONE_NUMBER", nullable = true, length = 255)
    private String phoneNumber;

    @Column(name = "FAX_NUMBER", nullable = true, length = 255)
    private String faxNumber;

    @Column(name = "EMAIL_ADDRESS", nullable = true, length = 255)
    private String emailAddress;

    @Column(name = "OFFICIAL_SITE", nullable = true, length = 255)
    private String officialSite;

    public ProviderManufacturerBase()
    {
    }

    public ProviderManufacturerBase(String shortName, String name, String address, String phoneNumber,
                                    String faxNumber, String emailAddress, String officialSite)
    {
        this.shortName = shortName;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.faxNumber = faxNumber;
        this.emailAddress = emailAddress;
        this.officialSite = officialSite;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getShortName()
    {
        return shortName;
    }

    public void setShortName(String shortName)
    {
        this.shortName = shortName;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public String getFaxNumber()
    {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber)
    {
        this.faxNumber = faxNumber;
    }

    public String getEmailAddress()
    {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress)
    {
        this.emailAddress = emailAddress;
    }

    public String getOfficialSite()
    {
        return officialSite;
    }

    public void setOfficialSite(String officialSite)
    {
        this.officialSite = officialSite;
    }
}
