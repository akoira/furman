package by.dak.report.jasper.cutting.data;

import by.dak.cutting.cut.guillotine.Segment;
import by.dak.persistence.entities.AOrderBoardDetail;
import by.dak.persistence.entities.Board;

import java.awt.image.BufferedImage;
import java.util.*;

/**
 * @author akoyro
 * @version 0.1 02.02.2009
 * @introduced [Preview Plugin Support]
 * @since 2.0.0
 */
public class CuttedSheetDataImpl implements CuttedSheetData
{
    private Map<Long, AOrderBoardDetail> furnitures = new HashMap<Long, AOrderBoardDetail>();
    private Board board;
    private BufferedImage bufferedImage;
    private Segment sheetSegment;


    public boolean containsFurniture(long orderNumber)
    {
        return furnitures.containsKey(orderNumber);
    }

    public void incrementCount(long orderNumber)
    {
        AOrderBoardDetail entity = furnitures.get(orderNumber);
        entity.setAmount(entity.getAmount() + 1);
    }

    public void addFurniture(AOrderBoardDetail furniture)
    {
        furnitures.put(furniture.getNumber(), furniture);
    }

    @Override
    public List<AOrderBoardDetail> getFurnitures()
    {
        return Collections.unmodifiableList(new ArrayList<AOrderBoardDetail>(furnitures.values()));
    }

    @Override
    public Board getBoard()
    {
        return board;
    }

    public void setBoard(Board board)
    {
        this.board = board;
    }

    @Override
    public BufferedImage getCuttedImage()
    {
        return bufferedImage;
    }

    public void setBufferedImage(BufferedImage bufferedImage)
    {
        this.bufferedImage = bufferedImage;
    }

    public Segment getSheetSegment()
    {
        return sheetSegment;
    }

    public void setSheetSegment(Segment sheetSegment)
    {
        this.sheetSegment = sheetSegment;
    }
}
