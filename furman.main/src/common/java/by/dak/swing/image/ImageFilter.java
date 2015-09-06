package by.dak.swing.image;

/**
 * User: akoyro
 * Date: 03.03.11
 * Time: 14:29
 */

import javax.swing.filechooser.FileFilter;
import java.io.File;

/* ImageFilter.java is used by FileChooserDemo2.java. */
public class ImageFilter extends FileFilter
{

    //Accept all directories and all gif, jpg, tiff, or png files.
    public boolean accept(File f)
    {
        if (f.isDirectory())
        {
            return true;
        }

        ImageType imageType = ImageType.getImageTypeBy(f);
        return imageType != null;
    }

    //The description of this filter
    public String getDescription()
    {
        return "Just Images";
    }
}
