package by.dak.utils.convert;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

/**
 * @author admin
 * @version 0.1 07.01.2009
 * @introduced [Preview Plugin Support]
 * @since 2.0.0
 */
public class TimeUtils
{
    public static Timestamp getDayTimestamp(java.util.Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Timestamp timestamp = new Timestamp(calendar.getTime().getTime());
        return timestamp;
    }

    public static Date getDayBefore(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return calendar.getTime();
    }
}
