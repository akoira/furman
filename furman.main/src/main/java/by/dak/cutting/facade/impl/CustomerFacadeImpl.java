package by.dak.cutting.facade.impl;

import by.dak.cutting.facade.BaseFacadeImpl;
import by.dak.cutting.facade.CustomerFacade;
import by.dak.lang.Period;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.NamedQueryDefinition;
import by.dak.persistence.NamedQueryParameter;
import by.dak.persistence.entities.Customer;
import by.dak.persistence.entities.OrderStatus;
import by.dak.persistence.entities.customer.CustomerAccount;
import org.hibernate.transform.AliasToBeanResultTransformer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vitaly Kozlovski
 * @version 0.1 24.01.2009
 * @introduced [Furniture constructor | Iteration 1]
 * @since 1.0.0
 */
public class CustomerFacadeImpl extends BaseFacadeImpl<Customer> implements CustomerFacade
{


    private String customerName;

    @Override
    public Customer findById(long id, boolean lock)
    {
        return dao.findById(id, lock);
    }

    @Override
    public Customer getCurrentCustomer()
    {
        return findUniqueByField(Customer.PROPERTY_name, getCustomerName());
    }

    public String getCustomerName()
    {
        return customerName;
    }

    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }

    @Override
    public List<CustomerAccount> loadCustomerAccounts(Period period)
    {
        NamedQueryDefinition definition = new NamedQueryDefinition();
        definition.setNameQuery("customerAccount");
        definition.getParameterList().add(NamedQueryParameter.getDateParameter("start", period.getStart()));
        definition.getParameterList().add(NamedQueryParameter.getDateParameter("end", period.getEnd()));
        definition.getParameterList().add(NamedQueryParameter.getObjectParameter("status", OrderStatus.made));
        definition.setResultTransformer(new AliasToBeanResultTransformer(CustomerAccountDTO.class));
        List<CustomerAccountDTO> dtos = getDao().findAllBy(definition);

        List<CustomerAccount> accounts = new ArrayList<CustomerAccount>(dtos.size());
        for (CustomerAccountDTO customerAccountDTO : dtos)
        {
            accounts.add(customerAccountDTO.toCustomerAccount());
        }
        return accounts;
    }


    public static class CustomerAccountDTO
    {
        private Long customerId;
        private String customerName;
        private Double total;
        private Long amount;

        public Long getCustomerId()
        {
            return customerId;
        }

        public void setCustomerId(Long customerId)
        {
            this.customerId = customerId;
        }

        public String getCustomerName()
        {
            return customerName;
        }

        public void setCustomerName(String customerName)
        {
            this.customerName = customerName;
        }

        public Double getTotal()
        {
            return total;
        }

        public void setTotal(Double total)
        {
            this.total = total;
        }

        public Long getAmount()
        {
            return amount;
        }

        public void setAmount(Long amount)
        {
            this.amount = amount;
        }

        public CustomerAccount toCustomerAccount()
        {
            CustomerAccount customerAccount = new CustomerAccount();
            customerAccount.setCustomer(FacadeContext.getCustomerFacade().findBy(getCustomerId()));
            customerAccount.setAmount(getAmount());
            customerAccount.setTotal(getTotal());
            return customerAccount;
        }
    }
}
