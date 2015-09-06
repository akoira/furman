package by.dak.profile;

import by.dak.persistence.entities.ANamed;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

/**
 * User: akoyro
 * Date: 25.08.11
 * Time: 14:18
 */
@Entity
@Table(name = "PSystem")

//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = "DISCRIMINATOR")
//@DiscriminatorOptions(force = true)

/**
 * Product system
 */
public class PSystem extends ANamed
{
    @OneToOne
    private PSystem parent;

    private List<PSystem> children;

    public List<PSystem> getChildren()
    {
        return children;
    }

    public void setChildren(List<PSystem> children)
    {
        this.children = children;
    }
}
