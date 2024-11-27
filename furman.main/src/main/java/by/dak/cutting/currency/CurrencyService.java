package by.dak.cutting.currency;

import by.dak.cutting.currency.persistence.entity.Currency;
import by.dak.cutting.currency.persistence.entity.CurrencyType;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.CashIncome;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CurrencyService {

    public List<CashIncome> convertAllIncomes(List<CashIncome> allIncomes) {
        return allIncomes.stream().map(income -> factor(income, income.getDate() != null ? income.getDate() : new Date(), CurrencyType.BYR))
                .collect(Collectors.toList());
    }

    public BigDecimal getSum(List<CashIncome> allIncomes) {
        return allIncomes.stream().map(CashIncome::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public CashIncome factor(CashIncome income, Date date, CurrencyType to) {
        CurrencyType from = getCurrencyFromName(income.getCurrency().getType().name());
        if (from.equals(to)) return income;

        BigDecimal newAmount = factor(income.getAmount(), date, from, to);
        income.setAmount(newAmount);
        income.getCurrency().setType(to);
        return income;
    }

    public BigDecimal factor(BigDecimal value, Date date, CurrencyType from, CurrencyType to, boolean round) {
        BigDecimal srcFactor = getCurrencyValue(from, date).orElse(BigDecimal.ONE);
        BigDecimal destFactor = getCurrencyValue(to, date).orElse(BigDecimal.ONE);

        BigDecimal result = destFactor.multiply(value).divide(srcFactor, RoundingMode.HALF_UP);
        return round ? result.setScale(2, RoundingMode.HALF_UP) : result;
    }

    public BigDecimal factor(BigDecimal value, Date date, CurrencyType from, CurrencyType to) {
        return factor(value, date, from, to, false);
    }

    private Optional<BigDecimal> getCurrencyValue(CurrencyType type, Date date) {
        Currency currency = FacadeContext.getCurrencyFacade().findCurrentBy(type, date);
        return Optional.of(BigDecimal.valueOf(currency.getPrice()));
    }

    private CurrencyType getCurrencyFromName(String name) {
        switch (name.toUpperCase()) {
            case "EUR":
                return CurrencyType.EUR;
            case "RUB":
                return CurrencyType.RUB;
            case "USD":
                return CurrencyType.USD;
            default:
                return CurrencyType.BYR;
        }
    }
}
