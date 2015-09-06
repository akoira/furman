package by.dak.buffer.exporter;

import by.dak.persistence.AExecuter;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Order;
import org.apache.commons.io.IOUtils;
import workbench.db.WbConnection;
import workbench.resource.Settings;
import workbench.sql.SqlCommand;
import workbench.sql.StatementRunner;
import workbench.sql.preparedstatement.StatementParameters;
import workbench.sql.wbcommands.WbExport;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 26.10.2010
 * Time: 17:45:03
 * To change this template use File | Settings | File Templates.
 */
public class OrderExporter extends AExecuter
{
    public static final String EXPORT_TYPE = "xml";

    private Order order = null;
    private WbExport wbExport = null;
    private WbConnection wbConnection = null;

    public Order getOrder()
    {
        return order;
    }

    public void setOrder(Order order)
    {
        this.order = order;
    }

    private void runExecution(String sql, File file) throws Exception
    {
        setShowDdInfo(false);
        Settings.getInstance().setCheckPreparedStatements(true);
        wbConnection.getPreparedStatementPool().addPreparedStatement(sql);

        StatementParameters statementParameters = wbConnection.getPreparedStatementPool().getParameters(sql);
        statementParameters.setParameterValue(0, order.getId());

        StatementRunner statementRunner = new StatementRunner();
        statementRunner.addCommand(new SqlCommand());
        statementRunner.setConnection(wbConnection);

        wbExport.setStatementRunner(statementRunner);

        wbExport.execute("-type=" + EXPORT_TYPE + " -file=" + file.getAbsolutePath());
        statementRunner.runStatement(sql);

    }

    @Override
    public void execute() throws Exception
    {
        List<File> list = new ArrayList<File>();

        try
        {
            wbConnection = new WbConnection(null, getConnection(), null);
            wbExport = new WbExport();
            wbExport.setConnection(wbConnection);
            for (ExportTables table : ExportTables.values())
            {
                File file = new File(getTargetDir(), table + ".xml");
                list.add(file);
                Logger.getLogger(this.getClass().getName()).
                        log(Level.INFO, "Export db \"{0}\" to \"{1}\".",
                                new Object[]{table, file.getAbsolutePath()});
                runExecution(table.getSql(), file);
            }
        }
        catch (Throwable t)
        {
            FacadeContext.getExceptionHandler().handle(t);
        }
        finally
        {
            closeConnection();
            zip(list);
            deleteFiles(list);
            deleteTargetDir();
        }
    }

    private void zip(List<File> files) throws IOException
    {
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead;
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(getZipFile()));
        for (File file : files)
        {
            ZipEntry entry = new ZipEntry(file.getName());
            FileInputStream in = new FileInputStream(file);
            out.putNextEntry(entry);
            while ((bytesRead = in.read(buffer)) != -1)
            {
                out.write(buffer, 0, bytesRead);
            }
            IOUtils.closeQuietly(in);
        }
        IOUtils.closeQuietly(out);
    }
}
