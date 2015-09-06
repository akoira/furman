package by.dak.cutting.swing;

import by.dak.cutting.cut.base.Element;
import by.dak.cutting.swing.cut.ElementDimensionItem;
import by.dak.persistence.entities.AOrderBoardDetail;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: dkoyro
 * Date: 27.02.11
 * Time: 15:58
 * To change this template use File | Settings | File Templates.
 */
public class BoardElementStringConverter extends AElement2StringConverter
{
    private ResourceMap resourceMap = Application.getInstance().getContext().getResourceMap(this.getClass());

    private static final BoardElementStringConverter CONVERTER = new BoardElementStringConverter();

    public static BoardElementStringConverter getConverter()
    {
        return CONVERTER;
    }


    @Override
    public String convertShort(Element element)
    {
        return String.valueOf(((ElementDimensionItem) element.getDimensionItem()).getFurniture().getNumber());
    }

    protected List<String> getDescription(Element element)
    {
        ArrayList<String> result = new ArrayList<String>();

        AOrderBoardDetail orderFurniture = ((ElementDimensionItem) element.getDimensionItem()).getFurniture();

        if (orderFurniture != null)
        {
            result.add(String.valueOf(orderFurniture.getNumber()));
            result.add(getSize(element));
            if (orderFurniture.getGlueing() != null || orderFurniture.getMilling() != null)
            {
                result.add(getGlueing());
            }
            String value = getGroove(orderFurniture);
            if (value != null)
            {
                result.add(value);
            }
            value = getAngle45(orderFurniture);
            if (value != null)
            {
                result.add(value);
            }
        }
        return result;
    }

    private String getAngle45(AOrderBoardDetail orderFurniture)
    {
        if (orderFurniture.getAngle45() != null)
        {
            return resourceMap.getString("angle45.text");
        }
        return null;
    }

    private String getGroove(AOrderBoardDetail orderFurniture)
    {
        if (orderFurniture.getGroove() != null)
        {
            return resourceMap.getString("groove.text");
        }
        return null;
    }


    private String getGlueing()
    {
        return resourceMap.getString("glueing.text");
    }

    private String getSize(Element element)
    {
        AOrderBoardDetail orderFurniture = ((ElementDimensionItem) element.getDimensionItem()).getFurniture();
        ElementFigure.LengthWidhtGetter lengthWidhtGetter = new ElementFigure.LengthWidhtGetter(element, orderFurniture.getBoardDef().getCutter(), element.isRotated());
        return new StringBuffer().
                append(lengthWidhtGetter.getLength().longValue()).append("x").
                append(lengthWidhtGetter.getWidth().longValue()).toString();
    }
}
