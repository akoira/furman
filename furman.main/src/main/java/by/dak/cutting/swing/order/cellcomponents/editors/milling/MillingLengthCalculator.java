package by.dak.cutting.swing.order.cellcomponents.editors.milling;

import by.dak.persistence.entities.BoardDef;
import by.dak.persistence.entities.OrderFurniture;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 26.8.2009
 * Time: 17.16.40
 * To change this template use File | Settings | File Templates.
 */
public class MillingLengthCalculator
{
    private HashMap<BoardDef, Double> map = new HashMap<BoardDef, Double>();

    public void add(OrderFurniture orderFurniture)
    {
        MillingConverter millingConverter = new MillingConverter();
        ElementDrawing elementDrawing = millingConverter.getElementDrawing(orderFurniture);
        Double count = map.get(orderFurniture.getBoardDef());
        if (count == null)
        {
            count = new Double(0);
        }
        count += elementDrawing.getMillingLength();
        map.put(orderFurniture.getBoardDef(), count);
    }

    public double getLength(BoardDef boardDef)
    {
        return map.get(boardDef);
    }
}
