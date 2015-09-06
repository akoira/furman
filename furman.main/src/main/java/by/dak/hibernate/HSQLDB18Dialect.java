package by.dak.hibernate;

import org.hibernate.dialect.HSQLDialect;

/**
 * @author Denis Koyro
 * @version 0.1 15.12.2008
 * @introduced [Furniture constructor | Iteration 1]
 * @since 1.0.0
 */
public class HSQLDB18Dialect extends HSQLDialect
{
    @Override
    public String[] getCreateSequenceStrings(String sequenceName)
    {
        return new String[]{
                "create table information_schema." + sequenceName
                        + " (zero integer)",
                "insert into information_schema." + sequenceName + " values (0)",
                "create sequence " + sequenceName + " start with 1"
        };
    }

    @Override
    public String[] getDropSequenceStrings(String sequenceName)
    {
        return new String[]{
                "drop table information_schema." + sequenceName + " if exists",
                "drop sequence " + sequenceName
        };
    }

    @Override
    public String getSequenceNextValString(String sequenceName)
    {
        return "select next value for " + sequenceName + " from information_schema." + sequenceName;
    }

    @Override
    public String getQuerySequencesString()
    {
        return "select sequence_name from information_schema.system_sequences";
    }

    @Override
    public boolean supportsSequences()
    {
        return true;
    }
}
