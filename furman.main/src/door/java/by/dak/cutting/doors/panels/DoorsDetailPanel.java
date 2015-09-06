package by.dak.cutting.doors.panels;

import by.dak.cutting.CuttingApp;
import by.dak.cutting.doors.Door;
import by.dak.cutting.doors.DoorImpl;
import by.dak.cutting.doors.Doors;
import by.dak.cutting.doors.mech.DoorColor;
import by.dak.cutting.doors.mech.DoorMechDef;
import by.dak.cutting.doors.mech.DoorMechType;
import by.dak.cutting.swing.BaseTable;
import by.dak.cutting.swing.DComboBox;
import by.dak.persistence.FacadeContext;
import by.dak.utils.BindingAdapter;
import com.jgoodies.validation.ValidationResult;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.observablecollections.ObservableCollections;
import org.jdesktop.observablecollections.ObservableList;
import org.jdesktop.swingbinding.JTableBinding;
import org.jdesktop.swingbinding.SwingBindings;

import javax.swing.*;
import java.util.List;

/*
 * DoorsPropertyPanel.java
 *
 * Created on 18.08.2009, 14:54:27
 */

/**
 * @author NiKO
 */
public class DoorsDetailPanel extends javax.swing.JPanel
{
    static final int sizeOfColumnNumber = 30;
    private JTableBinding tableBinding;
    private ObservableList<Door> listDoor;
    private DComboBox<DoorColor> colorDComboBox;
    private DComboBox<DoorMechType> typeDComboBox;
    private Doors doors;
    private Doors oldDoors;

    private Long widthDoors;

    /**
     * Creates new form DoorsPropertyPanel
     */
    public DoorsDetailPanel(Doors doors)
    {
        this.doors = doors;
        oldDoors = doors.clone();
        colorDComboBox = new DComboBox<DoorColor>();
        typeDComboBox = new DComboBox<DoorMechType>();

        initComponents();

        setDoors(doors);
    }

    public ValidationResult getValidationResult()
    {
        long sumWidth = 0;
        ValidationResult validationResult = new ValidationResult();
        for (Door door : listDoor)
        {
            sumWidth += door.getWidth();
            if (door.getWidth() <= 0)
            {
                validationResult.addError(CuttingApp.getApplication().getContext().getResourceMap(DoorsDetailPanel.class).getString("errorDoorWidth", listDoor.indexOf(door) + 1));
            }
//            if (door.getDoorMechType() == null)
//            {
//                validationResult.addError(CuttingApp.getApplication().getContext().getResourceMap(DoorsDetailPanel.class).getString("errorEmptyDoorType", listDoor.indexOf(door) + 1));
//            }
            if (door.getDoorColor() == null)
            {
                validationResult.addError(CuttingApp.getApplication().getContext().getResourceMap(DoorsDetailPanel.class).getString("errorEmptyDoorColor", listDoor.indexOf(door) + 1));
            }
        }

        if (sumWidth > widthDoors)
        {
            validationResult.addError(CuttingApp.getApplication().getContext().getResourceMap(DoorsDetailPanel.class).getString("errorSumWidth") + " " + widthDoors);
        }

        return validationResult;
    }

    private void iniBindingGroupe()
    {
        tableBinding = SwingBindings.createJTableBinding(AutoBinding.UpdateStrategy.READ_WRITE,
                listDoor, jTable1);
        JTableBinding.ColumnBinding number = tableBinding.addColumnBinding(BeanProperty.create("number")).setColumnName(CuttingApp.getApplication().getContext().getResourceMap(DoorsDetailPanel.class).getString("number"));

        JTableBinding.ColumnBinding typeMech = tableBinding.addColumnBinding(BeanProperty.create("doorMechType")).setColumnName(CuttingApp.getApplication().getContext().getResourceMap(DoorsDetailPanel.class).getString("doorMechType"));
        JTableBinding.ColumnBinding color = tableBinding.addColumnBinding(BeanProperty.create("doorColor")).setColumnName(CuttingApp.getApplication().getContext().getResourceMap(DoorsDetailPanel.class).getString("doorColor"));

        JTableBinding.ColumnBinding width = tableBinding.addColumnBinding(BeanProperty.create("width")).setColumnName(CuttingApp.getApplication().getContext().getResourceMap(DoorsDetailPanel.class).getString("width"));
        JTableBinding.ColumnBinding height = tableBinding.addColumnBinding(BeanProperty.create("height")).setColumnName(CuttingApp.getApplication().getContext().getResourceMap(DoorsDetailPanel.class).getString("height"));

        number.setEditable(false);
        number.setColumnClass(Long.class);
        color.setColumnClass(DoorColor.class);
        typeMech.setColumnClass(DoorMechType.class);
        width.setColumnClass(Long.class);
        height.setColumnClass(Long.class);
        height.setEditable(false);

        tableBinding.bind();

        jTable1.setDefaultEditor(DoorColor.class, new DefaultCellEditor(colorDComboBox));
        jTable1.setDefaultEditor(DoorMechType.class, new DefaultCellEditor(typeDComboBox));
        jTable1.getColumnModel().getColumn(0).setMaxWidth(sizeOfColumnNumber);

        tableBinding.addBindingListener(new BindingAdapter()
        {
            @Override
            public void syncFailed(Binding binding, Binding.SyncFailure failure)
            {
                for (int i = 0; i < doors.getDoors().size(); i++)
                {
                    if (!doors.getDoors().get(i).getWidth().equals(oldDoors.getDoors().get(i).getWidth()))
                    {
//                        ((DoorImpl) doors.getDoors().get(i)).setDoorDrawing(null);
                    }
                }
            }

            @Override
            public void synced(Binding binding)
            {
                for (int i = 0; i < doors.getDoors().size(); i++)
                {
                    if (!doors.getDoors().get(i).getWidth().equals(oldDoors.getDoors().get(i).getWidth()))
                    {
//                        ((DoorImpl) doors.getDoors().get(i)).setDoorDrawing(null);
                    }
                }
            }
        });
    }

    public Doors getDoors()
    {
        return doors;
    }

    public void setDoors(Doors doors)
    {
        this.doors = doors;

        updateComboBoxs();

        listDoor = ObservableCollections.observableList(doors.getDoors());

        this.widthDoors = doors.getWidth();

        for (int i = 0; i < doors.getDoors().size(); i++)
        {
            DoorImpl door = (DoorImpl) doors.getDoors().get(i);
            door.setNumber(new Long(i + 1));
            if (door.getDoorColor() == null)
            {
                door.setDoorColor((DoorColor) colorDComboBox.getItemAt(0));
            }
            if (door.getDoorMechType() == null)
            {
                door.setDoorMechType((DoorMechType) typeDComboBox.getItemAt(0));
            }
        }

        iniBindingGroupe();
    }

    private void updateComboBoxs()
    {
        DoorMechDef doorMechDef = doors.getDoorMechDef();

        List<DoorColor> doorColors = FacadeContext.getDoorColorFacade().findAllBy(doorMechDef);
        List<DoorMechType> doorMechTypes = FacadeContext.getDoorMechTypeFacade().findAllBy(doorMechDef);

        colorDComboBox.setModel(new DefaultComboBoxModel(doorColors.toArray()));
        colorDComboBox.setSelectedIndex(0);
        typeDComboBox.setModel(new DefaultComboBoxModel(doorMechTypes.toArray()));
        typeDComboBox.setSelectedIndex(0);
    }

    @SuppressWarnings("unchecked")

    private void initComponents()
    {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new BaseTable();

        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 594, Short.MAX_VALUE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                                .addContainerGap())
        );
    }


    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;

}
