package by.dak.persistence.dbscripts.update.t9199;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.SpringConfiguration;
import by.dak.cutting.linear.entity.LinearStripsEntity;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.cutting.entities.StripsEntity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * User: akoyro
 * Date: 05.09.11
 * Time: 23:25
 */
public class T9199Update
{
    static Connection con = null;
    static PreparedStatement preparedStatement = null;

    public static void main(String[] args) throws Exception
    {
        new SpringConfiguration();


        migrateStripsEntity();
        migrateLinearStripsEntity();

    }

    private static void migrateLinearStripsEntity() throws Exception
    {
        int count = FacadeContext.getLinearStripsFacade().getCount();
        SearchFilter filter = new SearchFilter();
        //filter.lt("id",19605L);
        filter.addDescOrder("id");

        getPreparedStatement("INSERT INTO PUBLIC.STRIPS (ID, FURNITURE_CODE_ID, FURNITURE_TYPE_ID, SAW_WIDTH, ROTATION, PAIDVALUE, STRIPS, ORDER_GROUP_ID, DISCRIMINATOR) VALUES (?,?,?,?,?,?,?,?,?)");
        while (filter.getStartIndex() < count)
        {
            List<LinearStripsEntity> list = FacadeContext.getLinearStripsFacade().loadAll(filter);
            for (LinearStripsEntity se : list)
            {
                System.out.println("=== Start process id = " + se.getId());
                preparedStatement.setLong(1, se.getId());
                preparedStatement.setLong(2, se.getFurnitureCode().getId());
                preparedStatement.setLong(3, se.getFurnitureType().getId());
                preparedStatement.setInt(4, se.getSawWidth());
                preparedStatement.setBoolean(5, se.getRotation());
                preparedStatement.setDouble(6, se.getPaidValue());
                preparedStatement.setString(7, se.getStrips());
                preparedStatement.setLong(8, se.getOrderGroup().getId());
                preparedStatement.setString(9, se.getDiscriminator());
                preparedStatement.execute();
                preparedStatement.clearBatch();
                System.out.println("=== Stop process id = " + se.getId());
            }

            filter.setStartIndex(filter.getStartIndex() + filter.getPageSize());
        }

        closePS();
        closeC();
    }


    private static void migrateStripsEntity() throws Exception
    {
        int count = FacadeContext.getStripsFacade().getCount();
        SearchFilter filter = new SearchFilter();
        //filter.ge("id",19605L);
        filter.addDescOrder("id");

        getPreparedStatement("INSERT INTO PUBLIC.STRIPS (ID, FURNITURE_CODE_ID, FURNITURE_TYPE_ID, SAW_WIDTH, ROTATION, PAIDVALUE, STRIPS, ORDER_ID, DISCRIMINATOR) VALUES (?,?,?,?,?,?,?,?,?)");
        while (filter.getStartIndex() < count)
        {
            List<StripsEntity> list = FacadeContext.getStripsFacade().loadAll(filter);
            for (StripsEntity se : list)
            {
                System.out.println("=== Start process id = " + se.getId());
                preparedStatement.setLong(1, se.getId());
                preparedStatement.setLong(2, se.getTexture().getId());
                preparedStatement.setLong(3, se.getBoardDef().getId());
                preparedStatement.setInt(4, se.getSawWidth());
                preparedStatement.setBoolean(5, se.getRotation());
                preparedStatement.setDouble(6, se.getPaidValue());
                preparedStatement.setString(7, se.getStrips());
                preparedStatement.setLong(8, se.getOrder().getId());
                preparedStatement.setString(9, se.getDiscriminator());
                preparedStatement.execute();
                preparedStatement.clearBatch();
                System.out.println("=== Stop process id = " + se.getId());
            }

            filter.setStartIndex(filter.getStartIndex() + filter.getPageSize());
        }

        closePS();
        closeC();
    }


    public static PreparedStatement getPreparedStatement(String sql) throws Exception
    {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        con = DriverManager.getConnection("jdbc:mysql://localhost/public",
                "root", "Beni7Reg");

        if (!con.isClosed())
        {
            preparedStatement = con.prepareStatement(sql);
        }
        return preparedStatement;
    }

    public static void closePS() throws SQLException
    {
        preparedStatement.close();
    }

    public static void closeC() throws SQLException
    {
        con.close();
    }


}
