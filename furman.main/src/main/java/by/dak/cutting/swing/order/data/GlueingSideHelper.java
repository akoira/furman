package by.dak.cutting.swing.order.data;

import by.dak.persistence.entities.BorderDefEntity;
import by.dak.persistence.entities.TextureEntity;
import by.dak.persistence.entities.predefined.Side;
import org.apache.commons.lang.StringUtils;

/**
 * User: akoyro
 * Date: 12.11.2010
 * Time: 14:19:08
 */
public class GlueingSideHelper
{
    private Glueing glueing;


    private Side side;

    public GlueingSideHelper(Glueing glueing, Side side)
    {
        this.glueing = glueing;
        this.side = side;
    }

    public boolean isGlueing()
    {
        try
        {
            Boolean isGlueing = (Boolean) Glueing.class.getMethod("is" + getSideMethodName(), new Class[]
                    {
                    }).invoke(glueing, new Object[]
                    {
                    });
            return isGlueing;

        }
        catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }

    }

    private String getSideMethodName()
    {
        return StringUtils.upperCase(side.name().substring(0, 1)) + side.name().substring(1);
    }

    public boolean isSide()
    {
        return isGlueing();
    }

    public TextureEntity getTexture()
    {
        try
        {
            return (TextureEntity) Glueing.class.getMethod("get" + getSideMethodName() + "Texture", new Class[]
                    {
                    }).invoke(glueing, new Object[]
                    {
                    });
        }
        catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }

    public BorderDefEntity getBorderDef()
    {
        try
        {
            return (BorderDefEntity) Glueing.class.getMethod("get" + getSideMethodName() + "BorderDef", new Class[]
                    {
                    }).invoke(glueing, new Object[]
                    {
                    });
        }
        catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }

    public Side getSide()
    {
        return side;
    }

}
