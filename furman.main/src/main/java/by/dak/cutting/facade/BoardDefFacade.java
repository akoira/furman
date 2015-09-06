package by.dak.cutting.facade;

import by.dak.cutting.SearchFilter;
import by.dak.persistence.entities.BoardDef;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Vitaly Kozlovski
 * @version 0.1 24.01.2009
 * @introduced [Furniture constructor | Iteration 1]
 * @since 1.0.0
 */
@Transactional
public interface BoardDefFacade extends PriceAwareFacade<BoardDef>
{

    List<BoardDef> findBoardDefs();

    List<BoardDef> findBoardDefsWithFilter(final SearchFilter filter);

    void saveBoardDef(BoardDef boardDef);

    List<BoardDef> findSimpleBoardDefs();

    BoardDef findDefaultBoardDef();

}