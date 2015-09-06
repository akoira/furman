package by.dak.cutting.afacade;

import by.dak.persistence.entities.PersistenceEntity;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 * User: akoyro
 * Date: 10.09.2010
 * Time: 12:37:57
 */
@MappedSuperclass
public abstract class ALink<P,C> extends PersistenceEntity
{
    public static final String PROPERTY_parent = "parent";
    public static final String PROPERTY_child = "child";
    public static final String PROPERTY_keyword = "keyword";

    @ManyToOne()
    private P parent;
    @ManyToOne()
    private C child;

    private String keyword;

    public P getParent()
    {
        return parent;
    }

    public void setParent(P parent)
    {
        this.parent = parent;
    }

    public C getChild()
    {
        return child;
    }

    public void setChild(C child)
    {
        this.child = child;
    }

    public String getKeyword()
    {
        return keyword;
    }

    public void setKeyword(String keyword)
    {
        this.keyword = keyword;
    }

}
