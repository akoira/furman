package by.dak.furman.templateimport.parser.template;

import by.dak.furman.templateimport.parser.AParser;
import by.dak.furman.templateimport.parser.ParserUtils;
import by.dak.furman.templateimport.parser.detail.BoardDetailParser;
import by.dak.furman.templateimport.values.BoardDetail;
import by.dak.furman.templateimport.values.BoardItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;

import static by.dak.furman.templateimport.parser.ParserUtils.adjustText;

/**
 * User: akoyro
 * Date: 9/22/13
 * Time: 9:40 PM
 */
public class BoardItemParser extends AParser {
	private static final Logger LOGGER = LogManager.getLogger(BoardItemParser.class);
	private File docFile;
	private Range range;

	private int index;

	private Paragraph paragraph;

	private BoardItem value = new BoardItem();

	private String parseBoardDef(Paragraph paragraph) {
		String boardDef;
		boardDef = adjustText(paragraph.text());
		Matcher matcher = Step.boardDef.getPattern().matcher(boardDef);
		if (matcher.find())
			boardDef = boardDef.substring(matcher.start(), matcher.end());
		else
			value.addMessage("error.cannotParserBoardDef", ParserUtils.adjustText(paragraph.text()));
		return boardDef;
	}

	private String parseBorderDef(Paragraph paragraph) {

		String result = adjustText(paragraph.text());
		Matcher matcher = Step.borderDef.getPattern().matcher(result);
		if (matcher.find())
			result = result.substring(matcher.start(), matcher.end());
		else
			value.addMessage("error.cannotParserBorderDef", ParserUtils.adjustText(paragraph.text()));
		return result;
	}

	@Override
	public void parse() {
		value.setFile(docFile);
		paragraph = range.getParagraph(index);

		value.setName(ParserUtils.adjustText(paragraph.text()));

		parseDefs();

		if (!paragraph.isInTable()) {
			value.addMessage("error.noDetails", ParserUtils.adjustText(paragraph.text()));
			return;
		}


		LOGGER.info(String.format("Parse BoardDetails %s:%s for file %s",
				value.getBoardDef(),
				value.getBorderDef(),
				docFile.getAbsolutePath()));


		Table table = range.getTable(paragraph);
		parseBoardDetail(table);

		index = getIndex() + table.numParagraphs();
	}

	private void parseBoardDetail(Table table) {
		BoardDetailParser parser = new BoardDetailParser();
		parser.setTable(table);
		parser.setDocFile(docFile);
		parser.parse();

		List<BoardDetail> list = parser.getResult();
		for (BoardDetail boardDetail : list) {
			boardDetail.setBoardDef(value.getBoardDef());
			boardDetail.setBorderDef(value.getBorderDef());
			boardDetail.setParent(value);
			value.addChild(boardDetail);
		}
	}

	private void parseDefs() {
		while (!paragraph.isInTable()) {
			if (Step.boardDef.is(paragraph))
				value.setBoardDef(parseBoardDef(paragraph));
			if (Step.borderDef.is(paragraph))
				value.setBorderDef(parseBorderDef(paragraph));
			index++;
			paragraph = range.getParagraph(index);
		}

		if (value.getBoardDef() == null)
			value.addMessage("error.wrongBoardDef");
	}

	public BoardItem getValue() {
		return value;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void setRange(Range range) {
		this.range = range;
	}

	public void setDocFile(File docFile) {
		this.docFile = docFile;
	}
}
