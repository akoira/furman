package by.dak.furman.templateimport.parser.detail;

import by.dak.furman.templateimport.values.BoardDetail;

/**
 * User: akoyro
 * Date: 9/24/13
 * Time: 11:18 PM
 */
public class BoardDetailParser extends ADetailParser<BoardDetail>
{
    @Override
    protected BoardDetail createValue()
    {
        BoardDetail boardDetail = new BoardDetail();
        return new BoardDetail();
    }
}
