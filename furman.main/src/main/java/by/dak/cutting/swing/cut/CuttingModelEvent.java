package by.dak.cutting.swing.cut;

import by.dak.cutting.swing.order.data.TextureBoardDefPair;

/**
 * Created by IntelliJ IDEA.
 * User: akoyro
 * Date: 07.03.2009
 * Time: 12:52:54
 * To change this template use File | Settings | File Templates.
 */
public class CuttingModelEvent
{

    private CuttingModel cuttingModel;
    private Type type;
    private TextureBoardDefPair changedStripsPair;

    public CuttingModelEvent(CuttingModel cuttingModel, Type type)
    {
        this.cuttingModel = cuttingModel;
        this.type = type;
    }

    public CuttingModel getCuttingModel()
    {
        return cuttingModel;
    }

    public Type getType()
    {
        return type;
    }

    public TextureBoardDefPair getChangedStripsPair()
    {
        return changedStripsPair;
    }

    public void setChangedStripsPair(TextureBoardDefPair changedStripsPair)
    {
        this.changedStripsPair = changedStripsPair;
    }

    public enum Type
    {
        REMOVE_ALL,
        REFRESH,
        STRIPS_CHANGED,
    }

}
