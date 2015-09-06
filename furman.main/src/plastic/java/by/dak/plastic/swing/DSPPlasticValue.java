package by.dak.plastic.swing;

import by.dak.cutting.swing.order.data.TextureBoardDefPair;
import by.dak.utils.validator.Validator;

import java.util.ArrayList;
import java.util.List;

/**
 * User: akoyro
 * Date: 02.11.11
 * Time: 13:59
 */
@Validator(validatorClass = DSPPlasticValueValidator.class)
public class DSPPlasticValue
{
    public static final String PROPERTY_dspPair = "dspPair";
    public static final String PROPERTY_sidePairs = "sidePairs";

    private TextureBoardDefPair dspPair = new TextureBoardDefPair(null, null);
    private List<SidePair> sidePairs = new ArrayList<SidePair>();


    public TextureBoardDefPair getDspPair()
    {
        return dspPair;
    }

    public void setDspPair(TextureBoardDefPair dspPair)
    {
        this.dspPair = dspPair;
    }

    public List<SidePair> getSidePairs()
    {
        return sidePairs;
    }

    public void setSidePairs(List<SidePair> sidePairs)
    {
        this.sidePairs = sidePairs;
    }

    public SidePair getSidePairBy(TextureBoardDefPair firstPair)
    {
        for (SidePair sidePair : sidePairs)
        {
            if (sidePair.getFirst().equals(firstPair))
            {
                return sidePair;
            }
        }
        throw new IllegalArgumentException();
    }

}
