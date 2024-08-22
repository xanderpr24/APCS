import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.stream.Collectors;

public class PiePanel extends JPanel {
    private int startX, startY, WIDTH, HEIGHT;
    private App app;
    private Enums.CatField field;
    private Color[] colors = new Color[] {
        Color.BLUE,
        Color.CYAN,
        Color.GREEN,
        Color.MAGENTA,
        Color.ORANGE,
        Color.RED,
        Color.YELLOW,
    };

    public PiePanel() {}
    
    public PiePanel(int x, int y, App app) {
        this.startX = x;
        this.startY = y;
        this.WIDTH = app.getWidth() - 30;
        this.HEIGHT = app.getHeight() - 100;
        this.app = app;
        this.field = (Enums.CatField)app.getXField();

        setSize(WIDTH, HEIGHT);
        setBackground(app.getBackground());
        setVisible(true);
    }

    private Map<Entry.Attribute, Double> getPortions() {
        double total = (double)app.getData().size();
        return app.getData()
            .stream()
            .collect(Collectors.groupingBy(field.getProperty()))
            .entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                e -> e.getValue().size() / total));
    }

    @Override
    public void paintComponent(Graphics g) {
        int color = 0;
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        double start = 0;

        for (Map.Entry<Entry.Attribute, Double> entry : getPortions().entrySet()) {
            g.setColor(colors[color]);
            g.fillArc(startX, startY, Integer.min(WIDTH, HEIGHT) - 100, Integer.min(WIDTH, HEIGHT) - 100, (int) (start * 360 + 0.99), (int) (entry.getValue() * 360 + 0.99));
            start = start + entry.getValue();
            color++;
        }

    }

}
