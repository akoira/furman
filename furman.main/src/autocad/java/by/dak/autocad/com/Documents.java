package by.dak.autocad.com;

/**
 * User: akoyro
 * Date: 27.03.11
 * Time: 20:33
 */
public class Documents extends ASet<Document>
{
    public Documents(Application application)
    {
        super(application.getProperty("Documents").getDispatch());
    }

    public void Open(String fullPath)
    {
        /**
         * Application.Documents.Open
         * Application.Documents.Add
         */
        this.invoke("Open", fullPath);
    }

}
