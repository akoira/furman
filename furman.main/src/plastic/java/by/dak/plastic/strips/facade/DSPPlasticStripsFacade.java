package by.dak.plastic.strips.facade;

import by.dak.cutting.cut.facade.ABoardStripsFacade;
import by.dak.plastic.strips.DSPPlasticStripsEntity;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 27.09.11
 * Time: 10:07
 * To change this template use File | Settings | File Templates.
 */
@Transactional
public interface DSPPlasticStripsFacade extends ABoardStripsFacade<DSPPlasticStripsEntity>
{
}
