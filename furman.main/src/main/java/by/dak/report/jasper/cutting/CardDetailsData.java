package by.dak.report.jasper.cutting;

import org.apache.commons.lang.StringUtils;

/**
 * @author Denis Koyro
 * @version 0.1 18.03.2009
 * @introduced [Furniture constructor | Iteration 1]
 * @since 1.0.0
 */
public class CardDetailsData implements Comparable<CardDetailsData>
{
    private String detailCode;
    private String detailSize;
    private String leftGroove;
    private String upGroove;
    private String rightGroove;
    private String downGroove;
    private String leftAngle;
    private String upAngle;
    private String rightAngle;
    private String downAngle;
    private String detailNumber;

    public CardDetailsData(String detailCode, String detailSize, String leftGroove, String upGroove, String rightGroove, String downGroove,
                           String leftAngle, String upAngle, String rightAngle, String downAngle, String detailNumber)
    {
        this.detailCode = detailCode;
        this.detailSize = detailSize;
        this.leftGroove = leftGroove;
        this.upGroove = upGroove;
        this.rightGroove = rightGroove;
        this.downGroove = downGroove;
        this.leftAngle = leftAngle;
        this.upAngle = upAngle;
        this.rightAngle = rightAngle;
        this.downAngle = downAngle;
        this.detailNumber = detailNumber;
    }

    public String getDetailCode()
    {
        return detailCode;
    }

    private void setDetailCode(String detailCode)
    {
        this.detailCode = detailCode;
    }

    public String getDetailSize()
    {
        return detailSize;
    }

    private void setDetailSize(String detailSize)
    {
        this.detailSize = detailSize;
    }

    public String getLeftGroove()
    {
        return leftGroove;
    }

    private void setLeftGroove(String leftGroove)
    {
        this.leftGroove = leftGroove;
    }

    public String getUpGroove()
    {
        return upGroove;
    }

    private void setUpGroove(String upGroove)
    {
        this.upGroove = upGroove;
    }

    public String getRightGroove()
    {
        return rightGroove;
    }

    private void setRightGroove(String rightGroove)
    {
        this.rightGroove = rightGroove;
    }

    public String getDownGroove()
    {
        return downGroove;
    }

    private void setDownGroove(String downGroove)
    {
        this.downGroove = downGroove;
    }

    public String getLeftAngle()
    {
        return leftAngle;
    }

    private void setLeftAngle(String leftAngle)
    {
        this.leftAngle = leftAngle;
    }

    public String getUpAngle()
    {
        return upAngle;
    }

    private void setUpAngle(String upAngle)
    {
        this.upAngle = upAngle;
    }

    public String getRightAngle()
    {
        return rightAngle;
    }

    private void setRightAngle(String rightAngle)
    {
        this.rightAngle = rightAngle;
    }

    public String getDownAngle()
    {
        return downAngle;
    }

    private void setDownAngle(String downAngle)
    {
        this.downAngle = downAngle;
    }

    public String getDetailNumber()
    {
        return detailNumber;
    }

    private void setDetailNumber(String detailNumber)
    {
        this.detailNumber = detailNumber;
    }

    public String getGrooveHtml()
    {
        return getValueHtml(getLeftGroove(), getUpGroove(), getRightGroove(), getDownGroove());
    }

    public String getAngleHtml()
    {
        return getValueHtml(getLeftAngle(), getUpAngle(), getRightAngle(), getDownAngle());
    }

    private String getValueHtml(String left, String up, String right, String down)
    {
        StringBuffer buffer = new StringBuffer();
        if (StringUtils.isNotEmpty(left))
        {
            buffer.append("<div>").append(left).append("</div>");
        }
        if (StringUtils.isNotEmpty(up))
        {
            buffer.append("<div>").append(up).append("</div>");
        }
        if (StringUtils.isNotEmpty(right))
        {
            buffer.append("<div>").append(right).append("</div>");
        }
        if (StringUtils.isNotEmpty(down))
        {
            buffer.append("<div>").append(down).append("</div>");
        }
        return buffer.toString();
    }

    @Override
    public int compareTo(CardDetailsData o)
    {
        return detailNumber.compareTo(o.detailNumber);
    }
}
