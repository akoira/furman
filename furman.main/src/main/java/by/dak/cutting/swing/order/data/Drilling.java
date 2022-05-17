package by.dak.cutting.swing.order.data;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.apache.commons.lang.builder.EqualsBuilder;

@XStreamAlias("drilling")
public final class Drilling
{
    private Integer number;
    private Integer numberForLoop;
    private Integer numberForHandle;
    private String note;

    @Override
    public Object clone()
    {
        Drilling drilling = new Drilling();
        drilling.number = this.number;
        drilling.numberForLoop = this.numberForLoop;
        drilling.numberForHandle = this.numberForHandle;
        drilling.note = this.note;
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
                .append(this.number, drilling.number)
                .append(this.numberForLoop, drilling.numberForLoop)
                .append(this.numberForHandle, drilling.numberForHandle)
                .append(this.note, drilling.note)
                .isEquals();
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getNumberForLoop() {
        return numberForLoop;
    }

    public void setNumberForLoop(Integer numberForLoop) {
        this.numberForLoop = numberForLoop;
    }

    public Integer getNumberForHandle() {
        return numberForHandle;
    }

    public void setNumberForHandle(Integer numberForHandle) {
        this.numberForHandle = numberForHandle;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
