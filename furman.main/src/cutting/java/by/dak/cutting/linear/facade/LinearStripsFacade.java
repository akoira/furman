package by.dak.cutting.linear.facade;

import by.dak.cutting.cut.facade.AStripsFacade;
import by.dak.cutting.linear.LinearCuttingModel;
import by.dak.cutting.linear.entity.LinearStripsEntity;
import by.dak.ordergroup.OrderGroup;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: dkoyro
 * Date: 06.05.11
 * Time: 16:47
 * To change this template use File | Settings | File Templates.
 */
@Transactional
public interface LinearStripsFacade extends AStripsFacade<LinearStripsEntity>
{
    public void saveCuttingModel(LinearCuttingModel cuttingModel);

    /**
     * в случае когда strips сохранённых нет - возвращается null
     * если null, то LinearCuttingModelCreator создаёт новую модель
     *
     * @param orderGroup
     * @return
     */
    public LinearCuttingModel loadLinearCuttingModelBy(OrderGroup orderGroup);

    void deleteAllBy(OrderGroup orderGroup);

    public List<LinearStripsEntity> loadAllBy(OrderGroup orderGroup);
}
