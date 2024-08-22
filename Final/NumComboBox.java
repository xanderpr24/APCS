import java.awt.event.ItemListener;

import javax.swing.*;

public class NumComboBox extends JComboBox<Enums.NumField> {
    
    public NumComboBox(ItemListener listener) {
        super(new Enums.NumField[] {
            Enums.NumField.AGE,
            Enums.NumField.T_REST_BPS,
            Enums.NumField.CHOL,
            Enums.NumField.MAX_RATE,
            Enums.NumField.VESSELS
        });

        this.addItemListener(listener);
    }

    public NumComboBox(ItemListener listener, Enums.NumField start) {
        super(new Enums.NumField[] {
            Enums.NumField.AGE,
            Enums.NumField.T_REST_BPS,
            Enums.NumField.CHOL,
            Enums.NumField.MAX_RATE,
            Enums.NumField.VESSELS
        });

        this.setSelectedItem(start);
        this.addItemListener(listener);
    }
}
