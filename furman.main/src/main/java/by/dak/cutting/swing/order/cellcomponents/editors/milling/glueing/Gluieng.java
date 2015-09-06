package by.dak.cutting.swing.order.cellcomponents.editors.milling.glueing;

import by.dak.persistence.FacadeContext;
import by.dak.persistence.convert.Gluieng2StringConverter;
import by.dak.persistence.entities.BorderDefEntity;
import by.dak.persistence.entities.TextureEntity;
import by.dak.utils.convert.StringValue;
import org.jhotdraw.xml.DOMInput;
import org.jhotdraw.xml.DOMOutput;
import org.jhotdraw.xml.DOMStorable;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 17.8.2009
 * Time: 15.14.56
 * To change this template use File | Settings | File Templates.
 */
@StringValue(converterClass = Gluieng2StringConverter.class)
public class Gluieng implements DOMStorable
{
    private BorderDefEntity borderDef;
    private TextureEntity texture;

    public Gluieng()
    {
    }

    public Gluieng(BorderDefEntity borderDef, TextureEntity texture)
    {
        this.borderDef = borderDef;
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

    public TextureEntity getTexture()
    {
        return texture;
    }

    public void setTexture(TextureEntity texture)
    {
        this.texture = texture;
    }

    @Override
    public void write(DOMOutput out) throws IOException
    {
        out.addAttribute("textureId", getTexture().getId());
        out.addAttribute("borderDefId", getBorderDef().getId());

    }

    @Override
    public void read(DOMInput in) throws IOException
    {
        long textureId = (long) in.getAttribute("textureId", 0L);
        long borderDefId = (long) in.getAttribute("borderDefId", 0L);
        this.borderDef = FacadeContext.getBorderDefFacade().findBy(borderDefId);
        this.texture = FacadeContext.getTextureFacade().findBy(textureId);

    }

    public Object clone()
    {
        Gluieng gluieng = new Gluieng();
        gluieng.borderDef = borderDef;
        gluieng.texture = texture;
        return gluieng;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Gluieng)
        {
            BorderDefEntity borderDef = ((Gluieng) obj).getBorderDef();
            TextureEntity texture = ((Gluieng) obj).getTexture();
            return borderDef != null && borderDef.equals(this.getBorderDef()) &&
                    texture != null && texture.equals(this.getTexture());
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        return super.hashCode();
    }
}
