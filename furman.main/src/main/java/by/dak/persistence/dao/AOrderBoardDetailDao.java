package by.dak.persistence.dao;

import by.dak.persistence.entities.AOrder;
import by.dak.persistence.entities.AOrderBoardDetail;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author admin
 * @version 0.1 18.10.2008
 * @introduced [Furniture constructor | Iteration 1]
 * @since 1.0.0
 */
@Repository
public interface AOrderBoardDetailDao<E extends AOrderBoardDetail> extends AOrderDetailDao<E>
{
    public List<Object[]> findUniquePairDefText(AOrder order);
}
