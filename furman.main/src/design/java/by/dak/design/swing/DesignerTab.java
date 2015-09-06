package by.dak.design.swing;

import by.dak.cutting.swing.BaseTabPanel;
import by.dak.design.draw.FrontDesignerDrawing;
import by.dak.design.draw.components.CellFigure;
import by.dak.design.entity.DesignXmlSerializer;
import by.dak.persistence.entities.OrderItem;
import by.dak.swing.ActionsPanel;
import org.jdesktop.application.Application;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.beans.Beans;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 09.08.11
 * Time: 0:45
 * To change this template use File | Settings | File Templates.
 */
public class DesignerTab extends BaseTabPanel<OrderItem>
{
    private ActionsPanel<MainDesignerPanel> designerPanel;
    private ActionsPanel<CellPanel> cellPanel;

    private CellChangedListener cellChangedListener = new CellChangedListener();

    public DesignerTab()
    {
        if (!Beans.isDesignTime())
        {
            setLayout(new BorderLayout());
            init();
        }
    }

    public void updateComponent()
    {
//        if (designerPanel..getFrontDesignerPanel().getFrontDesignerDrawing() != null)
//        {
//            int count = designerPanel.getFrontDesignerPanel().getFrontDesignerDrawing().getTopFigure().getChildCount();
//            if (count < 1)
//            {
//                setVisiblePanel(cellPanel);
//            }
//        }
    }

    @Override
    protected void valueChanged()
    {
        OrderItem orderItem = getValue();
        if (orderItem.getDesign() != null)
        {
            DesignXmlSerializer designXmlSerializer = DesignXmlSerializer.getInstance();

            FrontDesignerDrawing frontDesignerDrawing = (FrontDesignerDrawing) designXmlSerializer.unserialize(orderItem.getDesign());
            designerPanel.getContentComponent().getFrontDesignerPanel().setDrawing(frontDesignerDrawing);

            setVisiblePanel(designerPanel);
        }
    }

    private void update()
    {
        revalidate();
        repaint();
    }

    private void setVisiblePanel(JPanel panel)
    {

        removeAll();
        add(panel, BorderLayout.CENTER);
        update();
    }

    private void initCellPanel()
    {

        CellPanel cellPanel = new CellPanel();
        cellPanel.init();
        this.cellPanel = new ActionsPanel<CellPanel>(cellPanel,
                Application.getInstance().getContext().getActionMap(cellPanel), "prepareCell");
        this.cellPanel.init();
    }

    private void initDesignerPanel()
    {
        MainDesignerPanel mainDesignerPanel = new MainDesignerPanel();
        designerPanel = new ActionsPanel<MainDesignerPanel>(mainDesignerPanel,
                Application.getInstance().getContext().getActionMap(mainDesignerPanel));
        designerPanel.init();
    }


    private void init()
    {

        initCellPanel();
        cellPanel.getContentComponent().setValue(new CellPanel.Cell());
        cellPanel.getContentComponent().addPropertyChangeListener("cell", cellChangedListener);

        initDesignerPanel();
        setVisiblePanel(designerPanel);
    }


    private class CellChangedListener implements PropertyChangeListener
    {
        @Override
        public void propertyChange(PropertyChangeEvent evt)
        {

            setVisiblePanel(designerPanel);


            CellPanel.Cell cell = (CellPanel.Cell) evt.getNewValue();
            CellFigure frontCellFigure = new CellFigure();
            frontCellFigure.setBounds(new Point2D.Double(0, 0),
                    new Point2D.Double(cell.getLength(), cell.getHeight()));
            frontCellFigure.setWidth(cell.getWidth());

            FrontDesignerDrawing frontDesignerDrawing = new FrontDesignerDrawing();
            frontDesignerDrawing.setTopFigure(frontCellFigure);
            designerPanel.getContentComponent().getFrontDesignerPanel().setDrawing(frontDesignerDrawing);
        }
    }


}
