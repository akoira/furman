package by.dak.cutting;

import by.dak.persistence.entities.PriceAware;
import by.dak.persistence.entities.Priced;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.jdesktop.application.AbstractBean;

/**
 * User: akoyro
 * Date: 03.12.11
 * Time: 12:16
 */
public class ACodeTypePair<C extends Priced, T extends PriceAware> extends AbstractBean
{
    public static final String PROPERTY_code = "code";
    public static final String PROPERTY_type = "type";


    private C code;
    private T type;

    public C getCode()
    {
        return code;
    }

    public void setCode(C code)
    {
        C old = this.code;
        this.code = code;
        firePropertyChange(PROPERTY_code, old, code);
    }

    public T getType()
    {
        return type;
    }

    public void setType(T type)
    {
        T old = this.type;
        this.type = type;
        firePropertyChange(PROPERTY_type, old, type);
    }

    @Override
    public boolean equals(Object obj)
    {
        return EqualsBuilder.reflectionEquals(this, obj, new String[]{"pcs"});
    }

    @Override
    public int hashCode()
    {
        return HashCodeBuilder.reflectionHashCode(this, new String[]{"pcs"});
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this).append(getType().getName()).append(getCode().getName()).toString();
    }

}
