package by.dak.autocad.ocom;


public abstract interface AcadEntity
{
    public abstract void Delete();

    public abstract AcadDocument getDocument();

    public abstract String getObjectName();

    public abstract String getHandle();

    public abstract boolean getHasExtentionDictionary();

    public abstract AcadHyperlinks getHyperlinks();

    public abstract String getLayer();

    public abstract void setLayer(String paramString);
}
