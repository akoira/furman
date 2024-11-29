package by.dak.persistence.entities;


import by.dak.cutting.currency.persistence.entity.Currency;
import by.dak.persistence.convert.CashIncome2StringConverter;
import by.dak.utils.convert.StringValue;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "cash_income")
@NamedQueries(value =
        {
                @NamedQuery(name = "allByCustomerReasons",
                        query = "from CashIncome c where c.customer = :customer " +
                                "and c.reasonId in (:reasons)")
        }
)
@StringValue(converterClass = CashIncome2StringConverter.class)
public class CashIncome extends PersistenceEntity {
    public static final String PROPERTY_customer = "customer";

    @Column(name = "DATE", nullable = false)
    private Date date;

    @Column(name = "REASON_ID", nullable = false)
    private Long reasonId;

    @Column(name = "AMOUNT", nullable = false)
    private BigDecimal amount;

    @ManyToOne(targetEntity = Customer.class)
    @JoinColumns({@JoinColumn(name = "CUSTOMER_ID", nullable = true, referencedColumnName = "ID")})
    private Customer customer;

    @ManyToOne(targetEntity = Currency.class)
    @JoinColumns({@JoinColumn(name = "CURRENCY_ID", nullable = true, referencedColumnName = "ID")})
    private Currency currency;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getReasonId() {
        return reasonId;
    }

    public void setReasonId(Long reasonId) {
        this.reasonId = reasonId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
