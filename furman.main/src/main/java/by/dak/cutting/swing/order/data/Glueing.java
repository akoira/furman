package by.dak.cutting.swing.order.data;

import by.dak.cutting.swing.order.data.validator.GlueingValidator;
import by.dak.cutting.swing.xml.convertors.BorderDefCodeConvertor;
import by.dak.cutting.swing.xml.convertors.TextureCodeConvertor;
import by.dak.persistence.entities.BorderDefEntity;
import by.dak.persistence.entities.TextureEntity;
import by.dak.utils.validator.Validator;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import org.apache.commons.lang.builder.EqualsBuilder;

@XStreamAlias("glueing")
@Validator(validatorClass = GlueingValidator.class)
public class Glueing extends DTODecorator
{
    @XStreamConverter(TextureCodeConvertor.class)
    private TextureEntity upTexture;

    @XStreamConverter(BorderDefCodeConvertor.class)
    private BorderDefEntity upBorderDef;

    @XStreamConverter(TextureCodeConvertor.class)
    private TextureEntity downTexture;
    @XStreamConverter(BorderDefCodeConvertor.class)
    private BorderDefEntity downBorderDef;

    @XStreamConverter(TextureCodeConvertor.class)
    private TextureEntity rightTexture;
    @XStreamConverter(BorderDefCodeConvertor.class)
    private BorderDefEntity rightBorderDef;

    @XStreamConverter(TextureCodeConvertor.class)
    private TextureEntity leftTexture;
    @XStreamConverter(BorderDefCodeConvertor.class)
    private BorderDefEntity leftBorderDef;

    @XStreamOmitField
    private TextureEntity defaultTexture;
    @XStreamOmitField
    private BorderDefEntity defaultBorderDef;

    public Glueing()
    {
        super(new DefaultDTO());
    }

    public Glueing(boolean up, boolean down, boolean right, boolean left,
                   TextureEntity upTexture, TextureEntity downTexture, TextureEntity rightTexture, TextureEntity leftTexture)
    {
        super(new DefaultDTO(up, down, right, left));
        this.upTexture = upTexture;
        this.downTexture = downTexture;
        this.rightTexture = rightTexture;
        this.leftTexture = leftTexture;
    }

    public TextureEntity getUpTexture()
    {
        return upTexture;
    }

    public void setUpTexture(TextureEntity upTexture)
    {
        this.upTexture = upTexture;
    }

    public TextureEntity getDownTexture()
    {
        return downTexture;
    }

    public void setDownTexture(TextureEntity downTexture)
    {
        this.downTexture = downTexture;
    }

    public TextureEntity getRightTexture()
    {
        return rightTexture;
    }

    public void setRightTexture(TextureEntity rightTexture)
    {
        this.rightTexture = rightTexture;
    }

    public TextureEntity getLeftTexture()
    {
        return leftTexture;
    }

    public void setLeftTexture(TextureEntity leftTexture)
    {
        this.leftTexture = leftTexture;
    }

    public TextureEntity getDefaultTexture()
    {
        return defaultTexture;
    }

    public void setDefaultTexture(TextureEntity defaultTexture)
    {
        this.defaultTexture = defaultTexture;
    }

    public BorderDefEntity getUpBorderDef()
    {
        return upBorderDef;
    }

    public void setUpBorderDef(BorderDefEntity upBorderDef)
    {
        this.upBorderDef = upBorderDef;
    }

    public BorderDefEntity getDownBorderDef()
    {
        return downBorderDef;
    }

    public void setDownBorderDef(BorderDefEntity downBorderDef)
    {
        this.downBorderDef = downBorderDef;
    }

    public BorderDefEntity getRightBorderDef()
    {
        return rightBorderDef;
    }

    public void setRightBorderDef(BorderDefEntity rightBorderDef)
    {
        this.rightBorderDef = rightBorderDef;
    }

    public BorderDefEntity getLeftBorderDef()
    {
        return leftBorderDef;
    }

    public void setLeftBorderDef(BorderDefEntity leftBorderDef)
    {
        this.leftBorderDef = leftBorderDef;
    }

    public BorderDefEntity getDefaultBorderDef()
    {
        return defaultBorderDef;
    }

    public void setDefaultBorderDef(BorderDefEntity defaultBorderDef)
    {
        this.defaultBorderDef = defaultBorderDef;
    }

    @Override
    public Object clone()
    {
        Glueing glueing = new Glueing();
        glueing.setUp(isUp());
        glueing.setDown(isDown());
        glueing.setRight(isRight());
        glueing.setLeft(isLeft());
        if (getUpTexture() != null)
        {
            glueing.setUpTexture((TextureEntity) getUpTexture().clone());
        }
        if (getDownTexture() != null)
        {
            glueing.setDownTexture((TextureEntity) getDownTexture().clone());
        }
        if (getRightTexture() != null)
        {
            glueing.setRightTexture((TextureEntity) getRightTexture().clone());
        }
        if (getLeftTexture() != null)
        {
            glueing.setLeftTexture((TextureEntity) getLeftTexture().clone());
        }
        return glueing;
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
        Glueing glueing = (Glueing) obj;
        return new EqualsBuilder()
                .append(isUp(), glueing.isUp())
                .append(isDown(), glueing.isDown())
                .append(isLeft(), glueing.isLeft())
                .append(isRight(), glueing.isRight())
                .append(getUpTexture(), glueing.getUpTexture())
                .append(getDownTexture(), glueing.getDownTexture())
                .append(getRightTexture(), glueing.getRightTexture())
                .append(getLeftTexture(), glueing.getLeftTexture())
                .append(getUpBorderDef(), glueing.getUpBorderDef())
                .append(getDownBorderDef(), glueing.getDownBorderDef())
                .append(getRightBorderDef(), glueing.getRightBorderDef())
                .append(getLeftBorderDef(), glueing.getLeftBorderDef())
                .isEquals();
    }
}