package by.dak.cutting.statistics;

import by.dak.persistence.entities.PriceAware;
import by.dak.persistence.entities.PriceEntity;
import by.dak.persistence.entities.Priced;

/**
 * User: akoyro
 * Date: 18.11.2010
 * Time: 13:44:47
 */
public abstract class AStatistics<T extends PriceAware, C extends Priced>
{
    private T type;
    private C code;
    private Long amount;
    private Double size;
    private PriceEntity price;

    public T getType()
    {
        return type;
    }

    public void setType(T type)
    {
        this.type = type;
    }

    public C getCode()
    {
        return code;
    }

    public void setCode(C code)
    {
        this.code = code;
    }


    public PriceEntity getPrice()
    {
        return price;
    }

    public void setPrice(PriceEntity price)
    {
        this.price = price;
    }

    public Double getSize()
    {
        return size;
    }

    public void setSize(Double size)
    {
        this.size = size;
    }

    public Long getAmount()
    {
        return amount;
    }

    public void setAmount(Long amount)
    {
        this.amount = amount;
    }

    public Double getTotal()
    {

        return getSize() == null || getPrice() == null || getPrice().getPrice() == null ? 0 : getSize() * getPrice().getPrice();
    }

    public Double getTotalDealer()
    {
        return getSize() == null || getPrice() == null || getPrice().getPrice() == null ? 0 : getSize() * getPrice().getPriceDealer();
    }

}
