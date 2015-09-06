package by.dak.buffer.importer;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 05.11.2010
 * Time: 10:27:39
 * To change this template use File | Settings | File Templates.
 */
public interface Constants
{
    /**
     * cоздана временная таблица TEMP_TABLE. Таблица включает в себя все параметры(columns) что и те таблицы
     * которые были экспортированы
     */
    public static final String CREATE_TEMP_TABLE_STATEMENT_FOR_DILER_ORDER = "CREATE TABLE temp_table(ID " +
            "bigint," +
            "COST double,ORDER_NUMBER bigint,READY_DATE DATE,NAME VARCHAR(255),DESCRIPTION LONGTEXT," +
            "FK_CREATED_DAILY_SHEET_ID BIGINT,FK_WORKED_DAILY_SHEET_ID BIGINT, FK_CUSTOMER_ID BIGINT," +
            "FK_DESIGNER_ID bigint,STATUS VARCHAR(255) DEFAULT 'design',DILER_ORDER_ID bigint, FILE_UUID VARCHAR(255)," +
            "DISCRIMINATOR VARCHAR(255),CATEGORY_ID BIGINT,SALE_FACTOR DOUBLE,SALE_PRICE DOUBLE,DIALER_COST DOUBLE," +
            "ORDER_GROUP_ID bigint, " +
            "PRIMARY KEY (`ID`));";

    public static final String CREATE_TEMP_TABLE_STATEMENT_FOR_DILER_ORDER_ITEM = "CREATE TABLE temp_table(ID " +
            "bigint," +
            " NAME VARCHAR(255), ORDER_ID BIGINT, TYPE VARCHAR(255) DEFAULT 'first', NUMBER BIGINT DEFAULT 1," +
            " AMOUNT BIGINT DEFAULT 1,LENGTH DOUBLE,WIDTH DOUBLE, DISCRIMINATOR VARCHAR(255) DEFAULT 'OrderItem'," +
            " DESCRIPTION LONGTEXT, FILE_UUID VARCHAR(255),DESIGN LONGTEXT," +
            "PRIMARY KEY (`ID`));";

    public static final String CREATE_TEMP_TABLE_STATEMENT_FOR_DILER_ORDER_DETAIL = "CREATE TABLE temp_table(ID " +
            "bigint ," +
            "NUMBER BIGINT,NAME VARCHAR(255),LENGTH BIGINT,WIDTH BIGINT,AMOUNT BIGINT," +
            "ROTATABLE BIT,GLUEING LONGTEXT,MILLING LONGTEXT,GROOVE LONGTEXT," +
            "ANGLE45 LONGTEXT,DRILLING LONGTEXT,CUTOFF LONGTEXT,FURNITURE_TYPE_ID BIGINT," +
            "FURNITURE_CODE_ID BIGINT,COMLEX_BOARD_DEF_ID BIGINT,SECOND_BOARD_ID BIGINT," +
            "ORDER_ITEM_ID BIGINT,FPRIMARY BIT,DISCRIMINATOR VARCHAR(255) DEFAULT 'OrderFurniture'," +
            "TYPE VARCHAR(255),PRICE DOUBLE,FURNITURE_ID BIGINT,SIZE DOUBLE," +
            "SIDE VARCHAR(255)," +
            "PRIMARY KEY (`ID`));";
}
