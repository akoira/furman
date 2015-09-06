package by.dak.cutting.statistics;

import by.dak.persistence.entities.PriceAware;
import by.dak.persistence.entities.Service;

/**
 * User: akoyro
 * Date: 29.11.2010
 * Time: 14:31:45
 */
public class ServiceStatistics extends AStatistics<PriceAware, Service>
{
    public ServiceStatistics()
    {
        setAmount(1l);
    }
}
