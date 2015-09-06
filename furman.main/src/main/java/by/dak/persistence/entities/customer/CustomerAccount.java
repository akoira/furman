package by.dak.persistence.entities.customer;

import by.dak.persistence.entities.Customer;

/**
 * User: akoyro
 * Date: 04.05.11
 * Time: 15:26
 */
public class CustomerAccount
{
    private Customer customer;
    private Double total;
    private Double debit;


    private Long amount;

    public Customer getCustomer()
    {
        return customer;
    }

    public void setCustomer(Customer customer)
    {
        this.customer = customer;
    }

    public Double getTotal()
    {
        return total;
    }

    public void setTotal(Double total)
    {
        this.total = total;
    }

    public Double getDebit()
    {
        return debit;
    }

    public void setDebit(Double debit)
    {
        this.debit = debit;
    }

    public Double getCredit()
    {
        return getTotal() - getDebit();
    }

    public Long getAmount()
    {
        return amount;
    }

    public void setAmount(Long amount)
    {
        this.amount = amount;
    }
}
