package by.dak.swing.image;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * User: akoyro
 * Date: 03.03.11
 * Time: 14:32
 */
public enum ImageType
{
    jpeg("JPEG Image", "jpeg", "jpg"),
    gif("GIF Image", "gif"),
    tiff("TIFF Image", "tiff", "tif"),
    png("PNG Image", "png");


    ImageType(String description, String... extension)
    {
        this.description = description;
        this.extentions.addAll(Arrays.asList(extension));
    }

    private List<String> extentions = new ArrayList<String>();
    private String description;


    /*
    * Get the extension of a file.
    */
    public static ImageType getImageTypeBy(File f)
    {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 && i < s.length() - 1)
        {
            ext = s.substring(i + 1).toLowerCase();
        }
        return getImageTypeBy(ext);
    }

    public static ImageType getImageTypeBy(String extention)
    {
        ImageType[] imageTypes = ImageType.values();
        for (ImageType imageType : imageTypes)
        {
            if (imageType.extentions.contains(extention))
            {
                return imageType;
            }
        }
        return null;
    }

    public String getDescription()
    {
        return description;
    }
}
