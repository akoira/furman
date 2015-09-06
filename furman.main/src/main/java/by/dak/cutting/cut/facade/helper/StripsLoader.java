package by.dak.cutting.cut.facade.helper;

import by.dak.cutting.cut.facade.ABoardStripsFacade;
import by.dak.cutting.cut.guillotine.Strips;
import by.dak.cutting.swing.cut.CuttingModel;
import by.dak.cutting.swing.order.data.TextureBoardDefPair;
import by.dak.persistence.cutting.entities.ABoardStripsEntity;
import by.dak.persistence.cutting.entities.StripsXmlSerializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 27.09.11
 * Time: 13:30
 * To change this template use File | Settings | File Templates.
 */
public class StripsLoader
{
    private ABoardStripsFacade boardStripsFacade;
    private ExecutorService service;
    private CuttingModel cuttingModel;

    public CuttingModel getCuttingModel()
    {
        return cuttingModel;
    }

    public void setCuttingModel(CuttingModel cuttingModel)
    {
        this.cuttingModel = cuttingModel;
    }

    protected Strips loadStrips(TextureBoardDefPair pair)
    {
        ABoardStripsEntity stripsEntity = boardStripsFacade.findUniqueStrips(getCuttingModel().getOrder(),
                pair.getTexture(), pair.getBoardDef());
        if (stripsEntity != null)
        {
            Strips strips = StripsXmlSerializer.getInstance().unserialize(stripsEntity.getStrips());
            if (strips != null)
            {
                strips.setStripsEntity(stripsEntity);
            }
            return strips;
        }
        return null;
    }

    public CuttingModel load()
    {
        service = Executors.newCachedThreadPool();
        final HashMap<TextureBoardDefPair, Strips> hashMap = new HashMap<TextureBoardDefPair, Strips>();

        List<TextureBoardDefPair> pairs = cuttingModel.getPairs();
        List<StripsCallable> callables = new ArrayList<StripsCallable>();
        for (final TextureBoardDefPair pair : pairs)
        {
            StripsCallable stripsCallable = new StripsCallable(pair);
            callables.add(stripsCallable);
        }
        try
        {
            List<Future<Strips>> futures = service.invokeAll(callables);
            boolean done = true;
            do
            {
                for (int i = 0; i < futures.size(); i++)
                {
                    Future<Strips> stripsFuture = futures.get(i);
                    StripsCallable stripsCallable = callables.get(i);
                    if (!hashMap.containsKey(stripsCallable.getPair()))
                    {
                        if (stripsFuture.isDone())
                        {
                            Strips strips = stripsFuture.get();
                            if (strips != null)
                            {
                                hashMap.put(stripsCallable.getPair(), strips);
                            }
                        }
                        else
                        {
                            done = false;
                        }
                    }
                }
            }
            while (!done);
        }
        catch (Throwable e)
        {
            throw new IllegalArgumentException(e);
        }
        finally
        {
            service.shutdown();
        }
        cuttingModel.setStripsMap(hashMap);
        return cuttingModel;
    }

    public ABoardStripsFacade getBoardStripsFacade()
    {
        return boardStripsFacade;
    }

    public void setBoardStripsFacade(ABoardStripsFacade boardStripsFacade)
    {
        this.boardStripsFacade = boardStripsFacade;
    }


    private class StripsCallable implements Callable<Strips>
    {
        private TextureBoardDefPair pair;

        public StripsCallable(TextureBoardDefPair pair)
        {
            this.pair = pair;
        }

        @Override
        public Strips call() throws Exception
        {
            return loadStrips(getPair());
        }

        public TextureBoardDefPair getPair()
        {
            return pair;
        }
    }
}
