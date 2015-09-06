package by.dak.cutting.facade;

import by.dak.cutting.cut.guillotine.Segment;
import by.dak.cutting.facade.impl.helper.FurnitureLink2FurnitureLinker;
import by.dak.cutting.linear.LinearCuttingModel;
import by.dak.cutting.linear.LinearSheetDimensionItem;
import by.dak.cutting.linear.entity.LinearStripsEntity;
import by.dak.ordergroup.OrderGroup;
import by.dak.persistence.entities.Furniture;
import by.dak.persistence.entities.FurnitureLink;
import by.dak.persistence.entities.Order;
import by.dak.persistence.entities.StorageElementLink;
import by.dak.persistence.entities.types.FurnitureCode;
import by.dak.persistence.entities.types.FurnitureType;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface FurnitureFacade extends AStoreElementFacade<Furniture>
{
    public void putRestToStore(LinearCuttingModel linearCuttingModel);

    public List<Furniture> loadAllByOrder(Order order);

    public FurnitureLink2FurnitureLinker getFurnitureLink2FurnitureLinker();

    public void replace(Furniture furniture, FurnitureType type, FurnitureCode code);

    public List<Furniture> loadAllBy(FurnitureType type, FurnitureCode code, Double minSize);

    public LinearSheetDimensionItem[] initSheets(LinearStripsEntity stripsEntity, List<FurnitureLink> furnitureLinks, int countSheets);

    public List<Segment> initSegments(LinearSheetDimensionItem[] sheets);

    public void releaseBy(StorageElementLink storageElementLink, int usedCount);

    public void releaseAllBy(OrderGroup orderGroup);

    public void attachLinearTo(OrderGroup orderGroup);
}
