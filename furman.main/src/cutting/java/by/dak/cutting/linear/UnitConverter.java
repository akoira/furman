package by.dak.cutting.linear;

/**
 * Created by IntelliJ IDEA.
 * User: dkoyro
 * Date: 12.03.11
 * Time: 20:16
 * To change this template use File | Settings | File Templates.
 */
public class UnitConverter
{
    public static int convertToMiliMetre(double metre)
    {
        return (int) (metre * 1000);
    }

    public static double convertToMetre(int miliMetre)
    {
        return (double) miliMetre / 1000;
    }
}
