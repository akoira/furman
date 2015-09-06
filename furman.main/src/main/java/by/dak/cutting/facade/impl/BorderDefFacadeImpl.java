/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.facade.impl;

import by.dak.cutting.facade.BaseFacadeImpl;
import by.dak.cutting.facade.BorderDefFacade;
import by.dak.persistence.entities.BorderDefEntity;
import org.jdesktop.application.Application;

/**
 * @author Admin
 */
public class BorderDefFacadeImpl extends BaseFacadeImpl<BorderDefEntity> implements BorderDefFacade
{
    public BorderDefEntity findDefaultBorderDef()
    {
        return dao.findUniqueByField("name", Application.getInstance().getContext().getResourceMap().getString("Application.default.BorderDef.name"));
    }
}
