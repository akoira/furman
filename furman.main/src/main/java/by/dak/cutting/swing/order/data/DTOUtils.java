package by.dak.cutting.swing.order.data;

import org.apache.commons.lang.StringUtils;

/**
 * User: akoyro
 * Date: 13.03.2009
 * Time: 21:12:21
 */
public class DTOUtils
{
    public static boolean isValueSet(DTO dto)
    {
        if (dto == null)
            return false;
        if (dto instanceof Drilling)
        {
            return !StringUtils.isBlank(((Drilling) dto).getPicName()) || !StringUtils.isBlank(((Drilling) dto).getNotes());
        }
        return dto.isDown() || dto.isLeft() || dto.isUp() || dto.isRight();
    }

}
