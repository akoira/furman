import by.dak.repository.IRepositoryService;
import com.mongodb.gridfs.GridFSDBFile;
import org.apache.commons.io.FileUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.data.mongodb.gridfs.GridFsOperations;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

public class MongoRepositoryTest
{
    private ApplicationContext ctx = new GenericXmlApplicationContext("by/dak/repository/spring-configuration.xml");
    private IRepositoryService repositoryService = ctx.getBean(IRepositoryService.class);


    public static void main(String[] args)
    {
        MongoRepositoryTest test = new MongoRepositoryTest();
        test.addNewFile();
    }

    private static void getFile(GridFsOperations fsOperations)
    {
        List<GridFSDBFile> testObjects = fsOperations.find(null);

        if (testObjects.size() > 0)
        {
            GridFSDBFile gridFSDBFile = testObjects.get(0);
        }
    }

    private void addNewFile()
    {
        long time = System.currentTimeMillis();
        System.out.println();
        InputStream stream = null;

        try
        {
            stream = MongoRepositoryTest.class.getResourceAsStream("test.pdf");
            String uuid = UUID.randomUUID().toString();
            repositoryService.store(stream, uuid);

            stream = repositoryService.read(uuid);

            FileUtils.copyInputStreamToFile(stream, new File("test.pdf"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (stream != null)
                try
                {
                    stream.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
        }

        time = System.currentTimeMillis() - time;
        System.out.println(time);

    }
}
