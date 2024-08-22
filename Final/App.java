import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Main app frame; links view panels to controller panels
 */
public class App extends JFrame {
    private List<Entry> data;
    private int dataLimit = 44;
    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    private Enums.Graph type;
    private Enums.Field xField;
    private Enums.NumField yField;
    private JPanel currentPanel;
    private ActionsPanel actions;
    private BarPanel barPanel;
    private LinePanel linePanel;
    private PiePanel piePanel;
    private TablePanel tablePanel;
    private String[] tableLabels;
    private String[][] tableRows;
    private final Color BACKGROUND = new Color(230, 255, 230);
    private OptionsBar options;

    public App(List<Entry> data, String[] tableLabels) {
        this.data = data;
        this.tableLabels = tableLabels;
        this.type = Enums.Graph.EMPTY;
        this.xField = Enums.NumField.AGE;
        this.yField = Enums.NumField.T_REST_BPS;

        data.sort(EntryComparator.comparing(Entry::age));

        build();
        setVisible(true);
    }

    private void build() {
        setSize(WIDTH, HEIGHT);
        setLayout(new BorderLayout(10, 5));
        setLocationRelativeTo(null);
        getContentPane().setBackground(BACKGROUND);

        getTableRows();
        
        actions = new ActionsPanel(this, 150, Enums.Graph.EMPTY, BACKGROUND);

        barPanel = new BarPanel(100, 0, this);
        linePanel = new LinePanel(0, 0, this);
        piePanel = new PiePanel();
        
        if (xField instanceof Enums.CatField) {
            barPanel = new BarPanel(100, 0, this);
            piePanel = new PiePanel(100, 0, this);

        }
        if (xField instanceof Enums.NumField)
        { linePanel = new LinePanel(200, 0, this); }
        tablePanel = new TablePanel(this, tableLabels);

        add(actions, BorderLayout.WEST);
        for (JPanel p : new JPanel[] { linePanel, barPanel, piePanel, tablePanel }) {
            add(p, BorderLayout.CENTER);
            p.setVisible(false);
        }

        currentPanel = linePanel;
        
        options = new OptionsBar(this, BACKGROUND, this::switchGraph, barPanel, linePanel, piePanel, tablePanel);
        setJMenuBar(options);
    }
    
    private void switchGraph(JPanel panel) {
        currentPanel.setVisible(false);

        if (panel instanceof BarPanel)
        { type = Enums.Graph.BAR; }
        else if (panel instanceof LinePanel)
        { type = Enums.Graph.LINE; }
        else if (panel instanceof PiePanel)
        { type = Enums.Graph.PIE; }
        else if (panel instanceof TablePanel)
        { type = Enums.Graph.TABLE; }
        else {
            type = Enums.Graph.EMPTY;
        }

        actions.update(type);

        currentPanel = panel;
        panel.setVisible(true);
    }

    private void getTableRows() {
        tableRows = new String[data.size()][tableLabels.length];
        for (int i = 0; i < data.size(); i++)
        { tableRows[i] = data.get(i).toArray(); }
    }
    
    private void rebuildGraphs(Enums.Field xAxis, Enums.NumField yAxis) {
        this.xField = xAxis;
        this.yField = yAxis;
        for (JPanel p : new JPanel[] { linePanel, barPanel, piePanel, tablePanel }) {
            remove(p);
        }

        if (xAxis instanceof Enums.CatField) {
            barPanel = new BarPanel(100, 0, this);
            piePanel = new PiePanel(200, 0, this);
        }
        if (xAxis instanceof Enums.NumField)
        { linePanel = new LinePanel(100, 0, this); }
        tablePanel = new TablePanel(this, tableLabels);

        for (JPanel p : new JPanel[] { linePanel, barPanel, piePanel, tablePanel }) {
            add(p, BorderLayout.CENTER);
            p.setVisible(false);
        }

        switch (type) {
            case BAR -> barPanel.setVisible(true);
            case LINE -> linePanel.setVisible(true);
            case PIE -> piePanel.setVisible(true);
            case TABLE -> tablePanel.setVisible(true);
            case EMPTY -> {}
        }

    }

    public void sortData(Enums.NumField field) {
        data.sort(EntryComparator.comparing(field.getProperty()));
        rebuildGraphs(xField, yField);
    }

    public void screenshot() {
        File f = new File("screenshot.png");
        JFileChooser chooser = new JFileChooser(f);
        chooser.showSaveDialog(this);
        File saveLoc = new File(checkFileExtension(chooser.getSelectedFile().getName(), "png"));
        Rectangle rect = this.getBounds();
        BufferedImage img = new BufferedImage(rect.width, rect.height, BufferedImage.TYPE_INT_ARGB);
        this.paint(img.getGraphics());

        try
        { ImageIO.write(img, "png", saveLoc); }
        catch (IOException ex)
        { throw new RuntimeException(ex); }
    }

    public void exit() {
        int exit = JOptionPane.showConfirmDialog(this, "Exit?", "Confirm exit", JOptionPane.YES_NO_OPTION);
        if (exit == 0)
        { System.exit(0); }
    }

    private String checkFileExtension(String fileName, String extension) {
        String name = fileName.chars().mapToObj(i -> (char)i).takeWhile(c -> c != '.').reduce(new String(), (s, c) -> s + c, (a, b) -> a + b);
        return name + "." + extension;
    }


    public void setDataLimit(int dataLimit) {
        this.dataLimit = dataLimit;
        rebuildGraphs(xField, yField);
    }

    public void setXField(Enums.Field xField) {
        this.xField = xField;
        rebuildGraphs(xField, yField);
    }

    public void setYField(Enums.NumField yField) {
        this.yField = yField;
        rebuildGraphs(xField, yField);
    }

    public ActionsPanel getActions() { return this.actions; }
    public List<Entry> getData() { return this.data; }
    public int getDataLimit() { return this.dataLimit; }
    public Enums.Field getXField() { return this.xField; }
    public Enums.NumField getYField() { return this.yField; }
    public Color getBackground() { return this.BACKGROUND; }
    
}
