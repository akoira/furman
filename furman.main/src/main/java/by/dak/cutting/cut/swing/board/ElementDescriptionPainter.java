package by.dak.cutting.cut.swing.board;

import by.dak.cutting.cut.base.Element;
import by.dak.cutting.cut.swing.StringPainter;
import by.dak.cutting.swing.AElement2StringConverter;

import java.awt.*;

/**
 * @author akoyro
 * @version 0.1 01.02.2009
 * @introduced [Preview Plugin Support]
 * @since 2.0.0
 */
public class ElementDescriptionPainter extends StringPainter
{
    public ElementDescriptionPainter(Rectangle rectangle, Element element)
    {
        super(rectangle);
        AElement2StringConverter.setDescriptionsTo(element);
        setText(element.getDescription());
    }
}
