package by.dak.cutting.swing;

import by.dak.cutting.cut.base.Element;
import by.dak.cutting.cut.gui.cuttingApp.DimensionItem;
import by.dak.utils.convert.EntityToStringConverter;
import by.dak.utils.convert.StringValueAnnotationProcessor;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * User: akoyro
 * Date: 13.10.11
 * Time: 22:42
 */
public abstract class AElement2StringConverter<E extends DimensionItem> implements EntityToStringConverter<E>
{
    public static final String DELIM = ", ";

    private Element element;


    public String convert(E dimentionItem)
    {
        return StringUtils.join(getDescription(element), DELIM);
    }

    public abstract String convertShort(Element element);

    protected abstract List<String> getDescription(Element element);


    public static void setDescriptionsTo(Element element)
    {
        DimensionItem dimensionItem = element.getDimensionItem();
        AElement2StringConverter converter = (AElement2StringConverter) StringValueAnnotationProcessor.getProcessor().getEntityToStringConverter(dimensionItem.getClass());
        converter.setElement(element);
        element.setDescription(converter.convert(element.getDimensionItem()));
        element.setShortDescription(converter.convertShort(element));
    }

    public void setElement(Element element)
    {
        this.element = element;
    }

    public Element getElement()
    {
        return element;
    }

}
