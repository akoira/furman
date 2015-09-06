package by.dak.cutting.swing.order.popup;

import by.dak.cutting.swing.order.BorderSearchInsertPanel;
import by.dak.cutting.swing.order.SearchInsertPanel;
import by.dak.cutting.swing.order.TextureSearchInsertPanel;
import by.dak.cutting.swing.order.data.DTO;
import by.dak.cutting.swing.order.data.Glueing;
import by.dak.cutting.swing.order.data.OrderDetailsDTO;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.BorderDefEntity;
import by.dak.persistence.entities.TextureEntity;
import by.dak.utils.convert.Converter;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.table.TableCellEditor;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;


public class GlueingSideMenu extends CommonSideMenu
{

    protected TextureSearchInsertPanel upTextureCombo;
    protected TextureSearchInsertPanel leftTextureCombo;
    protected TextureSearchInsertPanel rightTextureCombo;
    protected TextureSearchInsertPanel downTextureCombo;

    protected BorderSearchInsertPanel upBorderCombo;
    protected BorderSearchInsertPanel leftBorderCombo;
    protected BorderSearchInsertPanel rightBorderCombo;
    protected BorderSearchInsertPanel downBorderCombo;
    //todo maybe refactored and moved default definition  to other place
    private static BorderDefEntity defaultBorderDefEntity;

    public GlueingSideMenu(TableCellEditor tableCellEditor)
    {
        super(tableCellEditor);
    }

    protected void initComponents()
    {
        super.initComponents();
        upTextureCombo = new TextureSearchInsertPanel();
        leftTextureCombo = new TextureSearchInsertPanel();
        rightTextureCombo = new TextureSearchInsertPanel();
        downTextureCombo = new TextureSearchInsertPanel();

        upBorderCombo = new BorderSearchInsertPanel();
        initBorderCombo(upBorderCombo, upTextureCombo);
        leftBorderCombo = new BorderSearchInsertPanel();
        initBorderCombo(leftBorderCombo, leftTextureCombo);
        rightBorderCombo = new BorderSearchInsertPanel();
        initBorderCombo(rightBorderCombo, rightTextureCombo);
        downBorderCombo = new BorderSearchInsertPanel();
        initBorderCombo(downBorderCombo, downTextureCombo);
        defaultBorderDefEntity = getDefault();
    }

    private void initBorderCombo(final BorderSearchInsertPanel borderCombo,
                                 final TextureSearchInsertPanel textureCombo)
    {
        borderCombo.getDComboBox().addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                BorderDefEntity borderDef = (BorderDefEntity) borderCombo.getDComboBox().getSelectedItem();
                Vector<TextureEntity> vector = new Vector<TextureEntity>();
                if (borderDef != null)
                {
                    List<TextureEntity> textures = FacadeContext.getTextureFacade().findTexturesBy(borderDef);
                    vector.addAll(textures);
                }
                textureCombo.getDComboBox().setModel(new DefaultComboBoxModel(vector));
                if (borderDef != null)
                {
                    textureCombo.getDComboBox().setSelectedItem(getTexture(null, borderDef, getData().getTexture()));
                }
                else
                    textureCombo.getDComboBox().setSelectedItem(null);
            }
        });
    }

    public void buildView()
    {
        //, 5dlu, 35dlu, 30dlu, 1dlu
        //"10dlu, 5dlu, 10dlu, 5dlu, 10dlu, 5dlu, 10dlu, 5dlu, 40dlu"
        FormLayout layout = new FormLayout("25dlu, 0dlu, 12dlu, 5dlu, 80dlu, 40dlu , 5dlu",
                "5dlu, 12dlu,1dlu,12dlu, " +
                        "5dlu, 12dlu, 1dlu, 12dlu," +
                        "5dlu, 12dlu, 1dlu, 12dlu," +
                        "5dlu, 12dlu, 1dlu, 12dlu," +
                        "5dlu, 20dlu");
        DefaultFormBuilder builder = new DefaultFormBuilder(layout/*, new FormDebugPanel()*/);


        builder.nextRow();
        addLine(builder, upLabel, upChBox, upTextureCombo, upBorderCombo);
        builder.nextRow();
        addLine(builder, leftLabel, leftChBox, leftTextureCombo, leftBorderCombo);
        builder.nextRow();
        addLine(builder, rightLabel, rightChBox, rightTextureCombo, rightBorderCombo);
        builder.nextRow();
        addLine(builder, downLabel, downChBox, downTextureCombo, downBorderCombo);
        builder.nextRow();
        builder.append(canvas, 4);
        builder.append(getOkButton());
        JPanel panel = builder.getPanel();
        panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        add(panel);
    }

    private void addLine(DefaultFormBuilder builder, JLabel label, JCheckBox checkBox, SearchInsertPanel tComboBox, SearchInsertPanel bComboBox)
    {
        builder.append(label);
        builder.append(checkBox);
        builder.append(tComboBox.buildView(), 2);
        builder.nextRow();
        builder.append(Converter.EMPTY_STRING);
        builder.append(Converter.EMPTY_STRING);
        builder.append(bComboBox.buildView(), 2);
    }

    @Override
    public void updateData()
    {
        Glueing dto = (Glueing) getDTO();
        dto.setUp(upChBox.isSelected());
        dto.setUpTexture(dto.isUp() && upTextureCombo.getDComboBox().getSelectedItem() != null
                ? (TextureEntity) upTextureCombo.getDComboBox().getSelectedItem() : null);
        dto.setDown(downChBox.isSelected());
        dto.setDownTexture(dto.isDown() && downTextureCombo.getDComboBox().getSelectedItem() != null
                ? (TextureEntity) downTextureCombo.getDComboBox().getSelectedItem() : null);
        dto.setLeft(leftChBox.isSelected());
        dto.setLeftTexture(dto.isLeft() && leftTextureCombo.getDComboBox().getSelectedItem() != null
                ? (TextureEntity) leftTextureCombo.getDComboBox().getSelectedItem() : null);
        dto.setRight(rightChBox.isSelected());
        dto.setRightTexture(dto.isRight() && rightTextureCombo.getDComboBox().getSelectedItem() != null
                ? (TextureEntity) rightTextureCombo.getDComboBox().getSelectedItem() : null);

        dto.setUpBorderDef(dto.isUp() && upBorderCombo.getDComboBox().getSelectedItem() != null
                ? (BorderDefEntity) upBorderCombo.getDComboBox().getSelectedItem() : null);
        dto.setDownBorderDef(dto.isDown() && downBorderCombo.getDComboBox().getSelectedItem() != null
                ? (BorderDefEntity) downBorderCombo.getDComboBox().getSelectedItem() : null);
        dto.setLeftBorderDef(dto.isLeft() && leftBorderCombo.getDComboBox().getSelectedItem() != null
                ? (BorderDefEntity) leftBorderCombo.getDComboBox().getSelectedItem() : null);
        dto.setRightBorderDef(dto.isRight() && rightBorderCombo.getDComboBox().getSelectedItem() != null
                ? (BorderDefEntity) rightBorderCombo.getDComboBox().getSelectedItem() : null);

        refreshComponent();
    }

    @Override
    protected DTO getDTO()
    {
        OrderDetailsDTO fullDTO = getData();
        Glueing dto = fullDTO.getGlueing();
        if (dto == null)
        {
            dto = new Glueing();
            getData().setGlueing(dto);
        }
        return dto;
    }

    @Override
    protected void flushComponentValues()
    {
        super.flushComponentValues();
        upTextureCombo.getDComboBox().setSelectedIndex(-1);
        downTextureCombo.getDComboBox().setSelectedIndex(-1);
        rightTextureCombo.getDComboBox().setSelectedIndex(-1);
        leftTextureCombo.getDComboBox().setSelectedIndex(-1);
        upBorderCombo.getDComboBox().setSelectedIndex(-1);
        downBorderCombo.getDComboBox().setSelectedIndex(-1);
        rightBorderCombo.getDComboBox().setSelectedIndex(-1);
        leftBorderCombo.getDComboBox().setSelectedIndex(-1);
    }

    @Override
    protected void mergeCompAndValues()
    {
        super.mergeCompAndValues();
        Glueing glueing = (Glueing) getDTO();

        updateBorderCombo(upBorderCombo, glueing.getUpBorderDef());
        updateBorderCombo(downBorderCombo, glueing.getDownBorderDef());
        updateBorderCombo(rightBorderCombo, glueing.getRightBorderDef());
        updateBorderCombo(leftBorderCombo, glueing.getLeftBorderDef());


        upTextureCombo.getDComboBox().setSelectedItem(
                getTexture(glueing.getUpTexture(), glueing.getUpBorderDef(), getData().getTexture()));
        downTextureCombo.getDComboBox().setSelectedItem(
                getTexture(glueing.getDownTexture(), glueing.getDownBorderDef(), getData().getTexture()));
        rightTextureCombo.getDComboBox().setSelectedItem(
                getTexture(glueing.getRightTexture(), glueing.getRightBorderDef(), getData().getTexture()));
        leftTextureCombo.getDComboBox().setSelectedItem(
                getTexture(glueing.getLeftTexture(), glueing.getLeftBorderDef(), getData().getTexture()));
    }

    private TextureEntity getTexture(TextureEntity glueingTexture,
                                     BorderDefEntity gluingBorderDef,
                                     TextureEntity detailTexture)
    {
        if (glueingTexture != null)
        {
            return glueingTexture;
        }
        else
        {
            //если не установлен оклеечный материал
            if (gluingBorderDef == null)
            {
                gluingBorderDef = defaultBorderDefEntity;
            }
            //если не установлена текстура детали
            if (detailTexture == null)
            {
                return null;
            }
            return FacadeContext.getTextureFacade().findBy(gluingBorderDef, detailTexture);
        }

    }

    private void updateBorderCombo(BorderSearchInsertPanel borderCombo, BorderDefEntity borderDef)
    {
        borderCombo.setData(getData());
        borderCombo.getDComboBox().setSelectedItem(borderDef != null ? borderDef : defaultBorderDefEntity);
    }


    private BorderDefEntity getDefault()
    {
        if (defaultBorderDefEntity == null)
            return FacadeContext.getBorderDefFacade().findDefaultBorderDef();
        return defaultBorderDefEntity;
    }

    @Override
    protected void createRules()
    {
        rules = new HashMap<JCheckBox, JComponent[]>();
        rules.put(upChBox, new JComponent[]{upTextureCombo, upBorderCombo});
        rules.put(downChBox, new JComponent[]{downTextureCombo, downBorderCombo});
        rules.put(rightChBox, new JComponent[]{rightTextureCombo, rightBorderCombo});
        rules.put(leftChBox, new JComponent[]{leftTextureCombo, leftBorderCombo});
    }

    @Override
    protected void refreshCompState()
    {
        for (Map.Entry<JCheckBox, JComponent[]> rule : rules.entrySet())
        {
            for (JComponent comp : rule.getValue())
                ((SearchInsertPanel) comp).setEnable(rule.getKey().isSelected());
        }
    }
}
