package by.dak.furman.templateimport.parser.detail;

import by.dak.furman.templateimport.Property;
import by.dak.furman.templateimport.parser.AParser;
import by.dak.furman.templateimport.values.ADetail;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.hwpf.usermodel.TableRow;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static by.dak.furman.templateimport.parser.ParserUtils.adjustText;

public abstract class ADetailParser<V extends ADetail> extends AParser
{
    //input
    private Table table;
    private File docFile;
    private List<Property> properties;

    private List<String> columns;

    //output
    private List<V> result = new ArrayList<V>();

    public void parse()
    {
        columns = parseColumns();

        for (int i = 1; i < table.numRows(); i++)
        {

            TableRow row = table.getRow(i);
            V value = createValue();
            value.setFile(getDocFile());
            value.setColumns(columns);
            result.add(value);


            if (row.numCells() == 0)
            {
                value.addMessage("error.wrongRowFormatWithoutCells", i);
                continue;
            }


            if (row.numCells() != columns.size())
            {
                value.addMessage("error.wrongRowFormatColumnCount", i);
                continue;
            }
            for (int c = 0; c < row.numCells(); c++)
            {
                String text = adjustText(row.getCell(c).text());
                try
                {
                    value.setValue(c, text);
                    //PropertyUtils.setProperty(value, properties.get(i).getPath(), text);
                }
                catch (Exception e)
                {
                    value.addMessage("error.cannotSetProperty", e, properties.get(i), text);
                }
            }
        }
    }

    public List<V> getResult()
    {
        return result;
    }

    public List<Property> getProperties()
    {
        return properties;
    }

    public void setProperties(List<Property> properties)
    {
        this.properties = properties;
    }

    private List<String> parseColumns()
    {
        ArrayList<String> columns = new ArrayList<String>();

        TableRow row = table.getRow(0);
        for (int i = 0; i < row.numCells(); i++)
        {
            TableCell cell = row.getCell(i);
            columns.add(adjustText(cell.text()));
        }
        return columns;
    }

    protected abstract V createValue();

    public void setDocFile(File docFile)
    {
        this.docFile = docFile;
    }

    public void setTable(Table table)
    {
        this.table = table;
    }

    public File getDocFile()
    {
        return docFile;
    }
}
