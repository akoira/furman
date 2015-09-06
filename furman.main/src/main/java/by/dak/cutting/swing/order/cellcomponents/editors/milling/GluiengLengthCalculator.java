package by.dak.cutting.swing.order.cellcomponents.editors.milling;

import by.dak.persistence.entities.BoardDef;
import by.dak.persistence.entities.OrderFurniture;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 26.8.2009
 * Time: 18.44.37
 * To change this template use File | Settings | File Templates.
 */
public class GluiengLengthCalculator
{
    private HashMap<BoardDef, Double> map = new HashMap<BoardDef, Double>();

    public void add(OrderFurniture orderFurniture)
    {
        MillingConverter converter = new MillingConverter();
        ElementDrawing elementDrawing = converter.getElementDrawing(orderFurniture);
        Double count = map.get(orderFurniture.getBoardDef());
        if (count == null)
        {
            count = new Double(0);
        }
        count += elementDrawing.getGlueingLength();
    }

    public Double getLength(BoardDef boardDef)
    {
        return map.get(boardDef);
    }
}
