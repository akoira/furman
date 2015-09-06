package by.dak.design.swing.action;

import by.dak.design.draw.components.BoardFigure;
import by.dak.design.swing.DElementPanel;
import net.infonode.docking.View;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.Figure;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 23.09.11
 * Time: 13:34
 * To change this template use File | Settings | File Templates.
 */
public class BoardViewSelectionHandler extends MouseAdapter
{
    private JPanel propertiesPanel;
    private View boardElementPropertiesView;
    private DrawingView drawingView;

    public DrawingView getDrawingView()
    {
        return drawingView;
    }

    public void setDrawingView(DrawingView drawingView)
    {
        this.drawingView = drawingView;
    }

    public View getBoardElementPropertiesView()
    {
        return boardElementPropertiesView;
    }

    public void setBoardElementPropertiesView(View boardElementPropertiesView)
    {
        this.boardElementPropertiesView = boardElementPropertiesView;
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        selectionChanged();

        if (e.getClickCount() > 1)
        {
            boardElementPropertiesView.restore();
        }
    }

    private void selectionChanged()
    {
        propertiesPanel = ((JPanel) boardElementPropertiesView.getComponent());
        propertiesPanel.removeAll();
        List<Figure> selectedFigures = new ArrayList<Figure>(drawingView.getSelectedFigures());
        if (selectedFigures.size() > 0 && selectedFigures.size() == 1)
        {
            if (selectedFigures.get(0) instanceof BoardFigure)
            {
                final BoardFigure boardFigure = (BoardFigure) selectedFigures.get(0);
                SwingUtilities.invokeLater(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        propertiesPanel.setLayout(new BorderLayout());
                        DElementPanel elementPanel = new DElementPanel();
                        elementPanel.init();
                        elementPanel.setValue(boardFigure.getBoardElement());
                        propertiesPanel.add(elementPanel);
                        updatePanel(propertiesPanel);
                    }
                });

            }
        }
        updatePanel(propertiesPanel);
    }

    private void updatePanel(JPanel panel)
    {
        panel.revalidate();
        panel.repaint();
    }
}
