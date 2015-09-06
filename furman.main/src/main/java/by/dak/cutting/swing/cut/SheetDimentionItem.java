package by.dak.cutting.swing.cut;

import by.dak.cutting.cut.gui.cuttingApp.DimensionItem;
import by.dak.persistence.cutting.entities.BoardXConverter;
import by.dak.persistence.cutting.entities.CutterXConverter;
import by.dak.persistence.cutting.entities.StorageElementLinkXConverter;
import by.dak.persistence.entities.Board;
import by.dak.persistence.entities.Cutter;
import by.dak.persistence.entities.StorageElementLink;
import by.dak.utils.convert.StringValueAnnotationProcessor;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

/**
 * User: akoyro
 * Date: 11.03.2009
 * Time: 16:17:34
 * To change this template use File | Settings | File Templates.
 */
@XStreamAlias("SheetDimentionItem")
public class SheetDimentionItem extends DimensionItem
{

    @XStreamConverter(value = BoardXConverter.class)
    @Deprecated
    private Board board;

    @XStreamConverter(value = StorageElementLinkXConverter.class)
    private StorageElementLink storageElementLink;

    @XStreamConverter(value = CutterXConverter.class)
    private Cutter cutter;


    public SheetDimentionItem()
    {
    }

    public SheetDimentionItem(Board board, Cutter cutter)
    {
        //все материалы добовляются по одному
    }

    public SheetDimentionItem(StorageElementLink storageElementLink, Cutter cutter)
    {
        this((Board) storageElementLink.getStoreElement(), cutter);

        this.cutter = cutter;
        setCount(1);
        this.storageElementLink = storageElementLink;
        init();

    }

    private void init()
    {
        switch (cutter.getDirection())
        {

            case horizontal:
                setWidth(getBoard().getWidth().intValue());
                setHeight(getBoard().getLength().intValue());
                break;
            case vertical:
                setHeight(getBoard().getWidth().intValue());
                setWidth(getBoard().getLength().intValue());
                break;
            default:
                throw new IllegalArgumentException();
        }

        setId(StringValueAnnotationProcessor.getProcessor().convert(getBoard().getPair()));
    }

    public StorageElementLink getStorageElementLink()
    {
        return storageElementLink;
    }

    public Cutter getCutter()
    {
        return cutter;
    }

    public void setStorageElementLink(StorageElementLink storageElementLink)
    {
        this.storageElementLink = storageElementLink;
    }

    public Board getBoard()
    {
        return (Board) storageElementLink.getStoreElement();
    }
}
