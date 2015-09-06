package by.dak.cutting.cut.facade.impl;

import by.dak.cutting.cut.facade.StripsFacade;
import by.dak.cutting.cut.facade.helper.StripsLoader;
import by.dak.cutting.swing.cut.CuttingModel;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.cutting.entities.StripsEntity;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class StripsFacadeImpl extends ABoardStripsFacadeImpl<StripsEntity> implements StripsFacade {
	protected StripsLoader createStripsLoader(CuttingModel cuttingModel) {
		StripsLoader loader = new StripsLoader();
		loader.setBoardStripsFacade(FacadeContext.getStripsFacade());
		loader.setCuttingModel(cuttingModel);
		return loader;
	}
}
