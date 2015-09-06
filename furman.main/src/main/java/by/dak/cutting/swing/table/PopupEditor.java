package by.dak.cutting.swing.table;

import by.dak.swing.FocusToFirstComponent;
import com.jidesoft.popup.JidePopup;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * User: akoyro
 * Date: 25.04.2009
 * Time: 15:06:59
 */
public class PopupEditor extends AbstractCellEditor implements TableCellEditor
{

    private ComponentProvider componentProvider;
    private PopupEditorController popupEditorController = new PopupEditorController();

    private JidePopup popup;

    public PopupEditor(ComponentProvider componentProvider)
    {
        this.componentProvider = componentProvider;
        this.componentProvider.setTogglePopupAction(popupEditorController.createTogglePopupAction());
        this.componentProvider.setCommitAction(popupEditorController.createCommitAction());
        this.componentProvider.setCancelAction(popupEditorController.createCancelAction());
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
    {
        CellContext cellContext = new CellContext(table, value, isSelected, row, column);
        this.componentProvider.init();
        this.componentProvider.setCellContext(cellContext);
        return this.componentProvider.getCellComponent();
    }

    @Override
    public Object getCellEditorValue()
    {
        Object value = this.componentProvider.getValue();
        return value;
    }


    public class PopupEditorController
    {
        private static final String CANCEL_KEY = "popupEditorCancel";
        private static final String COMMIT_KEY = "popupEditorCommit";

        private Action createTogglePopupAction()
        {
            return new AbstractAction()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    toggleShowPopup();
                }
            };
        }

        private Action createCancelAction()
        {
            Action action = new AbstractAction("Cancel")
            {

                public void actionPerformed(ActionEvent e)
                {
                    cancel();
                }

            };
            return action;
        }

        protected void installKeyboardActions()
        {
            // install picker's actions
            ActionMap pickerMap = componentProvider.getCellComponent().getActionMap();
            pickerMap.put(CANCEL_KEY, createCancelAction());
            pickerMap.put(COMMIT_KEY, createCommitAction());
            pickerMap.put("TOGGLE_POPUP", createTogglePopupAction());

            InputMap pickerInputMap = componentProvider.getCellComponent().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
            pickerInputMap.put(KeyStroke.getKeyStroke("ENTER"), JXDatePicker.COMMIT_KEY);
            pickerInputMap.put(KeyStroke.getKeyStroke("ESCAPE"), JXDatePicker.CANCEL_KEY);
            // PENDING: get from LF
            pickerInputMap.put(KeyStroke.getKeyStroke("SPACE"), "TOGGLE_POPUP");
        }


        private Action createCommitAction()
        {
            Action action = new AbstractAction("Ok")
            {
                public void actionPerformed(ActionEvent e)
                {
                    commit();
                }

            };
            return action;
        }

        protected void commit()
        {
            hidePopup(false);
            stopCellEditing();
        }

        protected void cancel()
        {
            hidePopup(true);
            cancelCellEditing();
        }

        /**
         * PENDING: widened access for debugging - need api to
         * control popup visibility?
         */
        public void hidePopup(boolean cancel)
        {
            if (popup != null)
            {
                popup.hidePopupImmediately(cancel);
            }
        }

        public void toggleShowPopup()
        {
            if (popup == null)
            {
                popup = new JidePopup();
                popup.setLayout(new BorderLayout());
                popup.setResizable(true);
                popup.setAttachable(true);
                popup.setFocusable(true);
                popup.setPopupType(JidePopup.HEAVY_WEIGHT_POPUP);
                popup.addPopupMenuListener(new PopupMenuListener()
                {
                    @Override
                    public void popupMenuWillBecomeVisible(PopupMenuEvent e)
                    {
                    }

                    @Override
                    public void popupMenuWillBecomeInvisible(PopupMenuEvent e)
                    {
                        popup.remove(componentProvider.getPopupComponent());
                        popup.revalidate();
                    }

                    @Override
                    public void popupMenuCanceled(PopupMenuEvent e)
                    {
                        cancelCellEditing();
                    }
                });
            }
            popup.add(componentProvider.getPopupComponent(), BorderLayout.CENTER);
            popup.revalidate();
            SwingUtilities.invokeLater(new Runnable()
            {
                public void run()
                {
                    popup.showPopup(componentProvider.getCellComponent());
                    if (componentProvider.getPopupComponent() instanceof FocusToFirstComponent)
                    {
                        ((FocusToFirstComponent) componentProvider.getPopupComponent()).setFocusToFirstComponent();
                    }
                }
            });

        }
    }

    public JidePopup getPopup()
    {
        return popup;
    }

    public ComponentProvider getComponentProvider()
    {
        return componentProvider;
    }


}
