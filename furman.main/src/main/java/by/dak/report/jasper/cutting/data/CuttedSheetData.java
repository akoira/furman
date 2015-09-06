package by.dak.report.jasper.cutting.data;

import by.dak.cutting.cut.guillotine.Segment;
import by.dak.persistence.entities.AOrderBoardDetail;
import by.dak.persistence.entities.Board;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * @author akoyro
 * @version 0.1 02.02.2009
 * @introduced [Preview Plugin Support]
 * @since 2.0.0
 */
public interface CuttedSheetData
{
    List<AOrderBoardDetail> getFurnitures();

    Board getBoard();

    BufferedImage getCuttedImage();

    Segment getSheetSegment();

}
