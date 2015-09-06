package by.dak.cutting.cut.guillotine;

/**
 * User: akoyro
 * Date: 04.08.2010
 * Time: 11:14:53
 */
public interface ElementConstraint
{

    public void setConstraintProperty(String key, Object value);

    public Object getConstraintProperty(String key);

    /**
     * Подходит
     * @param descriptor
     * @return
     */
    public boolean suit(ElementDescriptor descriptor);
}
