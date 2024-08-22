import javax.swing.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;

public class ActionsPanel extends JPanel {
    private int WIDTH, HEIGHT, dataLimit = 500;
    private App app;
    private Enums.Graph type;
    private Color BACKGROUND;
    private JLabel dataLimitLbl;
    private JSlider dataLimitSld;
    private JTextField dataLimitFld;
    private JLabel xFieldLbl, yFieldLbl, sortLbl;
    private JComboBox<?> xFieldCmb, yFieldCmb;
    private ChangeListener sldListener;
    private ItemListener xCmbListener, yCmbListener;
    private NumComboBox sortCmb;
    private NumComboBoxListener sortCmbListener;

    public ActionsPanel(App app, int width, Enums.Graph type, Color bg) {
        this.app = app;
        this.WIDTH = width;
        this.HEIGHT = app.getHeight();
        this.type = type;
        this.BACKGROUND = bg;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new FlowLayout(FlowLayout.CENTER, WIDTH, 10));
        
        sldListener = new ChangeListener() {
            @Override public void stateChanged(ChangeEvent e) {
                setDataLimit(getVal());
            }
        };

        xCmbListener = new NumComboBoxListener(app::setXField);
        yCmbListener = new NumComboBoxListener(app::setYField);


        buildDataLimitSld(0, 1000);
        
        dataLimitLbl = new JLabel("Data Limit");

        dataLimitFld = new JTextField(Integer.toString(dataLimit), 4);
        dataLimitFld.setHorizontalAlignment(JTextField.CENTER);
        dataLimitFld.setPreferredSize(new Dimension(WIDTH, 20));

        xFieldLbl = new JLabel("X-Axis");
        yFieldLbl = new JLabel("Y-Axis");
        
        xFieldCmb = new NumComboBox(xCmbListener);
        yFieldCmb = new NumComboBox(yCmbListener, Enums.NumField.T_REST_BPS);

        sortLbl = new JLabel("Sorting");
        sortCmbListener = new NumComboBoxListener(app::sortData);
        sortCmb = new NumComboBox(sortCmbListener, Enums.NumField.AGE);

        setPreferredSize(new Dimension(width, HEIGHT));
        setBackground(BACKGROUND);

        add(dataLimitLbl);
        add(dataLimitSld);
        add(dataLimitFld);
        add(xFieldLbl);
        add(xFieldCmb);
        add(yFieldLbl);
        add(yFieldCmb);
        add(sortLbl);
        add(sortCmb);
        
        setVisible(true);
    }

    private void buildDataLimitSld(int min, int max) {
        dataLimitSld = new JSlider(min, max);
        dataLimitSld.addChangeListener(sldListener);

        dataLimitSld.setBackground(BACKGROUND);
        dataLimitSld.setPreferredSize(new Dimension(WIDTH - 10, 20));

    }

    public void setDataLimit(int dataLimit) {
        this.dataLimit = dataLimit;
        dataLimitFld.setText(Integer.toString(dataLimit));
        app.setDataLimit(dataLimit);
    }
    
    public int getDataLimit()
    { return this.dataLimit; }

    public void update(Enums.Graph type) {
        this.type = type;

        remove(xFieldCmb);
        remove(yFieldCmb);

        xFieldCmb.setEnabled(true);
        yFieldCmb.setEnabled(true);
        sortCmb.setEnabled(true);

        switch (type) {
            case Enums.Graph.BAR:
                setDataLimit(20);
                xFieldCmb = new CatComboBox(new CatComboBoxListener(app::setXField), Enums.CatField.SEX);
                yFieldCmb.setSelectedItem(Enums.NumField.MAX_RATE);
                break;
            case Enums.Graph.LINE:
                setDataLimit(50);
                xFieldCmb = new NumComboBox(new NumComboBoxListener(app::setXField));
                xFieldCmb.setSelectedItem(Enums.NumField.T_REST_BPS);
                break;
            case Enums.Graph.PIE:
                xFieldCmb = new CatComboBox(new CatComboBoxListener(app::setXField), Enums.CatField.SEX);
                yFieldCmb.setEnabled(false);
                sortCmb.setEnabled(false);
                break;
            case Enums.Graph.TABLE:
                xFieldCmb.setEnabled(false);
                yFieldCmb.setEnabled(false);
                break;
            default:
                break;
        }

        add(xFieldCmb, 4);
        add(yFieldCmb, 6);
        
        app.setDataLimit(dataLimit);
    }

    private int getVal() {
        int val = dataLimitSld.getValue();
        return (int) switch (type) {
            case BAR -> val / 25. + 2;
            case LINE -> val + 44;
            case PIE -> val + 1;
            case TABLE -> val + 1;
            default -> val + 1;
        };
    }
}
