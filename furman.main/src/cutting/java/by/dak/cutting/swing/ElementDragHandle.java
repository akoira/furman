package by.dak.cutting.swing;

import by.dak.cutting.cut.base.Element;
import by.dak.cutting.cut.guillotine.Segment;
import org.jhotdraw.draw.DrawingView;

import java.util.List;

/**
 * User: akoyro
 * Date: 16.12.2010
 * Time: 22:49:17
 */
public class ElementDragHandle
{
    /**
     * сегмент в котором лежит фигура до претаскивания
     */
    private SegmentFigure elementSegmentFigure;
    private DrawingView view;
    private FigureClipboard figureClipboard;


    private ElementFigure elementFigure;

    private FreeSegmentHandler freeSegmentHandler;

    private SegmentFigure freeElementSegmentFigure;


    public ElementDragHandle(ElementFigure elementFigure)
    {
        this.elementFigure = elementFigure;
    }

    ElementFigure getElementFigure()
    {
        return elementFigure;
    }

    private FreeSegmentHandler getFreeSegmentHandler()
    {
        if (freeSegmentHandler == null)
        {
            freeSegmentHandler = new FreeSegmentHandler(getCuttingDrawing(), this);
        }
        return freeSegmentHandler;
    }

    public void trackStart()
    {
        if (elementFigure.getParent() != null)
        {
            elementSegmentFigure = elementFigure.getParent();
            elementSegmentFigure.remove(elementFigure);
            getView().getDrawing().add(elementFigure);
			removeElementSegment();
		}
    }

    public void trackEnd(boolean snapBack)
    {
        if (freeElementSegmentFigure != null)
        {
            freeSegmentHandler.removeFreeSegment(freeElementSegmentFigure.getSegment());
        }

        //должны быть очищены в начале чтобы не мешали остальным
        freeSegmentHandler.clearFreeSements();


        if (freeElementSegmentFigure != null)
        {
            removeElementSegment();
            getView().getDrawing().remove(elementFigure);
            freeElementSegmentFigure.getSegment().setElement(elementFigure.getElement(), elementFigure.isRotated());
            reloadSegment();
            return;
        }


        //опреция проводится первый раз когда вытаскиваем елемент
        if (elementSegmentFigure != null)
        {
			if (!snapBack) {
                removeElementSegment();
                insertIntoClipboard(elementFigure);
            }
        }

        elementSegmentFigure = null;
    }

    private void insertIntoClipboard(ElementFigure elementFigure)
    {
        if (figureClipboard != null)
        {
            figureClipboard.exportData(elementFigure, (CuttingDrawing) getView().getDrawing());
        }
    }

    public FigureClipboard getFigureClipboard()
    {
        return figureClipboard;
    }

    public void setFigureClipboard(FigureClipboard figureClipboard)
    {
        this.figureClipboard = figureClipboard;
    }

    private void removeElementSegment()
    {
        if (elementSegmentFigure != null)
        {
            //удаляем сегмент
            elementSegmentFigure.getSegment().setElement(null);
            removeSegment(elementSegmentFigure);
            //перегружаем сегмент
            reloadSegment();
        }
    }


    private CuttingDrawing getCuttingDrawing()
    {
        return (CuttingDrawing) getView().getDrawing();
    }

    public void trackStep()
    {
        getFreeSegmentHandler().prepareFreeSegments();

        freeElementSegmentFigure = getFreeSegmentHandler().getFreeSegmentFigure(elementFigure);
    }


    public void setView(DrawingView view)
    {
        this.view = view;
    }

    public DrawingView getView()
    {
        return view;
    }


    private boolean canAddToSegment(Segment segment, Element element, boolean rotated)
    {
        int padding = segment.getItems().size() > 0 ? segment.getPadding() : 0;
        if (segment.getLevel() % 2 == 0)
        {
            int width = (rotated ? element.getWidth() : element.getHeight());
            int lenght = (rotated ? (element.getHeight() + padding) : (element.getWidth() + padding));
            return segment.getWidth() >= width
                    && segment.getFreeLength() >= lenght;
        }
        else
        {
            int width = (rotated ? element.getHeight() : element.getWidth());
            int lenght = (rotated ? (element.getWidth() + padding) : (element.getHeight() + padding));
            return segment.getFreeLength() >= lenght
                    && segment.getWidth() >= width;
        }
    }


    /**
     * Поиск сегментов чтобы добавить в них free сегменты
     *
     * @param segment
     * @param element
     * @param segmentsForAdd
     */
    public void fillSegmentsToAdd(Segment segment, Element element, boolean rotated, List<Segment> segmentsForAdd)
    {
        //разрешаем только если element вытянут или добовляется не в собственный контейнер
        if (elementSegmentFigure == null || segment != elementSegmentFigure.getSegment().getParent())
        {
            if (canAddToSegment(segment, element, rotated))
            {
                segmentsForAdd.add(segment);
            }

            List<Segment> segments = segment.getItems();
            for (Segment segment1 : segments)
            {
                fillSegmentsToAdd(segment1, element, rotated, segmentsForAdd);
            }
        }
    }

    void reloadSegment()
    {
        Segment segment = getCuttingDrawing().getSegment();
        getCuttingDrawing().setSegment(null);
        getCuttingDrawing().setSegment(segment);
    }

    void removeSegment(SegmentFigure child)
    {
        SegmentFigure parent = child.getParent();
        if (parent != null)
        {
            parent.willChange();
            parent.remove(child);
            parent.changed();
            // удаляем до тех пор пока нет чайлдов
            if (parent.getChildCount() == 0)
            {
                removeSegment(parent);
            }

            parent.getSegment().removeSegment(child.getSegment());
        }
    }


}
