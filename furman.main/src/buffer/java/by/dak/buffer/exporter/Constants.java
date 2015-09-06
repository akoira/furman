package by.dak.buffer.exporter;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 01.11.2010
 * Time: 10:50:43
 * To change this template use File | Settings | File Templates.
 */
public interface Constants
{
    /**
     * Все запросы для OrderExporter
     */

    public static final String SQL_FURN_ORDER = "select fo.* FROM furn_order fo " +
            "where fo.ID = ?";
    public static final String SQL_ORDER_ITEM = "select oi.* from order_item oi INNER JOIN furn_order " +
            "fo ON oi.ORDER_ID = fo.ID WHERE fo.ID = ?";
    public static final String SQL_ORDER_DETAIL = "select od.* from order_detail od" +
            " INNER JOIN order_item io ON od.ORDER_ITEM_ID = io.ID" +
            " INNER JOIN furn_order fo ON io.ORDER_ID = fo.ID" +
            " WHERE fo.ID = ?";

}
