package by.dak.cutting.facade;

import by.dak.persistence.entities.AStoreElement;
import by.dak.persistence.entities.Order;
import by.dak.persistence.entities.PriceAware;
import by.dak.persistence.entities.Priced;
import by.dak.persistence.entities.predefined.StoreElementStatus;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: akoyro
 * Date: 21.11.2009
 * Time: 23:38:25
 */
@Transactional
public interface AStoreElementFacade<E extends AStoreElement> extends BaseFacade<E>
{
    /**
     * Присоединение матриала к заказу
     *
     * @param order
     */
    public void attachTo(Order order);

    /**
     * Списываем материал и того что надо заказать по заказанному материалу
     * <p/>
     * 1. Находим что надо заказать
     * Поставка больше чем заказать
     * 2. Из поставки отнимаем количиство сколько надо заказать
     * 3. Устанавлеваем поставщика, поставку и статус used
     * 4. Сохраняем
     * <p/>
     * Поставка меньше чем заказать
     * 2. Поставку удаляем
     * 3. Заказать делим
     * 4. То что хватает устанавлеваем поставщика, поставку и статус used
     * 5. Сохраяем
     *
     * @param ordered
     * @return возвращает список заказанных если в процессе списание они были разделены
     */
    E cancelOrdered(E ordered);

    /**
     * Возвращает эелемен с максимальной ценой
     *
     * @param priceAware
     * @param priced
     * @param status
     * @return
     */
    public E getMaxPricedBy(PriceAware priceAware, Priced priced, StoreElementStatus status);

}
