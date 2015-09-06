package by.dak.cutting.doors;

import by.dak.cutting.doors.profile.ProfileCompDef;
import by.dak.cutting.doors.profile.ProfileDef;
import by.dak.cutting.doors.profile.draw.ArcFigure;
import by.dak.cutting.doors.profile.draw.DockingProfileCompFigure;
import by.dak.cutting.swing.order.cellcomponents.editors.milling.CurveFigure;
import by.dak.draw.DefaultHierarchicalDrawing;
import by.dak.draw.ShowDimentionAction;
import by.dak.draw.swing.ADrawPanel;
import by.dak.test.TestUtils;
import by.dak.utils.BindingAdapter;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.action.ButtonFactory;
import org.jhotdraw.draw.tool.CreationTool;

import javax.swing.*;

/**
 * User: akoyro
 * Date: 12.09.2009
 * Time: 16:22:10
 */
public class TDoorFigure
{
    public static void main(String[] args)
    {


//        new SpringConfiguration();
        DefaultHierarchicalDrawing defaultDrawing = new DefaultHierarchicalDrawing();
        final ADrawPanel aDrawPanel = new ADrawPanel()
        {

            @Override
            protected void fillToolBarFigures()
            {
                ButtonFactory.addToolTo(getToolBarFigures(), getDrawingEditor(), new CreationTool(new DockingProfileCompFigure()), "edit.createVProfile", getResourceBundleUtil());
                ButtonFactory.addToolTo(getToolBarFigures(), getDrawingEditor(), new CreationTool(new CurveFigure()), "edit.createLine", getResourceBundleUtil());
                ButtonFactory.addToolTo(getToolBarFigures(), getDrawingEditor(), new CreationTool(new ArcFigure()), "edit.createLine", getResourceBundleUtil());
//                ButtonFactory.addToolTo(getToolBarFigures(), getDrawingEditor(), new CreationTool(new HProfileJoin(20)), "edit.createHProfile", getResourceBundleUtil());
            }

            @Override
            protected void fillToolBarAtributes()
            {
                super.fillToolBarAtributes();
                getToolBarAttributes().add(new ShowDimentionAction(getDrawingEditor()));
            }
        };

        final JCheckBox checkBox = getHierarchicalEnabledCheckBox(defaultDrawing, aDrawPanel.getDrawingView());
        aDrawPanel.getToolBarAttributes().add(checkBox);

        DoorImpl door = getTestStructure();
        DoorFigure doorFigure = new DoorFigure(door.getBounds());


        defaultDrawing.add(doorFigure);


        aDrawPanel.setDrawing(defaultDrawing);
        TestUtils.showFrame(aDrawPanel, "TDoorFigure");
    }


    public static JCheckBox getHierarchicalEnabledCheckBox(DefaultHierarchicalDrawing defaultDrawing, final DrawingView drawingView)
    {
        JCheckBox checkBox = new JCheckBox();
        BindingGroup bindingGroup = new BindingGroup();
        Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE, defaultDrawing, BeanProperty
                .create("hierarchicalEnabled"), checkBox, BeanProperty.create("selected"));
        binding.addBindingListener(new BindingAdapter()
        {
            @Override
            public void synced(Binding binding)
            {
                drawingView.clearSelection();
            }
        });
        bindingGroup.addBinding(binding);
        bindingGroup.bind();
        return checkBox;

    }

    private static DoorImpl getTestStructure()
    {
        ProfileCompDef profileComponentDef = new ProfileCompDef();
        profileComponentDef.setWidth(15);
        profileComponentDef.setIndent(5);

        ProfileCompDef vprofileComponentDef = new ProfileCompDef();
        vprofileComponentDef.setWidth(15);
        vprofileComponentDef.setIndent(5);

        ProfileDef profileDef = new ProfileDef();
        profileDef.setUpProfileCompDef(profileComponentDef);
        profileDef.setDownProfileCompDef(profileComponentDef);
        profileDef.setLeftProfileCompDef(vprofileComponentDef);
        profileDef.setRightProfileCompDef(vprofileComponentDef);

        DoorImpl door = new DoorImpl();

        door.setWidth(400L);
        door.setHeight(200L);
        door.setProfileDef(profileDef);
        return door;
    }
}
