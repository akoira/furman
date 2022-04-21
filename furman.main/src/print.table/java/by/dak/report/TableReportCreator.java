package by.dak.report;

import by.dak.cutting.swing.BaseTable;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.engine.design.*;
import net.sf.jasperreports.engine.type.EvaluationTimeEnum;
import net.sf.jasperreports.engine.type.HorizontalAlignEnum;
import net.sf.jasperreports.engine.type.HorizontalTextAlignEnum;
import net.sf.jasperreports.engine.type.OrientationEnum;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 14.10.11
 * Time: 17:18
 * To change this template use File | Settings | File Templates.
 */
public class TableReportCreator
{
    private ResourceBundle resourceBundle;
    private static final String RESOURCE_BUNDLE_PATH = "by/dak/report/TableReportCreator";
    private static final int startX = 0;
    private static final int startY = 0;
    private static final int textFieldHeight = 15;
    private static final int titleFieldHeight = 25;
    private static final int pageWidth = 595;
    private static final int pageHeight = 842;
    private static final int pageColumnWidth = 555;
    private static final int leftMargin = 20;
    private static final int rightMargin = 20;
    private static final int topMargin = 20;
    private static final int bottomMargin = 20;
    private static final int lineWidth = 1;
    private static final float titleFontSize = 15.0f;
    private static final int footerOffsetWidth = 20;

    private String title;
    private BaseTable table;

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setTable(BaseTable table)
    {
        this.table = table;
    }

    public BaseTable getTable()
    {
        return table;
    }

    public ResourceBundle getResourceBundle()
    {
        if (resourceBundle == null)
        {
            resourceBundle = ResourceBundle.getBundle(RESOURCE_BUNDLE_PATH, new Locale("ru"));
        }
        return resourceBundle;
    }

    public JasperPrint create() throws JRException
    {
        JasperDesign jasperDesign = createJasperDesign();

        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(JRParameter.REPORT_RESOURCE_BUNDLE, getResourceBundle());
        JRDataSource dataSource = new JRTableModelDataSource(table.getModel());
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        return jasperPrint;
    }

    private JasperDesign createJasperDesign() throws JRException
    {
        JasperDesign jasperDesign = new JasperDesign();
        jasperDesign.setResourceBundle(RESOURCE_BUNDLE_PATH);
        jasperDesign.setName(getResourceBundle().getString("jasperDesign.name"));
        jasperDesign.setPageWidth(pageWidth);
        jasperDesign.setPageHeight(pageHeight);
        jasperDesign.setColumnWidth(pageColumnWidth);
        jasperDesign.setOrientation(OrientationEnum.PORTRAIT);
        jasperDesign.setLeftMargin(leftMargin);
        jasperDesign.setRightMargin(rightMargin);
        jasperDesign.setTopMargin(topMargin);
        jasperDesign.setBottomMargin(bottomMargin);

        List<JRDesignField> fields = createDesignFields();
        for (JRDesignField field : fields)
        {
            jasperDesign.addField(field);
        }
        DesignDetailsCreator detailsCreator = new DesignDetailsCreator();
        JRBand detailsBand = detailsCreator.createDetails();
        ((JRDesignSection) jasperDesign.getDetailSection()).addBand(detailsBand);

        DesignHeaderCreator headerCreator = new DesignHeaderCreator();
        JRBand columnBand = headerCreator.createHeader();
        jasperDesign.setColumnHeader(columnBand);

        PageFooterCreator pageFooterCreator = new PageFooterCreator();
        JRBand pageFooter = pageFooterCreator.create();
        jasperDesign.setPageFooter(pageFooter);

        TitleCreator titleCreator = new TitleCreator();
        JRBand titleBand = titleCreator.create();
        jasperDesign.setPageHeader(titleBand);

        return jasperDesign;
    }


    private int calcFieldWidth(int columnWidth)
    {
        return columnWidth * pageColumnWidth / getTable().getWidth();
    }

    private List<JRDesignField> createDesignFields()
    {
        List<JRDesignField> fields = new ArrayList<JRDesignField>();
        for (int count = 0; count < getTable().getColumnCount(); count++)
        {
            JRDesignField field = new JRDesignField();
            field.setName(getTable().getColumnName(count));
            field.setValueClass(getTable().getColumnClass(count));
            fields.add(field);
        }
        return fields;
    }


    private void layoutBand(JRDesignBand band)
    {
        List<JRDesignLine> lines = new ArrayList<JRDesignLine>();
        for (JRElement textField : band.getElements())
        {
            JRDesignLine leftLine = new JRDesignLine();
            leftLine.setX(textField.getX());
            leftLine.setY(textField.getY());
            leftLine.setHeight(textField.getHeight());
            leftLine.setWidth(lineWidth);
            leftLine.setForecolor(Color.BLACK);

            JRDesignLine rightLine = new JRDesignLine();
            rightLine.setX(textField.getX() + textField.getWidth());
            rightLine.setY(textField.getY());
            rightLine.setHeight(textField.getHeight());
            rightLine.setWidth(lineWidth);
            rightLine.setForecolor(Color.BLACK);

            JRDesignLine topLine = new JRDesignLine();
            topLine.setX(textField.getX());
            topLine.setY(textField.getY());
            topLine.setHeight(lineWidth);
            topLine.setWidth(textField.getWidth());
            topLine.setForecolor(Color.BLACK);

            JRDesignLine bottomLine = new JRDesignLine();
            bottomLine.setX(textField.getX());
            //вычитается 1 иначе линия выйдет за границы группы
            bottomLine.setY(textField.getY() + textField.getHeight() - 1);
            bottomLine.setHeight(lineWidth);
            bottomLine.setWidth(textField.getWidth());
            bottomLine.setForecolor(Color.BLACK);

            lines.add(topLine);
            lines.add(leftLine);
            lines.add(bottomLine);
            lines.add(rightLine);
        }

        for (JRDesignLine line : lines)
        {
            band.addElement(line);
        }
    }

    /**
     * инициализация тела jasperDesign
     */
    private class DesignDetailsCreator
    {
        public JRDesignBand createDetails()
        {
            JRDesignBand designBand = new JRDesignBand();
            designBand.setHeight(textFieldHeight);
            List<JRDesignTextField> designTextFields = createDesignTextFields();

            for (JRDesignTextField textField : designTextFields)
            {
                designBand.addElement(textField);
            }
            layoutBand(designBand);
            return designBand;
        }

        private List<JRDesignTextField> createDesignTextFields()
        {
            List<JRDesignTextField> designTextFields = new ArrayList<JRDesignTextField>();
            int x = startX;
            for (int count = 0; count < getTable().getColumnCount(); count++)
            {
                int width = calcFieldWidth(getTable().getColumn(count).getWidth());
                JRDesignTextField textField = new JRDesignTextField();
                textField.setY(startY);
                textField.setX(x);
                textField.setHeight(textFieldHeight);
                textField.setWidth(width);
                JRDesignExpression expression = new JRDesignExpression();
                expression.setText("$F{" + getTable().getColumnName(count) + "}");
                textField.setExpression(expression);
                textField.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
                designTextFields.add(textField);
                x += width;
            }

            return designTextFields;
        }
    }

    private class DesignHeaderCreator
    {
        public JRDesignBand createHeader()
        {
            JRDesignBand columnBand = new JRDesignBand();
            columnBand.setHeight(textFieldHeight);
            List<JRDesignStaticText> columnHeaderTextFields = createColumnHeaderTextFields();
            for (JRDesignStaticText columnHeaderTextField : columnHeaderTextFields)
            {
                columnBand.addElement(columnHeaderTextField);
            }
            layoutBand(columnBand);
            return columnBand;
        }

        private List<JRDesignStaticText> createColumnHeaderTextFields()
        {
            List<JRDesignStaticText> staticTextFields = new ArrayList<JRDesignStaticText>();
            int x = startX;
            for (int count = 0; count < getTable().getColumnCount(); count++)
            {
                int width = calcFieldWidth(getTable().getColumn(count).getWidth());
                JRDesignStaticText staticTextField = new JRDesignStaticText();
                staticTextField.setText(getTable().getColumnName(count));
                staticTextField.setBold(true);
                staticTextField.setItalic(true);
                staticTextField.setX(x);
                staticTextField.setY(startY);
                staticTextField.setHeight(textFieldHeight);
                staticTextField.setWidth(width);
                staticTextField.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
                staticTextFields.add(staticTextField);
                x += width;
            }
            return staticTextFields;
        }
    }

    private class PageFooterCreator
    {

        public JRBand create()
        {
            JRDesignBand pageBand = new JRDesignBand();
            pageBand.setHeight(textFieldHeight);
            List<JRDesignTextField> textFields = createPageFooterColumns();
            for (JRDesignTextField textField : textFields)
            {
                pageBand.addElement(textField);
            }
            return pageBand;
        }

        private List<JRDesignTextField> createPageFooterColumns()
        {
            List<JRDesignTextField> textFields = new ArrayList<JRDesignTextField>();
            JRDesignTextField pageNumTextField = new JRDesignTextField();
            JRDesignExpression pageNumExpression = new JRDesignExpression();
            pageNumExpression.setText("$R{pages}+\"  \"+ $V{PAGE_NUMBER}+\" \"+" + "$R{from}");
            pageNumTextField.setExpression(pageNumExpression);
            pageNumTextField.setX(pageColumnWidth - footerOffsetWidth * 4);
            pageNumTextField.setY(startY);
            pageNumTextField.setWidth(pageNumTextField.getX() + footerOffsetWidth);
            pageNumTextField.setHeight(textFieldHeight);

            JRDesignTextField allPageNumTextField = new JRDesignTextField();
            JRDesignExpression allPageNumExpression = new JRDesignExpression();
            allPageNumExpression.setText("$V{PAGE_NUMBER}");
            allPageNumTextField.setExpression(allPageNumExpression);
            allPageNumTextField.setEvaluationTime(EvaluationTimeEnum.REPORT);
            allPageNumTextField.setX(pageColumnWidth - footerOffsetWidth);
            allPageNumTextField.setY(startY);
            allPageNumTextField.setWidth(allPageNumTextField.getX() + footerOffsetWidth);
            allPageNumTextField.setHeight(textFieldHeight);

            textFields.add(pageNumTextField);
            textFields.add(allPageNumTextField);

            return textFields;
        }
    }

    private class TitleCreator
    {
        public JRBand create()
        {
            JRDesignBand titleBand = new JRDesignBand();
            titleBand.setHeight(titleFieldHeight);
            JRDesignStaticText titleTextField = new JRDesignStaticText();
            titleTextField.setX(startX);
            titleTextField.setY(startY);
            titleTextField.setHeight(titleFieldHeight);
            titleTextField.setWidth(pageColumnWidth);
            titleTextField.setBold(true);
            titleTextField.setItalic(true);
            titleTextField.setFontSize(titleFontSize);
            String designTitle = getTitle() == null ? "" : getTitle();
            titleTextField.setText(designTitle);
            titleTextField.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
            titleBand.addElement(titleTextField);

            return titleBand;
        }
    }
}
