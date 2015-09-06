package by.dak.hibernate;

import org.hsqldb.types.Types;

/**
 * User: akoyro
 * Date: 13.07.2010
 * Time: 17:02:29
 */
public class CustomHSQLDialect extends org.hibernate.dialect.HSQLDialect
{
    public CustomHSQLDialect()
    {
        registerColumnType(Types.BOOLEAN, "boolean");
        registerHibernateType(Types.BOOLEAN, "boolean");
    }
}
