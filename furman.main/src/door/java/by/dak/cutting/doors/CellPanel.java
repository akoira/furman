/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

/*
 * CellPanel.java
 *
 * Created on 25.08.2009, 15:07:18
 */

package by.dak.cutting.doors;

import by.dak.cutting.CuttingApp;
import by.dak.cutting.swing.ItemSelector;
import by.dak.cutting.swing.order.data.TextureBoardDefPair;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.BoardDef;
import by.dak.persistence.entities.TextureEntity;
import by.dak.utils.BindingAdapter;
import com.jgoodies.validation.ValidationResult;
import org.jdesktop.beansbinding.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * @author vishutinov
 */
public class CellPanel extends javax.swing.JPanel
{
    private TextureBoardDefPair pair;

    /**
     * Creates new form CellPanel
     */
    public CellPanel(TextureBoardDefPair pair)
    {
        initComponents();

        itemBoard.setItems(FacadeContext.getBoardDefFacade().loadAll());

        itemBoard.getComboBoxItem().addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jComboBox1ActionPerformed(evt);
            }
        });

        if (pair == null)
        {
            itemBoard.getComboBoxItem().setSelectedIndex(0);
            pair = new TextureBoardDefPair(getSelectedTexture(), getSelectedBoard());
        }
        else
        {
            itemBoard.getComboBoxItem().setSelectedItem(pair.getBoardDef());
            itemTexture.getComboBoxItem().setSelectedItem(pair.getTexture());
        }
        this.pair = pair;

        initBinding();

//        initActionForNewInstance();
    }

    private void initBinding()
    {
        BindingGroup bindingGroup = new BindingGroup();

        Binding bindingBoard = Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE,
                pair, BeanProperty.create("boardDef"),
                itemBoard.getComboBoxItem(),
                BeanProperty.create("selectedItem"));
        bindingBoard.setSourceUnreadableValue(null);
        bindingGroup.addBinding(bindingBoard);

        Binding bindingTexture = Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE,
                pair, BeanProperty.create("texture"),
                itemTexture.getComboBoxItem(),
                BeanProperty.create("selectedItem"));
        bindingTexture.setSourceUnreadableValue(null);
        bindingGroup.addBinding(bindingTexture);

        bindingGroup.bind();

        bindingGroup.addBindingListener(new BindingAdapter()
        {
            @Override
            public void synced(Binding binding)
            {
                jButton1.setEnabled(getValidationResult().getErrors().size() == 0);
            }

            @Override
            public void syncFailed(Binding binding, Binding.SyncFailure failure)
            {
                jButton1.setEnabled(getValidationResult().getErrors().size() == 0);
            }
        });

    }

    public ValidationResult getValidationResult()
    {
        ValidationResult validationResult = new ValidationResult();

        if (getSelectedBoard() == null || itemBoard.getItems().isEmpty())
        {
            validationResult.addError(CuttingApp.getApplication().getContext().getResourceMap(CellPanel.class).getString("errorEmptyBoard"));
        }

        if (getSelectedTexture() == null || itemTexture.getItems().isEmpty())
        {
            validationResult.addError(CuttingApp.getApplication().getContext().getResourceMap(CellPanel.class).getString("errorEmptyTexture"));
        }
        return validationResult;
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <draw-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        itemBoard = new ItemSelector<BoardDef>();
        itemTexture = new ItemSelector<TextureEntity>();
        jButton1 = new javax.swing.JButton();

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText(CuttingApp.getApplication().getContext().getResourceMap(CellPanel.class).getString("BoardDef"));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText(CuttingApp.getApplication().getContext().getResourceMap(CellPanel.class).getString("Texture"));

        jButton1.setText(CuttingApp.getApplication().getContext().getResourceMap(CellPanel.class).getString("OK"));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(itemTexture, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(itemBoard, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(itemBoard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(itemTexture, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1)
                                .addContainerGap())
        );
    }// </draw-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jComboBox1ActionPerformed
    {//GEN-HEADEREND:event_jComboBox1ActionPerformed
        if (getSelectedBoard() != null)
        {
            itemTexture.setItems(FacadeContext.getTextureFacade().findTexturesBy(getSelectedBoard()));
            if (!itemTexture.getItems().isEmpty())
            {
                itemTexture.getComboBoxItem().setSelectedIndex(0);
            }
        }
    }//GEN-LAST:event_jComboBox1ActionPerformed

    public void setOkAction(Action action)
    {
        jButton1.setAction(action);
    }

    public BoardDef getSelectedBoard()
    {
        return (BoardDef) itemBoard.getComboBoxItem().getSelectedItem();
    }

    public TextureEntity getSelectedTexture()
    {
        return (TextureEntity) itemTexture.getComboBoxItem().getSelectedItem();
    }

    private void initActionForNewInstance()
    {
        Action action = new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
//                Window w = SwingUtilities.getWindowAncestor(getParent());
//                NewBoardDialog newBoardDialog = null;
//                if (w != null)
//                {
//                    if (w instanceof JFrame)
//                    {
//                        newBoardDialog = new NewBoardDialog((JFrame) w, false);
//                    }
//                    else if (w instanceof JDialog)
//                    {
//                        newBoardDialog = new NewBoardDialog((JDialog) w, false);
//                    }
//                    else
//                    {
//                        throw new IllegalArgumentException();
//                    }
//                    newBoardDialog.setVisible(true);
//                }

            }
        };
        action.putValue(Action.NAME, CuttingApp.getApplication().getContext().getResourceMap(CellPanel.class).getString("newItem"));
        itemBoard.setNewAction(action);

        action = new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Window w = SwingUtilities.getWindowAncestor(getParent());
//                NewTextureDialog newTextureDialog = new NewTextureDialog(w, true);
            }
        };
        action.putValue(Action.NAME, CuttingApp.getApplication().getContext().getResourceMap(CellPanel.class).getString("newItem"));
        itemTexture.setNewAction(action);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private ItemSelector<BoardDef> itemBoard;
    private ItemSelector<TextureEntity> itemTexture;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables

}
