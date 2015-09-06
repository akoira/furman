package by.dak.cutting.swing.dictionaries;

import by.dak.cutting.SpringConfiguration;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.BoardDef;
import by.dak.persistence.entities.PriceEntity;
import by.dak.test.TestUtils;

import java.util.List;

public class TTextureConfigurePanel
{
    public static void main(String[] args)
    {
        SpringConfiguration springConfiguration = new SpringConfiguration();
        TextureConfigurePanel panel = new TextureConfigurePanel();
        BoardDef boardDef = FacadeContext.getBoardDefFacade().findDefaultBoardDef();
        List<PriceEntity> list = FacadeContext.getPriceFacade().findAllBy(boardDef);
        panel.setValue(boardDef);
        panel.setPrices(list);
        TestUtils.showFrame(panel, "TextureConfigurePanel");
    }
}
