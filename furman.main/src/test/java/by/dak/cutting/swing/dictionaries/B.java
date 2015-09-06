package by.dak.cutting.swing.dictionaries;

import by.dak.persistence.entities.PersistenceEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "B")
public class B extends PersistenceEntity
{
    @ManyToOne(targetEntity = A.class)
    private A a;

    public A getA()
    {
        return a;
    }

    public void setA(A a)
    {
        this.a = a;
    }
}
