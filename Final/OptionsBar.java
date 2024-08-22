import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.util.function.Consumer;
import java.util.Map;
import java.util.HashMap;

public class OptionsBar extends JMenuBar {
    private JMenu fileMenu, viewMenu;
    private Map<String, JPanel> items;
    // private JMenuItem barItem, lineItem, pieItem, tableItem, saveItem;
    private Consumer<JPanel> onGraphSelect;
    private BarPanel bar;
    private LinePanel line;
    private PiePanel pie;
    private TablePanel table;
    private Color BACKGROUND;
    private App app;

    public OptionsBar(App app, Color bg, Consumer<JPanel> onGraphSelect,
        BarPanel bar, LinePanel line, PiePanel pie, TablePanel table) {
        this.app = app;
        this.BACKGROUND = bg;
        this.onGraphSelect = onGraphSelect;
        this.bar = bar;
        this.line = line;
        this.pie = pie;
        this.table = table;
        this.items = new HashMap<>();

        build();
        setVisible(true);
    }

    public void update(BarPanel bar, LinePanel line, PiePanel pie, TablePanel table) {
        items.put("Bar Graph", bar);
        items.put("Line Graph", line);
        items.put("Pie Chart", pie);
        items.put("Table", table);
    }

    private void build() {
        setBackground(BACKGROUND);
        
        fileMenu = new JMenu("File");
        viewMenu = new JMenu("View");

        items.put("Bar Graph", bar);
        items.put("Line Graph", line);
        items.put("Pie Chart", pie);
        items.put("Table", table);

        ActionListener listener;
        JMenuItem item;
        
        for (String label : items.keySet()) {
            listener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e)
                { onGraphSelect.accept(items.get(label)); }
            };

            item = new JMenuItem(label);
            item.addActionListener(listener);
            
            viewMenu.add(item);
        }

        JMenuItem screenshotItm = new JMenuItem("Screenshot");
        screenshotItm.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e)
            { app.screenshot(); }
        });
        JMenuItem exitItm = new JMenuItem("Exit");
        exitItm.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e)
            { app.exit(); }
        });
        fileMenu.add(screenshotItm);
        fileMenu.add(exitItm);

        add(fileMenu);
        add(viewMenu);



    }
}
