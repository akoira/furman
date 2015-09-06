package by.dak.cutting.doors;

import by.dak.cutting.swing.order.cellcomponents.editors.milling.ElementDrawing;
import by.dak.persistence.entities.BoardDef;
import by.dak.persistence.entities.TextureEntity;

import java.awt.*;
import java.awt.geom.GeneralPath;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 14.08.2009
 * Time: 11:43:45
 * To change this template use File | Settings | File Templates.
 */
public class Cell
{
    private GeneralPath path;
    private ElementDrawing cell;
    private BoardDef boardDef;
    private TextureEntity textureEntity;
    private Color color;

    public ElementDrawing getCell()
    {
        return cell;
    }

    public void setCell(ElementDrawing cell)
    {
        this.cell = cell;
    }

    public BoardDef getBoardDefEntity()
    {
        return boardDef;
    }

    public void setBoardDefEntity(BoardDef boardDef)
    {
        this.boardDef = boardDef;
    }

    public TextureEntity getTextureEntity()
    {
        return textureEntity;
    }

    public void setTextureEntity(TextureEntity textureEntity)
    {
        this.textureEntity = textureEntity;
    }

    public GeneralPath getPath()
    {
        return path;
    }

    public void setPath(GeneralPath path)
    {
        this.path = path;
    }

    public Color getColor()
    {
        return color;
    }

    public void setColor(Color color)
    {
        this.color = color;
    }
}
