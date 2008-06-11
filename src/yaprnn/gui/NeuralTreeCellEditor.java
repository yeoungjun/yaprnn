package yaprnngui;

import java.awt.Component;
import javax.swing.tree.TreeCellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.JTextField;
import java.util.EventObject;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.JTree;
import javax.swing.tree.*;
import javax.swing.*;
import javax.swing.CellEditor;
import java.util.Vector;

public class NeuralTreeCellEditor implements TreeCellEditor{
    CellEditor currentEditor;
    DefaultCellEditor neuronEditor;
    AVFEditor avfEditor;
    JTree tree;
    
    public NeuralTreeCellEditor(JTree tree){
        String[] tmp = {"tanh","sigmoid","linear"};
        neuronEditor = new DefaultCellEditor(new JTextField());
        avfEditor = new AVFEditor(tmp);
    }
    public Component getTreeCellEditorComponent(JTree tree, Object value,
                                                boolean isSelected, boolean expanded,
                                                boolean leaf, int row){
        
        DefaultMutableTreeNode tmp = (DefaultMutableTreeNode)value;
        currentEditor = neuronEditor;
	if(((String)tmp.getUserObject()).indexOf("Neurons") > -1){
            currentEditor = neuronEditor;
        }else if(((String)tmp.getUserObject()).indexOf("AVF") > -1){
            currentEditor = avfEditor;
        }
        return (Component)currentEditor;
    }
    
    public Object getCellEditorValue(){
        return currentEditor.getCellEditorValue();
    }
    public boolean isCellEditable(EventObject evt){
         if (evt instanceof MouseEvent) {
             MouseEvent me = (MouseEvent)evt;
             if(me.getClickCount() > 1){
                 return true;
             }
         }
         return false;
    }
    public boolean shouldSelectCell(EventObject evt){
        return currentEditor.shouldSelectCell(evt);
    }
    public boolean stopCellEditing(){
        return currentEditor.stopCellEditing();
    }
    public void cancelCellEditing(){
        currentEditor.cancelCellEditing();
    }
    public void addCellEditorListener(CellEditorListener l){
        neuronEditor.addCellEditorListener(l);
        avfEditor.addCellEditorListener(l);
    }
    public void removeCellEditorListener(CellEditorListener l){
        neuronEditor.removeCellEditorListener(l);
        avfEditor.removeCellEditorListener(l);
    }
    
    protected class AVFEditor extends JComboBox implements CellEditor {

      String value;

      Vector listeners = new Vector();

      // Mimic all the constructors people expect with ComboBoxes.
      public AVFEditor(Object[] list) {
        super(list);
        setEditable(false);
        value = list[0].toString();

        // Listen to our own action events so that we know when to stop editing.
        addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent ae) {
            if (stopCellEditing()) {
              fireEditingStopped();
            }
          }
        });
      }

      // Implement the CellEditor methods.
      public void cancelCellEditing() {
      }

      // Stop editing only if the user entered a valid value.
      public boolean stopCellEditing() {
        try {
          value = (String) getSelectedItem();
          if (value == null) {
            value = (String) getItemAt(0);
          }
          return true;
        } catch (Exception e) {
          // Something went wrong.
          return false;
        }
      }

      public Object getCellEditorValue() {
        return value;
      }

      // Start editing when the right mouse button is clicked.
      public boolean isCellEditable(EventObject eo) {
        if ((eo == null)
            || ((eo instanceof MouseEvent) && (((MouseEvent) eo)
                .isMetaDown()))) {
          return true;
        }
        return false;
      }

      public boolean shouldSelectCell(EventObject eo) {
        return true;
      }

      // Add support for listeners.
      public void addCellEditorListener(CellEditorListener cel) {
        listeners.addElement(cel);
      }

      public void removeCellEditorListener(CellEditorListener cel) {
        listeners.removeElement(cel);
      }

      protected void fireEditingStopped() {
        if (listeners.size() > 0) {
          ChangeEvent ce = new ChangeEvent(this);
          for (int i = listeners.size() - 1; i >= 0; i--) {
            ((CellEditorListener) listeners.elementAt(i))
                .editingStopped(ce);
          }
        }
      }
    }
}
