package by.dak.persistence.entities;

import by.dak.persistence.convert.Cutter2StringConverter;
import by.dak.persistence.entities.validator.CutterValidator;
import by.dak.utils.convert.StringValue;
import by.dak.utils.validator.Validator;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

@Entity
@Proxy(lazy = false)

@Table(name = "CUTTER")
@StringValue(converterClass = Cutter2StringConverter.class)
@Validator(validatorClass = CutterValidator.class)


public class Cutter extends PersistenceEntity
{
    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "SAWCUT_WIDTH", nullable = false)
    private Long sawcutWidth;

    @Column(name = "CUT_SIZE_LEFT", nullable = false)
    private Long cutSizeLeft;

    @Column(name = "CUT_SIZE_RIGHT", nullable = false)
    private Long cutSizeRight;

    @Column(name = "CUT_SIZE_BOTTOM", nullable = false)
    private Long cutSizeBottom;

    @Column(name = "CUT_SIZE_TOP", nullable = false)
    private Long cutSizeTop;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CutDirection direction = CutDirection.horizontal;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Long getSawcutWidth()
    {
        return sawcutWidth;
    }

    public void setSawcutWidth(Long sawcutWidth)
    {
        this.sawcutWidth = sawcutWidth;
    }

    public Long getCutSizeLeft()
    {
        return cutSizeLeft;
    }

    public void setCutSizeLeft(Long cutSizeLeft)
    {
        this.cutSizeLeft = cutSizeLeft;
    }

    public Long getCutSizeRight()
    {
        return cutSizeRight;
    }

    public void setCutSizeRight(Long cutSizeRight)
    {
        this.cutSizeRight = cutSizeRight;
    }

    public Long getCutSizeBottom()
    {
        return cutSizeBottom;
    }

    public void setCutSizeBottom(Long cutSizeBottom)
    {
        this.cutSizeBottom = cutSizeBottom;
    }

    public Long getCutSizeTop()
    {
        return cutSizeTop;
    }

    public void setCutSizeTop(Long cutSizeTop)
    {
        this.cutSizeTop = cutSizeTop;
    }

    public static Cutter createDefaultCutter()
    {
        Cutter defaultCutter = new Cutter();
        defaultCutter.setName("Станок");
        defaultCutter.setSawcutWidth(0l);
        defaultCutter.setCutSizeBottom(0l);
        defaultCutter.setCutSizeTop(0l);
        defaultCutter.setCutSizeRight(0l);
        defaultCutter.setCutSizeLeft(0l);

        return defaultCutter;
    }

    public CutDirection getDirection()
    {
        return direction;
    }

    public void setDirection(CutDirection direction)
    {
        this.direction = direction;
    }
}


