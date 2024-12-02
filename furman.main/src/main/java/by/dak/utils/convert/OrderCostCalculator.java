package by.dak.utils.convert;

import by.dak.cutting.currency.persistence.entity.CurrencyType;
import by.dak.cutting.facade.impl.OrderFacadeImpl.OrderDto;
import by.dak.persistence.entities.Order;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import static by.dak.cutting.currency.CurrencyService.factor;

public final class OrderCostCalculator {

    public static OrderDto convertToDto(Order order) {
        Date date = order.getCurrencyDate();

        Double doorsDealerCost = order.getDoorsDealerCost() != null
                ? factor(order.getDoorsDealerCost(), date, CurrencyType.USD, CurrencyType.BYR)
                : null;

        Double totalPrice = order.getTotalPrice() != null
                ? factor(order.getTotalPrice(), date, CurrencyType.USD, CurrencyType.BYR)
                : null;

        Double totalCost = BigDecimal.valueOf(calculateTotalCost(order, doorsDealerCost, totalPrice))
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();

        return new OrderDto(doorsDealerCost, totalPrice, totalCost);
    }

    private static Double calculateTotalCost(Order order, Double doorsDealerCost, Double totalPrice) {
        if (order.getSalePrice() != null) {
            return order.getSalePrice();
        } else if (order.getDiscount() != null) {
            return totalPrice != null ? totalPrice : 0.0;
        } else {
            return (order.getDialerCost() != null ? order.getDialerCost() : 0.0)
                    + (doorsDealerCost != null ? doorsDealerCost : 0.0);
        }
    }

}
