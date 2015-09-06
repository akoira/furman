package by.dak.cutting.facade.impl;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.configuration.Constants;
import by.dak.cutting.cut.base.Dimension;
import by.dak.cutting.cut.base.RestBoardDimension;
import by.dak.cutting.cut.facade.StripsFacade;
import by.dak.cutting.cut.gui.cuttingApp.DimensionItem;
import by.dak.cutting.cut.guillotine.Segment;
import by.dak.cutting.cut.guillotine.Strips;
import by.dak.cutting.cut.guillotine.helper.BoardDimensionsHelper;
import by.dak.cutting.cut.guillotine.helper.DimensionsHelper;
import by.dak.cutting.facade.BoardFacade;
import by.dak.cutting.facade.CutterFacade;
import by.dak.cutting.facade.PriceFacade;
import by.dak.cutting.facade.StorageElementLinkFacade;
import by.dak.cutting.facade.impl.helper.BoardOrderStatusRefactorer;
import by.dak.cutting.swing.cut.CuttingModel;
import by.dak.cutting.swing.cut.SheetDimentionItem;
import by.dak.cutting.swing.order.data.TextureBoardDefPair;
import by.dak.persistence.cutting.entities.ABoardStripsEntity;
import by.dak.persistence.dao.BoardDao;
import by.dak.persistence.entities.*;
import by.dak.persistence.entities.predefined.StoreElementStatus;
import by.dak.report.jasper.ReportUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Vitaly Kozlovski
 * @version 0.1 24.01.2009
 * @introduced [Furniture constructor | Iteration 1]
 * @since 1.0.0
 */
public class BoardFacadeImpl extends AStoreElementFacadeImpl<Board> implements BoardFacade
{

	@Autowired
	private StorageElementLinkFacade storageElementLinkFacade;

	@Autowired
	private StripsFacade stripsFacade;

	@Autowired
	private CutterFacade cutterFacade;

	@Autowired
	private PriceFacade priceFacade;

	/**
	 * Добавляет остатки на склад после распила
	 *
	 * @param order
	 */
    public void putRestsToStore(Order order)
    {

        if (!Constants.IS_PUT_REST_TO_STORE)
        {
            return;
        }

		CuttingModel cuttingModel = stripsFacade.loadCuttingModel(order).load();


		List<TextureBoardDefPair> pairs = cuttingModel.getPairs();
        for (TextureBoardDefPair pair : pairs)
        {
            Strips strips = cuttingModel.getStrips(pair);

            DimensionsHelper dimensionsHelper = new BoardDimensionsHelper();
            List<RestBoardDimension> dimensions = dimensionsHelper.getRestDimensions(strips);
            for (RestBoardDimension dimension : dimensions)
            {
                if (ReportUtils.isUsefulDimension(dimension))
                {
                    Board board = new Board();
                    board.setAmount(1);
                    board.setLength(Long.valueOf(dimension.getWidth()));
                    board.setWidth(Long.valueOf(dimension.getHeight()));
                    board.setPair(pair);
                    board.setCreatedByOrder(order);
                    board.setDelivery(dimension.getBoard().getDelivery());
                    board.setProvider(dimension.getBoard().getProvider());
                    board.setPrice(dimension.getBoard().getPrice());
                    board.setStatus(StoreElementStatus.exist);
                    save(board);
                }
            }
        }
    }

    /**
     * Поиск подходящего остатка для распила
     *
     * @param dimension
     * @param pair
     * @return
     */
    public List<Board> findMinFreeRestBoardsBy(Dimension dimension, TextureBoardDefPair pair, int maxCount)
    {
		List<Board> boards = ((BoardDao) getDao()).findMinFreeRestBoardsBy(cutterFacade.untrim(dimension, pair.getBoardDef().getCutter()),
				pair, maxCount);
		return boards;
    }

    @Override
    public Board getMinFreeRestBoardBy(Dimension dimension, TextureBoardDefPair pair)
    {
        List<Board> boards = findMinFreeRestBoardsBy(dimension, pair, 1);
        return boards.size() > 0 ? boards.get(0) : null;
    }

    @Override
    public List<Board> loadAll(SearchFilter filter)
    {
        filter.addAscOrder(Board.PROPERTY_priceAware);
        filter.addAscOrder(Board.PROPERTY_priced);
        filter.addDescOrder(Board.PROPERTY_id);
        filter.addAscOrder(Board.PROPERTY_length);

        filter.gt(Board.PROPERTY_amount, 0);
        return super.loadAll(filter);
    }

    @Override
    public Integer getCount(SearchFilter searchFilter)
    {
        searchFilter.gt(Board.PROPERTY_amount, 0);
        return super.getCount(searchFilter);
    }

    /**
     * Инициализируем Itemы для распила
     * releaseAllBy(order)->initSheets->releaseBy(segment)
     *
     * @param stripsEntity
     * @param pair
     * @param countSheets
     * @return
     */
    public SheetDimentionItem[] initSheets(ABoardStripsEntity stripsEntity, TextureBoardDefPair pair, int countSheets)
    {
        List<StorageElementLink> storageElementLinks = new ArrayList<StorageElementLink>();

        if (stripsEntity.getOrder() instanceof Order && ((Order) stripsEntity.getOrder()).getStatus() == OrderStatus.design)
        {
            //
            List<Board> boards = findMinFreeRestBoardsBy(new Dimension(0, 0), pair, Integer.MAX_VALUE);

            for (Board board : boards)
            {
				storageElementLinks.add(storageElementLinkFacade.createAndSaveBy(stripsEntity, board, board.getAmount()));
				board.setAmount(0);
                save(board);
            }
        }

        Board board = createDefaultBoardBy(pair);
        board.setStatus(StoreElementStatus.order);
        board.setAmount(0);
        save(board);

		StorageElementLink storageElementLink = storageElementLinkFacade.createAndSaveBy(stripsEntity, board, countSheets);
		storageElementLinks.add(storageElementLink);
        List<SheetDimentionItem> sheets = createSheets(storageElementLinks, pair.getBoardDef().getCutter());
        return sheets.toArray(new SheetDimentionItem[]{});
    }


    public Segment createGraySegmentBy(ABoardStripsEntity stripsEntity, Board board)
    {
		StorageElementLink storageElementLink = storageElementLinkFacade.createAndSaveBy(stripsEntity, board, 1);
		board.setAmount(board.getAmount() - 1);
        save(board);
        SheetDimentionItem dimentionItem = new SheetDimentionItem(storageElementLink, board.getBoardDef().getCutter());
        Segment segment = Strips.createGraySegmentBy(dimentionItem);
        DimensionsHelper dimensionsHelper = new BoardDimensionsHelper();
        dimensionsHelper.fillWithDimensionItem(segment, dimentionItem);

        return segment;
    }

    public List<SheetDimentionItem> createSheets(List<StorageElementLink> storageElementLinks, Cutter cutter)
    {
        ArrayList<SheetDimentionItem> sheets = new ArrayList<SheetDimentionItem>(storageElementLinks.size());
        for (StorageElementLink storageElementLink : storageElementLinks)
        {
            for (int i = 0; i < storageElementLink.getAmount(); i++)
            {
                sheets.add(new SheetDimentionItem(storageElementLink, cutter));
            }
        }

        Collections.sort(sheets, new Comparator<SheetDimentionItem>()
        {
            @Override
            public int compare(SheetDimentionItem o1, SheetDimentionItem o2)
            {
                if (o1.getHeight() == o2.getHeight() &&
                        o1.getWidth() == o2.getWidth())
                {
                    StoreElementStatus status1 = o1.getStorageElementLink().getStoreElement().getStatus();
                    StoreElementStatus status2 = o2.getStorageElementLink().getStoreElement().getStatus();
                    if (status1 != status2)
                    {
                        return status1 == StoreElementStatus.order ? -1 : 1;
                    }
                }
                int a1 = o1.getHeight() * o1.getWidth();
                int a2 = o2.getHeight() * o2.getWidth();
                return a1 < a2 ? -1 : 1;
            }
        });
        return sheets;
    }

    public Board createDefaultBoardBy(TextureBoardDefPair pair)
    {
        Board board = new Board();
        board.setPair(pair);
        board.setLength(pair.getBoardDef().getDefaultLength());
        board.setWidth(pair.getBoardDef().getDefaultWidth());
		PriceEntity price = priceFacade.getPrice(pair);
		//устанавлеваем цену как прайс по умолчанию
        board.setPrice(price != null ? price.getPrice().doubleValue() : 0.0);
        return board;
    }

    /**
     * Разлинковываем board от ордеры, метод вызывается при распиле когда Segment не используется
     */
    public void releaseBy(StorageElementLink storageElementLink, int countRest)
    {
        Board board = (Board) storageElementLink.getStoreElement();
        if (board.getStatus() != StoreElementStatus.order)
        {
            board = findBy(board.getId());
            board.setAmount(board.getAmount() + (storageElementLink.getAmount() - countRest));
			save(board);
		}


		if (countRest <= 0) {
			storageElementLinkFacade.delete(storageElementLink.getId());
			if (board.getStatus() == StoreElementStatus.order)
            {
                delete(board.getId());
            }
        } else {
			storageElementLink = storageElementLinkFacade.findBy(storageElementLink.getId());
			storageElementLink.setAmount(countRest);
			storageElementLinkFacade.save(storageElementLink);
		}
	}

    @Override
    public void releaseBy(Segment segment)
    {
        StorageElementLink storageElementLink = getStorageElementLinkBy(segment);
        if (storageElementLink != null)
        {
            releaseBy(storageElementLink, storageElementLink.getAmount() - 1);
        }
    }

    @Override
    public StorageElementLink getStorageElementLinkBy(Segment segment)
    {
        if (segment.getDimensionItem() != null &&
                segment.getDimensionItem() instanceof SheetDimentionItem)
        {
            return ((SheetDimentionItem) segment.getDimensionItem()).getStorageElementLink();
        }
        return null;
    }

    /**
     * @param order
     */
    @Override
    public void attachTo(Order order)
    {
		List<StorageElementLink> storageElementLinks = storageElementLinkFacade.loadAllBy(order);
		for (StorageElementLink storageElementLink : storageElementLinks)
        {
            Board board = (Board) storageElementLink.getStoreElement();
            if (board.getAmount() > 0)
            {
                board = board.clone();
            }
            board.setOrder(order);
            board.setAmount(storageElementLink.getAmount());
            if (board.getStatus() == StoreElementStatus.exist)
            {
                board.setStatus(StoreElementStatus.used);
            }
			storageElementLinkFacade.delete(storageElementLink);
			save(board);
		}
        new BoardOrderStatusRefactorer(order, this).refactor();
    }

    @Override
    public List<Board> findAllBy(Order order, StoreElementStatus status)
    {
        SearchFilter filter = new SearchFilter();
        filter.setPageSize(Integer.MAX_VALUE);
        filter.addCriterion(new SearchFilter.DCriterion<Criterion>("order", Restrictions.eq("order", order)));
        filter.addCriterion(new SearchFilter.DCriterion<Criterion>("status", Restrictions.eq("status", status)));
        return loadAll(filter);
    }


    protected List<Board> findByOrdered(Board ordered)
    {
        SearchFilter filter = SearchFilter.instanceSingle();
        filter.eq(AStoreElement.PROPERTY_priceAware, ordered.getBoardDef());
        filter.eq(AStoreElement.PROPERTY_priced, ordered.getTexture());
        filter.eq(Board.PROPERTY_status, StoreElementStatus.order);
        filter.eq(Board.PROPERTY_length, ordered.getLength());
        filter.eq(Board.PROPERTY_width, ordered.getWidth());
        filter.eq(Board.PROPERTY_order + "." + Order.PROPERTY_orderStatus, OrderStatus.production);
        filter.addDescOrder(Board.PROPERTY_amount);
        List<Board> boards = loadAll(filter);
        return boards;
    }

    @Override
    public List<Board> findAllBy(Order order, TextureBoardDefPair pair)
    {
        SearchFilter filter = SearchFilter.instanceUnbound();
        filter.eq(Board.PROPERTY_priceAware, pair.getBoardDef());
        filter.eq(Board.PROPERTY_priced, pair.getTexture());
        filter.eq("order", order);
        List<Board> boards = loadAll(filter);
        return boards;
    }


    @Override
    public void deleteBy(AOrder order)
    {
        List<Board> boards = findAllBy(order);
        for (Board board : boards)
        {
            if (board.getStatus() == StoreElementStatus.order ||
                    order.getStatus() == OrderStatus.made ||
                    order.getStatus() == OrderStatus.archive ||
                    order.getStatus() == OrderStatus.shipped)
            {
				delete(board);
			}
            else
            {
                board.setOrder(null);
                board.setStatus(StoreElementStatus.exist);
				save(board);
			}
        }
    }

    @Override
    public List<Board> findAllBy(AOrder order)
    {
        SearchFilter filter = SearchFilter.instanceUnbound();
        filter.eq(Board.PROPERTY_order, order);
        List<Board> boards = super.loadAll(filter);
        return boards;
    }

    public Segment getLastFullSegmentBy(Strips strips)
    {
        for (int i = strips.getItems().size() - 1; i >= 0; i--)
        {
            DimensionsHelper dimensionsHelper = new BoardDimensionsHelper();
            if (dimensionsHelper.isWhole(strips.getItems().get(i)))
            {
                return strips.getItems().get(i);
            }
        }
        return null;
    }

    public List<Segment> getWholeRedSegmentsBy(Strips strips)
    {
        ArrayList<Segment> redSegments = new ArrayList<Segment>();
        List<Segment> wholeGraySegments = getWholeGraySegmentsBy(strips);
        for (Segment wholeGraySegment : wholeGraySegments)
        {
            redSegments.addAll(wholeGraySegment.getItems());
        }
        return redSegments;
    }

    public List<Segment> getWholeGraySegmentsBy(Strips strips)
    {

        ArrayList<Segment> segments = new ArrayList<Segment>();

        for (Segment s : strips.getItems())
        {
            DimensionsHelper dimensionsHelper = new BoardDimensionsHelper();
            if (dimensionsHelper.isWhole(s))
            {
                segments.add(s);
            }
        }
        return segments;

    }

    @Override
    public List<Segment> initSegments(SheetDimentionItem[] sheets)
    {
        List<Segment> items = new ArrayList<Segment>();
        for (DimensionItem dimension : sheets)
        {
            int count = dimension.getCount();
            for (int i = 0; i < count; i++)
            {
                Segment segment = Strips.createGraySegmentBy(dimension);
                DimensionsHelper dimensionsHelper = new BoardDimensionsHelper();
                dimensionsHelper.fillWithDimensionItem(segment, dimension);
                items.add(segment);
            }
        }
        return items;
    }

}
