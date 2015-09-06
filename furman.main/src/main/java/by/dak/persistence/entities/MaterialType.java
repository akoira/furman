package by.dak.persistence.entities;

import by.dak.persistence.convert.MaterialType2StringConverter;
import by.dak.utils.convert.StringValue;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * User: akoyro
 * Date: 26.11.2009
 * Time: 19:59:18
 */
@Entity

@Table(name = "MATERIAL_TYPE")
@StringValue(converterClass = MaterialType2StringConverter.class)
public class MaterialType extends PersistenceEntity
{
    @Column(name = "NAME", nullable = false, length = 255)
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
