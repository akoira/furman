package by.dak.cutting.swing.order.models;

/**
 * User: akoyro
 * Date: 21.04.2009
 * Time: 12:36:48
 */
public enum OrderTableColumn
{
    //последовательность критична используется в TableModel
    number(0),
    name(1),
    boardDef(2),
    manufacturer(3),
    texture(4),
    length(5),
    width(6),
    count(7),
    rotatable(8),
    glueing(9),
    milling(10),
    drilling(11),
    groove(12),
    a45(13),
    cutoff(14);

    OrderTableColumn(int index)
    {
        this.index = index;
    }

    private int index;

    public int index()
    {
        return index;
    }

    public static OrderTableColumn valuerOf(int index)
    {
        return OrderTableColumn.values()[index];
    }
}
