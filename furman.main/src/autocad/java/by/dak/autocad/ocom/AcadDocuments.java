package by.dak.autocad.ocom;

import com.jacob.com.Dispatch;

/**
 * User: akoyro
 * Date: 22.02.11
 * Time: 18:05
 */
public class AcadDocuments extends AcadObject
{
    AcadDocuments(AcadApplication app)
    {
        super(app.component.getProperty("Documents"), app);
    }

    public void open(String fullPath)
    {
        /**
         * Application.Documents.Open
         * Application.Documents.Add
         */
        Dispatch.call(impl.getDispatch(), "Open", fullPath);
    }

}
