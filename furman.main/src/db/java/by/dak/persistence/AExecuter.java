package by.dak.persistence;

import org.apache.commons.lang.RandomStringUtils;
import workbench.resource.Settings;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * User: akoyro
 * Date: 01.10.2010
 * Time: 23:58:42
 */
public abstract class AExecuter
{
    private File targetDir;
    private File zipFile;
    private Connection connection;
    private String[] tables;


    public static int BUFFER_SIZE = 4096;


    public abstract void execute() throws Exception;

    public String[] getTables()
    {
        return tables;
    }

    public void setTables(String[] tables)
    {
        this.tables = tables;
    }


    public File getTargetDir()
    {
        if (targetDir == null)
        {
            File tmp = new File(System.getProperty("java.io.tmpdir"));
            targetDir = new File(tmp, RandomStringUtils.random(12, "qwertyuiopasdfghjklzxcvbnm"));
            getTargetDir().mkdirs();
        }
        return targetDir;
    }

    public void setTargetDir(File targetDir)
    {
        this.targetDir = targetDir;
    }

    public File getZipFile()
    {
        return zipFile;
    }

    public void setZipFile(File zipFile)
    {
        this.zipFile = zipFile;
    }


    protected void deleteTargetDir()
    {
        if (getTargetDir().listFiles() == null || targetDir.listFiles().length < 1)
        {
            getTargetDir().delete();
        }
    }

    protected void deleteFiles(List<File> files)
    {
        for (File file : files)
        {
            file.delete();
        }
    }

    public Connection getConnection()
    {
        return connection;
    }

    public void setConnection(Connection connection)
    {
        this.connection = connection;
    }

    public void closeConnection()
    {
        if (getConnection() != null)
        {
            try
            {
                setConstraintsCheck(true);
                getConnection().close();
            }
            catch (Throwable e)
            {
            }
        }
    }

    protected void setConstraintsCheck(boolean value)
            throws SQLException
    {
        Statement statement = null;
        try
        {
            statement = getConnection().createStatement();
            if (value)
            {
                statement.execute("SET FOREIGN_KEY_CHECKS = 1;");
            }
            else
            {
                statement.execute("SET FOREIGN_KEY_CHECKS = 0;");
            }
        }
        finally
        {
            if (statement != null)
            {
                statement.close();
            }
        }
    }

    protected void setShowDdInfo(boolean show)
    {
        Settings.getInstance().setProperty("workbench.export.xml.dbInfo", show);
    }
}
