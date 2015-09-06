package by.dak.autocad.ocom;

import com.jacob.com.Dispatch;

/**
 * User: akoyro
 * Date: 22.02.11
 * Time: 18:12
 */
public class AcadBlocks extends AcadObject
{
    AcadBlocks(AcadDocument acadDocument)
    {
        super(Dispatch.get(acadDocument.impl.toDispatch(), "Blocks"), acadDocument.application);
    }

    public Integer getCount()
    {
        return Dispatch.get(impl.getDispatch(), "Count").getInt();
    }

    public AcadBlock getItem(Integer index)
    {
        return new AcadBlock(Dispatch.call(impl.getDispatch(), "Item", index), application);
    }
}
