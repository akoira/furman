package by.dak.cutting.facade.impl;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.cut.gui.cuttingApp.DimensionItem;
import by.dak.cutting.cut.guillotine.Segment;
import by.dak.cutting.cut.guillotine.Strips;
import by.dak.cutting.cut.guillotine.helper.DimensionsHelper;
import by.dak.cutting.cut.guillotine.helper.FurnitureDimensionHelper;
import by.dak.cutting.facade.FurnitureFacade;
import by.dak.cutting.facade.impl.helper.FurnitureLink2FurnitureLinker;
import by.dak.cutting.linear.*;
import by.dak.cutting.linear.entity.LinearStripsEntity;
import by.dak.ordergroup.OrderGroup;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.dao.FurnitureDao;
import by.dak.persistence.entities.*;
import by.dak.persistence.entities.predefined.StoreElementStatus;
import by.dak.persistence.entities.predefined.Unit;
import by.dak.persistence.entities.types.FurnitureCode;
import by.dak.persistence.entities.types.FurnitureType;

import javax.persistence.DiscriminatorValue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: user0
 * Date: 27.01.2010
 * Time: 16:58:07
 * To change this template use File | Settings | File Templates.
 */
public class FurnitureFacadeImpl extends AStoreElementFacadeImpl<Furniture> implements FurnitureFacade
{
    private FurnitureLink2FurnitureLinker furnitureLink2FurnitureLinker = new FurnitureLink2FurnitureLinker();

    @Override
    public void putRestToStore(LinearCuttingModel linearCuttingModel)
    {
        List<FurnitureTypeCodePair> pairs = linearCuttingModel.getPairs();
        for (FurnitureTypeCodePair pair : pairs)
        {
            Strips strips = linearCuttingModel.getStrips(pair);
            DimensionsHelper helper = new FurnitureDimensionHelper();
            List<RestFurnitureDimension> restDimensions = helper.getRestDimensions(strips);
            for (RestFurnitureDimension restDimension : restDimensions)
            {
                if (helper.isUseful(restDimension))
                {
                    Furniture furniture = restDimension.getFurniture().clone();
                    furniture.setCreatedByOrder(restDimension.getOrder());
                    furniture.setOrder(null);   //потому что в статусе order фурнитуре ставится заказ
                    furniture.setAmount(1);
                    furniture.setSize(UnitConverter.convertToMetre(restDimension.getWidth()));
                    furniture.setStatus(StoreElementStatus.exist);
                    Furniture exist = findRestFurniture(furniture);
                    if (exist != null)
                    {
                        exist.setAmount(exist.getAmount() + 1);
                        save(exist);
                    }
                    else
                    {
                        save(furniture);
                    }
                }
            }
        }
    }

    /**
     * находит остаток на складе эквивалентный данному
     *
     * @param furniture
     * @return
     */
    private Furniture findRestFurniture(Furniture furniture)
    {
        SearchFilter searchFilter = SearchFilter.instanceSingle();
        searchFilter.eq(Furniture.PROPERTY_createdByOrder, furniture.getCreatedByOrder());
        searchFilter.eq(Furniture.PROPERTY_size, furniture.getSize());
        searchFilter.eq(Furniture.PROPERTY_status, furniture.getStatus());
        searchFilter.eq(Furniture.PROPERTY_priced, furniture.getFurnitureCode());
        searchFilter.eq(Furniture.PROPERTY_priceAware, furniture.getFurnitureType());
        return getFirstBy(searchFilter);
    }

    @Override
    public List<Furniture> loadAllByOrder(Order order)
    {
        return ((FurnitureDao) dao).loadAllByOrder(order);
    }

	@Override
	public List<Furniture> loadAll(SearchFilter filter) {
		filter.gt(Furniture.PROPERTY_amount, 0);
		return super.loadAll(filter);
	}

	@Override
	public void attachTo(Order order)
    {
        List<FurnitureLink> furnitureLinks = FacadeContext.getFurnitureLinkFacade().loadAllBy(order);
        attachTo(order, furnitureLinks);
    }

    private void attachTo(Order order, List<FurnitureLink> furnitureLinks)
    {
        for (FurnitureLink link : furnitureLinks)
        {
            switch (link.getFurnitureType().getUnit())
            {
                case piece:
                    attachPieceTo(order, link, link.getAmount() * link.getOrderItem().getAmount());
                    break;
                case linearMetre:
					//linearMetre Furnitures are being attached to order in group order.
					break;
				case squareMetre:
                case linearMiliMetre:
                case linearSantiMetre:
                case gramme:
                    SizeCount sizeCount = new SizeCount();
                    sizeCount.size = link.getSize();
                    //обязательно умножить на количество orderItem-ов
                    sizeCount.count = link.getAmount() * link.getOrderItem().getAmount();
                    attachLinearTo(order, sizeCount, link);
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        }
    }


    /**
     * цепляим штуки
     *
     * @param order
     * @param link
     * @return возвражает оставшееся кол не найденых furnitures
     */
    private void attachPieceTo(Order order, FurnitureLink link, int restCount)
    {
        if (restCount <= 0)
        {
            return;
        }
        Furniture existFurniture = getMaxPricedBy(link.getFurnitureType(), link.getFurnitureCode(), StoreElementStatus.exist);
        if (existFurniture == null)
        {
            // если нет на складе
            existFurniture = new Furniture();
            existFurniture.setFurnitureCode(link.getFurnitureCode());
            existFurniture.setFurnitureType(link.getFurnitureType());
            existFurniture.setAmount(restCount);
            existFurniture.setSize(1d);
            existFurniture.setOrder(order);
            existFurniture.setStatus(StoreElementStatus.order);
            saveAndLink(existFurniture, link);
            return;
        }
        else
        {
            //если есть на складе
            if (restCount > existFurniture.getAmount())
            {
                //если нужно залинковать больше чем есть
                existFurniture.setOrder(order);
                existFurniture.setStatus(StoreElementStatus.used);
                restCount -= existFurniture.getAmount();
            }
            else
            {
                //если нужно залинковать меньше или столько же
                if (restCount != existFurniture.getAmount())
                {
                    //если меньше залинковать
                    existFurniture.setAmount(existFurniture.getAmount() - restCount);
                    save(existFurniture);
                    existFurniture = existFurniture.clone();
                }
                existFurniture.setOrder(order);
                existFurniture.setSize(1d);
                existFurniture.setAmount(restCount);
                existFurniture.setStatus(StoreElementStatus.used);
                return;
            }
            saveAndLink(existFurniture, link);
        }

        attachPieceTo(order, link, restCount);
    }


    public Furniture getMaxPricedBy(PriceAware furnitureType, Priced furnitureCode, StoreElementStatus status, double minSize)
    {
        SearchFilter filter = filterMaxPrice(furnitureType, furnitureCode, status);
        filter.ge(Furniture.PROPERTY_size, minSize);
        return getFirstBy(filter);
    }


    private void attachLinearTo(Order order, SizeCount sizeCount, FurnitureLink link)
    {
        //останавлевемся если ничего не осталось
        if (sizeCount.count <= 0)
        {
            return;
        }

        Furniture furniture = getMaxPricedBy(link.getFurnitureType(), link.getFurnitureCode(), StoreElementStatus.exist, sizeCount.size);
        if (furniture == null)
        {
            // если нет на складе
            furniture = new Furniture();
            furniture.setFurnitureCode(link.getFurnitureCode());
            furniture.setFurnitureType(link.getFurnitureType());
            furniture.setAmount(sizeCount.count);
            furniture.setSize(sizeCount.size);
            furniture.setOrder(order);
            furniture.setStatus(StoreElementStatus.order);
            saveAndLink(furniture, link);
            return;
        }
        else
        {
            //на складе есть
            double size = furniture.getSize();
            int count = (int) (size / sizeCount.size);
            int attachedCount = sizeCount.count > count ? count : sizeCount.count;
            sizeCount.count -= attachedCount;
            size -= attachedCount * sizeCount.size;

            //если есть остаток после списания
            if (size > 0)
            {
                Furniture attached = attachOneFrom(order, furniture, attachedCount * sizeCount.size);
                saveAndLink(attached, link);

                if (furniture.getAmount() == 1) //выставляем оставшейся размер
                {
                    furniture.setSize(size);
                }
                else //выделяем один и списяваем.
                {
                    furniture.setAmount(furniture.getAmount() - 1);

                    Furniture newFurniture = furniture.clone();
                    newFurniture.setAmount(1);
                    newFurniture.setSize(size);
                    save(newFurniture);
                }
            }
            else  //если нет остаток после списания
            {
                if (furniture.getAmount() == 1) //списываем весь
                {
                    furniture.setStatus(StoreElementStatus.used);
                    saveAndLink(furniture, link);
                }
                else //списываем 1 и линкуем
                {
                    Furniture attached = attachOneFrom(order, furniture, furniture.getSize());
                    saveAndLink(attached, link);

                    furniture.setAmount(furniture.getAmount() - 1);
                }
            }
            save(furniture);
        }
        attachLinearTo(order, sizeCount, link);
    }

    private void saveAndLink(Furniture furniture, FurnitureLink link)
    {
        save(furniture);
        getFurnitureLink2FurnitureLinker().link(link, Collections.singletonList(furniture));
    }

    private Furniture attachOneFrom(Order order, Furniture furniture, double size)
    {
        Furniture attached = furniture.clone();
        attached.setOrder(order);
        attached.setAmount(1);
        attached.setSize(size);
        attached.setStatus(StoreElementStatus.used);
        return attached;
    }


    protected List<Furniture> findByOrdered(Furniture ordered)
    {
        SearchFilter filter = SearchFilter.instanceSingle();
        filter.eq(Furniture.PROPERTY_priceAware, ordered.getFurnitureType());
        filter.eq(Furniture.PROPERTY_priced, ordered.getFurnitureCode());
        filter.eq(Furniture.PROPERTY_status, StoreElementStatus.order);
        filter.eq("order.status", OrderStatus.production);
        filter.addDescOrder("size");
        filter.addDescOrder("amount");
        List<Furniture> furnitures = loadAll(filter);
        return furnitures;
    }

    @Override
    public Furniture cancelOrdered(Furniture ordered)
    {
        switch (ordered.getFurnitureType().getUnit())
        {
            case piece:
                return cancelPieceOrdered(ordered);
            case squareMetre:
            case linearMetre:
            case linearMiliMetre:
            case linearSantiMetre:
            case gramme:
                return cancelLinearOrdered(ordered);
            default:
                throw new IllegalArgumentException();

        }
    }

    //Списываем штуки

    private Furniture cancelPieceOrdered(Furniture ordered)
    {
        if (ordered.getAmount() < 1)
            return null;
        List<Furniture> list = findByOrdered(ordered);
        boolean next = false;
        if (list.size() > 0)
        {
            Furniture e = list.get(0);
            if (e.getAmount() <= ordered.getAmount())
            {
                //заказанных больше или равно чем заказываемых
                next = e.getAmount() < ordered.getAmount();
                ordered.setAmount(ordered.getAmount() - e.getAmount());
            }
            else
            {
                //заказанных меньше чем заказываемых
                Furniture cloned = e.clone();
                e.setAmount(e.getAmount() - ordered.getAmount());
                save(e);

                cloned.setAmount(ordered.getAmount());
                e = cloned;

                ordered.setAmount(0);
            }
            e.setStatus(StoreElementStatus.used);
            e.setProvider(ordered.getProvider());
            e.setDelivery(ordered.getDelivery());
            save(e);
        }
        if (next)
        {
            cancelOrdered(ordered);
        }
        return null;
    }

    //Списываем линейные и квадратные

    private Furniture cancelLinearOrdered(Furniture ordered)
    {
        if (ordered.getAmount() < 1)
        {
            return null;
        }
        Furniture result = ordered;
        List<Furniture> furnitures = findByOrdered(ordered);

        if (furnitures.size() > 0)
        {
            if (ordered.getAmount() > 1)
            {
                result = ordered.clone();
                result.setAmount(1);
                ordered.setAmount(ordered.getAmount() - 1);
            }
            cancelLinearOrdered(result, furnitures);
            if (findByOrdered(ordered).size() > 0 &&
                    //ordered может быть уже пустым
                    ordered.getSize() > 0)
            {
                result = cancelOrdered(ordered);
            }
        }
        if (result != null && (result.getSize() == 0 || result == ordered))
            return null;
        else
            return result;
    }

    private void cancelLinearOrdered(Furniture ordered, List<Furniture> borders)
    {
        boolean next = false;
        Furniture furniture = borders.get(0);
        if (furniture.getSize() <= ordered.getSize())
        {
            //заказанных больше или равно чем заказываемых
            next = furniture.getSize() < ordered.getSize();

            ordered.setSize(ordered.getSize() - furniture.getSize());
            if (ordered.getSize() == 0)
            {
                ordered.setAmount(0);
            }
        }
        else
        {
            //заказанных меньше чем заказываемых
            Furniture cloned = furniture.clone();
            furniture.setSize(furniture.getSize() - ordered.getSize());
            save(furniture);

            cloned.setSize(ordered.getSize());
            furniture = cloned;

            ordered.setSize(0D);
            ordered.setAmount(0);
        }
        furniture.setStatus(StoreElementStatus.used);
        furniture.setProvider(ordered.getProvider());
        furniture.setDelivery(ordered.getDelivery());
        save(furniture);
        if (next)
        {
            borders = findByOrdered(ordered);
            if (borders.size() > 0)
            {
                cancelLinearOrdered(ordered, borders);
            }
        }
    }

    public void replace(Furniture furniture, FurnitureType type, FurnitureCode code)
    {
        Order order = furniture.getOrder();
        //меняем тип у все залинкованных FurnitureLink
        List<FurnitureLink> furnitureLinks = FacadeContext.getFurnitureLinkFacade().findAllBy(furniture);
        getFurnitureLink2FurnitureLinker().unlink(furniture);
        for (FurnitureLink furnitureLink : furnitureLinks)
        {
            furnitureLink.setFurnitureType(type);
            furnitureLink.setFurnitureCode(code);
            FacadeContext.getFurnitureLinkFacade().save(furnitureLink);
        }
        attachTo(order, furnitureLinks);

    }

    @Override
    public List<Furniture> loadAllBy(FurnitureType type, FurnitureCode code, Double minSize)
    {
        SearchFilter searchFilter = SearchFilter.instanceUnbound();
        searchFilter.eq(Furniture.PROPERTY_priceAware, type);
        searchFilter.eq(Furniture.PROPERTY_priced, code);
        searchFilter.ge(Furniture.PROPERTY_size, minSize);
        searchFilter.gt(Furniture.PROPERTY_amount, 0);
        searchFilter.eq(Furniture.PROPERTY_status, StoreElementStatus.exist);
        return loadAll(searchFilter);
    }

    public FurnitureLink2FurnitureLinker getFurnitureLink2FurnitureLinker()
    {
        return furnitureLink2FurnitureLinker;
    }

    public void setFurnitureLink2FurnitureLinker(FurnitureLink2FurnitureLinker furnitureLink2FurnitureLinker)
    {
        this.furnitureLink2FurnitureLinker = furnitureLink2FurnitureLinker;
    }

    class SizeCount
    {
        double size;
        int count;
    }


    @Override
    public LinearSheetDimensionItem[] initSheets(LinearStripsEntity stripsEntity, List<FurnitureLink> furnitureLinks, int countSheets)
    {

        List<StorageElementLink> storageElementLinks = new ArrayList<StorageElementLink>();
        int restCount = countSheets;
        for (FurnitureLink furnitureLink : furnitureLinks)
        {
            List<Furniture> furnitures = FacadeContext.getFurnitureFacade().loadAllBy(stripsEntity.getFurnitureType(), stripsEntity.getFurnitureCode(),
                    furnitureLink.getSize());
            for (Furniture furniture : furnitures)
            {
                storageElementLinks.add(FacadeContext.getStorageElementLinkFacade().createAndSaveBy(stripsEntity, furniture, furniture.getAmount()));
                restCount -= furniture.getAmount();
                furniture.setAmount(0);
                save(furniture);
            }
        }

        if (restCount > 0)
        {
            Furniture defaultFurniture = createDefaultFurnitureBy(stripsEntity.getFurnitureType(), stripsEntity.getFurnitureCode());
            defaultFurniture.setAmount(0);
            defaultFurniture.setStatus(StoreElementStatus.temp);
            save(defaultFurniture);

            storageElementLinks.add(FacadeContext.getStorageElementLinkFacade().createAndSaveBy(stripsEntity, defaultFurniture, restCount));

        }

        List<LinearSheetDimensionItem> sheetDimensionItems = createSheets(storageElementLinks, stripsEntity.getFurnitureType().getCutter());

        return sheetDimensionItems.toArray(new LinearSheetDimensionItem[sheetDimensionItems.size()]);
    }

    private Furniture createDefaultFurnitureBy(FurnitureType furnitureType, FurnitureCode furnitureCode)
    {
        Furniture furniture = new Furniture();
        furniture.setFurnitureCode(furnitureCode);
        furniture.setFurnitureType(furnitureType);
        furniture.setSize(furnitureType.getDefaultSize());
        furniture.setUnit(Unit.linearMetre);

        return furniture;
    }

    @Override
    public List<Segment> initSegments(LinearSheetDimensionItem[] sheets)
    {
        List<Segment> items = new ArrayList<Segment>();
        for (DimensionItem dimension : sheets)
        {
            int count = dimension.getCount();
            for (int i = 0; i < count; i++)
            {
                Segment segment = Strips.createGraySegmentBy(dimension);
                DimensionsHelper dimensionsHelper = new FurnitureDimensionHelper();
                dimensionsHelper.fillWithDimensionItem(segment, dimension);
                items.add(segment);
            }
        }
        return items;
    }

    public void releaseAllBy(OrderGroup orderGroup)
    {
        FacadeContext.getLinearStripsFacade().deleteAllBy(orderGroup);
    }

    @Override
    public void attachLinearTo(OrderGroup orderGroup)
    {
        List<LinearStripsEntity> stripsEntities = FacadeContext.getLinearStripsFacade().loadAllBy(orderGroup);
        for (LinearStripsEntity linearStripsEntity : stripsEntities)
        {
            List<StorageElementLink> storageElementLinks = FacadeContext.getStorageElementLinkFacade().
                    loadAllBy(linearStripsEntity, Furniture.class.getAnnotation(DiscriminatorValue.class).value());
            for (StorageElementLink storageElementLink : storageElementLinks)
            {
                Furniture furniture = (Furniture) storageElementLink.getStoreElement();
                if (furniture.getStatus() == StoreElementStatus.exist)
                {
                    if (furniture.getAmount() > 0)
                    {
                        furniture = furniture.clone();
                    }
                    //todo this is not correct furniture should be linked to order
                    furniture.setOrder(FacadeContext.getOrderFacade().loadAllBy(orderGroup).get(0));
                    furniture.setAmount(storageElementLink.getAmount());
                    furniture.setStatus(StoreElementStatus.used);
                }
                FacadeContext.getStorageElementLinkFacade().delete(storageElementLink);
            }
        }
    }

    /**
     * очищается когда usedCount = 0
     *
     * @param storageElementLink
     * @param usedCount          кол-во использованной фурнитуры
     */

    public void releaseBy(StorageElementLink storageElementLink, int usedCount)
    {
        Furniture furniture = (Furniture) storageElementLink.getStoreElement();
        furniture = FacadeContext.getFurnitureFacade().findById(furniture.getId(), true);

        if (furniture.getStatus() == StoreElementStatus.exist)
        {
            //если существует, то надо прибавить фурнитуру из базы + кол-во storageElemlink - кол-во использующейся
            furniture.setAmount(furniture.getAmount() + storageElementLink.getAmount() - usedCount);
        }
        else if (furniture.getStatus() == StoreElementStatus.temp)
        {
            changeTempToOrderStatus(furniture, (LinearStripsEntity) storageElementLink.getStripsEntity(), usedCount);
        }
        save(furniture);

        storageElementLink = FacadeContext.getStorageElementLinkFacade().findBy(storageElementLink.getId());
        storageElementLink.setAmount(usedCount);
        FacadeContext.getStorageElementLinkFacade().save(storageElementLink);

        if (storageElementLink.getAmount() == 0)
        {
            FacadeContext.getStorageElementLinkFacade().delete(storageElementLink);
            if (furniture.getStatus() == StoreElementStatus.order)
            {
                //когда ничего не используется, то и хранить временную furniture не надо
                delete(furniture);
            }
        }
    }

    private void changeTempToOrderStatus(Furniture furniture, LinearStripsEntity linearStripsEntity, int amount)
    {
        furniture.setAmount(amount);
        furniture.setStatus(StoreElementStatus.order);
    }

    /**
     * подготовка склада
     *
     * @param furnitureLink
     * @param furnitures
     * @param linearStripsEntity
     * @return
     */
    private List<StorageElementLink> prepareElementStorage(FurnitureLink furnitureLink, List<Furniture> furnitures, LinearStripsEntity linearStripsEntity)
    {
        List<StorageElementLink> storageElementLinks = new ArrayList<StorageElementLink>();

        if (furnitures.isEmpty())
        {
            storageElementLinks.add(attachTempStorageElementLink(furnitureLink, linearStripsEntity, furnitureLink.getAmount() * furnitureLink.getOrderItem().getAmount()));
        }
        else
        {

            int result = furnitureLink.getAmount() * furnitureLink.getOrderItem().getAmount();
            int i = 0;
            do
            {   //если фурнитура не одна на складе и если первой не хватило кол-ва, то ищем фурнитуру кол-во у которой удовлетвояет заказанному, иначе(если нехватка) создаётся временная фурнитура
                result = attachToExistStorageElementLink(linearStripsEntity, furnitures.get(i), result, storageElementLinks);
                i++;
            }
            while (result > 0 && i < furnitures.size());

            if (result > 0)
            {
                storageElementLinks.add(attachTempStorageElementLink(furnitureLink, linearStripsEntity, result));
            }
        }

        return storageElementLinks;
    }


    /**
     * цепляет storageElementLink для отсутствующей furniture
     *
     * @param furnitureLink
     * @param linearStripsEntity
     * @param restCount          кол-во фурнитуры в заказе(или кол-во недостающей в складе)
     */
    private StorageElementLink attachTempStorageElementLink(FurnitureLink furnitureLink, LinearStripsEntity linearStripsEntity, int restCount)
    {
        StorageElementLink storageElementLink = FacadeContext.getStorageElementLinkFacade().findByTempStatus(linearStripsEntity, furnitureLink.getFurnitureType(), furnitureLink.getFurnitureCode());
        if (storageElementLink == null)
        {
            Furniture furniture = new Furniture();
            furniture.setStatus(StoreElementStatus.temp);
            furniture.setAmount(0);
            furniture.setSize(furnitureLink.getFurnitureType().getDefaultSize());
            furniture.setFurnitureType(furnitureLink.getFurnitureType());
            furniture.setFurnitureCode(furnitureLink.getFurnitureCode());
            save(furniture);

            storageElementLink = new StorageElementLink();
            storageElementLink.setStoreElement(furniture);
            storageElementLink.setAmount(0);
            storageElementLink.setStripsEntity(linearStripsEntity);
        }
        storageElementLink.setAmount(storageElementLink.getAmount() + restCount);
        FacadeContext.getStorageElementLinkFacade().save(storageElementLink);

        return storageElementLink;
    }

    /**
     * @param stripsEntity
     * @param furniture
     * @param restCount    кол-во заказанной фурнитуры
     * @return кол-во не хватившей фурнитуры в складе
     */
    private int attachToExistStorageElementLink(LinearStripsEntity stripsEntity, Furniture furniture, int restCount, List<StorageElementLink> storageElementLinks)
    {
        int result = 0;
        int count = restCount;
        StorageElementLink storageElementLink = FacadeContext.getStorageElementLinkFacade().findUniqueStorageElementLink(stripsEntity, furniture);
        if (storageElementLink == null)
        {
            storageElementLink = new StorageElementLink();
            storageElementLink.setStoreElement(furniture);
            storageElementLink.setStripsEntity(stripsEntity);
            storageElementLink.setAmount(0);
        }

        if (furniture.getAmount() >= count) //когда фурнитуры достаточно на складе
        {
            storageElementLink.setAmount(storageElementLink.getAmount() + count);
            furniture.setAmount(furniture.getAmount() - count);
        }
        else if (furniture.getAmount() == 0) //когда кол-во фурнитуры на складе 0, то надо создать storageElementLink для отсутствующей furniture
        {
            return count;
        }
        else //storageElementLink ставятся все остатки со склада
        {
            storageElementLink.setAmount(storageElementLink.getAmount() + furniture.getAmount());
            result = count - furniture.getAmount(); //кол-во не хватившей фурнитуры в складе(зависит от кол-ва заказа и содержания на складе)
            furniture.setAmount(0);
        }
        save(furniture);
        FacadeContext.getStorageElementLinkFacade().save(storageElementLink);
        storageElementLinks.add(storageElementLink);
        return result;
    }

    private List<LinearSheetDimensionItem> createSheets(List<StorageElementLink> storageElementLinks, Cutter cutter)
    {
        List<LinearSheetDimensionItem> sheets = new ArrayList<LinearSheetDimensionItem>(storageElementLinks.size());
        for (StorageElementLink storageElementLink : storageElementLinks)
        {
            for (int i = 0; i < storageElementLink.getAmount(); i++)
            {
                sheets.add(new LinearSheetDimensionItem(storageElementLink, cutter));
            }
        }

        Collections.sort(sheets, new Comparator<LinearSheetDimensionItem>()
        {
            @Override
            public int compare(LinearSheetDimensionItem o1, LinearSheetDimensionItem o2)
            {
                int height1 = o1.getHeight(); //сортировка по width(у сегмента width это height)
                int height2 = o2.getHeight();
				return height2 - height1;
			}
        });
        return sheets;
    }
}
