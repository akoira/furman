package by.dak.cutting.swing.order.data;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.apache.commons.lang.builder.EqualsBuilder;

@XStreamAlias("groove")
public class Groove extends DTODecorator
{
    //todo должны быть использованы в модели
    private int depthUp;
    private int distanceUp;

    //todo должны быть использованы в модели
    private int depthDown;
    private int distanceDown;

    //todo должны быть использованы в модели
    private int depthRight;
    private int distanceRight;

    //todo должны быть использованы в модели
    private int depthLeft;
    private int distanceLeft;


    public Groove()
    {
        super(new DefaultDTO());
    }

    public Groove(boolean up, boolean down, boolean right, boolean left)
    {
        super(new DefaultDTO(up, down, right, left));
    }

    public int getDepthUp()
    {
        return depthUp;
    }

    public void setDepthUp(int depthUp)
    {
        this.depthUp = depthUp;
    }

    public int getDistanceUp()
    {
        return distanceUp;
    }

    public void setDistanceUp(int distanceUp)
    {
        this.distanceUp = distanceUp;
    }

    public int getDepthDown()
    {
        return depthDown;
    }

    public void setDepthDown(int depthDown)
    {
        this.depthDown = depthDown;
    }

    public int getDistanceDown()
    {
        return distanceDown;
    }

    public void setDistanceDown(int distanceDown)
    {
        this.distanceDown = distanceDown;
    }

    public int getDepthRight()
    {
        return depthRight;
    }

    public void setDepthRight(int depthRight)
    {
        this.depthRight = depthRight;
    }

    public int getDistanceRight()
    {
        return distanceRight;
    }

    public void setDistanceRight(int distanceRight)
    {
        this.distanceRight = distanceRight;
    }

    public int getDepthLeft()
    {
        return depthLeft;
    }

    public void setDepthLeft(int depthLeft)
    {
        this.depthLeft = depthLeft;
    }

    public int getDistanceLeft()
    {
        return distanceLeft;
    }

    public void setDistanceLeft(int distanceLeft)
    {
        this.distanceLeft = distanceLeft;
    }

    @Override
    public Object clone()
    {
        Groove groove = new Groove();
        groove.setUp(isUp());
        groove.setDown(isDown());
        groove.setRight(isRight());
        groove.setLeft(isLeft());
        return groove;
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
        Groove groove = (Groove) obj;
        return new EqualsBuilder()
                .append(isUp(), groove.isUp())
                .append(isDown(), groove.isDown())
                .append(isLeft(), groove.isLeft())
                .append(isRight(), groove.isRight())
                .isEquals();
    }
}
