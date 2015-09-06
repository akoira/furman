/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ProfileDefDialog.java
 *
 * Created on 21.10.2009, 22.36.20
 */

package by.dak.cutting.doors.profile.def.swing;

import by.dak.cutting.CuttingApp;
import by.dak.cutting.DialogShowers;
import by.dak.cutting.SpringConfiguration;
import by.dak.cutting.doors.profile.ProfileDef;
import by.dak.cutting.doors.profile.def.swing.wizard.ProfileDefController;
import by.dak.cutting.swing.BaseGridDialog;
import by.dak.cutting.swing.DictionaryEditType;
import by.dak.cutting.swing.EntityTableHelper;
import by.dak.cutting.swing.MessageDialog;
import by.dak.persistence.FacadeContext;
import by.dak.swing.wizard.WizardDisplayerHelper;
import org.bushe.swing.event.annotation.EventSubscriber;
import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.observablecollections.ObservableCollections;
import org.jdesktop.swingbinding.JTableBinding;
import org.jdesktop.swingbinding.SwingBindings;

import javax.swing.*;
import java.beans.Beans;
import java.util.List;

/**
 * @author alkoyro
 */
public class ProfileDefDialog extends BaseGridDialog
{
    private ResourceMap rsMap = Application.getInstance(CuttingApp.class).getContext().getResourceMap(
            ProfileDefDialog.class);
    private ActionMap actionMap = org.jdesktop.application.Application.getInstance(
            by.dak.cutting.CuttingApp.class).getContext().getActionMap(ProfileDefDialog.class, this);
    private EntityTableHelper<ProfileDef, ProfileDefEvent> entityTableHelper;
    private List<ProfileDef> list;

    /**
     * Creates new form ProfileDefDialog
     */
    public ProfileDefDialog(DictionaryEditType editType)
    {
        super(editType);
        initComponents();
        initEnvironment();
    }

    @EventSubscriber(eventClass = ProfileDefEvent.class)
    public void onEvent(ProfileDefEvent profileDefEvent)
    {
        updateGrid();
    }

    private void initEnvironment()
    {
        if (!Beans.isDesignTime())
        {
            entityTableHelper = new EntityTableHelper<ProfileDef, ProfileDefEvent>(naviTable1.getTable());
        }
        getRootPane().setDefaultButton(okButton);
    }

    public void setList(List<ProfileDef> profileDefList)
    {
        list = ObservableCollections.observableList(profileDefList);
        initBinding();
        updateGrid();
        naviTable1.hideColumn(0);
    }

    private void initBinding()
    {
        JTableBinding jTableBinding = SwingBindings.createJTableBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, list, naviTable1.getTable());

        JTableBinding.ColumnBinding columnBinding = jTableBinding
                .addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${id}"));
        columnBinding.setColumnName(rsMap.getString("profileTable.column.ID"));
        columnBinding.setColumnClass(Long.class);
        columnBinding.setEditable(false);

        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${name}"));
        columnBinding.setColumnName(rsMap.getString("profileTable.column.Name"));
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);

        entityTableHelper.getBindingGroup().addBinding(jTableBinding);
        entityTableHelper.getBindingGroup().bind();
    }

    @Action
    public void onDel()
    {
        if (naviTable1.getSelectedId() != -1)
        {
            if (MessageDialog.showConfirmationMessage(MessageDialog.IS_DELETE_RECORD) == JOptionPane.OK_OPTION)
            {

                FacadeContext.getBoardDefFacade().delete(naviTable1.getSelectedId());

                updateGrid();
            }
        }
        else
        {
            MessageDialog.showSimpleMessage(MessageDialog.NO_ROW_SELECTED);
        }
    }

    @Action
    public void onNew()
    {
        ProfileDef profeleDef = new ProfileDef();
        showWizard(profeleDef);

        updateGrid();
    }

    @Action
    public void onOk()
    {
        dispose();
    }

    @Action
    public void onOpen()
    {
        if (naviTable1.getSelectedId() != -1)
        {
            ProfileDef profileDef = FacadeContext
                    .getProfileDefFacade().findById(naviTable1.getSelectedId(), true);

            showWizard(profileDef);
        }
        else
        {
            MessageDialog.showSimpleMessage(MessageDialog.NO_ROW_SELECTED);
        }
    }

    private void showWizard(ProfileDef profileDef)
    {
        ProfileDefController profileDefController = new ProfileDefController(profileDef);
        WizardDisplayerHelper helper = new WizardDisplayerHelper(profileDefController);
        helper.showWizard();
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        basePanel1 = new by.dak.cutting.swing.BasePanel();
        naviTable1 = new by.dak.cutting.swing.NaviTable();
        deleteButton = new javax.swing.JButton();
        openButton = new javax.swing.JButton();
        newButton = new javax.swing.JButton();
        okButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance().getContext().getResourceMap(ProfileDefDialog.class);
        setTitle(resourceMap.getString("title")); // NOI18N

        basePanel1.setName("basePanel1"); // NOI18N

        naviTable1.setName("naviTable1"); // NOI18N

        deleteButton.setAction(actionMap.get("onDel"));
        deleteButton.setText("Удалить");
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("by/dak/cutting/doors/profile/def/swing/resources/ProfileDefDialog"); // NOI18N
        deleteButton.setName(bundle.getString("deleteButton.name")); // NOI18N

        openButton.setAction(actionMap.get("onOpen"));
        openButton.setText("Открыть");
        openButton.setName(bundle.getString("openButton.name")); // NOI18N

        newButton.setAction(actionMap.get("onNew"));
        newButton.setText("Новая запись");
        newButton.setName(bundle.getString("newButton.name")); // NOI18N

        javax.swing.GroupLayout basePanel1Layout = new javax.swing.GroupLayout(basePanel1);
        basePanel1.setLayout(basePanel1Layout);
        basePanel1Layout.setHorizontalGroup(
                basePanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(basePanel1Layout.createSequentialGroup()
                                .addGroup(basePanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(naviTable1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(basePanel1Layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(newButton)
                                                .addGap(18, 18, 18)
                                                .addComponent(openButton)
                                                .addGap(18, 18, 18)
                                                .addComponent(deleteButton)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        basePanel1Layout.setVerticalGroup(
                basePanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(basePanel1Layout.createSequentialGroup()
                                .addComponent(naviTable1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(basePanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(newButton)
                                        .addComponent(openButton)
                                        .addComponent(deleteButton))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        okButton.setAction(actionMap.get("onOk"));
        okButton.setText(resourceMap.getString("okButton.text")); // NOI18N
        okButton.setName("okButton"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(basePanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(321, Short.MAX_VALUE)
                                .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(basePanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(okButton)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void onMouseClick()
    {

    }

    public void onMouseDbClick()
    {
        onOpen();
    }

    public void onMouseRight()
    {

    }

    public void onMouseLeft()
    {

    }

    public void updateGrid()
    {
        list.clear();
        list.addAll(FacadeContext.getProfileDefFacade().findProfileDefinitionsWithFilter(
                naviTable1.getSearchFilter()));
        naviTable1.setSize(FacadeContext.getProfileDefFacade().loadAll().size());
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new SpringConfiguration();

                DialogShowers.getProfileDefDialogShower().show();
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private by.dak.cutting.swing.BasePanel basePanel1;
    private javax.swing.JButton deleteButton;
    private by.dak.cutting.swing.NaviTable naviTable1;
    private javax.swing.JButton newButton;
    private javax.swing.JButton okButton;
    private javax.swing.JButton openButton;
    // End of variables declaration//GEN-END:variables

}
