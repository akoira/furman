package by.dak.utils.convert;

import by.dak.cutting.currency.persistence.entity.CurrencyType;
import by.dak.persistence.entities.Order;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import static by.dak.cutting.currency.CurrencyService.factor;
import static by.dak.persistence.entities.Order.valueOf;

public final class UsdToBynConverter {

    public static Order convertAndCalculateTotalCost(Order order) {
        Date date = order.getCurrencyDate();
        Order newOrder = valueOf(order);

        if (newOrder.getDoorsDealerCost() != null) {
            newOrder.setDoorsDealerCost(factor(newOrder.getDoorsDealerCost(), date, CurrencyType.USD, CurrencyType.BYR));
        }
        if (newOrder.getTotalPrice() != null) {
            newOrder.setTotalPrice(factor(newOrder.getTotalPrice(), date, CurrencyType.USD, CurrencyType.BYR));
        }

        newOrder.setTotalCost(BigDecimal.valueOf(calculateTotalCost(newOrder)).setScale(2, RoundingMode.HALF_UP).doubleValue());

        return newOrder;
    }

    private static Double calculateTotalCost(Order order) {
        if (order.getSalePrice() != null) {
            return order.getSalePrice();
        } else if (order.getDiscount() != null) {
            return order.getTotalPrice();
        } else {
            return (order.getDialerCost() != null ? order.getDialerCost() : 0.0)
                    + (order.getDoorsDealerCost() != null ? order.getDoorsDealerCost() : 0.0);
        }
    }

}
