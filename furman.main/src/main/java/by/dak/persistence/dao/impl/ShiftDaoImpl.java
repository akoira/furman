package by.dak.persistence.dao.impl;

import by.dak.persistence.dao.ShiftDao;
import by.dak.persistence.entities.ShiftEntity;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * @author Denis Koyro
 * @version 0.1 09.12.2008
 * @introduced [Furniture constructor | Iteration 1]
 * @since 1.0.0
 */
public class ShiftDaoImpl extends GenericDaoImpl<ShiftEntity> implements ShiftDao
{

    public List<ShiftEntity> findShiftsByDepartmentId(Long departmentID)
    {
        List<ShiftEntity> list = getSession().createCriteria(ShiftEntity.class).createCriteria("department").add(
                Restrictions.idEq(departmentID)).list();
        return list;
    }

}
