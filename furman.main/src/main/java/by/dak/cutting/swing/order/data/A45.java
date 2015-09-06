package by.dak.cutting.swing.order.data;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.apache.commons.lang.builder.EqualsBuilder;

@XStreamAlias("a45")
public class A45 extends DTODecorator
{
    private String upValue;
    private String downValue;
    private String rightValue;
    private String leftValue;

    public A45()
    {
        super(new DefaultDTO());
    }

    public A45(boolean up, boolean down, boolean right, boolean left, String upValue, String downValue, String rightValue, String leftValue)
    {
        super(new DefaultDTO(up, down, right, left));
        this.upValue = upValue;
        this.downValue = downValue;
        this.rightValue = rightValue;
        this.leftValue = leftValue;
    }

    public String getUpValue()
    {
        return upValue;
    }

    public void setUpValue(String upValue)
    {
        this.upValue = upValue;
    }

    public String getDownValue()
    {
        return downValue;
    }

    public void setDownValue(String downValue)
    {
        this.downValue = downValue;
    }

    public String getRightValue()
    {
        return rightValue;
    }

    public void setRightValue(String rightValue)
    {
        this.rightValue = rightValue;
    }

    public String getLeftValue()
    {
        return leftValue;
    }

    public void setLeftValue(String leftValue)
    {
        this.leftValue = leftValue;
    }

    @Override
    public Object clone()
    {
        A45 a45 = new A45();
        a45.setUp(isUp());
        a45.setDown(isDown());
        a45.setRight(isRight());
        a45.setLeft(isLeft());
        if (getUpValue() != null)
        {
            a45.setUpValue(new String(getUpValue()));
        }
        if (getDownValue() != null)
        {
            a45.setDownValue(new String(getDownValue()));
        }
        if (getRightValue() != null)
        {
            a45.setRightValue(new String(getRightValue()));
        }
        if (getLeftValue() != null)
        {
            a45.setLeftValue(new String(getLeftValue()));
        }
        return a45;
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
        A45 a45 = (A45) obj;
        return new EqualsBuilder()
                .append(isUp(), a45.isUp())
                .append(isDown(), a45.isDown())
                .append(isLeft(), a45.isLeft())
                .append(isRight(), a45.isRight())
                .append(getUpValue(), a45.getUpValue())
                .append(getDownValue(), a45.getDownValue())
                .append(getRightValue(), a45.getRightValue())
                .append(getLeftValue(), a45.getLeftValue())
                .isEquals();
    }
}
