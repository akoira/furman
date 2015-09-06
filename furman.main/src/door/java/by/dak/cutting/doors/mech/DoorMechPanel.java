/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DoorMechPanel.java
 *
 * Created on 9.9.2009, 16.25.28
 */

package by.dak.cutting.doors.mech;

import by.dak.cutting.swing.component.MaskTextField;
import com.jgoodies.validation.ValidationResult;
import org.jdesktop.beansbinding.BindingGroup;

import javax.swing.*;

/**
 * @author alkoyro
 */
public class DoorMechPanel extends javax.swing.JPanel
{
    org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(by.dak.cutting.CuttingApp.class).getContext().getResourceMap(DoorMechPanel.class);
    //    private ShapeDefImpl shape;
    private String shapeCode;
    private Integer shapeThickness;
    private Integer shapeWidth;
    private Long shapeLength;
    private Integer shapeIndent;
    private final String regExp = "\\d{0,20}";

    /**
     * Creates new form DoorMechPanel
     */
    public DoorMechPanel()
    {
        initComponents();
        textFieldLength.setMask(regExp);
        textFieldThickness.setMask(regExp);
        textFieldIndent.setMask(regExp);
        textFieldWidth.setMask(regExp);
    }

//    public ShapeDef getShape()
//    {
//        return shape;
//    }
//
//    public void setShape(ShapeDef shape)
//    {
//        this.shape = (ShapeDefImpl) shape;
//        textFieldCode.setText(shape.getCode());
//        textFieldLength.setText(shape.getLength().toString());
//        textFieldIndent.setText(shape.getIndent().toString());
//        textFieldThickness.setText(shape.getMatThickness().toString());
//        textFieldWidth.setText(shape.getWidth().toString());
//
//        initBinding();
//    }

    private String getShapeCode()
    {
        shapeCode = textFieldCode.getText();
        return shapeCode;
    }

    private Long getShapeLength() throws NumberFormatException
    {
        try
        {
            shapeLength = Long.valueOf(textFieldLength.getText());
        }
        catch (NumberFormatException e)
        {
            shapeLength = new Long(0);
        }
        return shapeLength;
    }

    private Integer getShapeWidth() throws NumberFormatException
    {
        try
        {
            shapeWidth = Integer.valueOf(textFieldWidth.getText());
        }
        catch (NumberFormatException e)
        {
            shapeWidth = new Integer(0);
        }
        return shapeWidth;
    }

    private Integer getShapeThickness() throws NumberFormatException
    {
        try
        {
            shapeThickness = Integer.valueOf(textFieldThickness.getText());
        }
        catch (NumberFormatException e)
        {
            shapeThickness = new Integer(0);
        }
        return shapeThickness;
    }

    private Integer getShapeIndent() throws NumberFormatException
    {
        try
        {
            shapeIndent = Integer.valueOf(textFieldIndent.getText());
        }
        catch (NumberFormatException e)
        {
            shapeIndent = new Integer(0);
        }
        return shapeIndent;
    }

    private void initBinding()
    {
        BindingGroup group = new BindingGroup();

//        Binding bindingCode = Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE,
//                shape, BeanProperty.create("code"),
//                textFieldCode, BeanProperty.create("text")
//
//        );
//        bindingCode.setSourceUnreadableValue(null);
//        group.addBinding(bindingCode);
//        Binding bindingThickness = Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE,
//                shape, BeanProperty.create("matThickness"),
//                textFieldThickness, BeanProperty.create("text")
//
//        );
//        bindingThickness.setSourceUnreadableValue(null);
//        group.addBinding(bindingThickness);
//        Binding bindingWidth = Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE,
//                shape, BeanProperty.create("width"),
//                textFieldWidth, BeanProperty.create("text")
//
//        );
//        bindingWidth.setSourceUnreadableValue(null);
//        group.addBinding(bindingWidth);
//        Binding bindingLength = Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE,
//                shape, BeanProperty.create("length"),
//                textFieldLength, BeanProperty.create("text")
//
//        );
//        bindingLength.setSourceUnreadableValue(null);
//        group.addBinding(bindingLength);
//        Binding bindingIndent = Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE,
//                shape, BeanProperty.create("indent"),
//                textFieldIndent, BeanProperty.create("text")
//
//        );
//        bindingIndent.setSourceUnreadableValue(null);
//        group.addBinding(bindingIndent);
//        group.bind();
//
//        group.addBindingListener(new BindingAdapter()
//        {
//            @Override
//            public void synced(Binding binding)
//            {
//                okButton.setEnabled(getValidationResult().getErrors().size() == 0);
//            }
//
//            @Override
//            public void syncFailed(Binding binding, Binding.SyncFailure failure)
//            {
//                okButton.setEnabled(getValidationResult().getErrors().size() == 0);
//            }
//        });
//
//
    }

    public JButton getOkButton()
    {
        return okButton;
    }

    public void setOkAction(Action action)
    {
        okButton.setAction(action);
    }

    public ValidationResult getValidationResult()
    {
        ValidationResult validationResult = new ValidationResult();

        if (getShapeCode() == null || getShapeCode().isEmpty())
        {
            validationResult.addError(resourceMap.getString("validator.codeError.text"));
        }
        else if (getShapeThickness() == null || shapeThickness == 0)
        {
            validationResult.addError(resourceMap.getString("validator.thicknessError.text"));
        }
        else if (getShapeWidth() == null || shapeWidth == 0)
        {
            validationResult.addError(resourceMap.getString("validator.widthError.text"));
        }
        else if (getShapeLength() == null || shapeLength == 0)
        {
            validationResult.addError(resourceMap.getString("validator.heightError.text"));
        }
        else if (getShapeIndent() == null || shapeIndent == 0)
        {
            validationResult.addError(resourceMap.getString("validator.indentError.text"));
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
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        okButton = new javax.swing.JButton();
        textFieldCode = new MaskTextField();
        textFieldThickness = new MaskTextField();
        textFieldLength = new MaskTextField();
        textFieldWidth = new MaskTextField();
        textFieldIndent = new MaskTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(by.dak.cutting.CuttingApp.class).getContext().getResourceMap(DoorMechPanel.class);
        okButton.setText(resourceMap.getString("okButton.text")); // NOI18N
        okButton.setAlignmentX(0.5F);
        okButton.setName("okButton"); // NOI18N

        textFieldCode.setText(resourceMap.getString("textFieldCode.text")); // NOI18N
        textFieldCode.setName("textFieldCode"); // NOI18N

        textFieldThickness.setText(resourceMap.getString("textFieldThickness.text")); // NOI18N
        textFieldThickness.setName("textFieldThickness"); // NOI18N

        textFieldLength.setText(resourceMap.getString("textFieldLength.text")); // NOI18N
        textFieldLength.setName("textFieldLength"); // NOI18N

        textFieldWidth.setText(resourceMap.getString("textFieldWidth.text")); // NOI18N
        textFieldWidth.setName("textFieldWidth"); // NOI18N

        textFieldIndent.setText(resourceMap.getString("textFieldIndent.text")); // NOI18N
        textFieldIndent.setName("textFieldIndent"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel5)
                                        .addComponent(jLabel4)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(okButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                                        .addComponent(textFieldIndent, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                                        .addComponent(textFieldWidth, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                                        .addComponent(textFieldLength, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                                        .addComponent(textFieldThickness, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                                        .addComponent(textFieldCode, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(textFieldCode)
                                        .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(textFieldThickness, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                                        .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(textFieldLength, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                                        .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(textFieldWidth, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                                        .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(textFieldIndent)
                                        .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(okButton)
                                .addGap(36, 36, 36))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JButton okButton;
    private MaskTextField textFieldCode;
    private MaskTextField textFieldIndent;
    private MaskTextField textFieldLength;
    private MaskTextField textFieldThickness;
    private MaskTextField textFieldWidth;
    // End of variables declaration//GEN-END:variables

}
