/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.cut.gui.cuttingApp;

import by.dak.cutting.cut.guillotine.Segment;
import by.dak.cutting.cut.guillotine.Strips;
import by.dak.cutting.cut.measurement.utils.CsvWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Peca
 */
public class CuttingPlan
{

    private Strips strips;
    private ArrayList<ArrayList<Object>> table;
    private int maximumLevel = 0;
    private int sawWidth = 0;

    public CuttingPlan(Strips strips) throws FileNotFoundException, IOException
    {
        table = new ArrayList<ArrayList<Object>>();
        this.strips = strips;
        this.sawWidth = strips.getSawWidth();
    }

    private ArrayList<Object> newRow()
    {
        ArrayList<Object> row = new ArrayList<Object>();
        table.add(row);
        return row;
    }

    private void insert(ArrayList<Object> row, int index, Object value)
    {
        while (row.size() <= index)
        {
            row.add("");
        }
        row.set(index, value);
    }

    private void createPlan(Segment segment)
    {
        float position = 0;
        for (Segment item : segment.getItems())
        {
            ArrayList<Object> row = newRow();
            boolean isContainer = item.getElement() != null;

            position += item.getWidth() + sawWidth;
            int columnIndex = item.getLevel();
            maximumLevel = Math.max(maximumLevel, item.getLevel());
            insert(row, columnIndex, position - (sawWidth * 0.5f));

            if (isContainer)
            {
                insert(row, columnIndex + 1, item.getElement().getId());
            }

            createPlan(item);
        }
    }

    private void createPlan()
    {
        table.clear();
        int sheetIndex = 0;
        for (Segment segment : strips.getSegments())
        {
            insert(newRow(), 0, "Sheet" + (++sheetIndex));
            createPlan(segment);
        }
    }

    public void save(String fileName) throws IOException
    {
        createPlan();

        CsvWriter writer = new CsvWriter(fileName);
        writer.writeString("sheet");
        for (int i = 1; i <= maximumLevel; i++)
        {
            writer.writeString("level" + i);
        }
        writer.writeString("id");
        writer.newRow();
        for (ArrayList<Object> row : table)
        {
            for (Object value : row)
            {
                writer.writeObject(value);
            }
            writer.newRow();
        }
        writer.close();
    }
}
