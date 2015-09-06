package by.dak.process;

/**
 * User: akoyro
 * Date: 20.09.11
 * Time: 16:00
 */

import by.dak.persistence.entities.PriceAware;
import by.dak.persistence.entities.Priced;

/**
 * An instanse of the class describes material handling
 */
public class AProcess
{
    private PriceAware priceAware;
    private Priced code;

    public PriceAware getPriceAware()
    {
        return priceAware;
    }

    public void setPriceAware(PriceAware priceAware)
    {
        this.priceAware = priceAware;
    }

    public Priced getCode()
    {
        return code;
    }

    public void setCode(Priced code)
    {
        this.code = code;
    }
}
