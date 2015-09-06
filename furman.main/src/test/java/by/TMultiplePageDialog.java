package by;

import com.jidesoft.dialog.AbstractDialogPage;
import com.jidesoft.dialog.MultiplePageDialog;
import com.jidesoft.dialog.PageList;

/**
 * User: akoyro
 * Date: 29.03.2010
 * Time: 14:31:26
 */
public class TMultiplePageDialog
{
    public static void main(String[] args)
    {
        AbstractDialogPage page = new AbstractDialogPage()
        {
            @Override
            public void lazyInitialize()
            {

            }
        };
        page.setTitle("Test");
        page.setDescription("Test");
        PageList pageList = new PageList();
        pageList.append(page);
        MultiplePageDialog pageDialog = new MultiplePageDialog();
        pageDialog.setStyle(MultiplePageDialog.TREE_STYLE);
        pageDialog.setPageList(pageList);
        pageDialog.setVisible(true);
    }
}
