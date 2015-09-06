/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.cut.measurement.utils;

import java.io.FileNotFoundException;

/**
 * @author Peca
 */
public class TextWriter extends CsvWriter
{

    public TextWriter(String fileName) throws FileNotFoundException
    {
        super(fileName);
    }

    public TextWriter(String fileName, boolean append) throws FileNotFoundException
    {
        super(fileName, append);
    }

    @Override
    protected String getFieldSeparator()
    {
        return " ";
    }

}
