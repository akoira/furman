package by.dak.persistence.dao.impl;

import by.dak.cutting.SearchFilter;
import by.dak.persistence.dao.BoardDefDao;
import by.dak.persistence.entities.BoardDef;
import org.hibernate.Criteria;

import java.util.List;

/**
 * @author Denis Koyro
 * @version 0.1 16.10.2008
 * @introduced [Builder | Overview ]
 * @since 2.0.0
 */
public class BoardDefDaoImpl extends GenericDaoImpl<BoardDef> implements BoardDefDao {
	/* this is the only method of this class, which is used in src(other are used only in test) */

	public List<BoardDef> findBoardDefinitions() {
		return findDefinitionsBy(BoardDef.class);
	}

	public List<BoardDef> findBoardDefinitionsWithFilter(final SearchFilter filter) {
		Criteria execCriteria = getSession().createCriteria(BoardDef.class);
		execCriteria.setFirstResult(filter.getStartIndex());
		execCriteria.setMaxResults(filter.getPageSize());
		return execCriteria.list();

	}

	/**
	 * used in tests only
	 */
	public BoardDef createBoardDefinition(String name, Double height) {
		// TODO move bean creation out of facade and dao
		BoardDef definition = new BoardDef();
		definition.setName(name);
		definition.setThickness(height);
		save(definition);
		return definition;
	}


	public void deleteBoardDefinition(BoardDef boardDefinition) {
		deleteDefinition(boardDefinition);
	}


	private <D extends BoardDef> List<D> findDefinitionsBy(Class<D> definitionClass) {
		return createCriteria(definitionClass).list();
	}

	private <D extends BoardDef> void deleteDefinition(D definition) {
		delete(definition, true);
	}

	@Override
	public List<BoardDef> findSimpleBoardDefitions() {
		return (List<BoardDef>) getSession().getNamedQuery("simpleTypes").list();
	}
}
