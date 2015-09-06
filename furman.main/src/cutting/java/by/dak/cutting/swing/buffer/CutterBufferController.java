package by.dak.cutting.swing.buffer;

import by.dak.cutting.swing.*;
import com.jidesoft.swing.JideTabbedPane;
import org.jhotdraw.draw.event.FigureSelectionEvent;
import org.jhotdraw.draw.event.FigureSelectionListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 01.02.11
 * Time: 22:35
 * To change this template use File | Settings | File Templates.
 */
public class CutterBufferController
{
    private CutterPanel cutterPanel;

    private FigureClipboard figureClipboard = new FigureClipboard();

    /**
     * Listners
     */
    private TabChangedListener tabChangedListener = new TabChangedListener();
    private RemoveElementListener removeElementListener = new RemoveElementListener();
    private SelectionChangedListener selectionChangedListener = new SelectionChangedListener();
    private NextFigureSelectionListener figureSelectionListener = new NextFigureSelectionListener();

    //changing properties
    private FreeSegmentHandler freeSegmentHandler;
    private TopSegmentPanel topSegmentPanel;
    private ElementFigure elementFigure;


    public CutterBufferController(CutterPanel cutterPanel)
    {
        this.cutterPanel = cutterPanel;
        addListeners();
    }

    private void addListeners()
    {
        FigureListUpdater figureListUpdater = figureClipboard.getListUpdater();

        FigureListUpdater.UpdaterNEDActions actions = (FigureListUpdater.UpdaterNEDActions) figureListUpdater.getNewEditDeleteActions();
        actions.addPropertyChangeListener("selectedElement", selectionChangedListener);
        actions.addPropertyChangeListener("removedElement", removeElementListener);

        if (topSegmentPanel != null)
        {
            //надо проверять на null потому что слушатели добаляются в конструторе
            topSegmentPanel.getView().addFigureSelectionListener(figureSelectionListener);
        }
    }

    /**
     * проверка на оставшиеся элементы в буффере
     *
     * @return
     */
    public boolean hasBufferedElements()
    {
        return figureClipboard.getListUpdater().getCount() > 0;
    }

    private void removeListeners()
    {
        FigureListUpdater figureListUpdater = figureClipboard.getListUpdater();

        FigureListUpdater.UpdaterNEDActions actions = (FigureListUpdater.UpdaterNEDActions) figureListUpdater.getNewEditDeleteActions();
        actions.removePropertyChangeListener("selectedElement", selectionChangedListener);
        actions.removePropertyChangeListener("removedElement", removeElementListener);
        if (topSegmentPanel != null) // проверка на null для нажатия start потому что когда удаляются табы нет нет выбранного компонента(выбранный компонет = null)
        {
            topSegmentPanel.getView().removeFigureSelectionListener(figureSelectionListener);
        }
    }

    private void showFreeSegments()
    {
        if (freeSegmentHandler != null)
        {
            freeSegmentHandler.clearFreeSements();
        }

        if (topSegmentPanel != null && elementFigure != null)
        {
            CuttingDrawing cuttingDrawing = topSegmentPanel.getCuttingDrawing();
            ElementDragHandle elementDragHandle = new ElementDragHandle(elementFigure);
            elementDragHandle.setView(topSegmentPanel.getView());
            freeSegmentHandler = new FreeSegmentHandler(cuttingDrawing, elementDragHandle);
            freeSegmentHandler.prepareFreeSegments();
        }
    }

    public TabChangedListener getTabChangedListener()
    {
        return tabChangedListener;
    }


    public FigureClipboard getFigureClipboard()
    {
        return figureClipboard;
    }

    private class SelectionChangedListener implements PropertyChangeListener
    {
        @Override
        public void propertyChange(PropertyChangeEvent evt)
        {
            elementFigure = (ElementFigure) evt.getNewValue();
            showFreeSegments();
        }
    }

    private class TabChangedListener implements ChangeListener
    {
        @Override
        public void stateChanged(ChangeEvent e)
        {
            JideTabbedPane tabbedPane = (JideTabbedPane) e.getSource();
            topSegmentPanel = (TopSegmentPanel) tabbedPane.getSelectedComponent();
            removeListeners();
            showFreeSegments();
            addListeners();
        }
    }

    private class RemoveElementListener implements PropertyChangeListener
    {
        @Override
        public void propertyChange(PropertyChangeEvent evt)
        {
            final ElementFigure removedElementFigure = (ElementFigure) evt.getNewValue();
            if (freeSegmentHandler != null)
            {
                if (freeSegmentHandler.hasFreeSegments())
                {
                    //удалеем free елементы так как они сейчас будут строится при драге елемента

                    figureClipboard.getListUpdater().remove(removedElementFigure);
                    getFigureClipboard().getListUpdater().getNewEditDeleteActions().setSelectedElement(null);
                    removeListeners();
                    cutterPanel.getBufferNaviTable().getTable().clearSelection();
                    figureClipboard.importData(topSegmentPanel.getCuttingDrawing(), removedElementFigure, topSegmentPanel.getView());
                    Runnable runnable = new Runnable()
                    {
                        public void run()
                        {
                            topSegmentPanel.getView().clearSelection(); // сначала область должна очищаться
                            topSegmentPanel.getView().addToSelection(removedElementFigure);
                        }
                    };
                    SwingUtilities.invokeLater(runnable);
                    addListeners();
                }
            }

        }
    }

    /**
     * слушатель фигур. как только выбрана фигуры в раскрое, надо убирать selectedElement из буффера, чтобы почистить пустые сегменты.
     * решает проблемы пустых сегментов выбранной фигуры на раскрое и выбранного элемента в буффере
     */
    private class NextFigureSelectionListener implements FigureSelectionListener
    {
        @Override
        public void selectionChanged(FigureSelectionEvent evt)
        {
            getFigureClipboard().getListUpdater().getNewEditDeleteActions().setSelectedElement(null);
            removeListeners();
            cutterPanel.getBufferNaviTable().getTable().clearSelection();
            addListeners();
        }

    }
}
