package by.dak.cutting.swing.cut;

import by.dak.cutting.cut.base.Element;
import by.dak.cutting.cut.gui.CutSettings;
import by.dak.cutting.cut.gui.cuttingApp.DimensionItem;
import by.dak.cutting.cut.guillotine.Segment;
import by.dak.cutting.cut.guillotine.Strips;
import by.dak.cutting.cut.guillotine.helper.BoardDimensionsHelper;
import by.dak.cutting.cut.guillotine.helper.DimensionsHelper;
import by.dak.cutting.swing.order.data.TextureBoardDefPair;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.cutting.entities.ABoardStripsEntity;
import by.dak.persistence.entities.AOrderBoardDetail;
import by.dak.persistence.entities.Cutter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Класс создает CutSettings для набора OrderFurniture и соответствующего материала
 * User: akoyro
 * Date: 11.03.2009
 * Time: 16:12:01
 */
public class CutSettingsCreator
{
    private CutSettings cutSettings;

    private List<? extends AOrderBoardDetail> furnitures;
    private TextureBoardDefPair pair;
    private Cutter cutter;
    private ABoardStripsEntity stripsEntity;


    public CutSettingsCreator create()
    {
        cutSettings = new CutSettings();
        cutSettings.setCutter(this.cutter);
        int countSheets = initElements();
        initStrips(countSheets);
        return this;
    }

    /**
     * подготовка strips для cutSettings
     *
     * @param countSheets
     */
    protected void initStrips(int countSheets)
    {
        SheetDimentionItem[] sheets = FacadeContext.getBoardFacade().initSheets(stripsEntity, pair, countSheets);
        List<Segment> segments = FacadeContext.getBoardFacade().initSegments(sheets);
        Strips strips = new Strips(segments);
        strips.setAllowRotation(pair.getTexture().isRotatable());
        strips.setSawWidth(cutter.getSawcutWidth().intValue());
        strips.setStripsEntity(stripsEntity);
        cutSettings.setStrips(strips);
    }

    /**
     * подготовка элементов для cutSettings
     *
     * @return
     */
    private int initElements()
    {
        ElementDimensionItem[] furnitureItems = new ElementDimensionItem[furnitures.size()];

        int countSheets = 0;
        for (int i = 0; i < furnitures.size(); i++)
        {
            AOrderBoardDetail boardDetail = furnitures.get(i);

            furnitureItems[i] = new ElementDimensionItem(boardDetail, cutter);
            countSheets += boardDetail.getAmount() * boardDetail.getOrderItem().getAmount();
        }
        Arrays.sort(furnitureItems, new Comparator<ElementDimensionItem>()
        {
            @Override
            public int compare(ElementDimensionItem o1, ElementDimensionItem o2)
            {
                //boolean result = (o1.getWidth() * o1.getHeight()) < (o2.getWidth() * o2.getHeight());
				return o1.getHeight() - o2.getHeight();
			}
        });

        List<Element> elements = new ArrayList<Element>();
        for (DimensionItem item : furnitureItems)
        {
            int count = item.getCount();
            for (int i = 0; i < count; i++)
            {
                Element element = new Element(item);
                DimensionsHelper helper = new BoardDimensionsHelper();
                helper.fillWithDimensionItem(element, item);
                elements.add(element);
            }
        }
        cutSettings.setElements(elements.toArray(new Element[elements.size()]));

        return countSheets;
    }


    public CutSettings getCutSettings()
    {
        return cutSettings;
    }

    public List<? extends AOrderBoardDetail> getFurnitures()
    {
        return furnitures;
    }

    public void setFurnitures(List<? extends AOrderBoardDetail> furnitures)
    {
        this.furnitures = furnitures;
    }

    public TextureBoardDefPair getPair()
    {
        return pair;
    }

    public void setPair(TextureBoardDefPair pair)
    {
        this.pair = pair;
    }

    public Cutter getCutter()
    {
        return cutter;
    }

    public void setCutter(Cutter cutter)
    {
        this.cutter = cutter;
    }

    public ABoardStripsEntity getStripsEntity()
    {
        return stripsEntity;
    }

    public void setStripsEntity(ABoardStripsEntity stripsEntity)
    {
        this.stripsEntity = stripsEntity;
    }


}
