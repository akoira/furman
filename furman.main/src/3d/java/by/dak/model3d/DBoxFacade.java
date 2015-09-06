package by.dak.model3d;

import by.dak.design.draw.FrontDesignerDrawing;
import by.dak.design.draw.components.CellFigure;
import by.dak.view3d.FalseFactory;

import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.geom.Point2D;

/**
 * User: akoyro
 * Date: 12.09.11
 * Time: 12:02
 */
public class DBoxFacade
{
    private FalseFactory falseFactory = new FalseFactory();

    private DBox root = new DBox();

    {
        root.setLength(3000f);
        root.setHeight(2500f);
        root.setWidth(650f);
    }

    public DBox getRoot()
    {
        return root;
    }


    private void divideBox(DBox parent, DBox divider)
    {
        parent.getBoxDivideHandler().setDivider(divider);
        divider.setParent(parent);
        parent.getBoxDivideHandler().divide();
    }


    private void addFalses(DBox parent)
    {
        DBox falseBox = falseFactory.getFalseBy(DBoxLocation.LEFT, parent);
        divideBox(parent, falseBox);

        parent = parent.getBoxDivideHandler().getRightBox();
        falseBox = falseFactory.getFalseBy(DBoxLocation.RIGHT, parent);
        divideBox(parent, falseBox);

        parent = parent.getBoxDivideHandler().getLeftBox();
        falseBox = falseFactory.getFalseBy(DBoxLocation.BOTTOM, parent);
        divideBox(parent, falseBox);

        parent = parent.getBoxDivideHandler().getTopBox();
        falseBox = falseFactory.getFalseBy(DBoxLocation.TOP, parent);
        divideBox(parent, falseBox);
    }

    public DefaultMutableTreeNode getRootTreeNode()
    {
        return getTreeNode(getRoot());
    }

    public DefaultMutableTreeNode getTreeNode(DBox box)
    {
        DefaultMutableTreeNode result = new DefaultMutableTreeNode();
        result.setUserObject(box);
        if (box.getBoxDivideHandler().getDivider() != null)
        {
            result.add(new DefaultMutableTreeNode(box.getBoxDivideHandler().getDivider()));
            DBox[] boxes = box.getBoxDivideHandler().getDividedBoxes();
            for (DBox cBox : boxes)
            {
                result.add(getTreeNode(cBox));
            }
        }
        return result;
    }


    public CellFigure getFrontCellFigureBy(DBox box)
    {
        CellFigure cellFigure = new CellFigure();
        cellFigure.setBounds(new Point2D.Double(box.getX(), box.getY()),
                new Point2D.Double(box.getLength(), box.getHeight()));
        cellFigure.setWidth(box.getWidth());
        cellFigure.setBox(box);
        return cellFigure;
    }

    public CellFigure getTopCellFigureBy(DBox box)
    {
        CellFigure cellFigure = new CellFigure();
        cellFigure.setBounds(new Point2D.Double(box.getX(), box.getZ()),
                new Point2D.Double(box.getLength(), box.getWidth()));
        cellFigure.setBox(box);
        return cellFigure;
    }


    public FrontDesignerDrawing getFrontDesignerDrawing()
    {
        CellFigure cellFigure = getFrontCellFigureBy(getRoot());
        FrontDesignerDrawing frontDesignerDrawing = new FrontDesignerDrawing();
        frontDesignerDrawing.setTopFigure(cellFigure);
        return frontDesignerDrawing;
    }


}
