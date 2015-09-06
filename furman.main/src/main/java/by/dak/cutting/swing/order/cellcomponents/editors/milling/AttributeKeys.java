package by.dak.cutting.swing.order.cellcomponents.editors.milling;

import by.dak.cutting.swing.order.cellcomponents.editors.milling.glueing.Gluieng;
import org.jhotdraw.draw.AttributeKey;
import org.jhotdraw.util.ResourceBundleUtil;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 24.08.2009
 * Time: 11:37:02
 * To change this template use File | Settings | File Templates.
 */
public class AttributeKeys
{
    public static final ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.draw.Labels");

    public final static AttributeKey<Gluieng> GLUEING = new AttributeKey<Gluieng>("glueing", Gluieng.class, null, true, labels);

    // движимая фигура или нет(есть ли handles на редактирование фигуры (true/false))
    public final static AttributeKey<Boolean> MOVEABLE = new AttributeKey<Boolean>("moveable", Boolean.class, true, true, labels);

    static
    {
/*
        addSupportedAttribute(GLUEING);
        addSupportedAttribute(MOVEABLE);
*/
    }

}
