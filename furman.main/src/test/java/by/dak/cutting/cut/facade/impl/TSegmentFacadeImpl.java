package by.dak.cutting.cut.facade.impl;

import by.dak.cutting.SpringConfiguration;
import by.dak.cutting.statistics.StatisticFilter;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.BoardDef;
import by.dak.persistence.entities.TextureEntity;

/**
 * User: akoyro
 * Date: 29.08.2010
 * Time: 10:52:33
 */
public class TSegmentFacadeImpl
{
    public static void main(String[] args)
    {
        SpringConfiguration springConfiguration = new SpringConfiguration();
        BoardDef boardDef = FacadeContext.getBoardDefFacade().findDefaultBoardDef();
        TextureEntity texture = FacadeContext.getTextureFacade().findTexturesBy(boardDef).get(0);
        FacadeContext.getStripsFacade().getStatistics(new StatisticFilter(), boardDef, texture);
    }
}
