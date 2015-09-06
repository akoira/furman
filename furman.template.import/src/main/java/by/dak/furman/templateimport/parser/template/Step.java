package by.dak.furman.templateimport.parser.template;

import by.dak.furman.templateimport.parser.ParserUtils;
import org.apache.poi.hwpf.usermodel.Paragraph;

import java.util.regex.Pattern;

/**
 * User: akoyro
 * Date: 5/1/13
 * Time: 2:32 PM
 */
public enum Step
{
    description("[\\w]"),
    borderDef("ПВХ[ ]?(-|–)[ ]?[0-9]{1,3}мм"),
    boardDef("(ДСТП|ДВП|ДСП)-[0-9]{1,3}мм"),
    detailsItem("^ДЕТАЛИРОВКА на"),
    boardItem("^ДЕТАЛИРОВКА НА (ДСТП|ДВП|ДСП)-[0-9]{1,3}мм"),
    facadeItem("^ДЕТАЛИРОВКА НА ФАСАД"),
    furnitureItem("^ФУРНИТУРА");

    private Pattern pattern;

    Step(String pattern)
    {
        this.pattern =
                Pattern.compile(pattern, Pattern.CASE_INSENSITIVE |
                        Pattern.UNICODE_CHARACTER_CLASS |
                        Pattern.UNICODE_CASE);
    }

    public boolean is(Paragraph paragraph)
    {
        String text = ParserUtils.adjustText(paragraph.text());
        return pattern.matcher(text).find();
    }

    public Pattern getPattern()
    {
        return pattern;
    }
}
