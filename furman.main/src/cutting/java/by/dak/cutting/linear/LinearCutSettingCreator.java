package by.dak.cutting.linear;

import by.dak.cutting.cut.base.Element;
import by.dak.cutting.cut.gui.CutSettings;
import by.dak.cutting.cut.guillotine.Segment;
import by.dak.cutting.cut.guillotine.Strips;
import by.dak.cutting.cut.guillotine.helper.DimensionsHelper;
import by.dak.cutting.cut.guillotine.helper.FurnitureDimensionHelper;
import by.dak.cutting.linear.entity.LinearStripsEntity;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.FurnitureLink;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: dkoyro
 * Date: 01.03.11
 * Time: 18:44
 * To change this template use File | Settings | File Templates.
 */
public class LinearCutSettingCreator
{
    private CutSettings cutSettings;
    private List<FurnitureLink> furnitures;
    private LinearStripsEntity stripsEntity;

    public LinearCutSettingCreator(LinearStripsEntity stripsEntity, List<FurnitureLink> furnitures)
    {
        this.stripsEntity = stripsEntity;
        this.furnitures = furnitures;
    }


    public LinearCutSettingCreator create()
    {
        cutSettings = new CutSettings();
        int countSheets = initElements();
        initStrips(countSheets);
        return this;
    }


    private void initStrips(int countSheets)
    {
        LinearSheetDimensionItem[] sheets = FacadeContext.getFurnitureFacade().initSheets(stripsEntity, furnitures, countSheets);

        List<Segment> segments = FacadeContext.getFurnitureFacade().initSegments(sheets);
        Strips strips = new Strips(segments);
        strips.setAllowRotation(false);
        strips.setSawWidth(stripsEntity.getFurnitureType().getCutter().getSawcutWidth().intValue());
        strips.setStripsEntity(stripsEntity);
        cutSettings.setStrips(strips);
    }

    public CutSettings getCutSettings()
    {
        return cutSettings;
    }

    private int initElements()
    {
        int countSheets = 0;
        LinearElementDimensionItem[] furnitureItems = new LinearElementDimensionItem[furnitures.size()];
        List<Element> elements = new ArrayList<Element>();

        for (int i = 0; i < furnitures.size(); i++)
        {
            FurnitureLink furnitureLink = furnitures.get(i);
            countSheets += furnitureLink.getAmount() * furnitureLink.getOrderItem().getAmount();

            furnitureItems[i] = new LinearElementDimensionItem(furnitureLink);
            furnitureItems[i].setNumber(i + 1);
            int count = furnitureItems[i].getCount();
            while (count > 0)
            {
                Element element = new Element(furnitureItems[i]);
                DimensionsHelper helper = new FurnitureDimensionHelper();
                helper.fillWithDimensionItem(element, furnitureItems[i]);
                elements.add(element);
                count--;
            }
        }

        cutSettings.setElements(elements.toArray(new Element[elements.size()]));

        return countSheets;
    }


}
