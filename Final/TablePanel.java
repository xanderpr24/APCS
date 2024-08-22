import javax.swing.*;

import java.util.List;

public class TablePanel extends JPanel {
    private JTable table;
    private JScrollPane scroll;
    private String[][] rows;
    
    public TablePanel(App app, String[] labels) {
        
        Utils utils = new Utils(app);
        List<Entry> data = utils.limit(app.getDataLimit());


        rows = new String[data.size()][labels.length];
        for (int i = 0; i < data.size(); i++)
        { rows[i] = data.get(i).toArray(); }

        setBackground(app.getBackground());

        table = new JTable(rows, labels);
        scroll = new JScrollPane(table);

        table.setEnabled(false);

        add(scroll);
    }
}
