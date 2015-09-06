package by.dak.plastic.swing;

import by.dak.cutting.afacade.AFacade;
import by.dak.cutting.facade.PriceAwareFacade;
import by.dak.cutting.facade.PricedFacade;
import by.dak.cutting.swing.DComboBox;
import by.dak.order.replace.swing.AEditorsProvider;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.BoardDef;
import by.dak.persistence.entities.TextureEntity;
import by.dak.persistence.entities.types.FurnitureType;
import org.jdesktop.swingx.autocomplete.ComboBoxCellEditor;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Vector;

/**
 * User: akoyro
 * Date: 21.07.2010
 * Time: 11:21:22
 */
public class EditorsProvider extends AEditorsProvider<BoardDef, TextureEntity>
{
    private List<SidePair> values;

    public EditorsProvider(List<SidePair> values)
    {
        this.values = values;
    }

    @Override
    protected List<BoardDef> getTypes()
    {
        return FacadeContext.getDSPPlasticDetailFacade().getPlasticBoardDefs();
    }

    @Override
    protected BoardDef getCurrentTypeBy(int row)
    {
        SidePair sidePair = values.get(row);
        if (sidePair != null)
        {
            return sidePair.getSecond().getBoardDef();
        }
        return null;
    }

    @Override
    protected List<TextureEntity> getCodesBy(BoardDef type)
    {
        return FacadeContext.getTextureFacade().findBy(type);
    }
}
