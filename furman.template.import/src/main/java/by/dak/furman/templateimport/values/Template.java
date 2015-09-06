package by.dak.furman.templateimport.values;

import java.awt.image.BufferedImage;

/**
 * User: akoyro
 * Date: 5/1/13
 * Time: 4:51 PM
 */
public class Template extends AFileValue<TemplateCategory, AItem>
{
    private Description description = new Description();
    private BufferedImage image;

    public Description getDescription()
    {
        return description;
    }

    public void setDescription(Description description)
    {
        this.description = description;
    }

    public BufferedImage getImage()
    {
        return image;
    }

    public void setImage(BufferedImage image)
    {
        this.image = image;
    }
}
