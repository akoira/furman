/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.swing.order.data;

import by.dak.cutting.swing.order.cellcomponents.editors.cuttoff.CutoffPanel;
import by.dak.utils.convert.EntityToStringConverter;
import by.dak.utils.convert.StringValue;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.jdesktop.application.Application;

/**
 * @author admin
 */
public class Cutoff
{

    @StringValue(converterClass = Angle2StringConverter.class)
    public enum Angle
    {
        upLeft,
        upRight,
        downLeft,
        downRight
    }

    private Angle angle;
    private Double hOffset;
    private Double vOffset;

    public Angle getAngle()
    {
        return angle;
    }

    public void setAngle(Angle angle)
    {
        this.angle = angle;
    }

    public Double getHOffset()
    {
        return hOffset;
    }

    public void setHOffset(Double hOffset)
    {
        this.hOffset = hOffset;
    }

    public Double getVOffset()
    {
        return vOffset;
    }

    public void setVOffset(Double vOffset)
    {
        this.vOffset = vOffset;
    }

    public static class Angle2StringConverter implements EntityToStringConverter<Angle>
    {
        @Override
        public String convert(Angle angle)
        {
            return Application.getInstance().getContext().getResourceMap(CutoffPanel.class).getString(angle.name());
        }
    }

    @Override
    protected Object clone()
    {
        Cutoff cutoff = new Cutoff();
        cutoff.setAngle(getAngle());
        cutoff.setHOffset(getHOffset());
        cutoff.setVOffset(getVOffset());
        return cutoff;
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }
}
