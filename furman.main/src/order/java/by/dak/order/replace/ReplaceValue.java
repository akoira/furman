package by.dak.order.replace;

import by.dak.cutting.ACodeTypePair;

/**
 * User: akoyro
 * Date: 03.12.11
 * Time: 12:15
 */
public class ReplaceValue<S extends ACodeTypePair, R extends ACodeTypePair>
{
    public static final String PROPERTY_sourcePair = "sourcePair";
    public static final String PROPERTY_replacePair = "replacePair";

    private S sourcePair;
    private R replacePair;

    public S getSourcePair()
    {
        return sourcePair;
    }

    public void setSourcePair(S sourcePair)
    {
        this.sourcePair = sourcePair;
    }

    public R getReplacePair()
    {
        return replacePair;
    }

    public void setReplacePair(R replacePair)
    {
        this.replacePair = replacePair;
    }
}
