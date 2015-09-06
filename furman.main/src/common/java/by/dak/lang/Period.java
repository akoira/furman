/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package by.dak.lang;

import by.dak.utils.convert.StringValue;
import org.apache.commons.lang.time.DateUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * @author admin
 */
@StringValue(converterClass = Period2StringConverter.class)
public class Period
{

    public static final Period current = new Period(PeriodType.month, "current", getMonthPeriod(Calendar.getInstance().getTime()));

    public static final Period previous = new Period(PeriodType.month, "previous", getMonthPeriod(DateUtils.addMonths(Calendar.getInstance().getTime(), -1)));

    public static final Period all = new Period(PeriodType.all, "all", getAllPeriod(Calendar.getInstance().getTime()));

    private final PeriodType type;


    private final String name;
    private final Date start;
    private final Date end;


    public Period(PeriodType type, String name, Date start, Date end)
    {
        this.type = type;
        this.name = name;
        this.start = start;
        this.end = end;
    }

    public Period(PeriodType type, String name, Date[] dates)
    {
        this(type, name, dates[0], dates[1]);
    }

    public String getName()
    {
        return name;
    }


    public PeriodType getType()
    {
        return type;
    }


    public Date getEnd()
    {
        return end;
    }

    public Date getStart()
    {
        return start;
    }

    public static Date[] getAllPeriod(Date curentdate)
    {
        Date[] dates = new Date[2];
        dates[0] = new Date(0l);
        dates[1] = DateUtils.ceiling(curentdate, Calendar.MONTH);
        return dates;
    }

    public static Date[] getYearPeriod(Date date, boolean curentdate)
    {
        Date[] dates = new Date[2];
        dates[0] = DateUtils.addYears(DateUtils.ceiling(date, Calendar.YEAR), -1);

        if (curentdate)
        {
            dates[1] = DateUtils.ceiling(date, Calendar.MONTH);
        }
        else
        {
            dates[1] = DateUtils.addYears(dates[0], 1);
        }
        return dates;
    }


    public static Date[] getMonthPeriod(Date monthDate)
    {
        Date[] dates = new Date[2];
        dates[1] = DateUtils.ceiling(monthDate, Calendar.MONTH);

        dates[0] = DateUtils.addMonths(dates[1], -1);
        return dates;

    }
}
