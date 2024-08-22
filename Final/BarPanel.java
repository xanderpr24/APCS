import javax.swing.*;
import java.awt.*;
import java.util.stream.IntStream;

public class BarPanel extends JPanel {   
    private int startX, startY, WIDTH, HEIGHT; 
    private final int LEFT_MARGIN = 60;
    private final int RIGHT_MARGIN = 0;
    private final int BOTTOM_MARGIN = 40;
    private final int OFFSET = 5;
    private int[] heights;
    private double relativeHeight;
    private String xTitle, yTitle;
    private String[] xLabels;
    private Color BACKGROUND;

    public BarPanel(int x, int y, App app) {
        Enums.Field xField = app.getXField();
        Enums.NumField yField = app.getYField();

        this.startX = x + 50;
        this.startY = y;
        this.WIDTH = app.getWidth() - 30;
        this.HEIGHT = app.getHeight() - 100;

        setSize(WIDTH, HEIGHT);
        Utils utils = new Utils(app);
        this.heights = utils.getProperty(yField.getProperty());
        this.xTitle = xField.getLabel();
        this.yTitle = yField.getLabel();
        this.BACKGROUND = app.getBackground();

        if (xField instanceof Enums.CatField)
        { xLabels = utils.getProperty(((Enums.CatField)xField).getProperty()); }
        else
        { xLabels = Utils.toStringArray(utils.getProperty(yField.getProperty())); }

        this.relativeHeight = (double) (HEIGHT - 60) / IntStream.of(heights).max().orElseThrow();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setup(g);
        build(g);
    }

    private void setup(Graphics g) {
        g.setColor(BACKGROUND);
        g.fillRect(startX, startY, WIDTH, HEIGHT);

        g.setColor(Color.BLACK);
        g.drawLine(startX, HEIGHT - BOTTOM_MARGIN, WIDTH, HEIGHT - BOTTOM_MARGIN); // X-Axis

    }

    private void drawTitles(Graphics g) {
        byte[] xTitleBytes = xTitle.getBytes();
        byte[] yTitleBytes = yTitle.getBytes();
   
        g.drawBytes(xTitleBytes, 0, xTitleBytes.length, (WIDTH - startX) / 2 + startX, HEIGHT - BOTTOM_MARGIN + 30);
        g.drawBytes(yTitleBytes, 0, yTitleBytes.length, startX, HEIGHT / 2);
    }

    private void build(Graphics g) {
        int BAR_WIDTH = (WIDTH - (LEFT_MARGIN + RIGHT_MARGIN + startX) ) / (heights.length) - OFFSET;
        int x, y, barHeight;

        for (int i = 0; i < heights.length; i++) {
            byte[] xLabel = xLabels[i].getBytes();
            byte[] yLabel = Integer.toString(heights[i]).getBytes();
            x = LEFT_MARGIN + startX + (OFFSET * i) + BAR_WIDTH * i;
            barHeight = (int) (relativeHeight * heights[i]);
            y = HEIGHT - BOTTOM_MARGIN - barHeight;

            g.setColor(Color.BLACK);
            g.drawRect(x - 1, y - 1, BAR_WIDTH + 1, barHeight + 1);
            g.setColor(Color.RED);
            g.fillRect(x, y, BAR_WIDTH, barHeight);
            g.setColor(Color.BLACK);
            g.drawBytes(xLabel, 0, xLabel.length, x, HEIGHT - BOTTOM_MARGIN + 12);
            g.drawBytes(yLabel, 0, yLabel.length, x + 2, HEIGHT - BOTTOM_MARGIN - 5 - barHeight);
            
        }

        drawTitles(g);


    }
}
