package by.dak.buffer.exporter;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 29.10.2010
 * Time: 17:22:52
 * To change this template use File | Settings | File Templates.
 */
public enum ExportTables
{
    /**
     * Список таблиц для OrderExporter
     */
    FURN_ORDER(Constants.SQL_FURN_ORDER),
    ORDER_ITEM(Constants.SQL_ORDER_ITEM),
    ORDER_DETAIL(Constants.SQL_ORDER_DETAIL);

    private String sql;

    ExportTables(String sql)
    {
        this.sql = sql;
    }

    public String getSql()
    {
        return sql;
    }
}