/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.cut.guillotine;

import by.dak.cutting.cut.common.Common;
import by.dak.cutting.cut.genetics.IndividualSign;

import java.util.Arrays;

/**
 * @author Peca
 */
public class GIndividualSign extends IndividualSign
{
    private int[] indexes;
    private static int hashSalt = Common.nextInt();

    public GIndividualSign(int[] indexes)
    {
        this.indexes = indexes;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof GIndividualSign)) return false;
        return Arrays.equals(indexes, ((GIndividualSign) obj).indexes);
    }

    @Override
    public int hashCode()
    {
        //return indexes.hashCode();
        int res = 0;
        int l = indexes.length;
        for (int i = 0; i < indexes.length; i++)
        {
            res += indexes[i] * (i + hashSalt + l);
        }
        return res;
        //return 0;
    }

    @Override
    public int compare(IndividualSign sign)
    {
        if (!(sign instanceof GIndividualSign)) return -1;
        GIndividualSign asign = (GIndividualSign) sign;
        for (int i = 0; i < Math.min(this.indexes.length, asign.indexes.length); i++)
        {
            int res = this.indexes[i] - asign.indexes[i];
            if (res != 0) return res;
        }
        return 0;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for (int i : indexes)
        {
            if (sb.length() > 0) sb.append(',');
            sb.append(i);
        }
        return sb.toString();
    }


}
