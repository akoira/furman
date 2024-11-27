package by.dak.persistence.entities;

import by.dak.persistence.convert.CashIncome2StringConverter;
import by.dak.utils.convert.StringValue;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "discounts")
@StringValue(converterClass = CashIncome2StringConverter.class)
@AttributeOverride(name = "deleted", column = @Column(name = "deleted", nullable = false, columnDefinition = "tinyint(4) default 0"))
public class Discounts extends PersistenceEntity {
    public static final String PROPERTY_order = "order";

    @Column(name = "TYPE", nullable = false)
    private String type;

    @Column(name = "AMOUNT", nullable = false)
    private BigDecimal amount;

    @OneToOne
    @JoinColumn(name = "ORDER_ID", nullable = false)
    private Order order;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

//    public Long getOrder() {
//        return orderId;
//    }
//
//    public void setOrder(Long orderId) {
//        this.orderId = orderId;
//    }

}
