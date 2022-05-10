/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.facade.impl;

import by.dak.cutting.facade.BaseFacadeImpl;
import by.dak.cutting.facade.BorderDefFacade;
import by.dak.persistence.entities.BorderDefEntity;
import org.jdesktop.application.Application;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Admin
 */
public class BorderDefFacadeImpl extends BaseFacadeImpl<BorderDefEntity> implements BorderDefFacade
{
    private AtomicReference<BorderDefEntity> defaultBorderDef = new AtomicReference<>();
    public BorderDefEntity findDefaultBorderDef()
    {
        return defaultBorderDef.updateAndGet(v -> {
            if (v == null)
                return dao.findUniqueByField("name",
                        Application.getInstance().getContext().getResourceMap().getString("Application.default.BorderDef.name"));
                else return v;
        });
    }
}
