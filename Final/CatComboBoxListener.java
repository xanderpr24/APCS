import java.awt.event.*;
import java.util.function.Consumer;

public class CatComboBoxListener implements ItemListener {
    private Consumer<Enums.CatField> action;

    public CatComboBoxListener(Consumer<Enums.CatField> action) {
        this.action = action;
    }
    
    @Override
    public void itemStateChanged(ItemEvent e) {
        action.accept(
            (Enums.CatField)
            ((CatComboBox)e.getSource())
            .getSelectedItem()
        );
    }
}
