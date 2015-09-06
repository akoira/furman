package by.dak.persistence.dbscripts.update.t8293;

import by.dak.cutting.SpringConfiguration;
import by.dak.persistence.FacadeContext;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateInterceptor;

import java.math.BigInteger;
import java.util.List;

/**
 * User: akoyro
 * Date: 07.07.2010
 * Time: 12:14:33
 */
public class TypeUpdate
{
    public static void main(String[] args)
    {
        new SpringConfiguration();
        FacadeContext.getBoardFacade().findBy(54621L);
        HibernateInterceptor hibernateInterceptor = (HibernateInterceptor) FacadeContext.getApplicationContext().getBean("hibernateInterceptor");
        Session session = hibernateInterceptor.getSessionFactory().openSession();
        updateBordersDef(session);
        updateBoardDef(session);

        updateTexture(session);
        updateService(session);

        updateFurnitureLink(session);
        updateAdditional(session);
        updateBOARD(session);
        updateBORDER(session);

        session.close();
    }

    private static void updateBORDER(Session session)
    {
        SQLQuery sqlQuery = session.createSQLQuery("select ID," +
                "AMOUNT," +
                "LENGTH," +
                "BORDER_DEF_ID," +
                "TEXTURE_ID," +
                "ORDER_ID," +
                "PROVIDER_ID," +
                "DELIVERY_ID," +
                "CREATEDBY_ORDER_ID," +
                "STATUS," +
                "PRICE," +
                "ORDERED" +
                " from BORDER");
        List list = sqlQuery.list();
        for (Object o : list)
        {
            BigInteger ID = (BigInteger) ((Object[]) o)[0];
            BigInteger AMOUNT = (BigInteger) ((Object[]) o)[1];
            Double LENGTH = (Double) ((Object[]) o)[2];
            BigInteger BORDER_DEF_ID = (BigInteger) ((Object[]) o)[3];
            BigInteger TEXTURE_ID = (BigInteger) ((Object[]) o)[4];
            BigInteger ORDER_ID = (BigInteger) ((Object[]) o)[5];
            BigInteger PROVIDER_ID = (BigInteger) ((Object[]) o)[6];
            BigInteger DELIVERY_ID = (BigInteger) ((Object[]) o)[7];
            BigInteger CREATEDBY_ORDER_ID = (BigInteger) ((Object[]) o)[8];
            String STATUS = (String) ((Object[]) o)[9];
            Double PRICE = (Double) ((Object[]) o)[10];
            String DISCRIMINATOR = "Border";
            Boolean ORDERED = (Boolean) ((Object[]) o)[11];

            sqlQuery = session.createSQLQuery("INSERT INTO FURNITURE (" +
                    "ID," +
                    "AMOUNT," +
                    "LENGTH," +
                    "FURNITURE_TYPE_ID," +
                    "FURNITURE_CODE_ID," +
                    "ORDER_ID," +
                    "PROVIDER_ID," +
                    "DELIVERY_ID," +
                    "CREATEDBY_ORDER_ID," +
                    "STATUS," +
                    "PRICE," +
                    "DISCRIMINATOR," +
                    "ORDERED" +
                    ")" +
                    " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)");

            sqlQuery.setBigInteger(0, ID);
            sqlQuery.setBigInteger(1, AMOUNT);
            sqlQuery.setDouble(2, LENGTH);
            sqlQuery.setBigInteger(3, BORDER_DEF_ID);
            sqlQuery.setBigInteger(4, TEXTURE_ID);
            sqlQuery.setBigInteger(5, ORDER_ID);
            sqlQuery.setBigInteger(6, PROVIDER_ID);
            sqlQuery.setBigInteger(7, DELIVERY_ID);
            sqlQuery.setBigInteger(8, CREATEDBY_ORDER_ID);
            sqlQuery.setString(9, STATUS);
            sqlQuery.setDouble(10, PRICE);
            sqlQuery.setString(11, DISCRIMINATOR);
            sqlQuery.setBoolean(12, ORDERED != null ? ORDERED : Boolean.FALSE);
            sqlQuery.executeUpdate();
        }

    }


    private static void updateBOARD(Session session)
    {
        SQLQuery sqlQuery = session.createSQLQuery("select ID," +
                "AMOUNT," +
                "LENGTH," +
                "WIDTH ," +
                "BOARD_DEF_ID," +
                "TEXTURE_ID," +
                "ORDER_ID," +
                "PROVIDER_ID," +
                "DELIVERY_ID," +
                "CREATEDBY_ORDER_ID," +
                "STATUS," +
                "PRICE," +
                "ORDERED" +
                " from BOARD");
        List list = sqlQuery.list();
        for (Object o : list)
        {
            BigInteger ID = (BigInteger) ((Object[]) o)[0];
            BigInteger AMOUNT = (BigInteger) ((Object[]) o)[1];
            BigInteger LENGTH = (BigInteger) ((Object[]) o)[2];
            BigInteger WIDTH = (BigInteger) ((Object[]) o)[3];
            BigInteger BOARD_DEF_ID = (BigInteger) ((Object[]) o)[4];
            BigInteger TEXTURE_ID = (BigInteger) ((Object[]) o)[5];
            BigInteger ORDER_ID = (BigInteger) ((Object[]) o)[6];
            BigInteger PROVIDER_ID = (BigInteger) ((Object[]) o)[7];
            BigInteger DELIVERY_ID = (BigInteger) ((Object[]) o)[8];
            BigInteger CREATEDBY_ORDER_ID = (BigInteger) ((Object[]) o)[9];
            String STATUS = (String) ((Object[]) o)[10];
            Double PRICE = (Double) ((Object[]) o)[11];
            String DISCRIMINATOR = "Board";
            Boolean ORDERED = (Boolean) ((Object[]) o)[12];

            sqlQuery = session.createSQLQuery("INSERT INTO FURNITURE (" +
                    "ID," +
                    "AMOUNT," +
                    "LENGTH," +
                    "WIDTH ," +
                    "FURNITURE_TYPE_ID," +
                    "FURNITURE_CODE_ID," +
                    "ORDER_ID," +
                    "PROVIDER_ID," +
                    "DELIVERY_ID," +
                    "CREATEDBY_ORDER_ID," +
                    "STATUS," +
                    "PRICE," +
                    "DISCRIMINATOR," +
                    "ORDERED" +
                    ")" +
                    " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

            sqlQuery.setBigInteger(0, ID);
            sqlQuery.setBigInteger(1, AMOUNT);
            sqlQuery.setBigInteger(2, LENGTH);
            sqlQuery.setBigInteger(3, WIDTH);
            sqlQuery.setBigInteger(4, BOARD_DEF_ID);
            sqlQuery.setBigInteger(5, TEXTURE_ID);
            sqlQuery.setBigInteger(6, ORDER_ID);
            sqlQuery.setBigInteger(7, PROVIDER_ID);
            sqlQuery.setBigInteger(8, DELIVERY_ID);
            sqlQuery.setBigInteger(9, CREATEDBY_ORDER_ID);
            sqlQuery.setString(10, STATUS);
            sqlQuery.setDouble(11, PRICE);
            sqlQuery.setString(12, DISCRIMINATOR);

            sqlQuery.setBoolean(13, ORDERED != null ? ORDERED : Boolean.FALSE);
            sqlQuery.executeUpdate();
        }

    }


    private static void updateAdditional(Session session)
    {
        SQLQuery sqlQuery = session.createSQLQuery("select ID," +
                "NAME," +
                "TYPE," +
                "AMOUNT," +
                "PRICE," +
                "ORDER_ID" +
                " from ADDITIONAL");
        List list = sqlQuery.list();
        for (Object o : list)
        {
            BigInteger ID = (BigInteger) ((Object[]) o)[0];
            String NAME = (String) ((Object[]) o)[1];
            String TYPE = (String) ((Object[]) o)[2];
            Double AMOUNT = (Double) ((Object[]) o)[3];
            Double PRICE = (Double) ((Object[]) o)[4];
            BigInteger ORDER_ID = (BigInteger) ((Object[]) o)[5];

            SQLQuery sqlQuery1 = session.createSQLQuery("select ID" +
                    " from ORDER_ITEM where ORDER_ID = ?");
            sqlQuery1.setBigInteger(0, ORDER_ID);
            BigInteger ORDER_ITEM_ID = (BigInteger) sqlQuery1.uniqueResult();

            sqlQuery = session.createSQLQuery("INSERT INTO ORDER_DETAIL (" +
                    "ID,NUMBER,NAME,SIZE,TYPE,PRICE, ORDER_ITEM_ID,DISCRIMINATOR)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            sqlQuery.setBigInteger(0, ID);
            sqlQuery.setBigInteger(1, BigInteger.valueOf(0));
            sqlQuery.setString(2, NAME);
            sqlQuery.setDouble(3, AMOUNT);
            sqlQuery.setString(4, TYPE);
            sqlQuery.setDouble(5, PRICE);
            sqlQuery.setBigInteger(6, ORDER_ITEM_ID);
            sqlQuery.setString(7, "Additional");
            sqlQuery.executeUpdate();
        }

    }


    private static void updateFurnitureLink(Session session)
    {
        SQLQuery sqlQuery = session.createSQLQuery("select ID," +
                "SIZE," +
                "FURNITURE_CODE_ID," +
                "FURNITURE_TYPE_ID," +
                "FURN_ORDER_ID," +
                "FURNITURE_ID" +
                " from FURNITURE_LINK");
        List list = sqlQuery.list();
        for (Object o : list)
        {
            BigInteger ID = (BigInteger) ((Object[]) o)[0];
            Double SIZE = (Double) ((Object[]) o)[1];
            BigInteger FURNITURE_CODE_ID = (BigInteger) ((Object[]) o)[2];
            BigInteger FURNITURE_TYPE_ID = (BigInteger) ((Object[]) o)[3];
            BigInteger FURN_ORDER_ID = (BigInteger) ((Object[]) o)[4];
            BigInteger FURNITURE_ID = (BigInteger) ((Object[]) o)[5];

            SQLQuery sqlQuery1 = session.createSQLQuery("select ID" +
                    " from ORDER_ITEM where ORDER_ID = ?");
            sqlQuery1.setBigInteger(0, FURN_ORDER_ID);
            BigInteger ORDER_ITEM_ID = (BigInteger) sqlQuery1.uniqueResult();

            sqlQuery = session.createSQLQuery("INSERT INTO ORDER_DETAIL (" +
                    "ID,NUMBER,NAME,SIZE,FURNITURE_CODE_ID,FURNITURE_TYPE_ID,ORDER_ITEM_ID,FURNITURE_ID,DISCRIMINATOR)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            sqlQuery.setBigInteger(0, ID);
            sqlQuery.setBigInteger(1, BigInteger.valueOf(0));
            sqlQuery.setString(2, "Фурнитура");
            sqlQuery.setDouble(3, SIZE);
            sqlQuery.setBigInteger(4, FURNITURE_CODE_ID);
            sqlQuery.setBigInteger(5, FURNITURE_TYPE_ID);
            sqlQuery.setBigInteger(6, ORDER_ITEM_ID);
            sqlQuery.setBigInteger(7, FURNITURE_ID);
            sqlQuery.setString(8, "FurnitureLink");
            sqlQuery.executeUpdate();
        }

    }

    private static void updateService(Session session)
    {
        SQLQuery sqlQuery = session.createSQLQuery("select ID," +
                "SERVICE_TYPE," +
                "PRICED_TYPE" +
                " from SERVICE");

        List list = sqlQuery.list();
        for (Object o : list)
        {
            BigInteger ID = (BigInteger) ((Object[]) o)[0];
            String SERVICE_TYPE = (String) ((Object[]) o)[1];
            String PRICED_TYPE = (String) ((Object[]) o)[2];
            sqlQuery = session.createSQLQuery("INSERT INTO FURNITURE_CODE (" +
                    "ID," +
                    "SERVICE_TYPE," +
                    "PRICED_TYPE," +
                    "MANUFACTURER_ID, " +
                    "CODE," +
                    "NAME)" +
                    " VALUES (?, ?, ?, ?, ?, ?)");
            sqlQuery.setBigInteger(0, ID);
            sqlQuery.setString(1, SERVICE_TYPE);
            sqlQuery.setString(2, PRICED_TYPE);
            sqlQuery.setBigInteger(3, new BigInteger("20"));
            sqlQuery.setString(4, "0");
            sqlQuery.setString(5, SERVICE_TYPE);
            sqlQuery.executeUpdate();
        }
    }


    private static void updateTexture(Session session)
    {
        SQLQuery sqlQuery = session.createSQLQuery("select ID," +
                "GROUP_IDENTIFIER," +
                "CODE," +
                "SURFACE," +
                "ROTATABLE," +
                "NAME_RUS," +
                "PRICED_TYPE," +
                "MANUFACTURER_ID" +
                " from TEXTURE");

        List list = sqlQuery.list();
        for (Object o : list)
        {

            BigInteger ID = (BigInteger) ((Object[]) o)[0];
            String GROUP_IDENTIFIER = (String) ((Object[]) o)[1];
            String CODE = (String) ((Object[]) o)[2];
            String SURFACE = (String) ((Object[]) o)[3];
            Boolean ROTATABLE = (Boolean) ((Object[]) o)[4];
            String NAME_RUS = (String) ((Object[]) o)[5];
            String PRICED_TYPE = (String) ((Object[]) o)[6];
            BigInteger MANUFACTURER_ID = (BigInteger) ((Object[]) o)[7];
            sqlQuery = session.createSQLQuery("INSERT INTO FURNITURE_CODE (" +
                    "ID,GROUP_IDENTIFIER,CODE,SURFACE,ROTATABLE,NAME,PRICED_TYPE,MANUFACTURER_ID)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            sqlQuery.setBigInteger(0, ID);
            sqlQuery.setString(1, GROUP_IDENTIFIER);
            sqlQuery.setString(2, CODE);
            sqlQuery.setString(3, SURFACE);
            sqlQuery.setBoolean(4, ROTATABLE);
            sqlQuery.setString(5, NAME_RUS);
            sqlQuery.setString(6, PRICED_TYPE);
            sqlQuery.setBigInteger(7, MANUFACTURER_ID);
            sqlQuery.executeUpdate();
        }
    }


    private static void updateBoardDef(Session session)
    {
        SQLQuery sqlQuery = session.createSQLQuery("select ID," +
                "NAME," +
                "TYPE," +
                "HEIGHT," +
                "THICKNESS," +
                "DEFAULT_LENGTH," +
                "DEFAULT_WIDTH," +
                "SIMPLE_ID_1," +
                "SIMPLE_ID_2," +
                "MAT_TYPE," +
                "UNIT" +
                " from BOARD_DEF");

        List list = sqlQuery.list();
        for (Object o : list)
        {

            BigInteger ID = (BigInteger) ((Object[]) o)[0];
            String NAME = (String) ((Object[]) o)[1];
            String TYPE = (String) ((Object[]) o)[2];
            BigInteger HEIGHT = (BigInteger) ((Object[]) o)[3];
            BigInteger THICKNESS = (BigInteger) ((Object[]) o)[4];
            BigInteger DEFAULT_LENGTH = (BigInteger) ((Object[]) o)[5];
            BigInteger DEFAULT_WIDTH = (BigInteger) ((Object[]) o)[6];
            BigInteger SIMPLE_ID_1 = (BigInteger) ((Object[]) o)[7];
            BigInteger SIMPLE_ID_2 = (BigInteger) ((Object[]) o)[8];
            String MAT_TYPE = (String) ((Object[]) o)[9];
            String UNIT = (String) ((Object[]) o)[10];
            sqlQuery = session.createSQLQuery("INSERT INTO FURNITURE_TYPE (" +
                    "ID,NAME,TYPE,HEIGHT,THICKNESS,DEFAULT_LENGTH,DEFAULT_WIDTH,SIMPLE_ID_1,SIMPLE_ID_2,MAT_TYPE,UNIT,DISCRIMINATOR)" +
                    " VALUES (?, ?, ?, ?, ?,?,?,?,?,?,?,?)");
            sqlQuery.setBigInteger(0, ID);
            sqlQuery.setString(1, NAME);
            sqlQuery.setString(2, TYPE);
            sqlQuery.setBigInteger(3, HEIGHT);
            sqlQuery.setBigInteger(4, THICKNESS);
            sqlQuery.setBigInteger(5, DEFAULT_LENGTH);
            sqlQuery.setBigInteger(6, DEFAULT_WIDTH);
            sqlQuery.setBigInteger(7, SIMPLE_ID_1);
            sqlQuery.setBigInteger(8, SIMPLE_ID_2);
            sqlQuery.setString(9, MAT_TYPE);
            sqlQuery.setString(10, UNIT);
            sqlQuery.setString(11, "BoardDef");
            sqlQuery.executeUpdate();
        }
    }

    private static void updateBordersDef(Session session)
    {
        SQLQuery sqlQuery = session.createSQLQuery("select ID,NAME,HEIGHT,THICKNESS,MAT_TYPE from BORDER_DEF");
        List list = sqlQuery.list();
        for (Object o : list)
        {
            BigInteger ID = (BigInteger) ((Object[]) o)[0];
            String NAME = (String) ((Object[]) o)[1];
            BigInteger HEIGHT = (BigInteger) ((Object[]) o)[2];
            BigInteger THICKNESS = (BigInteger) ((Object[]) o)[3];
            String MAT_TYPE = (String) ((Object[]) o)[4];
            sqlQuery = session.createSQLQuery("INSERT INTO FURNITURE_TYPE (ID, NAME, HEIGHT, THICKNESS, MAT_TYPE, DISCRIMINATOR) VALUES (?, ?, ?, ?, ?,?)");
            sqlQuery.setBigInteger(0, ID);
            sqlQuery.setString(1, NAME);
            sqlQuery.setBigInteger(2, HEIGHT);
            sqlQuery.setBigInteger(3, THICKNESS);
            sqlQuery.setString(4, MAT_TYPE);
            sqlQuery.setString(5, "BorderDef");
            sqlQuery.executeUpdate();
        }
    }

}
