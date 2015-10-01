package by.dak.report.jasper;

import by.dak.cutting.cut.guillotine.Segment;
import by.dak.cutting.cut.guillotine.Strips;
import by.dak.persistence.entities.BoardDef;
import by.dak.persistence.entities.Cutter;
import by.dak.report.jasper.cutting.data.CuttedSheetData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akoiro on 9/29/15.
 */
public class GetSawLength {
    private BoardDef boardDef;
    private Cutter cutter;
    private List<Segment> segments = new ArrayList<>();

    public long get() {
        long result = 0;
        for (Segment segment : segments) {
            result += segment.getSawLength();
            if (!isRest(segment)) {
                if (cutter.getCutSizeBottom() > 0) {
                    result += segment.getMaterialLength();
                }

                if (cutter.getCutSizeTop() > 0) {
                    result += segment.getMaterialLength();
                }
                if (cutter.getCutSizeLeft() > 0) {
                    result += segment.getMaterialWidth();
                }
                if (cutter.getCutSizeRight() > 0) {
                    result += segment.getMaterialWidth();
                }
            }
        }

        return result;
    }

    private boolean isRest(Segment segment) {
        return segment.getMaterialLength() < boardDef.getDefaultLength() ||
                segment.getMaterialWidth() < boardDef.getDefaultWidth();
    }

    public static GetSawLength valueOf(Segment segment, BoardDef boardDef) {
        GetSawLength result = new GetSawLength();
        result.boardDef = boardDef;
        result.cutter = result.boardDef.getCutter();
        result.segments.add(segment);
        return result;
    }

    public static GetSawLength valueOf(CuttedSheetData sheetData) {
        return valueOf(sheetData.getSheetSegment(), sheetData.getBoard().getBoardDef());
    }


    public static GetSawLength valueOf(Strips strips, BoardDef boardDef) {
        GetSawLength result = new GetSawLength();
        result.boardDef = boardDef;
        result.cutter = result.boardDef.getCutter();
        result.segments.addAll(strips.getItems());
        return result;
    }

}
