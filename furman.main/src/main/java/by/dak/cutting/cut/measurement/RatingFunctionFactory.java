/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.cut.measurement;

import by.dak.cutting.cut.base.Element;
import by.dak.cutting.cut.genetics.RatingFunction;
import by.dak.cutting.cut.guillotine.BRatingFunction;
import by.dak.cutting.cut.guillotine.CRatingFunction;
import by.dak.cutting.cut.guillotine.GRatingFunction;
import by.dak.cutting.cut.guillotine.Strips;

import java.lang.reflect.Type;

/**
 * @author Peca
 */
public class RatingFunctionFactory
{

    public static RatingFunction createInstance(Strips strips, Element[] elements, Type type)
    {
        if (GRatingFunction.class == type)
        {
            return new GRatingFunction(strips, elements);
        }
        else if (BRatingFunction.class == type)
        {
            return new BRatingFunction(strips, elements);
        }
        else if (CRatingFunction.class == type)
        {
            return new CRatingFunction(strips, elements);
        }
        else
        {
            throw new IllegalArgumentException("invalid individual type");
        }
    }
}
