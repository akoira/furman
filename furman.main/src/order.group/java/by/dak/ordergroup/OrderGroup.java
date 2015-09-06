package by.dak.ordergroup;

import by.dak.ordergroup.converter.OrderGroup2StringConverter;
import by.dak.ordergroup.valdator.OrderGroupValidator;
import by.dak.persistence.entities.AOrder;
import by.dak.persistence.entities.Dailysheet;
import by.dak.persistence.entities.PersistenceEntity;
import by.dak.utils.convert.StringValue;
import by.dak.utils.validator.Validator;

import javax.persistence.*;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: user0
 * Date: 30.03.2010
 * Time: 16:56:03
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "ORDER_GROUP")

@StringValue(converterClass = OrderGroup2StringConverter.class)
@Validator(validatorClass = OrderGroupValidator.class)
public class OrderGroup extends PersistenceEntity
{
    public static final String PROPERTY_name = "name";
    public static final String PROPERTY_dailysheet = "dailysheet";
    public static final String PROPERTY_orders = "orders";


    @OneToMany(mappedBy = "orderGroup", fetch = FetchType.LAZY)
    private List<AOrder> orders;

    @Column(name = "NAME", nullable = false)
    private String name;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, targetEntity = Dailysheet.class)
    @JoinColumns({@JoinColumn(name = "DAILYSHEET_ID", referencedColumnName = "ID", nullable = false)})
    private Dailysheet dailysheet;

    public List<AOrder> getOrders()
    {
        return orders;
    }

    public void setOrders(List<AOrder> orders)
    {
        this.orders = orders;
    }


    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Dailysheet getDailysheet()
    {
        return dailysheet;
    }

    public void setDailysheet(Dailysheet dailysheet)
    {
        this.dailysheet = dailysheet;
    }
}
