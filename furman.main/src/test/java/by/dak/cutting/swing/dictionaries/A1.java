package by.dak.cutting.swing.dictionaries;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@DiscriminatorValue(value = "A1")
@Table(name = "A1")
public class A1 extends A
{
}
