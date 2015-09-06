package by.dak.cutting.facade.impl;

import by.dak.cutting.facade.BaseFacadeImpl;
import by.dak.cutting.facade.ShiftFacade;
import by.dak.persistence.dao.ShiftDao;
import by.dak.persistence.entities.ShiftEntity;

import java.util.List;

/**
 * @author Vitaly Kozlovski
 * @version 0.1 24.01.2009
 * @introduced [Furniture constructor | Iteration 1]
 * @since 1.0.0
 */
public class ShiftFacadeImpl extends BaseFacadeImpl<ShiftEntity> implements ShiftFacade
{

    @Override
    public ShiftEntity findShiftByName(String name)
    {
        return dao.findUniqueByField("name", name);
    }

    @Override
    public List<ShiftEntity> findShiftsByDepartmentId(Long id)
    {
        return ((ShiftDao) dao).findShiftsByDepartmentId(id);
    }

}
