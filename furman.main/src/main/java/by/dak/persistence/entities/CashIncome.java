package by.dak.persistence.entities;


import by.dak.persistence.convert.CashIncome2StringConverter;
import by.dak.utils.convert.StringValue;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "cash_income")
@StringValue(converterClass = CashIncome2StringConverter.class)
public class CashIncome extends PersistenceEntity {
    public static final String PROPERTY_customer = "customer";

    @Column(name = "DATE", nullable = false)
    private Date date;

    @Column(name = "REASON_ID", nullable = false)
    private Long reasonId;

    @Column(name = "CURRENCY_ID", nullable = true)
    private Long currencyId;

    @Column(name = "AMOUNT", nullable = false)
    private BigDecimal amount;

    @ManyToOne(targetEntity = Customer.class)
    @JoinColumns({@JoinColumn(name = "CUSTOMER_ID", nullable = true, referencedColumnName = "ID")})
    private Customer customer;

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

    public Long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
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
}
