package by.dak.utils;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Future;

public class CollectionUtils
{
    public static boolean isEmpty(Collection collection)
    {
        return collection == null || collection.isEmpty();
    }

    public static void waitWhileAllDone(List<Future> futures)
    {
        boolean wait = true;
        while (wait)
        {
            wait = false;
            for (Future future : futures)
            {
                if (!future.isDone())
                {
                    wait = true;
                    break;
                }
            }
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                break;
            }
        }

    }


}
