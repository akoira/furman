package by.dak.cutting.swing.dictionaries;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@DiscriminatorValue(value = "A2")
@Table(name = "A2")
public class A2 extends A
{
}
