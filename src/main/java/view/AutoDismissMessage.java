package view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.UIManager;

public class AutoDismissMessage implements ActionListener {
    private JDialog dialog;

    public AutoDismissMessage(JDialog dialog) {
        this.dialog = dialog;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        dialog.dispose();
    }    

    static public void showMessageDialog(Component parent, Object message, 
    		int duration) {
      // run all of this on the EDT
      final JOptionPane optionPane = new JOptionPane(message);
      String title = UIManager.getString("OptionPane.messageDialogTitle");
      optionPane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
      final JDialog dialog = optionPane.createDialog(parent, title);
      Timer timer = new Timer(duration, new AutoDismissMessage(dialog));
      timer.setRepeats(false);
      timer.start();
      if (dialog.isDisplayable()) {
          dialog.setVisible(true);
      }
    }
}
