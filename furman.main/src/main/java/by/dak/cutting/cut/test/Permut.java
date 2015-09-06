/*
 * Permut.java
 *
 * Created on 9. kv�ten 2007, 10:47
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the draw.
 */

package by.dak.cutting.cut.test;

import java.util.ArrayList;

/**
 * @author Peca
 */
public class Permut
{

    public static long fact(int n)
    {
        long f = 1;
        for (int i = 2; i <= n; i++)
        {
            f = f * i;
        }
        return f;
    }

    public static Object[] permute(long k, Object[] list)
    {
        ArrayList list2 = new ArrayList(list.length);
        for (int i = 0; i < list.length; i++) list2.add(list[i]);
        int index = 0;
        Object[] list3 = list.clone();
        for (int n = list.length - 1; n >= 0; n--)
        {
            long fn = fact(n);
            int i = (int) (k / fn);
            k = k % fn;
            list3[index++] = list2.remove(i);
        }
        return list3;
    }

    public static void main(String args[])
    {
        String[] list = {"a", "b", "c", "d"};
        
       /* for(int j=0; j < fact(list.length); j++){
            String[] data = (String[])permute(j, list);
            for(int n=0; n < list.length; n++){
                System.out.print(data[n]);
            }
            System.out.println();
        }*/
        for (int i = 0; i < 1000; i++)
        {
            for (int j = 0; j < fact(list.length); j++)
            {
                permute(j, list);
            }
        }
    }

}
