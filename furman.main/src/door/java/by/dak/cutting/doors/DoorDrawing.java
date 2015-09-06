package by.dak.cutting.doors;

import by.dak.cutting.doors.mech.stringConverter.DoorMechType2StringConverter;
import by.dak.cutting.swing.order.cellcomponents.editors.milling.ArcEveryFigure;
import by.dak.cutting.swing.order.cellcomponents.editors.milling.AttributeKeys;
import by.dak.cutting.swing.order.cellcomponents.editors.milling.CurveFigure;
import by.dak.cutting.swing.order.cellcomponents.editors.milling.ElementDrawing;
import by.dak.persistence.entities.BoardDef;
import by.dak.persistence.entities.TextureEntity;
import org.jhotdraw.draw.DefaultDrawing;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.TextFigure;
import org.jhotdraw.geom.Dimension2DDouble;

import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 10.08.2009
 * Time: 10:04:34
 * To change this template use File | Settings | File Templates.
 */
public class DoorDrawing extends DefaultDrawing
{
    private double widthProfile = 30; // тестовая величина профиля

    private Point2D.Double start = new Point2D.Double(0, 0); //начало отрисовки елемента
    static final private float size = 2f;
    static final private double textIndent = 20;

    private double deltaX;
    private double deltaY;
    private double offset;
    //= Graphics2DUtils.OFFSET;
    private Rectangle2D.Double elementRec = new Rectangle2D.Double();
    private Door door;
    private CurveFigure lineWidth;
    private CurveFigure lineHeight;
    private boolean drawlineHeight;
    private TextFigure doorType;
    private TextFigure doorNumber;
    private int numberOfDoor;
    private ElementDrawing doorElements = null;
    private List<Cell> cells = new ArrayList<Cell>();
    private CellDrawing cellDrawing = new CellDrawing();
    private List<Figure> defProfiles;
    private List<Figure> lineForDefProfile;

    @Override
    public void draw(Graphics2D g, Collection<Figure> children)
    {
        drawFrameDoor(g);

        super.draw(g, this.children);
    }

    public void drawFrameDoor(Graphics2D g)
    {
        Color color = g.getColor();
        Stroke stroke = g.getStroke();

        g.setStroke(new BasicStroke(size));
//        g.setColor(Graphics2DUtils.DIMENTION_LINE_COLOR);
        g.draw(elementRec);

        g.setStroke(stroke);
        g.setColor(color);

        drawDoor(g);
    }

    private void drawDoor(Graphics2D g)
    {
        if (lineHeight != null)
        {
            lineHeight.draw(g);
        }
        if (lineWidth != null)
        {
            lineWidth.draw(g);
        }
        if (doorType != null)
        {
            doorType.draw(g);
        }
        if (doorNumber != null)
        {
            doorNumber.draw(g);
        }

        if (defProfiles != null)
        {
            for (Figure figure : defProfiles)
            {
                figure.draw(g);
            }
        }
    }


    private void addToElementDrawing()
    {
        doorElements = new ElementDrawing();
        doorElements.setElement(new Dimension2DDouble(door.getWidth(), door.getHeight()));
        doorElements.setCut(false);
        for (int i = 0; i < 4; i++)
        {
            Figure figure = doorElements.createDefaultLine(i);
            figure.set(AttributeKeys.MOVEABLE, false);
            doorElements.add(figure);
        }

        for (Figure figure : this.children)
        {
            if (!doorElements.getChildren().contains(figure))
            {
                if (lineHeight != null && figure == lineHeight)
                {
                    continue;
                }
                else if (lineWidth != null && figure == lineWidth)
                {
                    continue;
                }
                else if (doorType != null && figure == doorType)
                {
                    continue;
                }
                figure.set(AttributeKeys.MOVEABLE, false);
                doorElements.add(figure);
            }
        }

    }

    private void splitElementDrawing()
    {
        List<Figure> deleteF = new ArrayList<Figure>();
        List<Figure> addF = new ArrayList<Figure>();

        for (int i = 0; i < doorElements.getChildren().size() - 1; i++)
        {
            Figure figure1 = doorElements.getChildren().get(i);
            for (int j = i + 1; j < doorElements.getChildren().size(); j++)
            {
                Figure figure2 = doorElements.getChildren().get(j);
                if (figure2 != figure1 && !deleteF.contains(figure1) && !deleteF.contains(figure2))
                {
                    Point2D.Double point = SplitUtil.isIntersect(figure1, figure2);
                    if (point != null)
                    {
                        List<Figure> figures = SplitUtil.split(figure1, point);
                        if (!figures.isEmpty())
                        {
                            for (Figure figure : figures)
                            {
                                figure.set(AttributeKeys.MOVEABLE, false);
                            }
                            addF.addAll(figures);
                            deleteF.add(figure1);
                        }
                        figures = SplitUtil.split(figure2, point);
                        if (!figures.isEmpty())
                        {
                            for (Figure figure : figures)
                            {
                                figure.set(AttributeKeys.MOVEABLE, false);
                            }
                            addF.addAll(figures);
                            deleteF.add(figure2);
                        }
                    }
                }
            }
        }
        doorElements.removeAll(deleteF);
        doorElements.addAll(addF);
        if (!addF.isEmpty())
        {
            splitElementDrawing();
        }
    }

    public void setStart(Point2D.Double start)
    {
        Dimension2D d = new Dimension2DDouble(door.getWidth(), door.getHeight());
        this.start = start;
        elementRec.setFrame(start.getX(), start.getY(), door.getWidth(), door.getHeight());

        createText();
        reCreateChildren();
        createDimension();
        createProfile();

        firePropertyChange("element", d, door.getHeight());
        deltaX = this.start.x;
        deltaY = this.start.y;
    }

    public Rectangle2D.Double getDrawingArea()
    {
        Rectangle2D.Double d = super.getDrawingArea();
        d.add(elementRec);
        if (lineHeight != null)
        {
            d.add(lineHeight.getDrawingArea());
        }
        if (lineWidth != null)
        {
            d.add(lineWidth.getDrawingArea());
        }

        return d;
    }

    public double getOffset()
    {
        return offset;
    }

    public void setOffset(double offset)
    {
        this.offset = offset;
    }

    public void setDrawlineHeight(boolean drawlineHeight)
    {
        this.drawlineHeight = drawlineHeight;
    }

    public Door getDoor()
    {
        return door;
    }

    public void setDoor(Door door)
    {
        this.door = door;
        setStart(start);
    }

    private void createDimension()
    {
        if (lineWidth == null)
        {
            lineWidth = new CurveFigure(false);
        }
        if (lineHeight == null)
        {
            lineHeight = new CurveFigure(false);
            lineHeight.setDirection(1);
        }

        if (!drawlineHeight)
        {
            lineHeight = null;
        }
        else
        {
            lineHeight.willChange();
            lineHeight.setStartPoint(start);
            lineHeight.setEndPoint(new Point2D.Double(start.getX(), start.getY() + door.getHeight()));
            lineHeight.changed();
        }

        lineWidth.willChange();
        lineWidth.setStartPoint(start);
        lineWidth.setEndPoint(new Point2D.Double(start.getX() + door.getWidth(), start.getY()));
        lineWidth.changed();
    }

    private void createProfile()
    {
//        if (defProfiles == null)
//        {
//            defProfiles = new ArrayList<Figure>();
//        }
//        HProfile hProfile1;
//        HProfile hProfile2;
//        VProfile vProfile1;
//        VProfile vProfile2;
//
//        if (defProfiles.isEmpty())
//        {
//            hProfile1 = new HProfile(widthProfile);
//            hProfile2 = new HProfile(widthProfile);
//            vProfile1 = new VProfile(widthProfile);
//            vProfile2 = new VProfile(widthProfile);
//        }
//        else
//        {
//            hProfile1 = (HProfile) defProfiles.get(0);
//            hProfile2 = (HProfile) defProfiles.get(1);
//            vProfile1 = (VProfile) defProfiles.get(2);
//            vProfile2 = (VProfile) defProfiles.get(3);
//        }
//        Point2D.Double anchor = new Point2D.Double(start.x + widthProfile, start.y);
//        Point2D.Double lead = new Point2D.Double(start.x + elementRec.getWidth() - widthProfile, start.y);
//        hProfile1.setBounds(anchor, lead);
//
//        anchor = new Point2D.Double(start.x + widthProfile, start.y + elementRec.getHeight() - widthProfile);
//        lead = new Point2D.Double(start.x + elementRec.getWidth() - widthProfile, start.y + elementRec.getHeight() - widthProfile);
//        hProfile2.setBounds(anchor, lead);
//
//        anchor = new Point2D.Double(start.x, start.y);
//        lead = new Point2D.Double(start.x, start.y + elementRec.getHeight());
//        vProfile1.setBounds(anchor, lead);
//
//        anchor = new Point2D.Double(start.x + elementRec.getWidth() - widthProfile, start.y);
//        lead = new Point2D.Double(start.x + elementRec.getWidth() - widthProfile, start.y + elementRec.getHeight());
//        vProfile2.setBounds(anchor, lead);
//
//        if (defProfiles.isEmpty())
//        {
//            defProfiles.add(hProfile1);
//            defProfiles.add(hProfile2);
//            defProfiles.add(vProfile1);
//            defProfiles.add(vProfile2);
//        }
//        if (lineForDefProfile == null)
//        {
//            lineForDefProfile = new ArrayList<Figure>();
//        }
//
//        CurveFigure line0 = new CurveFigure();
//        CurveFigure line1 = new CurveFigure();
//        CurveFigure line2 = new CurveFigure();
//        CurveFigure line3 = new CurveFigure();
//
//        if (lineForDefProfile.isEmpty())
//        {
//            line0 = new CurveFigure();
//            line1 = new CurveFigure();
//            line2 = new CurveFigure();
//            line3 = new CurveFigure();
//        }
//        else
//        {
//            line0 = (CurveFigure) lineForDefProfile.get(0);
//            line1 = (CurveFigure) lineForDefProfile.get(1);
//            line2 = (CurveFigure) lineForDefProfile.get(2);
//            line3 = (CurveFigure) lineForDefProfile.get(3);
//        }
//
//        line0.setStartPoint(new Point2D.Double(hProfile1.getRec().getMinX(), hProfile1.getRec().getMaxY()));
//        line0.setEndPoint(new Point2D.Double(hProfile1.getRec().getMaxX(), hProfile1.getRec().getMaxY()));
//        line0.setDirection(1);
//
//        line2.setStartPoint(new Point2D.Double(hProfile2.getRec().getMinX(), hProfile2.getRec().getMinY()));
//        line2.setEndPoint(new Point2D.Double(hProfile2.getRec().getMaxX(), hProfile2.getRec().getMinY()));
//        line2.setDirection(-1);
//
//        line1.setStartPoint(new Point2D.Double(vProfile1.getRec().getMaxX(), vProfile1.getRec().getMinY() + vProfile1.getProfileWidth()));
//        line1.setEndPoint(new Point2D.Double(vProfile1.getRec().getMaxX(), vProfile1.getRec().getMaxY() - vProfile1.getProfileWidth()));
//        line1.setDirection(-1);
//
//        line3.setStartPoint(new Point2D.Double(vProfile2.getRec().getMinX(), vProfile2.getRec().getMinY() + vProfile1.getProfileWidth()));
//        line3.setEndPoint(new Point2D.Double(vProfile2.getRec().getMinX(), vProfile2.getRec().getMaxY() - vProfile1.getProfileWidth()));
//        line3.setDirection(1);
//
//        if (lineForDefProfile.isEmpty())
//        {
//            lineForDefProfile.add(line0);
//            lineForDefProfile.add(line1);
//            lineForDefProfile.add(line2);
//            lineForDefProfile.add(line3);
//        }
    }

    private void createText()
    {
        if (doorType == null)
        {
            doorType = new TextFigure();
        }
        doorType.setBounds(new Point2D.Double(textIndent + start.getX(), textIndent + start.getY()), new Point2D.Double());
//            add(doorType);

        DoorMechType2StringConverter stringConverter = new DoorMechType2StringConverter();

//        doorType.setText(stringConverter.convert(door.getDoorMechType()));

        if (doorNumber == null)
        {
            doorNumber = new TextFigure();
        }
        doorNumber.setBounds(new Point2D.Double(textIndent + start.getX(), textIndent + start.getY() + elementRec.getHeight()), new Point2D.Double());
//            add(doorNumber);

        doorNumber.setText(String.valueOf(numberOfDoor));
    }

    private void reCreateChildren()
    {
        List<Figure> figures = new ArrayList<Figure>();
        figures.addAll(getChildren());
        figures.add(doorType);
        figures.add(doorNumber);
        for (Figure figure : figures)
        {
            figure.willChange();
            if (figure instanceof ArcEveryFigure)
            {
                Arc2D.Double arc2D = (Arc2D.Double) ((ArcEveryFigure) figure).getArc().clone();

                double x = arc2D.getStartPoint().getX() + start.getX() - deltaX;
                double y = arc2D.getStartPoint().getY() + start.getY() - deltaY;

                double xc = x - Math.cos(Math.toRadians(arc2D.getAngleStart())) * arc2D.getHeight() / 2;
                double yc = y + Math.sin(Math.toRadians(arc2D.getAngleStart())) * arc2D.getHeight() / 2;

                arc2D.setFrame(xc - arc2D.getHeight() / 2, yc - arc2D.getHeight() / 2, arc2D.getHeight(), arc2D.getHeight());

                ((ArcEveryFigure) figure).setArc(arc2D);
            }
            else
            {
                figure.setBounds(new Point2D.Double(start.getX() + figure.getStartPoint().getX() - deltaX,
                        start.getY() + figure.getStartPoint().getY() - deltaY),
                        new Point2D.Double(start.getX() + figure.getEndPoint().getX() - deltaX,
                                start.getY() + figure.getEndPoint().getY() - deltaY));
            }
            figure.changed();
        }
    }

    public ElementDrawing getDoorElements()
    {
        addToElementDrawing();
        splitElementDrawing();

        doorElements.addAll(defProfiles);

        return doorElements;
    }

    public List<Cell> getCells()
    {
        return cells;
    }

    public void createCell(List<Figure> figures, BoardDef boardDef, TextureEntity textureEntity, GeneralPath path)
    {
        if (cells.isEmpty())
        {
            createNewCell(figures, boardDef, textureEntity, path);
        }
        for (Cell cell : cells)
        {
            if (cell.getCell().getChildren().containsAll(figures)
                    && cell.getCell().getChildren().size() == figures.size())
            {
                cell.setBoardDefEntity(boardDef);
                cell.setTextureEntity(textureEntity);
                cell.setPath(path);
                return;
            }
        }
        createNewCell(figures, boardDef, textureEntity, path);
    }

    private void createNewCell(List<Figure> figures, BoardDef boardDef, TextureEntity textureEntity, GeneralPath path)
    {
        Cell cell = new Cell();
        ElementDrawing drawing = new ElementDrawing();
        drawing.setCut(false);
        drawing.setElement(createBound(figures));
        drawing.addAll(figures);
        cell.setCell(drawing);
        cell.setBoardDefEntity(boardDef);
        cell.setTextureEntity(textureEntity);
        cell.setPath(path);
        cells.add(cell);
    }

    private Dimension2DDouble createBound(List<Figure> figures)
    {
        Point2D.Double min = new Point2D.Double(0, 0);
        Point2D.Double max = new Point2D.Double(0, 0);
        for (Figure figure : figures)
        {
            if (Math.min(figure.getStartPoint().getX(), figure.getEndPoint().getX()) < min.getX())
            {
                min.setLocation(Math.min(figure.getStartPoint().getX(), figure.getEndPoint().getX()), min.getY());
            }
            if (Math.min(figure.getStartPoint().getY(), figure.getEndPoint().getY()) < min.getY())
            {
                min.setLocation(min.getX(), Math.min(figure.getStartPoint().getY(), figure.getEndPoint().getY()));
            }

            if (Math.max(figure.getStartPoint().getX(), figure.getEndPoint().getX()) > max.getX())
            {
                max.setLocation(Math.max(figure.getStartPoint().getX(), figure.getEndPoint().getX()), max.getY());
            }
            if (Math.max(figure.getStartPoint().getY(), figure.getEndPoint().getY()) > max.getY())
            {
                max.setLocation(max.getX(), Math.max(figure.getStartPoint().getY(), figure.getEndPoint().getY()));
            }
        }
        return new Dimension2DDouble(max.getX() - min.getX(), max.getY() - min.getY());
    }

    public int getNumberOfDoor()
    {
        return numberOfDoor;
    }

    public void setNumberOfDoor(int numberOfDoor)
    {
        this.numberOfDoor = numberOfDoor;
    }

    public List<Figure> getDoorElementsChildren()
    {
        if (doorElements != null)
        {
            return doorElements.getChildren();
        }
        else
        {
            return null;
        }
    }

    public CellDrawing getCellDrawing()
    {
        getDoorElements();

        List<Figure> list = new ArrayList<Figure>();
        for (Figure figure : doorElements.getChildren())
        {
//            if (figure instanceof HProfileJoin && !(figure instanceof HProfile))
//            {
//                list.addAll(((HProfileJoin) figure).getLines());
//                list.addAll(((HProfileJoin) figure).getLinesButt());
//                ((AbstractAttributedFigure) figure).setSelectable(false);
//            }
//            else if (figure instanceof VProfileJoin && !(figure instanceof VProfile))
//            {
//                list.addAll(((VProfileJoin) figure).getLines());
//                list.addAll(((VProfileJoin) figure).getLinesButt());
//                ((AbstractAttributedFigure) figure).setSelectable(false);
//            }
        }
        doorElements.addAll(list);
        doorElements.addAll(lineForDefProfile);
        splitElementDrawing();
        deleteButt();

        for (Figure figure : doorElements.getChildren())
        {
            figure.set(AttributeKeys.MOVEABLE, false);
        }
        cellDrawing.setElementDrawing(doorElements);
        cellDrawing.setCells(cells);
        return cellDrawing;
    }

    private void deleteButt()
    {
        List<Figure> delete = new ArrayList<Figure>();

        for (int i = 0; i < doorElements.getChildren().size() - 1; i++)
        {
            Figure figure = doorElements.getChildren().get(i);
            for (int j = i + 1; j < doorElements.getChildren().size(); j++)
            {
                Figure figure1 = doorElements.getChildren().get(j);
                if (figure instanceof CurveFigure && figure1 instanceof CurveFigure
                        && (figure == figure1 || figure.equals(figure1)))
                {
                    delete.add(figure1);
                }
            }
        }
        doorElements.removeAll(delete);

        List<Figure> list = new ArrayList<Figure>();
        for (Figure figure : doorElements.getChildren())
        {
//            if (figure instanceof HProfileJoin && !(figure instanceof HProfile))
//            {
//                list.addAll(((HProfileJoin) figure).getLinesButt());
//                ((AbstractAttributedFigure) figure).setSelectable(false);
//            }
//            else if (figure instanceof VProfileJoin && !(figure instanceof VProfile))
//            {
//                list.addAll(((VProfileJoin) figure).getLinesButt());
//                ((AbstractAttributedFigure) figure).setSelectable(false);
//            }
        }

        delete = new ArrayList<Figure>();
        for (Figure figure : doorElements.getChildren())
        {
            if (figure instanceof CurveFigure)
            {
                for (Figure figure1 : list)
                {
                    if (figure.equals(figure1))
                    {
                        delete.add(figure);
                    }
                }
            }
        }
        doorElements.removeAll(delete);
    }

    @Override
    public List<Figure> getChildren()
    {
        return this.children;
    }

    @Override
    public void removeAllChildren()
    {
        this.children.clear();
    }

    public void setChildren(List<Figure> children)
    {
        removeAllChildren();
        this.children.addAll(children);
        return;
    }

    public Rectangle2D.Double getElementRec()
    {
        return elementRec;
    }

    public Point2D.Double getStart()
    {
        return start;
    }

    public void setLineWidth(CurveFigure lineWidth)
    {
        this.lineWidth = lineWidth;
    }

    public void setLineHeight(CurveFigure lineHeight)
    {
        this.lineHeight = lineHeight;
    }

    public void setDoorType(TextFigure doorType)
    {
        this.doorType = doorType;
    }

    public void setDoorNumber(TextFigure doorNumber)
    {
        this.doorNumber = doorNumber;
    }

    public CurveFigure getLineWidth()
    {
        return lineWidth;
    }

    public CurveFigure getLineHeight()
    {
        return lineHeight;
    }

    public boolean isDrawlineHeight()
    {
        return drawlineHeight;
    }

    public TextFigure getDoorType()
    {
        return doorType;
    }

    public TextFigure getDoorNumber()
    {
        return doorNumber;
    }
}
