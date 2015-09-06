/**
 * "Visual Paradigm: DO NOT MODIFY THIS FILE!"
 *
 * This is an automatic generated file. It will be regenerated every time you generate persistence class.
 *
 * Modifying its content may cause the program not work, or your work may lost.
 */

/**
 * Licensee: Anonymous License Type: Purchased
 */
package by.dak.persistence.entities;

import by.dak.persistence.convert.Designer2StringConverter;
import by.dak.utils.convert.StringValue;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity

@Table(name = "DESIGNER")
@StringValue(converterClass = Designer2StringConverter.class)
public class DesignerEntity extends PersistenceEntity
{
    public DesignerEntity(String name)
    {
        this.name = name;
    }

    public DesignerEntity()
    {
    }

    @Column(name = "NAME", nullable = false, length = 255)
    private String name;

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
}