package by.dak.furman.templateimport.parser.template;

import by.dak.furman.templateimport.parser.AParser;
import by.dak.furman.templateimport.parser.ParserUtils;
import by.dak.furman.templateimport.parser.detail.BoardDetailParser;
import by.dak.furman.templateimport.values.BoardDetail;
import by.dak.furman.templateimport.values.FacadeItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;

import java.io.File;
import java.util.List;

/**
 * User: akoyro
 * Date: 9/22/13
 * Time: 9:40 PM
 */
public class FacadeItemParser extends AParser
{
    private static final Logger LOGGER = LogManager.getLogger(FacadeItemParser.class);
    private File docFile;
    private Range range;

    private int index;

    private Paragraph paragraph;

    private FacadeItem value = new FacadeItem();

    @Override
    public void parse()
    {
        value.setFile(docFile);

        paragraph = range.getParagraph(index);
        value.setName(ParserUtils.adjustText(paragraph.text()));

        index++;
        paragraph = range.getParagraph(index);

        while (!paragraph.isInTable())
        {
            index++;
            paragraph = range.getParagraph(index);
        }

        if (!paragraph.isInTable())
        {
            value.addMessage("error.noDetails", ParserUtils.adjustText(paragraph.text()));
            return;
        }

        Table table = range.getTable(paragraph);
        parseBoardDetail(table);
        index = getIndex() + table.numParagraphs();
    }

    private void parseBoardDetail(Table table)
    {
        BoardDetailParser parser = new BoardDetailParser();
        parser.setTable(table);
        parser.setDocFile(docFile);
        parser.parse();

        List<BoardDetail> list = parser.getResult();
        for (BoardDetail boardDetail : list)
        {
            boardDetail.setParent(value);
            value.addChild(boardDetail);
        }
    }

    public FacadeItem getValue()
    {
        return value;
    }

    public int getIndex()
    {
        return index;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }

    public void setRange(Range range)
    {
        this.range = range;
    }

    public void setDocFile(File docFile)
    {
        this.docFile = docFile;
    }
}
