import java.awt.event.*;
import java.util.function.Consumer;

public class NumComboBoxListener implements ItemListener {
    private Consumer<Enums.NumField> action;

    public NumComboBoxListener(Consumer<Enums.NumField> action) {
        this.action = action;
    }
    
    @Override
    public void itemStateChanged(ItemEvent e) {
        action.accept(
            (Enums.NumField)
            ((NumComboBox)e.getSource())
            .getSelectedItem()
        );
    }
}
