package by.dak.design.entity.converter;

import by.dak.design.draw.components.BoardElement;
import by.dak.persistence.cutting.entities.BoardDefXConverter;
import by.dak.persistence.cutting.entities.TextureXConverter;
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
 * Date: 14.08.11
 * Time: 12:52
 * To change this template use File | Settings | File Templates.
 */
public class DElementXConverter implements Converter
{
    @Override
    public void marshal(Object o, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext)
    {
        if (o instanceof BoardElement)
        {
            double width = ((BoardElement) o).getWidth();
            double length = ((BoardElement) o).getLength();
            BoardDef boardDef = ((BoardElement) o).getBoardDef();
            TextureEntity texture = ((BoardElement) o).getTexture();
            Long overmesureLength = ((BoardElement) o).getOvermeasureLength();
            Long overmesureWidth = ((BoardElement) o).getOvermeasureWidth();
            BoardElement.Location location = ((BoardElement) o).getLocation();
            BoardElement.Type type = ((BoardElement) o).getType();

            hierarchicalStreamWriter.addAttribute("width", String.valueOf(width));
            hierarchicalStreamWriter.addAttribute("length", String.valueOf(length));
            hierarchicalStreamWriter.addAttribute("overmesureLength", String.valueOf(overmesureLength));
            hierarchicalStreamWriter.addAttribute("overmesureWidth", String.valueOf(overmesureWidth));
            if (location != null)
            {
                hierarchicalStreamWriter.addAttribute("location", location.name());
            }
            hierarchicalStreamWriter.addAttribute("type", type.name());

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
        BoardElement element = new BoardElement();

        String width = hierarchicalStreamReader.getAttribute("width");
        String length = hierarchicalStreamReader.getAttribute("length");
        String overmesureLength = hierarchicalStreamReader.getAttribute("overmesureLength");
        String overmesureWidth = hierarchicalStreamReader.getAttribute("overmesureWidth");
        String location = hierarchicalStreamReader.getAttribute("location");
        String type = hierarchicalStreamReader.getAttribute("type");

        if (location != null)
        {
            element.setLocation(Enum.valueOf(BoardElement.Location.class, location));
        }
        element.setType(Enum.valueOf(BoardElement.Type.class, type));
        element.setWidth(Double.valueOf(width));
        element.setLength(Double.valueOf(length));
        element.setOvermeasureLength(Long.valueOf(overmesureLength));
        element.setOvermeasureWidth(Long.valueOf(overmesureWidth));

        while (hierarchicalStreamReader.hasMoreChildren())
        {
            hierarchicalStreamReader.moveDown();
            if (hierarchicalStreamReader.getNodeName().equals("boardDef"))
            {
                BoardDef boardDef = (BoardDef) unmarshallingContext.
                        convertAnother(element, BoardDef.class, new BoardDefXConverter());
                if (boardDef != null)
                {
                    element.setBoardDef(boardDef);
                }
            }
            else if (hierarchicalStreamReader.getNodeName().equals("texture"))
            {
                TextureEntity texture = (TextureEntity) unmarshallingContext.
                        convertAnother(element, TextureEntity.class, new TextureXConverter());
                if (texture != null)
                {
                    element.setTexture(texture);
                }
            }
            hierarchicalStreamReader.moveUp();
        }

        return element;
    }

    @Override
    public boolean canConvert(Class aClass)
    {
        return true;
    }
}
