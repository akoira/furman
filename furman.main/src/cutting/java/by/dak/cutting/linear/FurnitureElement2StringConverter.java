package by.dak.cutting.linear;

import by.dak.cutting.cut.base.Element;
import by.dak.cutting.swing.AElement2StringConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: dkoyro
 * Date: 02.03.11
 * Time: 18:53
 * To change this template use File | Settings | File Templates.
 */
public class FurnitureElement2StringConverter extends AElement2StringConverter
{

    @Override
    public String convertShort(Element element)
    {
        return Long.toString(((LinearElementDimensionItem) element.getDimensionItem()).getNumber());
    }

    public List<String> getDescription(Element element)
    {
        ArrayList<String> result = new ArrayList<String>();
        result.add(Long.toString(((LinearElementDimensionItem) element.getDimensionItem()).getNumber()));
        result.add(Double.toString(UnitConverter.convertToMetre(element.getHeight())));
        return result;
    }
}
