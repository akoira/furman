package by.dak.cutting.facade.impl;

import by.dak.cutting.cut.base.Dimension;
import by.dak.cutting.facade.BaseFacadeImpl;
import by.dak.cutting.facade.CutterFacade;
import by.dak.persistence.dao.CutterDao;
import by.dak.persistence.entities.Cutter;
import org.jdesktop.application.Application;

public class CutterFacadeImpl extends BaseFacadeImpl<Cutter> implements CutterFacade
{
    private String defaultName;
    private String defaultLinearName;

    @Override
    public Cutter loadByName(String name)
    {
        return ((CutterDao) dao).loadByName(name);
    }

    @Override
    public Cutter getDefault()
    {
        return ((CutterDao) dao).loadByName(getDefaultName());
    }

    /**
     * Торцуем деталь
     *
     * @param sheet
     * @param cutter
     * @return
     */
    @Override
    public Dimension trim(Dimension sheet, Cutter cutter)
    {
        long dL = getDeltaLength(cutter);
        long dW = getDeltaWidth(cutter);
        return new Dimension((int) (sheet.getWidth() - dL), (int) (sheet.getHeight() - dW));
    }

    private long getDeltaWidth(Cutter cutter)
    {
        long dW = cutter.getCutSizeBottom() + cutter.getCutSizeTop();
        return dW;
    }

    private long getDeltaLength(Cutter cutter)
    {
        long dL = cutter.getCutSizeLeft() + cutter.getCutSizeRight();
        return dL;
    }

    @Override
    public Dimension untrim(Dimension sheet, Cutter cutter)
    {
        long dL = getDeltaLength(cutter);
        long dW = getDeltaWidth(cutter);
        return new Dimension((int) (sheet.getWidth() + dL), (int) (sheet.getHeight() + dW));
    }

    @Override
    public String getDefaultName()
    {
        if (defaultName == null)
        {
            defaultName = Application.getInstance().getContext().getResourceMap().getString("Application.default.Cutter.name");
            if (defaultName == null)
            {
                defaultName = "Cutter-5-7";
            }
        }
        return defaultName;
    }

    @Override
    public void setDefaultName(String defaultName)
    {
        this.defaultName = defaultName;
    }

    @Override
    public void setDefaultLinearName(String defaultLinearName)
    {
        this.defaultLinearName = defaultLinearName;
    }

    @Override
    public Cutter getDefaultLinearCutter()
    {
        return dao.findUniqueByField("name", getDefaultLinearName());
    }

    public String getDefaultLinearName()
    {
        return defaultLinearName;
    }
}
