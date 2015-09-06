/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.cut.measurement.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * @author Peca
 */
public class CsvWriter
{
    private FileOutputStream fileOutputStream;
    private OutputStreamWriter outputStreamWriter;

    public CsvWriter(String fileName) throws FileNotFoundException
    {
        this(fileName, false);
    }

    public CsvWriter(String fileName, boolean append) throws FileNotFoundException
    {
        fileOutputStream = new FileOutputStream(fileName, append);
        outputStreamWriter = new OutputStreamWriter(fileOutputStream);
    }

    public void close() throws IOException
    {
        outputStreamWriter.flush();
        outputStreamWriter.close();
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    protected String getFieldSeparator()
    {
        return ";";
    }

    protected String getRowSeparator()
    {
        return "\n";
    }

    public void newField() throws IOException
    {
        outputStreamWriter.write(getFieldSeparator());
    }

    public void newRow() throws IOException
    {
        outputStreamWriter.write(getRowSeparator());
    }

    public void writeObject(Object value) throws IOException
    {
        if (value instanceof Integer) writeInteger((Integer) value);
        else if (value instanceof Long) writeFloat((Long) value);
        else if (value instanceof Float) writeFloat((Float) value);
        else writeString(value.toString());
    }

    public void writeInteger(int value) throws IOException
    {
        outputStreamWriter.write(String.valueOf(value));
        newField();
    }

    public void writeFloat(float value) throws IOException
    {
        outputStreamWriter.write(String.valueOf(value));
        newField();
    }

    public void writeString(String value) throws IOException
    {
        if (value != null && value.length() > 0)
        {
            boolean useQuotes = value.contains(getFieldSeparator());
            useQuotes |= value.contains("\n");
            if (useQuotes)
            {
                outputStreamWriter.write("\"" + value + "\"");
            }
            else
            {
                outputStreamWriter.write(value);
            }
        }
        newField();
    }

    public void writeLong(long value) throws IOException
    {
        outputStreamWriter.write(String.valueOf(value));
        newField();
    }

    public void flush() throws IOException
    {
        outputStreamWriter.flush();
        fileOutputStream.flush();
    }
}
