package by.dak.design.entity.converter;

import by.dak.persistence.cutting.entities.BoardDefXConverter;
import by.dak.persistence.cutting.entities.TextureXConverter;
import by.dak.persistence.entities.Board;
import by.dak.persistence.entities.BoardDef;
import by.dak.persistence.entities.TextureEntity;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 09.08.11
 * Time: 22:45
 * To change this template use File | Settings | File Templates.
 */
public class BoardXConverter implements Converter
{
    @Override
    public void marshal(Object o, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext)
    {
        if (o instanceof Board)
        {
            long length = ((Board) o).getLength();
            long width = ((Board) o).getWidth();

            hierarchicalStreamWriter.addAttribute("length", String.valueOf(length));
            hierarchicalStreamWriter.addAttribute("width", String.valueOf(width));

            TextureEntity texture = ((Board) o).getTexture();
            BoardDef boardDef = ((Board) o).getBoardDef();

            if (texture != null)
            {
                hierarchicalStreamWriter.startNode("texture");
                marshallingContext.convertAnother(texture, new TextureXConverter());
                hierarchicalStreamWriter.endNode();
            }
            if (boardDef != null)
            {
                hierarchicalStreamWriter.startNode("boardDef");
                marshallingContext.convertAnother(boardDef, new BoardDefXConverter());
                hierarchicalStreamWriter.endNode();
            }

        }
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext)
    {
        Board board = new Board();

        String length = hierarchicalStreamReader.getAttribute("length");
        String width = hierarchicalStreamReader.getAttribute("width");

        board.setLength(Long.valueOf(length));
        board.setWidth(Long.valueOf(width));

        while (hierarchicalStreamReader.hasMoreChildren())
        {
            hierarchicalStreamReader.moveDown();
            if (hierarchicalStreamReader.getNodeName().equals("boardDef"))
            {
                BoardDef boardDef = (BoardDef) unmarshallingContext.
                        convertAnother(board, BoardDef.class, new BoardDefXConverter());
                if (boardDef != null)
                {
                    board.setBoardDef(boardDef);
                }
            }
            else if (hierarchicalStreamReader.getNodeName().equals("texture"))
            {
                TextureEntity texture = (TextureEntity) unmarshallingContext.
                        convertAnother(board, TextureEntity.class, new TextureXConverter());
                if (texture != null)
                {
                    board.setTexture(texture);
                }
            }
            hierarchicalStreamReader.moveUp();
        }

        return board;
    }

    @Override
    public boolean canConvert(Class aClass)
    {
        return true;
    }
}
