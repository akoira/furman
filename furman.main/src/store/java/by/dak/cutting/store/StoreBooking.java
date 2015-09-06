package by.dak.cutting.store;

import java.util.Calendar;
import java.util.Date;

/**
 * User: akoyro
 * Date: 09.01.2011
 * Time: 12:57:24
 */
public class StoreBooking
{
    private Date productionDate = Calendar.getInstance().getTime();

    public Date getProductionDate()
    {
        return productionDate;
    }

    public void setProductionDate(Date productionDate)
    {
        this.productionDate = productionDate;
    }
}
