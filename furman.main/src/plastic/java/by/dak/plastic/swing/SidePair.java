package by.dak.plastic.swing;

import by.dak.cutting.swing.order.data.TextureBoardDefPair;
import by.dak.utils.validator.Validator;

/**
 * User: akoyro
 * Date: 28.11.11
 * Time: 17:36
 */
@Validator(validatorClass = SidePairValidator.class)
public class SidePair
{
    public static final String PROPERTY_need = "need";
    public static final String PROPERTY_first = "first";
    public static final String PROPERTY_second = "second";

    private TextureBoardDefPair first;
    private TextureBoardDefPair second;


    private Boolean need = false;

    public Boolean getNeed()
    {
        return need;
    }

    public void setNeed(Boolean need)
    {
        this.need = need;
    }


    public TextureBoardDefPair getFirst()
    {
        return first;
    }

    public void setFirst(TextureBoardDefPair first)
    {
        this.first = first;
    }

    public TextureBoardDefPair getSecond()
    {
        return second;
    }

    public void setSecond(TextureBoardDefPair second)
    {
        this.second = second;
    }
}
