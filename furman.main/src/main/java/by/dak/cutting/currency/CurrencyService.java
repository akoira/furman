package by.dak.cutting.currency;

import by.dak.cutting.currency.persistence.entity.Currency;
import by.dak.cutting.currency.persistence.entity.CurrencyType;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.CashIncome;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class CurrencyService {

    public static BigDecimal factor(BigDecimal value, Map<CurrencyType, Currency> orderCurrency, CurrencyType from, CurrencyType to, boolean round) {
        BigDecimal srcFactor = getCurrencyValue(from, orderCurrency).orElse(BigDecimal.ONE);
        BigDecimal destFactor = getCurrencyValue(to, orderCurrency).orElse(BigDecimal.ONE);

        BigDecimal result = destFactor.multiply(value).divide(srcFactor, RoundingMode.HALF_UP);
        return round ? result.setScale(2, RoundingMode.HALF_UP) : result;
    }

    public static BigDecimal factor(BigDecimal value, Map<CurrencyType, Currency> orderCurrency, CurrencyType from, CurrencyType to) {
        return factor(value, orderCurrency, from, to, false);
    }

    public static double factor(Double value, Map<CurrencyType, Currency> orderCurrency, CurrencyType from, CurrencyType to) {
        return factor(BigDecimal.valueOf(value), orderCurrency, from, to, false).doubleValue();
    }

    private static Optional<BigDecimal> getCurrencyValue(CurrencyType type, Map<CurrencyType, Currency> orderCurrency) {
        Currency currency = orderCurrency.get(type);
        return Optional.of(BigDecimal.valueOf(currency.getPrice()));
    }
}
