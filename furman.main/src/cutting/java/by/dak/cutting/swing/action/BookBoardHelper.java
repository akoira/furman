package by.dak.cutting.swing.action;

import by.dak.cutting.swing.cut.SheetDimentionItem;
import by.dak.persistence.entities.StorageElementLink;

/**
 * User: akoyro
 * Date: 2/23/14
 * Time: 7:33 PM
 */
public class BookBoardHelper
{
    private SheetDimentionItem item;


    public void book()
    {
        StorageElementLink link = item.getStorageElementLink();
    }

    public void free()
    {

    }

    public SheetDimentionItem getItem()
    {
        return item;
    }

    public void setItem(SheetDimentionItem item)
    {
        this.item = item;
    }
}
