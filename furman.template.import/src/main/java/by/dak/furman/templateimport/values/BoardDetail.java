package by.dak.furman.templateimport.values;

import org.apache.commons.lang3.StringUtils;

/**
 * User: akoyro
 * Date: 5/1/13
 * Time: 11:10 AM
 */
public class BoardDetail extends ADetail
{
    private String boardDef;
    private String borderDef;

    public String getBoardDef()
    {
        return boardDef;
    }

    public void setBoardDef(String boardDef)
    {
        this.boardDef = boardDef;
    }

    public String getBorderDef()
    {
        return borderDef;
    }

    public void setBorderDef(String borderDef)
    {
        this.borderDef = borderDef;
    }

    public String getCode()
    {
        return getValue(1);
    }

    public Double getLength()
    {
        return Double.valueOf(StringUtils.trimToNull(getValue(Column.length.getIndex()).replace('/', ' ')));
    }

    public Double getWidth()
    {
        return Double.valueOf(StringUtils.trimToNull(getValue(Column.width.getIndex()).replace('/', ' ')));
    }

    public Integer getAmount()
    {
        return Integer.valueOf(StringUtils.trimToNull(getValue(Column.amount.getIndex()).replace("шт.", StringUtils.EMPTY)));
    }

    public String getHoles()
    {
        return StringUtils.trimToNull(getValue(Column.holes.getIndex()).replace("отв.", StringUtils.EMPTY));
    }

    public String getNote()
    {
        String value = getValue(Column.note.getIndex());
        return value;
    }

    public static enum Column
    {
        number(0, "№"),
        code(1, "КОД"),
        length(2, "ДЛИНА"),
        width(3, "ШИРИНА"),
        amount(4, "КОЛ-ВО деталей"),
        height(5, "ТОЛЩИНА"),
        pic(6, "Рис.-№"),
        holes(7, "Кол. отверстий"),
        note(8, "ПРИМЕЧАНИЕ");
        private int index;
        private String label;

        Column(int index, String label)
        {
            this.index = index;
            this.label = label;
        }

        public int getIndex()
        {
            return index;
        }

        public String getLabel()
        {
            return label;
        }
    }

}
