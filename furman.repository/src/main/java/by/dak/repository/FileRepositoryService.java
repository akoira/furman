package by.dak.repository;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.data.mongodb.core.query.Query;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

/**
 * User: akoyro
 * Date: 10/6/13
 * Time: 12:08 PM
 */
public class FileRepositoryService implements IRepositoryService
{
    private File baseDir;


    @Override
    public void store(BufferedImage image, String uuid)
    {

        try
        {
            File file = File.createTempFile(uuid, uuid);
            ImageIO.write(image, "png", file);
            store(file, uuid);
        }
        catch (IOException e)
        {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void store(InputStream content, String uuid)
    {
        FileOutputStream os = null;
        try
        {
            os = new FileOutputStream(new File(baseDir, uuid));
            IOUtils.copy(content, os);
        }
        catch (IOException e)
        {
            throw new IllegalArgumentException(e);
        }
        finally
        {
            IOUtils.closeQuietly(content);
            IOUtils.closeQuietly(os);
        }
    }


    @Override
    public void store(File file, String uuid)
    {
        try
        {
            FileInputStream fileInputStream = new FileInputStream(file);
            store(fileInputStream, uuid);
        }
        catch (FileNotFoundException e)
        {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public InputStream read(String uuid)
    {
        try
        {
            File file = new File(baseDir, uuid);
            if (!file.exists())
                return null;
            FileInputStream fileInputStream = new FileInputStream(file);
            return fileInputStream;
        }
        catch (FileNotFoundException e)
        {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void read(String uuid, File toFile)
    {
        FileInputStream is = null;
        FileOutputStream os = null;
        try
        {
            is = new FileInputStream(new File(baseDir, uuid));
            os = new FileOutputStream(toFile);
            IOUtils.copy(is, os);
        }
        catch (IOException e)
        {
            throw new IllegalArgumentException(e);
        }
        finally
        {
            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(os);
        }
    }

    @Override
    public File readTempFile(String uuid)
    {
        try
        {
            File temp = File.createTempFile(uuid, uuid);
            read(uuid, temp);
            return temp;
        }
        catch (IOException e)
        {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void delete(String uuid)
    {
        File file = new File(baseDir, uuid);
        FileUtils.deleteQuietly(file);
    }


    public void setBaseDir(File baseDir)
    {
        this.baseDir = baseDir;
    }

    public File getBaseDir()
    {
        return baseDir;
    }

    @Override
    public <O> void store(O object)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public <O> List<O> find(Query query, Class<O> objectClass)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public <O> O read(Query query, Class<O> objectClass)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean exist(String uuid)
    {
        throw new UnsupportedOperationException();
    }
}
