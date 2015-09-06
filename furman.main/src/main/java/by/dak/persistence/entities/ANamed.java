package by.dak.persistence.entities;

/**
 * User: akoyro
 * Date: 25.08.11
 * Time: 14:20
 */
public abstract class ANamed extends PersistenceEntity
{
    public static final String PROPERTY_name = "name";

    private String name;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
