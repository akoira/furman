package by.dak.cutting.doors.profile;

import by.dak.persistence.entities.PersistenceEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * User: akoyro
 * Date: 12.09.2009
 * Time: 18:32:31
 */
@Entity
@Table(name = "PROFILE_DEF")


public class ProfileDef extends PersistenceEntity
{
//    @OneToMany(targetEntity = ProfileCompDef.class , fetch = FetchType.LAZY, cascade = CascadeType.REMOVE,mappedBy = "profileCompDef")                       // у каждого ProfileDef профиля - одно описание компонента ProfileCompDef
    @Transient
    private ProfileCompDef upProfileCompDef;

//    @OneToMany(targetEntity = ProfileCompDef.class , fetch = FetchType.LAZY, cascade = CascadeType.REMOVE,mappedBy = "profileCompDef")
    @Transient
    private ProfileCompDef downProfileCompDef;

//    @OneToMany(targetEntity = ProfileCompDef.class , fetch = FetchType.LAZY, cascade = CascadeType.REMOVE,mappedBy = "profileCompDef")
    @Transient
    private ProfileCompDef leftProfileCompDef;

//    @OneToMany(targetEntity = ProfileCompDef.class , fetch = FetchType.LAZY, cascade = CascadeType.REMOVE,mappedBy = "profileCompDef")
    @Transient
    private ProfileCompDef rightProfileCompDef;

    @Column(name = "NAME", nullable = false, length = 255)
    private String name;

    public ProfileCompDef getUpProfileCompDef()
    {
        return upProfileCompDef;
    }

    public void setUpProfileCompDef(ProfileCompDef upProfileCompDef)
    {
        this.upProfileCompDef = upProfileCompDef;
    }

    public ProfileCompDef getDownProfileCompDef()
    {
        return downProfileCompDef;
    }

    public void setDownProfileCompDef(ProfileCompDef downProfileCompDef)
    {
        this.downProfileCompDef = downProfileCompDef;
    }

    public ProfileCompDef getLeftProfileCompDef()
    {
        return leftProfileCompDef;
    }

    public void setLeftProfileCompDef(ProfileCompDef leftProfileCompDef)
    {
        this.leftProfileCompDef = leftProfileCompDef;
    }

    public ProfileCompDef getRightProfileCompDef()
    {
        return rightProfileCompDef;
    }

    public void setRightProfileCompDef(ProfileCompDef rightProfileCompDef)
    {
        this.rightProfileCompDef = rightProfileCompDef;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}