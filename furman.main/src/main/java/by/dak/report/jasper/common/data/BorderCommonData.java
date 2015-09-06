package by.dak.report.jasper.common.data;

import by.dak.persistence.entities.BorderDefEntity;
import by.dak.persistence.entities.TextureEntity;
import by.dak.utils.convert.StringValueAnnotationProcessor;

/**
 * User: akoyro
 * Date: 12.03.2010
 * Time: 11:41:02
 */

//@Entity
//@DiscriminatorValue(value = "BorderCommonData")
//@DiscriminatorOptions(force = true)
public class BorderCommonData extends CommonData
{

    //    @ManyToOne(cascade = CascadeType.REFRESH)
//    @JoinColumn(nullable = false)
    private TextureEntity texture;

    //    @ManyToOne(cascade = CascadeType.REFRESH)
//    @JoinColumn(nullable = false)
    private BorderDefEntity borderDef;

    public BorderCommonData()
    {
        super();
    }

    public BorderCommonData(BorderDefEntity borderDef, TextureEntity texture)
    {
        super(StringValueAnnotationProcessor.getProcessor().convert(borderDef),
                StringValueAnnotationProcessor.getProcessor().convert(texture));
        this.texture = texture;
        this.borderDef = borderDef;
    }

    public TextureEntity getTexture()
    {
        return texture;
    }

    public BorderDefEntity getBorderDef()
    {
        return borderDef;
    }

    public void setTexture(TextureEntity texture)
    {
        this.texture = texture;
    }

    public void setBorderDef(BorderDefEntity borderDef)
    {
        this.borderDef = borderDef;
    }

    @Override
    public CommonData cloneForDialer()
    {
        BorderCommonData borderCommonData = new BorderCommonData(this.borderDef, this.texture);
        fillCloneForDialer(borderCommonData);
        return borderCommonData;
    }
}
