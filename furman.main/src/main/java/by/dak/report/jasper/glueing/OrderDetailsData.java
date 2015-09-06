package by.dak.report.jasper.glueing;

import java.awt.*;

/**
 * @author Denis Koyro
 * @version 0.1 29.01.2009
 * @introduced [Furniture constructor | Iteration 1]
 * @since 1.0.0
 */
public class OrderDetailsData
{
    private String detail;
    private String material;
    private String texture;
    private Integer count;
    private String width;
    private String length;

    private Image glueing;
    private String glueingUpValue;
    private String glueingDownValue;
    private String glueingLeftValue;
    private String glueingRightValue;
    private String millingPictureName;
    private String millingComments;
    private String drillingPictureName;
    private String drillingComments;

    public OrderDetailsData(String detail, String material, String texture, Integer count, String width, String length,
                            Image glueing, String glueingUpValue, String glueingDownValue, String glueingLeftValue, String glueingRightValue,
                            String millingPictureName, String millingComments, String drillingPictureName, String drillingComments)
    {
        this.detail = detail;
        this.material = material;
        this.texture = texture;
        this.count = count;
        this.width = width;
        this.length = length;
        this.glueing = glueing;
        this.glueingUpValue = glueingUpValue;
        this.glueingDownValue = glueingDownValue;
        this.glueingLeftValue = glueingLeftValue;
        this.glueingRightValue = glueingRightValue;
        this.millingPictureName = millingPictureName;
        this.millingComments = millingComments;
        this.drillingPictureName = drillingPictureName;
        this.drillingComments = drillingComments;
    }

    public String getDetail()
    {
        return detail;
    }

    private void setDetail(String detail)
    {
        this.detail = detail;
    }

    public String getMaterial()
    {
        return material;
    }

    private void setMaterial(String material)
    {
        this.material = material;
    }

    public String getTexture()
    {
        return texture;
    }

    private void setTexture(String texture)
    {
        this.texture = texture;
    }

    public Integer getCount()
    {
        return count;
    }

    private void setCount(Integer count)
    {
        this.count = count;
    }

    public String getWidth()
    {
        return width;
    }

    private void setWidth(String width)
    {
        this.width = width;
    }

    public String getLength()
    {
        return length;
    }

    private void setLength(String length)
    {
        this.length = length;
    }

    public Image getGlueing()
    {
        return glueing;
    }

    private void setGlueing(Image glueing)
    {
        this.glueing = glueing;
    }

    public String getGlueingUpValue()
    {
        return glueingUpValue;
    }

    private void setGlueingUpValue(String glueingUpValue)
    {
        this.glueingUpValue = glueingUpValue;
    }

    public String getGlueingDownValue()
    {
        return glueingDownValue;
    }

    private void setGlueingDownValue(String glueingDownValue)
    {
        this.glueingDownValue = glueingDownValue;
    }

    public String getGlueingLeftValue()
    {
        return glueingLeftValue;
    }

    private void setGlueingLeftValue(String glueingLeftValue)
    {
        this.glueingLeftValue = glueingLeftValue;
    }

    public String getGlueingRightValue()
    {
        return glueingRightValue;
    }

    private void setGlueingRightValue(String glueingRightValue)
    {
        this.glueingRightValue = glueingRightValue;
    }

    public String getMillingPictureName()
    {
        return millingPictureName;
    }

    private void setMillingPictureName(String millingPictureName)
    {
        this.millingPictureName = millingPictureName;
    }

    public String getMillingComments()
    {
        return millingComments;
    }

    private void setMillingComments(String millingComments)
    {
        this.millingComments = millingComments;
    }

    public String getDrillingPictureName()
    {
        return drillingPictureName;
    }

    private void setDrillingPictureName(String drillingPictureName)
    {
        this.drillingPictureName = drillingPictureName;
    }

    public String getDrillingComments()
    {
        return drillingComments;
    }

    private void setDrillingComments(String drillingComments)
    {
        this.drillingComments = drillingComments;
    }
}