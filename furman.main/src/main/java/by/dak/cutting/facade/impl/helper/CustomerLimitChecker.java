package by.dak.cutting.facade.impl.helper;

import by.dak.cutting.facade.CashIncomeFacade;
import by.dak.cutting.facade.OrderFacade;
import by.dak.cutting.facade.impl.OrderFacadeImpl.OrderDto;
import by.dak.cutting.swing.archive.OrderStatusManager;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.CashIncome;
import by.dak.persistence.entities.Customer;
import by.dak.persistence.entities.Order;
import by.dak.persistence.entities.OrderStatus;
import org.jdesktop.application.Application;

import javax.swing.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static by.dak.utils.convert.TimeUtils.parseDateFromString;

public class CustomerLimitChecker {
    private static final OrderFacade orderFacade = FacadeContext.getOrderFacade();
    private static final CashIncomeFacade cashIncomeFacade = FacadeContext.getCashIncomeFacade();
    private static final Date DATE_FROM = parseDateFromString("01-01-2019");
    private static final List<OrderStatus> STATUSES = new ArrayList<>(Arrays.asList(OrderStatus.design, OrderStatus.production, OrderStatus.webDesign));

    public static boolean isCustomerLimitReached(Order order) {
        double limit = roundToTwoDecimalPlaces(order.getCustomer().getLimit().doubleValue());
        if (limit == 0) return false;

        double dialerCost = order.getDialerCost() != null ? order.getDialerCost() : 0.0;
        double totalArrear = getCustomerArrear(order.getCustomer()) + dialerCost;

        return totalArrear > limit;
    }

    public static boolean isCustomerLimitReached(Customer customer) {
        double limit = roundToTwoDecimalPlaces(customer.getLimit().doubleValue());
        if (limit == 0) return false;

        return getCustomerArrear(customer) >= limit;
    }

    private static double getCustomerArrear(Customer customer) {
        List<OrderDto> orders = orderFacade.getAllForArrear(customer, DATE_FROM, STATUSES);
        List<CashIncome> cashIncomes = cashIncomeFacade.getAllIncomesForArrear(customer);

        double ordersSum = orders.stream().mapToDouble(OrderDto::getTotalCost).sum();
        double cashIncomeSum = cashIncomes.stream().mapToDouble(income -> income.getAmount().doubleValue()).sum();

        return roundToTwoDecimalPlaces(ordersSum - cashIncomeSum);
    }

    private static double roundToTwoDecimalPlaces(double value) {
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
