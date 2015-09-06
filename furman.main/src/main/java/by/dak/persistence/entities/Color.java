package by.dak.persistence.entities;

import javax.persistence.Column;

/**
 * User: akoyro
 * Date: 27.10.2010
 * Time: 10:06:34
 */
public class Color extends PersistenceEntity
{
    //    public stat
    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "CODE", nullable = false)
    private String code = "0";

    @Column(name = "ROTATABLE", nullable = false)
    private Boolean rotatable = Boolean.FALSE;

    @Column(name = "SURFACE", nullable = true, length = 255)
    private String surface;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public Boolean getRotatable()
    {
        return rotatable;
    }

    public void setRotatable(Boolean rotatable)
    {
        this.rotatable = rotatable;
    }

    public String getSurface()
    {
        return surface;
    }

    public void setSurface(String surface)
    {
        this.surface = surface;
    }
}
