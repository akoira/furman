package by.dak.cutting.swing.action;

import by.dak.cutting.cut.base.Dimension;
import by.dak.cutting.cut.guillotine.Segment;
import by.dak.cutting.swing.AListTab;
import by.dak.cutting.swing.CutterPanel;
import by.dak.cutting.swing.MessageDialog;
import by.dak.cutting.swing.TopSegmentPanel;
import by.dak.cutting.swing.cut.CuttingModel;
import by.dak.cutting.swing.order.data.TextureBoardDefPair;
import by.dak.cutting.swing.store.helpers.BoardListUpdater;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.cutting.entities.ABoardStripsEntity;
import by.dak.persistence.entities.Board;
import by.dak.persistence.entities.StorageElementLink;
import by.dak.swing.WindowCallback;
import by.dak.swing.table.AListUpdater;
import by.dak.swing.table.NewEditDeleteActions;
import org.jdesktop.application.Application;

import java.util.List;

/**
 * User: akoyro
 * Date: 25.01.2011
 * Time: 16:32:24
 */
public class RestTab extends AListTab
{
    private TopSegmentPanel topSegmentPanel;
    private Segment graySegment;
    private StorageElementLink storageElementLink;
    private WindowCallback windowCallback;

    public RestTab(TopSegmentPanel topSegmentPanel, WindowCallback windowCallback)
    {
        this.topSegmentPanel = topSegmentPanel;
        this.windowCallback = windowCallback;
    }

    @Override
    public void init()
    {
        graySegment = topSegmentPanel.getCuttingDrawing().getSegment();
        storageElementLink = FacadeContext.getBoardFacade().getStorageElementLinkBy(graySegment);

        TextureBoardDefPair pair = ((Board) storageElementLink.getStoreElement()).getPair();
        List<Board> boards = FacadeContext.getBoardFacade().findMinFreeRestBoardsBy(new Dimension(0, 0), pair, Integer.MAX_VALUE);


        Board defaultBoard = FacadeContext.getBoardFacade().createDefaultBoardBy(pair);
        FacadeContext.getBoardFacade().save(defaultBoard);
        boards.add(defaultBoard);
        AListUpdater<Board> updater = new Updater(boards);
        getListNaviTable().setListUpdater(updater);
        super.init();
    }

    @Override
    protected void initBindingListeners()
    {
    }

    private class Updater extends AListUpdater<Board>
    {
        private Actions actions = new Actions();

        private Updater(List<Board> boards)
        {
            setResourceMap(Application.getInstance().getContext().getResourceMap(BoardListUpdater.class));
            getList().addAll(boards);
        }

        @Override
        public int getCount()
        {
            return getList().size();
        }

        @Override
        public void update()
        {
        }

        @Override
        public void adjustFilter()
        {
        }

        @Override
        public String[] getVisibleProperties()
        {
            return new String[]{Board.PROPERTY_length, Board.PROPERTY_width};
        }

        @Override
        public NewEditDeleteActions getNewEditDeleteActions()
        {
            return actions;
        }
    }


    private class Actions extends NewEditDeleteActions<Board>
    {
        @Override
        public void newValue()
        {
        }

        @Override
        public void openValue()
        {
            if (getSelectedElement() != null)
            {
                Board board = (Board) storageElementLink.getStoreElement();
                Segment segment = FacadeContext.getBoardFacade().createGraySegmentBy((ABoardStripsEntity) storageElementLink.getStripsEntity(), getSelectedElement());
                segment.setPadding(board.getBoardDef().getCutter().getSawcutWidth().intValue());
                CutterPanel cutterPanel = topSegmentPanel.getParentCutterPanel();

                cutterPanel.getStrips().addSegment(segment);
                cutterPanel.getCuttingModel().putStrips(board.getPair(), cutterPanel.getStrips());
                CuttingModel cuttingModel = cutterPanel.getCuttingModel();
                cutterPanel.setCuttingModel(null);
                cutterPanel.setCuttingModel(cuttingModel);
                windowCallback.dispose();
            }
            else
            {
                MessageDialog.showSimpleMessage(MessageDialog.NO_ROW_SELECTED);
            }
        }

        @Override
        public void deleteValue()
        {
        }

    }
}
