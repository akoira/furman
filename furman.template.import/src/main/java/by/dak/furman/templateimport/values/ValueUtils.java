package by.dak.furman.templateimport.values;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User: akoyro
 * Date: 9/22/13
 * Time: 11:06 AM
 */
public class ValueUtils
{

    public static AValue[] getPath(AValue value)
    {
        List<AValue> path = new ArrayList<AValue>();
        addToPath(value, path);
        Collections.reverse(path);
        return path.toArray(new AValue[path.size()]);

    }

    private static void addToPath(AValue value, List<AValue> path)
    {
        path.add(value);
        if (value.getParent() != null)
            addToPath(value.getParent(), path);
    }

}
