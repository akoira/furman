package by.dak.cutting.facade.impl;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.facade.BaseFacadeImpl;
import by.dak.cutting.facade.BoardDefFacade;
import by.dak.persistence.dao.BoardDefDao;
import by.dak.persistence.entities.BoardDef;

import java.util.List;

/**
 * @author Vitaly Kozlovski
 * @version 0.1 24.01.2009
 * @introduced [Furniture constructor | Iteration 1]
 * @since 1.0.0
 */
public class BoardDefFacadeImpl extends BaseFacadeImpl<BoardDef> implements BoardDefFacade {
	private String defaultName;

	@Override
	public void save(BoardDef entity) {
		if (entity.getComposite()) {
			recountParams(entity);
		}
		super.save(entity);
	}

	@Override
	public void saveBoardDef(BoardDef boardDef) {
		dao.save(boardDef);
	}

	private void recountParams(BoardDef boardDef) {
		if (boardDef.isInstanceOf(BoardDef.class)) {
			boardDef.setThickness(boardDef.getSimpleType1().getThickness()
					+ boardDef.getSimpleType2().getThickness());
		}
	}

	@Override
	public List<BoardDef> findBoardDefs() {
		List<BoardDef> findByClass = ((BoardDefDao) dao).findBoardDefinitions();
		return findByClass;
	}

	public List<BoardDef> findBoardDefsWithFilter(SearchFilter filter) {
		return ((BoardDefDao) dao).findBoardDefinitionsWithFilter(filter);
	}

	@Override
	public List<BoardDef> findSimpleBoardDefs() {
		return ((BoardDefDao) dao).findSimpleBoardDefitions();
	}

	public BoardDef findDefaultBoardDef() {
		return dao.findUniqueByField("name", getDefaultName());
	}

	public String getDefaultName() {
		return defaultName;
	}

	public void setDefaultName(String defaultName) {
		this.defaultName = defaultName;
	}
}
