package by.dak.cutting.swing.order.data;

import by.dak.cutting.swing.xml.convertors.BorderDefCodeConvertor;
import by.dak.cutting.swing.xml.convertors.TextureCodeConvertor;
import by.dak.persistence.entities.BorderDefEntity;
import by.dak.persistence.entities.TextureEntity;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import org.apache.commons.lang.builder.EqualsBuilder;

@XStreamAlias("milling")
public class Milling
{
    private String imageFileUuid;
    private String fileUuid;

    private double curveLength = 0;
    private double directLength = 0;

    private double curveGluingLength = 0;
    private double directGluingLength = 0;


    @XStreamConverter(TextureCodeConvertor.class)
    private TextureEntity texture;

    @XStreamConverter(BorderDefCodeConvertor.class)
    private BorderDefEntity borderDef;


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
        Milling milling = (Milling) obj;
        return new EqualsBuilder()
                .append(getFileUuid(), milling.getFileUuid())
                .append(getCurveLength(), milling.getCurveLength())
                .append(getDirectLength(), milling.getDirectLength())
                .append(getImageFileUuid(), milling.getImageFileUuid())
                .isEquals();
    }

    public String getFileUuid()
    {
        return fileUuid;
    }

    public void setFileUuid(String fileUuid)
    {
        this.fileUuid = fileUuid;
    }

    public double getCurveLength()
    {
        return curveLength;
    }

    public void setCurveLength(double curveLength)
    {
        this.curveLength = curveLength;
    }

    public double getDirectLength()
    {
        return directLength;
    }

    public void setDirectLength(double directLength)
    {
        this.directLength = directLength;
    }

    public String getImageFileUuid()
    {
        return imageFileUuid;
    }

    public void setImageFileUuid(String imageFileUuid)
    {
        this.imageFileUuid = imageFileUuid;
    }

    public TextureEntity getTexture()
    {
        return texture;
    }

    public void setTexture(TextureEntity texture)
    {
        this.texture = texture;
    }

    public BorderDefEntity getBorderDef()
    {
        return borderDef;
    }

    public void setBorderDef(BorderDefEntity borderDef)
    {
        this.borderDef = borderDef;
    }


    public double getCurveGluingLength()
    {
        return curveGluingLength;
    }

    public void setCurveGluingLength(double curveGluingLength)
    {
        this.curveGluingLength = curveGluingLength;
    }

    public double getDirectGluingLength()
    {
        return directGluingLength;
    }

    public void setDirectGluingLength(double directGluingLength)
    {
        this.directGluingLength = directGluingLength;
    }
}
