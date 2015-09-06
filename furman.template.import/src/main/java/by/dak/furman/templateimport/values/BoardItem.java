package by.dak.furman.templateimport.values;

/**
 * User: akoyro
 * Date: 9/22/13
 * Time: 7:08 PM
 */
public class BoardItem extends AItem<BoardDetail>
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
}
