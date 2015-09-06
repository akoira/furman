package by.dak.plastic.facade;

import by.dak.cutting.swing.cut.CutSettingsCreator;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.cutting.entities.ABoardStripsEntity;

/**
 * User: akoyro
 * Date: 01.11.11
 * Time: 13:13
 */
public class DSPPlasticCutSettingsCreator extends CutSettingsCreator
{
    protected void initStrips(int countSheets)
    {
        //the code need to exclude second reservetion of the material. we take a material from common cutting and to the cutting on this material
        if (FacadeContext.getDSPPlasticDetailFacade().isPlastic(getPair().getBoardDef()))
        {
            ABoardStripsEntity aBoardStripsEntity = FacadeContext.getStripsFacade().findUniqueStrips(getStripsEntity().getOrder(), getPair().getTexture(), getPair().getBoardDef());
            FacadeContext.getStorageElementLinkFacade().deleteAllBy(aBoardStripsEntity);
        }
        super.initStrips(countSheets);
    }

}
