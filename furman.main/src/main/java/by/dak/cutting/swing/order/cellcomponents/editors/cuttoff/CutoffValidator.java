package by.dak.cutting.swing.order.cellcomponents.editors.cuttoff;

import by.dak.cutting.swing.order.data.Cutoff;


/**
 * User: akoyro
 * Date: 20.04.2009
 * Time: 20:22:56
 */
public class CutoffValidator
{
    private Cutoff cutoff;

    public CutoffValidator(Cutoff cutoff)
    {
        super();
        this.cutoff = cutoff;
    }

    public boolean validate()
    {
        return cutoff != null && cutoff.getAngle() != null &&
                cutoff.getHOffset() != null &&
                cutoff.getHOffset() > 0 &&
                cutoff.getVOffset() != null &&
                cutoff.getVOffset() > 0;
    }
}
