import java.awt.event.ItemListener;

import javax.swing.*;

public class CatComboBox extends JComboBox<Enums.CatField> {

    public CatComboBox(ItemListener listener) {
        super(new Enums.CatField[] {
            Enums.CatField.REST_ECG,
            Enums.CatField.SEX,
            Enums.CatField.SLOPE,
            Enums.CatField.THAL,
        });

        this.addItemListener(listener);
    }

    public CatComboBox(ItemListener listener, Enums.CatField field) {
        this(listener);
        this.setSelectedItem(field);
    }
}
