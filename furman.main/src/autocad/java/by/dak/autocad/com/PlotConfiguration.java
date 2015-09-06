package by.dak.autocad.com;

import com.jacob.com.Dispatch;

/**
 * User: akoyro
 * Date: 28.03.11
 * Time: 11:16
 */
public class PlotConfiguration extends AObject
{
    public static final String CONFIG_NAME_PublishToWeb_JPG = "PublishToWeb JPG.pc3";

    public PlotConfiguration(Dispatch dispatchToBeWrapped)
    {
        super(dispatchToBeWrapped);
    }

    public String getConfigName()
    {
        return this.getPropertyAsString("ConfigName");
    }

    public void setConfigName(String configName)
    {
        this.setProperty("ConfigName", configName);
    }

    public String getCanonicalMediaName()
    {
        return this.getPropertyAsString("CanonicalMediaName");
    }

    public void setCanonicalMediaName(String canonicalMediaName)
    {
        this.setProperty("CanonicalMediaName", canonicalMediaName);
    }


}
