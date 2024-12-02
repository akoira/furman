package by.dak.cutting;

import by.dak.cutting.facade.impl.OrderFacadeImpl.OrderDto;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.CashIncome;
import by.dak.persistence.entities.Customer;
import by.dak.persistence.entities.Order;
import by.dak.persistence.entities.OrderStatus;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static by.dak.utils.convert.TimeUtils.parseDateFromString;

public class TDealerArrear {

    @Test
    public void getAllForArrear__customerId_159951() {
        new SpringConfiguration();

        List<Customer> customers = FacadeContext.getCustomerFacade().loadAll();
        Customer customer = customers.get(0);

        Date dateFrom = parseDateFromString("01-01-2019");
        List<OrderStatus> statuses = new ArrayList<>(Arrays.asList(OrderStatus.design, OrderStatus.production, OrderStatus.webDesign));

        List<OrderDto> orders = FacadeContext.getOrderFacade().getAllForArrear(customer, dateFrom, statuses);
        List<CashIncome> cashIncomes = FacadeContext.getCashIncomeFacade().getAllIncomesForArrear(customer);

        Double ordersSum = orders.stream().mapToDouble(OrderDto::getTotalCost).sum();
        Double cashIncomeSum = cashIncomes.stream().mapToDouble(income -> income.getAmount().doubleValue()).sum();

        Double arrear = BigDecimal.valueOf(ordersSum - cashIncomeSum).setScale(2, RoundingMode.HALF_UP).doubleValue();

        System.out.println(arrear);
    }
}
