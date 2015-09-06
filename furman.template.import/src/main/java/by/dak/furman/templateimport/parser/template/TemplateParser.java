package by.dak.furman.templateimport.parser.template;

import by.dak.furman.templateimport.parser.ParserUtils;
import by.dak.furman.templateimport.values.FurnitureDetail;
import by.dak.furman.templateimport.values.FurnitureItem;
import by.dak.furman.templateimport.values.Template;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.model.PicturesTable;
import org.apache.poi.hwpf.usermodel.*;

import javax.imageio.ImageIO;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: akoyro
 * Date: 4/30/13
 * Time: 11:27 AM
 */
public class TemplateParser
{
    private static final Logger LOGGER = LogManager.getLogger(TemplateParser.class);
    private static final String PATTERN_CODE = "^[0-9A-Za-z]*\\.([0-9A-Za-z]*\\.|[0-9A-Za-z]*)*";
    private static final String DEF_PATTERN_CODE = "^[\\w]*\\.([\\w]*\\.|[\\w]*)*";
    private static final String PATTERN_WRONG_CODE_SYMBOL = "[^a-zA-Z0-9\\.]";
    private File docFile;
    private HWPFDocument document;
    private Step step = Step.description;
    private Template template = new Template();

    public static void main(String[] args)
    {
        File file = new File("d:/_projects/БАЗА/Нижняя база/BN.HR.1s.030. Шкаф распашной с 1-им фасадом/Деталировка BN.HR.1s.030/BN.HR.1s.030.20.  Шкаф распашной с 1-им фасадом.doc");
        TemplateParser parser = new TemplateParser();
        parser.setDocFile(file);
        parser.parse();
    }

    private int parseFurnitureItem(Range range, int index)
    {
        FurnitureItem result = new FurnitureItem();

        LOGGER.info(String.format("Parse Furniture for file %s", getDocFile().getAbsolutePath()));

        Paragraph paragraph = range.getParagraph(index);
        result.setName(ParserUtils.adjustText(paragraph.text()));

        while (!paragraph.isInTable())
        {
            index++;
            paragraph = range.getParagraph(index);
        }

        if (!paragraph.isInTable())
        {
            template.addMessage("error.wrongFormatFurnitureNotTable");
            return -1;
        }

        ArrayList<String> columns = new ArrayList<String>();
        Table table = range.getTable(paragraph);
        TableRow row = table.getRow(0);
        for (int i = 0; i < row.numCells(); i++)
        {
            TableCell cell = row.getCell(i);
            String column = ParserUtils.adjustText(cell.text());
            if (!columns.contains(column))
                columns.add(column);
        }
        for (int i = 1; i < table.numRows(); i++)
        {
            row = table.getRow(i);

            FurnitureDetail detail = new FurnitureDetail();
            for (int c = 0; c < row.numCells(); c++)
            {
                if (c % 3 == 0)
                {
                    detail = new FurnitureDetail();
                    detail.setColumns(columns);
                    result.addChild(detail);
                }
                detail.setValue(c % 3, ParserUtils.adjustText(row.getCell(c).text()));
            }
        }
        getTemplate().addChild(result);
        index += table.numParagraphs();
        return index;
    }

    public int parseBoardItem(Range range, int index)
    {
        BoardItemParser parser = new BoardItemParser();
        parser.setDocFile(docFile);
        parser.setRange(range);
        parser.setIndex(index);
        parser.parse();

        template.addChild(parser.getValue());
        return parser.getIndex();
    }

    private boolean isDescription(Paragraph paragraph)
    {
        return !(Step.boardItem.is(paragraph) ||
                Step.facadeItem.is(paragraph) ||
                Step.furnitureItem.is(paragraph));
    }

    private int parseDescription(Range range, int index)
    {
        LOGGER.info(String.format("Parse description for file %s", getDocFile().getAbsolutePath()));
        StringBuilder stringBuilder = new StringBuilder();
        Paragraph paragraph = range.getParagraph(index);
        while (isDescription(paragraph))
        {
            if (paragraph.isInTable())
            {
                template.addMessage("error.wrongDescriptionFormat");
                return -1;
            }

            String text = ParserUtils.adjustText(paragraph.text());
            if (text.length() > 0)
                stringBuilder.append(text).append("\n");
            index++;
            paragraph = range.getParagraph(index);
        }
        getTemplate().getDescription().setDescription(stringBuilder.toString());
        getTemplate().getDescription().setCode(parserCode(getTemplate().getDescription().getDescription()));

        return index;
    }

    private String parserCode(String text)
    {
        String[] values = StringUtils.split(text, "\n");
        String code = null;
        for (String value : values)
        {
            Pattern pattern = Pattern.compile(DEF_PATTERN_CODE, Pattern.UNICODE_CHARACTER_CLASS);
            if (pattern.matcher(value).matches())
                code = value;
        }

        if (code == null)
        {
            template.addMessage("error.codeNotFound", text);
            return null;
        }

        if (code.matches(PATTERN_CODE))
            return code;
        else
        {
            Pattern pattern = Pattern.compile(PATTERN_WRONG_CODE_SYMBOL);
            Matcher matcher = pattern.matcher(code);
            String replaced = matcher.replaceAll("?");
            template.addMessage("error.codeHasWrongSymbol", code, replaced);
            return code;
        }
    }

    private void parseImage()
    {
        LOGGER.info(String.format("Parse image from file %s", getDocFile().getAbsolutePath()));
        try
        {
            PicturesTable table = document.getPicturesTable();
            if (table != null)
            {
                List<Picture> pictures = table.getAllPictures();
                if (pictures != null && pictures.size() > 0)
                {
                    Picture picture = pictures.get(0);
                    getTemplate().setImage(ImageIO.read(new ByteArrayInputStream(picture.getRawContent())));
                }
            }
            if (getTemplate().getImage() == null)
                template.addMessage("error.imageNotFound");

        }
        catch (IOException e)
        {
            template.addMessage("error.cannotGetImage");
        }
    }

    private boolean defineStep(Paragraph paragraph)
    {
        switch (step)
        {
            case description:
            case boardItem:
            case facadeItem:
            case furnitureItem:
                if (Step.boardItem.is(paragraph))
                    step = Step.boardItem;
                else if (Step.facadeItem.is(paragraph))
                    step = Step.facadeItem;
                else if (Step.furnitureItem.is(paragraph))
                    step = Step.furnitureItem;
                else if (step == Step.description)
                    step = Step.description;
                else
                {
                    parseWrongCharsDetailsItem(paragraph);
                    return false;
                }
                break;
            default:
                throw new IllegalArgumentException();
        }
        return true;
    }

    public void parse()
    {
        template.setFile(docFile);

        load();

        LOGGER.info(String.format("Parse document from file %s", getDocFile().getAbsolutePath()));
        parseImage();

        Range range = document.getRange();
        int currentIndex = 0;
        while (currentIndex < range.numParagraphs())
        {
            Paragraph paragraph = range.getParagraph(currentIndex);
            LOGGER.info(String.format("Parse paragraph %s from file %s", paragraph.text(), getDocFile().getAbsolutePath()));

            if (!defineStep(paragraph))
            {
                String text = ParserUtils.adjustText(paragraph.text());
                if (!text.equals(StringUtils.EMPTY))
                    template.addMessage("error.unknownItem", ParserUtils.adjustText(paragraph.text()));
                currentIndex++;
                continue;
            }

            switch (step)
            {
                case description:
                    currentIndex = parseDescription(range, currentIndex);
                    break;
                case boardItem:
                    currentIndex = parseBoardItem(range, currentIndex);
                    break;
                case facadeItem:
                    currentIndex = parseFacadeItem(range, currentIndex);
                    break;
                case furnitureItem:
                    currentIndex = parseFurnitureItem(range, currentIndex);
                    break;
                default:
                    throw new IllegalArgumentException();
            }
            //something wrong when parse step
            if (currentIndex == -1)
                return;
        }
        template.setName(template.getDescription().getCode());
    }

    private void parseWrongCharsDetailsItem(Paragraph paragraph)
    {
        if (Step.detailsItem.is(paragraph))
        {
            String text = ParserUtils.adjustText(paragraph.text());
            Pattern pattern = Pattern.compile("[a-z]", Pattern.CASE_INSENSITIVE |
                    Pattern.UNICODE_CHARACTER_CLASS |
                    Pattern.UNICODE_CASE);
            Matcher matcher = pattern.matcher(text);
            String result = matcher.replaceAll("?");
            template.addMessage("error.wrongChar", text, result);
        }
    }

    private int parseFacadeItem(Range range, int index)
    {
        FacadeItemParser parser = new FacadeItemParser();
        parser.setDocFile(docFile);
        parser.setRange(range);
        parser.setIndex(index);
        parser.parse();

        template.addChild(parser.getValue());
        return parser.getIndex();
    }

    private void load()
    {
        LOGGER.info(String.format("Load template file %s", docFile.getAbsolutePath(), docFile.getAbsolutePath()));

        InputStream stream = null;
        try
        {

            stream = new FileInputStream(getDocFile());
            document = new HWPFDocument(stream);
        }
        catch (Exception e)
        {
            template.addMessage("error.cannotLoadDocument", e);
        }
        finally
        {
            IOUtils.closeQuietly(stream);
        }
    }

    public Template getTemplate()
    {
        return template;
    }

    public File getDocFile()
    {
        return docFile;
    }

    public void setDocFile(File docFile)
    {
        this.docFile = docFile;
    }
}
