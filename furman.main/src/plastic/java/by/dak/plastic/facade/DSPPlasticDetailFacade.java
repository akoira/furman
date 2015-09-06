package by.dak.plastic.facade;

import by.dak.cutting.facade.AOrderBoardDetailFacade;
import by.dak.persistence.entities.AOrder;
import by.dak.persistence.entities.BoardDef;
import by.dak.plastic.DSPPlasticDetail;

import java.util.List;

/**
 * User: akoyro
 * Date: 25.09.11
 * Time: 12:33
 */
public interface DSPPlasticDetailFacade extends AOrderBoardDetailFacade<DSPPlasticDetail>
{
    public List<BoardDef> getPlasticBoardDefs();

    public List<Long> getPlasticBoardDefIds();

    public boolean hasPlasticDetails(AOrder order);

    public boolean isPlastic(BoardDef boardDef);
}
