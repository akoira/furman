package by.dak.buffer.importer;

import by.dak.buffer.entity.DilerOrder;
import by.dak.buffer.entity.DilerOrderDetail;
import by.dak.buffer.entity.DilerOrderItem;
import by.dak.buffer.exporter.ExportTables;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.Importer;
import workbench.db.WbConnection;
import workbench.sql.wbcommands.WbImport;

import java.io.File;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 26.10.2010
 * Time: 17:44:47
 * To change this template use File | Settings | File Templates.
 */
public class OrderImporter extends Importer
{

    public static final String IMPORT_FILE_TYPE = "xml";
    public static final String IMPORT_TARGET_TABLE = "temp_table";
    public static final String DROP_TEMP_TABLE_STATEMENT = "DROP TABLE temp_table";

    private DilerOrder dilerOrder = null;
    private List<DilerOrderItem> dilerOrderItems = null;
    private List<DilerOrderDetail> dilerOrderDetails = null;


    public static final Map<ImportTables, ExportTables> TABLES_MAP = new HashMap<ImportTables, ExportTables>();

    static
    {
        TABLES_MAP.put(ImportTables.diler_order, ExportTables.FURN_ORDER);
        TABLES_MAP.put(ImportTables.diler_order_item, ExportTables.ORDER_ITEM);
        TABLES_MAP.put(ImportTables.diler_order_detail, ExportTables.ORDER_DETAIL);
    }

    @Override
    public void execute() throws Exception
    {
        PreparedStatement dropTableStatement = null;
        PreparedStatement createTableStatement = null;

        try
        {
            List<File> files = unzip();
            setConstraintsCheck(false);
            WbConnection wbConnection = new WbConnection(null, getConnection(), null);
            WbImport wbImport = new WbImport();
            wbImport.setConnection(wbConnection);

            for (ImportTables table : ImportTables.values())
            {
                File file = getFileBy(files, TABLES_MAP.get(table).name());

                createTableStatement = getConnection().prepareStatement(table.getTempTableStatement());
                createTableStatement.execute();

                wbImport.execute(" -type=" + IMPORT_FILE_TYPE + " -file=" + file.getAbsolutePath() + " -mode='INSERT'" +
                        " -table=" + IMPORT_TARGET_TABLE);

                pullEntitiesFromTempTable(table);

                dropTableStatement = getConnection().prepareStatement(DROP_TEMP_TABLE_STATEMENT);
                dropTableStatement.execute();

                createTableStatement = null;
                dropTableStatement = null;
            }
        }
        catch (Throwable e)
        {
            FacadeContext.getExceptionHandler().handle(e);
        }
        finally
        {
            if (createTableStatement != null)
            {
                if (dropTableStatement == null)
                {
                    dropTableStatement = getConnection().prepareStatement(DROP_TEMP_TABLE_STATEMENT);
                    dropTableStatement.execute();
                    dropTableStatement.close();
                }
            }
            closeConnection();
        }
    }

    /**
     * вытягивание объектов из таблиц
     * и установка связей между ними
     *
     * @param table
     */
    private void pullEntitiesFromTempTable(ImportTables table)
    {
        switch (table)
        {
            case diler_order:
                dilerOrder = FacadeContext.getDilerOrderFacade().getDilerOrderFromTempTable();
                break;
            case diler_order_item:
                dilerOrderItems = FacadeContext.getDilerOrderItemFacade().getDilerOrderItemsFromTempTable();
                for (DilerOrderItem dilerOrderItem : dilerOrderItems)
                {
                    dilerOrderItem.setDilerOrder(dilerOrder);
                }
                break;
            case diler_order_detail:
                dilerOrderDetails = FacadeContext.getDilerOrderDetailFacade().getDilerOrderDetailsFromTempTable();
                for (DilerOrderDetail dilerOrderDetail : dilerOrderDetails)
                {
                    for (DilerOrderItem dilerOrderItem : dilerOrderItems)
                    {
                        if (dilerOrderItem.getOrderItemId().equals(dilerOrderDetail.getOrderItemId()))
                        {
                            dilerOrderDetail.setDilerOrderItem(dilerOrderItem);
                        }
                    }

                }
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    public DilerOrder getDilerOrder()
    {
        return dilerOrder;
    }

    public void setDilerOrder(DilerOrder dilerOrder)
    {
        this.dilerOrder = dilerOrder;
    }

    /**
     * сохранение контеста
     */
    public void saveContext()
    {
        FacadeContext.getDilerOrderFacade().save(dilerOrder);
        for (DilerOrderItem dilerOrderItem : dilerOrderItems)
        {
            FacadeContext.getDilerOrderItemFacade().save(dilerOrderItem);
        }
        for (DilerOrderDetail dilerOrderDetail : dilerOrderDetails)
        {
            FacadeContext.getDilerOrderDetailFacade().save(dilerOrderDetail);
        }
    }
}
