package by.dak.cutting.facade;

import by.dak.cutting.cut.base.Dimension;
import by.dak.cutting.cut.guillotine.Segment;
import by.dak.cutting.cut.guillotine.Strips;
import by.dak.cutting.swing.cut.SheetDimentionItem;
import by.dak.cutting.swing.order.data.TextureBoardDefPair;
import by.dak.persistence.cutting.entities.ABoardStripsEntity;
import by.dak.persistence.entities.*;
import by.dak.persistence.entities.predefined.StoreElementStatus;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Vitaly Kozlovski
 * @version 0.1 24.01.2009
 * @introduced [Furniture constructor | Iteration 1]
 * @since 1.0.0
 */
@Transactional
public interface BoardFacade extends AStoreElementFacade<Board>
{

    public void putRestsToStore(Order order);

    public List<Board> findMinFreeRestBoardsBy(Dimension dimension, TextureBoardDefPair pair, int maxCount);

    public Board getMinFreeRestBoardBy(Dimension dimension, TextureBoardDefPair pair);

    public SheetDimentionItem[] initSheets(ABoardStripsEntity stripsEntity, TextureBoardDefPair pair, int countSheets);

    public Board createDefaultBoardBy(TextureBoardDefPair pair);

    public void releaseBy(StorageElementLink storageElementLink, int countRest);

    public void releaseBy(Segment segment);

    public StorageElementLink getStorageElementLinkBy(Segment segment);

    /**
     * списывает материалы сос склада
     *
     * @param order
     */
    void attachTo(Order order);

    List<Board> findAllBy(Order order, StoreElementStatus status);

    /**
     * Возвращает листы прикрепленные к заказу
     *
     * @param order
     * @param pair
     * @return
     */
    List<Board> findAllBy(Order order, TextureBoardDefPair pair);

    /**
     * Удаляет boards при удалении заказа
     *
     * @param order
     */
    void deleteBy(AOrder order);

    List<Board> findAllBy(AOrder order);

    public List<SheetDimentionItem> createSheets(List<StorageElementLink> storageElementLinks, Cutter cutter);

    public Segment createGraySegmentBy(ABoardStripsEntity stripsEntity, Board board);

    public Segment getLastFullSegmentBy(Strips strips);

    public List<Segment> getWholeRedSegmentsBy(Strips strips);

    public List<Segment> getWholeGraySegmentsBy(Strips strips);

    /**
     * создаёт сегменты и присваивает значения свойств Board
     * нужно для strips
     *
     * @param sheets
     * @return
     */
    public List<Segment> initSegments(SheetDimentionItem[] sheets);


}
