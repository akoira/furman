package by.dak.cutting.swing.order.data;

import by.dak.cutting.ACodeTypePair;
import by.dak.persistence.convert.TextureBoardDefPair2StringConverter;
import by.dak.persistence.entities.BoardDef;
import by.dak.persistence.entities.TextureEntity;
import by.dak.utils.convert.StringValue;

@StringValue(converterClass = TextureBoardDefPair2StringConverter.class)
public class TextureBoardDefPair extends ACodeTypePair<TextureEntity, BoardDef>
{
    public static final String PROPERTY_texture = "texture";
    public static final String PROPERTY_boardDef = "boardDef";

    public TextureBoardDefPair(TextureEntity texture, BoardDef boardDef)
    {
        setCode(texture);
        setType(boardDef);
    }

    public TextureEntity getTexture()
    {
        return getCode();
    }

    public BoardDef getBoardDef()
    {
        return getType();
    }

    public void setTexture(TextureEntity texture)
    {
        this.setCode(texture);
    }

    public void setBoardDef(BoardDef boardDef)
    {
        this.setType(boardDef);
    }

    public static TextureBoardDefPair valueOf(TextureBoardDefPair pair)
    {
        return new TextureBoardDefPair(pair.getCode(), pair.getType());
    }
}
