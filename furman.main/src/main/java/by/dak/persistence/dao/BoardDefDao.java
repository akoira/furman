package by.dak.persistence.dao;

import by.dak.cutting.SearchFilter;
import by.dak.persistence.entities.BoardDef;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Denis Koyro
 * @version 0.1 16.10.2008
 * @introduced [Builder | Overview ]
 * @since 2.0.0
 */
@Repository
public interface BoardDefDao extends GenericDao<BoardDef>
{
    List<BoardDef> findBoardDefinitions();

    List<BoardDef> findBoardDefinitionsWithFilter(final SearchFilter filter);

    BoardDef createBoardDefinition(String name, Long thickness);

    void deleteBoardDefinition(BoardDef boardDefinition);

    List<BoardDef> findSimpleBoardDefitions();
}