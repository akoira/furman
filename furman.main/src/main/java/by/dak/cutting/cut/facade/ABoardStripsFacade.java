package by.dak.cutting.cut.facade;

import by.dak.cutting.cut.facade.helper.StripsLoader;
import by.dak.cutting.cut.gui.CutSettings;
import by.dak.cutting.facade.AOrderBoardDetailFacade;
import by.dak.cutting.statistics.StatisticFilter;
import by.dak.cutting.swing.cut.CuttingModel;
import by.dak.cutting.swing.order.data.TextureBoardDefPair;
import by.dak.persistence.cutting.entities.ABoardStripsEntity;
import by.dak.persistence.entities.AOrder;
import by.dak.persistence.entities.BoardDef;
import by.dak.persistence.entities.TextureEntity;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Map;


/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 27.09.11
 * Time: 9:37
 * To change this template use File | Settings | File Templates.
 */
@Transactional
public interface ABoardStripsFacade<S extends ABoardStripsEntity> extends AStripsFacade<S>
{
    public AOrderBoardDetailFacade getOrderBoardDetailFacade();

    void saveAll(CuttingModel cuttingModel);

    void deleteAll(AOrder order);

    public List<S> findBy(Date start, Date end);

    public S findUniqueStrips(AOrder order, TextureEntity texture, BoardDef boardDef);

    StripsLoader loadCuttingModel(AOrder order);

    public Map<TextureBoardDefPair, CutSettings> loadCutSettings(AOrder order, List<TextureBoardDefPair> pairs);

    public Map<BoardDef, List<TextureEntity>> findUniquePairsBy(StatisticFilter statisticFilter);

    public List<StatisticDTO> getStatistics(StatisticFilter statisticFilter, BoardDef boardDef, TextureEntity texture);

    public static class StatisticDTO
    {
        private Long type;
        private Long code;
        private Long amount;
        private Double size;

        public Long getType()
        {
            return type;
        }

        public void setType(Long type)
        {
            this.type = type;
        }

        public Long getCode()
        {
            return code;
        }

        public void setCode(Long code)
        {
            this.code = code;
        }

        public Double getSize()
        {
            return size;
        }

        public void setSize(Double size)
        {
            this.size = size;
        }

        public Long getAmount()
        {
            return amount;
        }

        public void setAmount(Long amount)
        {
            this.amount = amount;
        }
    }
}
