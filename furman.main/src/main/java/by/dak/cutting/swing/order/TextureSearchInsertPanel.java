package by.dak.cutting.swing.order;

import by.dak.cutting.swing.order.data.OrderDetailsDTO;
import by.dak.cutting.swing.renderer.EntityListRenderer;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.BoardDef;
import by.dak.persistence.entities.Manufacturer;
import by.dak.persistence.entities.TextureEntity;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class TextureSearchInsertPanel extends SearchInsertPanel
{

    public static HashMap<BoardDef, List<TextureEntity>> cacheTextures;

    public TextureSearchInsertPanel()
    {
        super(false);
    }

    public TextureSearchInsertPanel(boolean isRenderer)
    {
        super(isRenderer);
    }

    @Override
    protected void showDialog()
    {
        setCallNew(true);
    }

    @Override
    protected void initEnvironment()
    {
        super.initEnvironment();
        dComboBox.setRenderer(new EntityListRenderer<TextureEntity>());
    }

    @Override
    protected void bind()
    {
        if (cacheTextures == null)
        {
            cacheTextures = new HashMap<BoardDef, List<TextureEntity>>();
        }

        OrderDetailsDTO dto = getData();
        List<TextureEntity> textures = new ArrayList<TextureEntity>();
        if (dto != null)
        {
            BoardDef boardDef = dto.getBoardDef();
            Manufacturer manufacturer = dto.getManufacturer();
            if (manufacturer != null)
            {
                textures = FacadeContext.getTextureFacade().findTexturesBy(boardDef, manufacturer);
            }
            else
            {
                textures = FacadeContext.getTextureFacade().findTexturesBy(boardDef);
            }
        }
        dComboBox.setModel(new DefaultComboBoxModel(new Vector<TextureEntity>(textures)));
    }


    @Override
    public void setData(OrderDetailsDTO data)
    {
        super.setData(data);
        dComboBox.setSelectedItem(data.getTexture());

    }
}
