package by.dak.design.swing;

import by.dak.cutting.SpringConfiguration;
import by.dak.design.draw.components.BoardElement;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.BoardDef;
import by.dak.persistence.entities.TextureEntity;
import by.dak.test.TestUtils;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 18.08.11
 * Time: 11:39
 * To change this template use File | Settings | File Templates.
 */
public class TDElementPanel
{
    public static void main(String[] args)
    {
        new SpringConfiguration();
        DElementPanel elementPanel = new DElementPanel();
        elementPanel.init();
        BoardDef defaultBoardDef = FacadeContext.getBoardDefFacade().findDefaultBoardDef();
        TextureEntity textureEntity = FacadeContext.getTextureFacade().findTexturesBy(defaultBoardDef).get(0);
        BoardElement element = new BoardElement();
        element.setLength(1000d);
        element.setWidth(1200d);
        element.setBoardDef(defaultBoardDef);
        element.setTexture(textureEntity);
        elementPanel.setValue(element);
        TestUtils.showFrame(elementPanel, "BoardElement");

    }
}
