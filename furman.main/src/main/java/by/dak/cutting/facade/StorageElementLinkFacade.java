package by.dak.cutting.facade;

import by.dak.cutting.linear.entity.LinearStripsEntity;
import by.dak.persistence.cutting.entities.ABoardStripsEntity;
import by.dak.persistence.cutting.entities.AStripsEntity;
import by.dak.persistence.entities.AOrder;
import by.dak.persistence.entities.Board;
import by.dak.persistence.entities.Furniture;
import by.dak.persistence.entities.StorageElementLink;
import by.dak.persistence.entities.types.FurnitureCode;
import by.dak.persistence.entities.types.FurnitureType;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface StorageElementLinkFacade extends BaseFacade<StorageElementLink>
{
    StorageElementLink findBy(Board board);

    List<StorageElementLink> loadAllBy(AOrder order);

    /**
     * поиск должен совершаться по ордеру и дискриминатору содержащегося элемента
     *
     * @param stripsEntity
     * @param stroreElementDiscriminator
     * @return
     */
    List<StorageElementLink> loadAllBy(AStripsEntity stripsEntity, String stroreElementDiscriminator);


    /**
     * storageWlwmwntLink для фурнитуры
     *
     * @param stripsEntity
     * @param furniture
     * @param count
     * @return
     */
    public StorageElementLink createAndSaveBy(LinearStripsEntity stripsEntity, Furniture furniture, int count);

    /**
     * Создает и сохраняет StorageElementLink по
     *
     * @param stripsEntity
     * @param board
     * @param count
     * @return
     */
    public StorageElementLink createAndSaveBy(ABoardStripsEntity stripsEntity, Board board, int count);

    /**
     * уникальный StorageElementLink в статусе temp по
     *
     * @param stripsEntity
     * @param type
     * @param code
     * @return
     */
    public StorageElementLink findByTempStatus(LinearStripsEntity stripsEntity, FurnitureType type, FurnitureCode code);

    /**
     * уникальный StorageElementLink по существующей фурнитуре
     *
     * @param stripsEntity
     * @param furniture
     * @return
     */
    public StorageElementLink findUniqueStorageElementLink(LinearStripsEntity stripsEntity, Furniture furniture);

    void deleteAllBy(LinearStripsEntity linearStripsEntity);

    void deleteAllBy(ABoardStripsEntity boardStripsEntity);

    void deleteAllBy(AOrder order);
}
