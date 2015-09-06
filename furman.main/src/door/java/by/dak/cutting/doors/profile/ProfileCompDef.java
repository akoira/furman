package by.dak.cutting.doors.profile;

import by.dak.persistence.entities.PersistenceEntity;
import by.dak.utils.convert.StringValue;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 10.9.2009
 * Time: 15.11.45
 * To change this template use File | Settings | File Templates.
 */
@Entity

@Table(name = "PROFILE_COMP_DEF")
@StringValue(converterClass = ProfileCompDef2StringConverter.class)

public class ProfileCompDef extends PersistenceEntity
{
    @Column(name = "CODE", nullable = false, length = 255)
    private String code;
    @Column(name = "MAT_THICKESS", nullable = false)
    private Integer matThickness;
    @Column(name = "WIDTH", nullable = false)
    private Integer width;
    @Column(name = "LENGTH", nullable = false)
    private Long length;
    @Column(name = "INDENT", nullable = false)
    private Integer indent;

//    @OneToMany(targetEntity = ProfileComp.class , fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "profileCompDef")
//    private List<ProfileComp> profileComp;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, targetEntity = ProfileDef.class)
    // у одного ProfileDefImpl профиля - множество описаний компонентов ProfileCompDef
    @JoinColumn(name = "PROFILE_DEF_ID", referencedColumnName = "ID", nullable = false)
    private ProfileDef profileDef;

    @Column(name = "PROFILE_POSITION", nullable = false)
    private ProfilePosition position = ProfilePosition.top;

    public void setCode(String code)
    {
        this.code = code;
    }

    public void setIndent(Integer indent)
    {
        this.indent = indent;
    }

    public void setLength(Long length)
    {
        this.length = length;
    }

    public void setMatThickness(Integer matThickness)
    {
        this.matThickness = matThickness;
    }

    public void setProfileDef(ProfileDef profileDef)
    {
        this.profileDef = profileDef;
    }

    public void setWidth(Integer width)
    {
        this.width = width;
    }

    public String getCode()
    {
        return code;
    }

    public Integer getMatThickness()
    {
        return matThickness;
    }

    public Integer getWidth()
    {
        return width;
    }

    public Long getLength()
    {
        return length;
    }

    public Integer getIndent()
    {
        return indent;
    }

    public ProfileDef getProfileDef()
    {
        return profileDef;
    }

    public ProfilePosition getPosition()
    {
        return position;
    }

    public void setPosition(ProfilePosition position)
    {
        this.position = position;
    }
}
