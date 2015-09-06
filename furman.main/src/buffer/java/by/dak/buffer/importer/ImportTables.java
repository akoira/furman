package by.dak.buffer.importer;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 02.11.2010
 * Time: 18:46:54
 * To change this template use File | Settings | File Templates.
 */
public enum ImportTables
{
    diler_order(Constants.CREATE_TEMP_TABLE_STATEMENT_FOR_DILER_ORDER),
    diler_order_item(Constants.CREATE_TEMP_TABLE_STATEMENT_FOR_DILER_ORDER_ITEM),
    diler_order_detail(Constants.CREATE_TEMP_TABLE_STATEMENT_FOR_DILER_ORDER_DETAIL);

    ImportTables(String tempTableStatement)
    {
        this.tempTableStatement = tempTableStatement;
    }

    private String tempTableStatement = null;


    public String getTempTableStatement()
    {
        return tempTableStatement;
    }
}
