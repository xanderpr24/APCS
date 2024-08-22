import javax.swing.*;
import java.awt.Graphics;
import java.awt.Color;

import java.util.stream.IntStream;
import java.util.stream.Collectors;
import java.util.*;

public class LinePanel extends JPanel {
    private int WIDTH, HEIGHT, startX, startY, xMax, xMin, yMin, yMax, BOTTOM_OFFSET, LEFT_OFFSET; 
    private String xLabel, yLabel;
    private List<Integer> sortedX;
    private Map<Integer, Integer> points;
    private Color BACKGROUND;

    public LinePanel(int x, int y, App app) {
        WIDTH = app.getWidth() - 30;
        HEIGHT = app.getHeight() - 62;
        setSize(WIDTH, HEIGHT);
        this.startX = x;
        this.startY = y;

        Enums.NumField xAxis = (Enums.NumField)app.getXField();
        Enums.NumField yAxis = (Enums.NumField)app.getYField();
        
        this.xLabel = xAxis.getLabel();
        this.yLabel = yAxis.getLabel();
        this.points = new HashMap<>();
        this.BACKGROUND = app.getBackground();

        Utils utils = new Utils(app);
        int[] xVals = utils.getProperty(xAxis.getProperty());
        int[] yVals = utils.getProperty(yAxis.getProperty());
        IntStream.range(0, yVals.length)
        .forEach(i -> points.put(xVals[i], yVals[i]));
        this.xMin = IntStream.of(xVals).min().orElseThrow();
        this.xMax = IntStream.of(xVals).max().orElseThrow();
        this.yMin = points.values().stream().mapToInt(i -> i).min().orElseThrow();
        this.yMax = points.values().stream().mapToInt(i -> i).max().orElseThrow();
        this.BOTTOM_OFFSET = 50;
        this.LEFT_OFFSET = 65;

        sortedX = new ArrayList<>(
            IntStream.of(xVals)
            .boxed()
            .collect(Collectors.toSet())
        );
        Collections.sort(sortedX);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setup(g);
        build(g);
    }

    private void setup(Graphics g) {
        byte[] xLabelBytes = xLabel.getBytes();
        byte[] yLabelBytes = yLabel.getBytes();

        g.setColor(BACKGROUND);
        g.fillRect(startX, startY, WIDTH, HEIGHT);
        g.setColor(Color.BLACK);
        g.drawLine(startX + LEFT_OFFSET, startY, startX +LEFT_OFFSET, HEIGHT - BOTTOM_OFFSET);
        g.drawLine(startX + LEFT_OFFSET, HEIGHT - BOTTOM_OFFSET, WIDTH + startX, HEIGHT - BOTTOM_OFFSET);

        int xInc = (int) ((double) (xMax - xMin) / 10);
        for (int i = xMin + xInc; i <= xMax; i += xInc) {
            int x = (int) relX(i);
            byte[] bytes = Integer.toString(i).getBytes();
            g.drawBytes(bytes, 0, bytes.length, x - 5, HEIGHT - BOTTOM_OFFSET - 20);
            g.drawLine(x, HEIGHT - BOTTOM_OFFSET, x, HEIGHT - BOTTOM_OFFSET - 10);
        }

        
        int yInc = (int) ((double) (yMax - yMin) / 10);
        for (int i = yMin + yInc; i <= yMax; i += yInc) {
            int y = HEIGHT - (int) relY(i);
            
            byte[] bytes = Integer.toString(i).getBytes();
            g.drawBytes(bytes, 0, bytes.length, startX + LEFT_OFFSET + 20, y + 5);
            g.drawLine(startX + LEFT_OFFSET, y, startX + LEFT_OFFSET + 10, y);
        }

        g.setColor(Color.BLACK);
        g.drawBytes(xLabelBytes, 0, xLabelBytes.length, startX + ((WIDTH - startX) / 2), HEIGHT - (BOTTOM_OFFSET / 2));
        g.drawBytes(yLabelBytes, 0, yLabelBytes.length, startX, HEIGHT / 2);
    }

    private void build(Graphics g) {
        int x, y;

        g.setColor(Color.BLACK);
        for (int i = 1; i < sortedX.size(); i++) {
            int x1 = sortedX.get(i - 1);
            int y1 = points.get(x1);
            int x2 = sortedX.get(i);
            int y2 = points.get(x2);
            g.drawLine(
                (int) relX(x1), 
                HEIGHT - (int) relY(y1),
                (int) relX(x2),
                HEIGHT - (int) relY(y2));
        }

        for (int i = 0; i < sortedX.size(); i++) {
            int xVal = sortedX.get(i);
            x = -4 + (int)relX(xVal);
            y = -4 + HEIGHT - (int)relY(points.get(xVal));

            g.setColor(Color.RED);
            g.fillArc(x, y, 8, 8, 0, 360);
            g.setColor(Color.BLACK);
            g.drawArc(x, y, 8, 8, 0, 360);
            
        }

    }

    private double relX(int x) {
        return (WIDTH - startX - LEFT_OFFSET - 6) * (x - xMin) / (xMax - xMin) + startX + LEFT_OFFSET;
    }

    private double relY(int y) {
        double relY = (HEIGHT - BOTTOM_OFFSET - 6) * (y - yMin) / (yMax - yMin) + BOTTOM_OFFSET;
        return relY;
    }
    
}
