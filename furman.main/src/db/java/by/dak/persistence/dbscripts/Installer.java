package by.dak.persistence.dbscripts;

import by.dak.cutting.SpringConfiguration;
import by.dak.persistence.dao.impl.GenericDaoImpl;

import java.sql.SQLException;

/**
 * User: akoyro
 * Date: 14.03.2009
 * Time: 20:02:24
 */
public class Installer
{
    private SpringConfiguration springConfiguration;
    private GenericDaoImpl genericDao;

    public Installer()
    {
        springConfiguration = new SpringConfiguration();
        genericDao = (GenericDaoImpl) springConfiguration.getApplicationContext().getBean("commonDAO");
    }


    public static void main(String[] args) throws SQLException
    {
        String[] scriptNames = {"create_tables.ddl",
                "fill_boardDefs.sql",
                "fill_borderDefs.sql",
                "fill_manufacturers.sql",
                "fill_textures.sql",
                "fill_default_structure.sql",
        };
        if (args != null && args.length > 0)
        {
            if (args[0].equals("uninstall"))
            {
                scriptNames = new String[]{"drop_tables.ddl"};
            }
            else if (args[0].equals("update"))
            {
                scriptNames = new String[]{"update.price.sql"};
            }
        }

        Installer installer = new Installer();
        for (String scriptName : scriptNames)
        {
            installer.executeScript(scriptName);
        }
    }

    private void executeScript(final String scriptName)
            throws SQLException
    {
    }

}
