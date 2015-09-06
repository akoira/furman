/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package by.dak.lang;

import by.dak.utils.convert.EntityToStringConverter;
import org.apache.commons.lang.time.DateFormatUtils;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

/**
 * @author admin
 */
public class Period2StringConverter implements EntityToStringConverter<Period>
{
    private ResourceMap resourceMap = Application.getInstance().
            getContext().getResourceMap(this.getClass());

    @Override
    public String convert(Period e)
    {
        if (e == Period.all)
        {
            return resourceMap.getString("period.all.name");
        }
        else
        {
            if (e.getType() == PeriodType.year)
                return DateFormatUtils.format(e.getStart(), "yyyy");
            else
                return DateFormatUtils.format(e.getStart(), "MM-yyyy");
        }
    }

}
