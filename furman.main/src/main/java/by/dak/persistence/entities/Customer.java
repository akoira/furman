package by.dak.persistence.entities;


import by.dak.persistence.convert.Customer2StringConverter;
import by.dak.utils.convert.Converter;
import by.dak.utils.convert.StringValue;

import javax.persistence.*;

/**
 * @author Denis Koyro
 * @version 0.1 16.10.2008
 * @introduced [Builder | Overview ]
 * @since 2.0.0
 */
@Entity

@Table(name = "CUSTOMER")
@NamedQueries(value =
        {

                @NamedQuery(name = "customerAccount", query = "select distinct o.customer.id as customerId ,o.customer.name as customerName, sum(o.cost) as total , count(o.id) as amount " +
                        "from Order o where " +
                        "o.readyDate >= :start and " +
                        "o.readyDate < :end and " +
                        "o.status = :status " +
                        "group by o.customer.id, o.customer.name " +
                        "order by o.customer.name"
                )
        })

@StringValue(converterClass = Customer2StringConverter.class)
public class Customer extends PersistenceEntity
{
    public static final Customer NULL_CUSTOMER = new Customer();
    public static final String PROPERTY_name = "name";

    static
    {
        NULL_CUSTOMER.setName(Converter.NULL_STRING);
    }

    public Customer(String name, String address, String phoneNumber, String phoneNumber1, String faxNumber,
                    String emailAddress)
    {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.phoneNumber1 = phoneNumber1;
        this.faxNumber = faxNumber;
        this.emailAddress = emailAddress;
    }

    public Customer()
    {
    }

    @Column(name = "NAME", nullable = false, length = 255)
    private String name;

    @Column(name = "ADDRESS", nullable = true, length = 255)
    private String address;

    @Column(name = "PHONE_NUMBER", nullable = true)
    private String phoneNumber;

    @Column(name = "PHONE_NUMBER1", nullable = true)
    private String phoneNumber1;

    @Column(name = "FAX_NUMBER", nullable = true)
    private String faxNumber;

    @Column(name = "EMAIL_ADDRESS", nullable = true, length = 255)
    private String emailAddress;

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getAddress()
    {
        return address;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setFaxNumber(String faxNumber)
    {
        this.faxNumber = faxNumber;
    }

    public String getFaxNumber()
    {
        return faxNumber;
    }

    public void setEmailAddress(String emailAddress)
    {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress()
    {
        return emailAddress;
    }

    public String getPhoneNumber1()
    {
        return phoneNumber1;
    }

    public void setPhoneNumber1(String phoneNumber1)
    {
        this.phoneNumber1 = phoneNumber1;
    }

    public static boolean isNull(Customer customer)
    {
        return customer == null || customer == Customer.NULL_CUSTOMER;
    }
}
