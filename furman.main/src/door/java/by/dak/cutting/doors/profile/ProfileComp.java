package by.dak.cutting.doors.profile;

import by.dak.persistence.entities.PersistenceEntity;

import javax.persistence.*;

/**
 * User: akoyro
 * Date: 12.09.2009
 * Time: 17:50:07
 */
@Entity

@Table(name = "PROFILE_COMP")
public class ProfileComp extends PersistenceEntity
{
    @ManyToOne(targetEntity = ProfileCompDef.class)
    @JoinColumn(name = "PROFILE_COMP_DEF_ID", referencedColumnName = "ID",nullable = false)
    private ProfileCompDef profileCompDef;

    @Column(name = "LENGTH", nullable = false)
    private Long length;

    public ProfileCompDef getProfileCompDef()
    {
        return profileCompDef;
    }

    public void setProfileComponentDef(ProfileCompDef profileCompDef)
    {
        this.profileCompDef = profileCompDef;
    }

    public Long getLength()
    {
        return length;
    }

    public void setLength(Long length)
    {
        this.length = length;
    }
}
