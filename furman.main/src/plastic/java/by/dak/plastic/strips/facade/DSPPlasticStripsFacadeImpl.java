package by.dak.plastic.strips.facade;

import by.dak.cutting.cut.facade.helper.StripsLoader;
import by.dak.cutting.cut.facade.impl.ABoardStripsFacadeImpl;
import by.dak.cutting.swing.cut.CuttingModel;
import by.dak.persistence.FacadeContext;
import by.dak.plastic.strips.DSPPlasticStripsEntity;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 27.09.11
 * Time: 10:07
 * To change this template use File | Settings | File Templates.
 */
public class DSPPlasticStripsFacadeImpl extends ABoardStripsFacadeImpl<DSPPlasticStripsEntity> implements DSPPlasticStripsFacade {
	protected StripsLoader createStripsLoader(CuttingModel cuttingModel) {
		StripsLoader loader = new StripsLoader();
		loader.setBoardStripsFacade(FacadeContext.getDSPPlasticStripsFacade());
		loader.setCuttingModel(cuttingModel);
		return loader;
	}
}
