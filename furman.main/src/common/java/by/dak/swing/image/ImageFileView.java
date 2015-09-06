package by.dak.swing.image;

/**
 * User: akoyro
 * Date: 03.03.11
 * Time: 14:31
 */

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.filechooser.FileView;
import java.awt.*;
import java.io.File;
import java.util.Hashtable;

/* ImageFileView.java is used by FileChooserDemo2.java. */
public class ImageFileView extends FileView
{
    protected Icon directoryIcon = null;
    protected Icon fileIcon = null;
    protected Icon computerIcon = null;
    protected Icon hardDriveIcon = null;
    protected Icon floppyDriveIcon = null;

    protected Icon newFolderIcon = null;
    protected Icon upFolderIcon = null;
    protected Icon homeFolderIcon = null;
    protected Icon listViewIcon = null;
    protected Icon detailsViewIcon = null;
    private JFileChooser fileChooser;
    protected Hashtable<File, Icon> iconCache = new Hashtable<File, Icon>();


    public ImageFileView(JFileChooser fileChooser)
    {
        this.fileChooser = fileChooser;
        installIcons();
    }

    private void installIcons()

    {
        directoryIcon = UIManager.getIcon("FileView.directoryIcon");
        fileIcon = UIManager.getIcon("FileView.fileIcon");
        computerIcon = UIManager.getIcon("FileView.computerIcon");
        hardDriveIcon = UIManager.getIcon("FileView.hardDriveIcon");
        floppyDriveIcon = UIManager.getIcon("FileView.floppyDriveIcon");

        newFolderIcon = UIManager.getIcon("FileChooser.newFolderIcon");
        upFolderIcon = UIManager.getIcon("FileChooser.upFolderIcon");
        homeFolderIcon = UIManager.getIcon("FileChooser.homeFolderIcon");
        detailsViewIcon = UIManager.getIcon("FileChooser.detailsViewIcon");
        listViewIcon = UIManager.getIcon("FileChooser.listViewIcon");
    }


    public String getDescription(File f)
    {
        ImageType imageType = ImageType.getImageTypeBy(f);
        if (imageType != null)
        {
            return imageType.getDescription();
        }
        else
        {
            return null;
        }
    }

    public Boolean isTraversable(File f)
    {
        return null; //let the L&F FileView figure this out
    }


    public void clearIconCache()
    {
        iconCache = new Hashtable<File, Icon>();
    }

    public String getName(File f)
    {
        // Note: Returns display name rather than file name
        String fileName = null;
        if (f != null)
        {
            fileName = fileChooser.getFileSystemView().getSystemDisplayName(f);
        }
        return fileName;
    }

    public String getTypeDescription(File f)
    {
        return fileChooser.getFileSystemView().getSystemTypeDescription(f);
    }

    public Icon getCachedIcon(File f)
    {
        return (Icon) iconCache.get(f);
    }

    public void cacheIcon(File f, Icon i)
    {
        if (f == null || i == null)
        {
            return;
        }
        iconCache.put(f, i);
    }

    public Icon getIcon(File f)
    {
        Icon icon = getCachedIcon(f);
        if (icon != null)
        {
            return icon;
        }
        ImageType imageType = ImageType.getImageTypeBy(f);
        if (imageType != null)
        {
            ImageIcon imageIcon = new ImageIcon(f.getAbsolutePath());
            Image image = imageIcon.getImage().getScaledInstance(42, 42, Image.SCALE_DEFAULT);
            icon = new ImageIcon(image);
        }
        else
        {

            icon = fileIcon;
            if (f != null)
            {
                FileSystemView fsv = fileChooser.getFileSystemView();

                if (fsv.isFloppyDrive(f))
                {
                    icon = floppyDriveIcon;
                }
                else if (fsv.isDrive(f))
                {
                    icon = hardDriveIcon;
                }
                else if (fsv.isComputerNode(f))
                {
                    icon = computerIcon;
                }
                else if (f.isDirectory())
                {
                    icon = directoryIcon;
                }
            }
            cacheIcon(f, icon);
        }
        return icon;
    }

    public Boolean isHidden(File f)
    {
        String name = f.getName();
        if (name != null && name.charAt(0) == '.')
        {
            return Boolean.TRUE;
        }
        else
        {
            return Boolean.FALSE;
        }
    }
}
