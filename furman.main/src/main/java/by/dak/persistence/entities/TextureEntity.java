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

import by.dak.persistence.convert.Texture2StringConverter;
import by.dak.persistence.entities.types.FurnitureCode;
import by.dak.persistence.entities.validator.TextureValidator;
import by.dak.utils.convert.StringValue;
import by.dak.utils.validator.Validator;
import org.hibernate.annotations.DiscriminatorOptions;
import org.hibernate.annotations.Proxy;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Proxy(lazy = false)
@StringValue(converterClass = Texture2StringConverter.class)
@Validator(validatorClass = TextureValidator.class)
@DiscriminatorValue(value = "TextureEntity")
@DiscriminatorOptions(force = true)
public class TextureEntity extends FurnitureCode
{
    //surface может быть ноль но должна быть уникальна в наборе surface,code,manf
    @Column(name = "SURFACE", nullable = true, length = 255)
    private String surface;

    @Column(name = "ROTATABLE", nullable = false, columnDefinition = "bit")
    private boolean rotatable;

    @Column(name = "IN_SIZE", nullable = false, columnDefinition = "bit")
    private boolean inSize;


    public void setSurface(String surface)
    {
        this.surface = surface;
    }

    public String getSurface()
    {
        return surface;
    }

    public void setRotatable(boolean value)
    {
        this.rotatable = value;
    }

    public boolean isRotatable()
    {
        return rotatable;
    }


    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (getCode() == null ? 0 : getCode().hashCode());
        result = prime * result + (getGroupIdentifier() == null ? 0 : getGroupIdentifier().hashCode());
        result = prime * result + (getManufacturer() == null ? 0 : getManufacturer().hashCode());
        result = prime * result + (getName() == null ? 0 : getName().hashCode());
        result = prime * result + (rotatable ? 1231 : 1237);
        result = prime * result + (inSize ? 1231 : 1237);
        result = prime * result + (surface == null ? 0 : surface.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {

        if (true)
        {
            super.equals(obj);
        }
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        TextureEntity other = (TextureEntity) obj;
        if (getCode() == null)
        {
            if (other.getCode() != null)
            {
                return false;
            }
        }
        else if (!getCode().equals(other.getCode()))
        {
            return false;
        }
        if (getGroupIdentifier() == null)
        {
            if (other.getGroupIdentifier() != null)
            {
                return false;
            }
        }
        else if (!getGroupIdentifier().equals(other.getGroupIdentifier()))
        {
            return false;
        }
        if (getManufacturer() == null)
        {
            if (other.getManufacturer() != null)
            {
                return false;
            }
        }
        else if (!getManufacturer().equals(other.getManufacturer()))
        {
            return false;
        }
        if (getName() == null)
        {
            if (other.getName() != null)
            {
                return false;
            }
        }
        else if (!getName().equals(other.getName()))
        {
            return false;
        }
        if (rotatable != other.rotatable)
        {
            return false;
        }
        if (surface == null)
        {
            if (other.surface != null)
            {
                return false;
            }
        }
        else if (!surface.equals(other.surface))
        {
            return false;
        }
        return true;
    }

    @Override
    public Object clone()
    {
        TextureEntity textureEntity = new TextureEntity();
        if (getCode() != null)
        {
            textureEntity.setCode(getCode());
        }
        if (getGroupIdentifier() != null)
        {
            textureEntity.setGroupIdentifier(new String(getGroupIdentifier()));
        }
        if (getManufacturer() != null)
        {
            textureEntity.setManufacturer((Manufacturer) getManufacturer().clone());
        }
        if (getName() != null)
        {
            textureEntity.setName(new String(getName()));
        }
        if (surface != null)
        {
            textureEntity.setSurface(new String(surface));
        }
        textureEntity.setRotatable(rotatable);
        textureEntity.setInSize(inSize);
        return textureEntity;
    }

    public boolean isInSize() {
        return inSize;
    }

    public void setInSize(boolean inSize) {
        this.inSize = inSize;
    }
}