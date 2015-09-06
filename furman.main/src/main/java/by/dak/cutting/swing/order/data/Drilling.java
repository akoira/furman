package by.dak.cutting.swing.order.data;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.apache.commons.lang.builder.EqualsBuilder;

@XStreamAlias("drilling")
public class Drilling extends DTODecorator
{
    public Drilling()
    {
        super(new DefaultDTO());
    }

    public Drilling(String picName, String notes)
    {
        super(new DefaultDTO(picName, notes));
    }

    @Override
    public Object clone()
    {
        Drilling drilling = new Drilling();
        if (getPicName() != null)
        {
            drilling.setPicName(new String(getPicName()));
        }
        if (getNotes() != null)
        {
            drilling.setNotes(new String(getNotes()));
        }
        return drilling;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (obj == this)
        {
            return true;
        }
        if (obj.getClass() != getClass())
        {
            return false;
        }
        Drilling drilling = (Drilling) obj;
        return new EqualsBuilder()
                .append(getPicName(), drilling.getPicName())
                .append(getNotes(), drilling.getNotes())
                .isEquals();
    }
}