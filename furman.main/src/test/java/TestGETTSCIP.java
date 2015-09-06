import org.apache.commons.exec.*;
import org.apache.commons.exec.util.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * User: akoyro
 * Date: 27.04.11
 * Time: 9:07
 */
public class TestGETTSCIP
{
    public static void main(String[] args)
    {
        try
        {
            CommandLine cmdLine = new CommandLine("GETTSCIP.exe");
            DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();

            Executor executor = new DefaultExecutor();

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            executor.setStreamHandler(new PumpStreamHandler(outputStream));
            executor.execute(cmdLine);

            String s = new String(outputStream.toByteArray());
            String[] result = StringUtils.split(s, ":");
            System.out.println(result[1]);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


    }
}
