package by.dak.utils;

/**
 * User: akoyro
 * Date: 19.04.2009
 * Time: 18:25:45
 */
public class MathUtils
{
    public static double round(double value, int signs)
    {
        //округление до двух знаков
        java.math.BigDecimal x = new java.math.BigDecimal(value);
        x = x.setScale(signs, java.math.BigDecimal.ROUND_HALF_UP);
        return x.doubleValue();
    }
}
