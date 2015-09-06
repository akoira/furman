package by.dak.cutting.swing.dictionaries;

import by.dak.persistence.entities.PersistenceEntity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import java.util.List;


@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class A extends PersistenceEntity
{
    @OneToMany(mappedBy = "a")
    private List<B> bs;

    public List<B> getBs()
    {
        return bs;
    }

    public void setBs(List<B> bs)
    {
        this.bs = bs;
    }

}
