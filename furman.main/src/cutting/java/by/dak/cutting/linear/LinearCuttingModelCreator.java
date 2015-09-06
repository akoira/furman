package by.dak.cutting.linear;

import by.dak.cutting.cut.gui.CutSettings;
import by.dak.cutting.linear.entity.LinearStripsEntity;
import by.dak.ordergroup.OrderGroup;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Cutter;
import by.dak.persistence.entities.FurnitureLink;
import by.dak.persistence.entities.predefined.Unit;
import by.dak.persistence.entities.types.FurnitureType;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: dkoyro
 * Date: 12.03.11
 * Time: 17:32
 * To change this template use File | Settings | File Templates.
 */
public class LinearCuttingModelCreator
{
    private LinearCuttingModel cuttingModel;

    /**
     * поднимаются пары -> создаются настройки
     *
     * @param orderGroup
     * @return
     */
    public LinearCuttingModel createCuttingModel(OrderGroup orderGroup)
    {
        cuttingModel = new LinearCuttingModel();
        cuttingModel.setOrderGroup(orderGroup);

        List<FurnitureTypeCodePair> pairs = initPairs(orderGroup);
        cuttingModel.setPairs(pairs);

        for (FurnitureTypeCodePair pair : pairs)
        {
            List<FurnitureLink> furnitureLinks = FacadeContext.getFurnitureLinkFacade().loadAllBy(orderGroup, pair, Unit.linearMetre);
            CutSettings cutSettings = createCutSettings(pair, furnitureLinks);
            cuttingModel.putCutSettings(pair, cutSettings);
        }

        return cuttingModel;
    }

    private CutSettings createCutSettings(FurnitureTypeCodePair pair, List<FurnitureLink> furnitureLinks)
    {
        LinearStripsEntity linearStripsEntity = new LinearStripsEntity();
        linearStripsEntity.setFurnitureCode(pair.getFurnitureCode());
        linearStripsEntity.setFurnitureType(pair.getFurnitureType());
        linearStripsEntity.setOrderGroup(cuttingModel.getOrderGroup());
        linearStripsEntity.setRotation(false);
        linearStripsEntity.setSawWidth(getCutterBy(pair.getFurnitureType()).getSawcutWidth().intValue());
        linearStripsEntity.setStrips("");
        FacadeContext.getLinearStripsFacade().save(linearStripsEntity);

        LinearCutSettingCreator cutSettingsCreator = new LinearCutSettingCreator(linearStripsEntity, furnitureLinks);
        CutSettings cutSettings = cutSettingsCreator.create().getCutSettings();
        return cutSettings;

    }

    /**
     * для furnitureType из пары проверяем cutter на null
     * и сразу же сохраняем, чтобы в справочнике стоял defaultCutter
     *
     * @param furnitureType
     * @return
     */
    private Cutter getCutterBy(FurnitureType furnitureType)
    {
        if (furnitureType.getCutter() == null)
        {
            furnitureType.setCutter(FacadeContext.getCutterFacade().getDefaultLinearCutter());
            FacadeContext.getFurnitureTypeFacade().save(furnitureType);
        }
        return furnitureType.getCutter();
    }

    private List<FurnitureTypeCodePair> initPairs(OrderGroup orderGroup)
    {
        List<FurnitureTypeCodePair> pairs = FacadeContext.getFurnitureLinkFacade().loadUniquePairsBy(orderGroup, Unit.linearMetre);

        return pairs;
    }

}
