package by.dak.report.swing;

import by.dak.cutting.swing.store.EditorCreators;
import by.dak.report.ReportProperties;
import by.dak.swing.APropertyEditorCreator;
import by.dak.swing.AValueTab;

import java.util.HashMap;
import java.util.Map;

/**
 * User: akoyro
 * Date: 20.04.11
 * Time: 17:20
 */
public class ReportPropertiesTab extends AValueTab<ReportProperties>
{
    public ReportPropertiesTab()
    {
        setVisibleProperties(new String[]{ReportProperties.PROPERTY_priceReportType});

        Map<Class, APropertyEditorCreator> creatorMap = new HashMap<Class, APropertyEditorCreator>();
////        creatorMap.put(PriceReportType.class, new APropertyEditorCreator()
////        {
////            @Override
////            public Component createEditor(Object value, PropertyDescriptor property, BindingGroup bindingGroup)
////            {
////                DPanel panel = new DPanel(new GridLayout(3,1,5,5));
////                ButtonGroup buttonGroup = new ButtonGroup();
////                for (PriceReportType reportType: PriceReportType.values())
////                {
////                    JRadioButton radioButton = new JRadioButton(StringValueAnnotationProcessor.getProcessor().convert(reportType));
////                    panel.add(radioButton);
////                    buttonGroup.add(radioButton);
////                }
////                return panel;
////            }
////        });
//        setCacheEditorCreator(creatorMap);
        setCacheEditorCreator(EditorCreators.EDITOR_CREATORS);
    }
}
