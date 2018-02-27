import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MazeGame {
    int cellPxSize = 10;
    Cell cell = new Cell();
    Grid grid = new Grid(10, 10);
    private JFrame frame = new JFrame();
    private JPanel panel = new JPanel(){
        public void paint(Graphics g){
            //Setup
            Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            //Upper line
            if(cell.getWallUp()) g2.drawLine(0, 0, 0+cellPxSize, 0);
            //Bottom line
            if(cell.getWallDown()) g2.drawLine(0, 0+cellPxSize, 0+cellPxSize, 0+cellPxSize);
            //Left line
            if(cell.getWallLeft()) g2.drawLine(0, 0, 0, 0+cellPxSize);
            //Right line
            if(cell.getWallRight()) g2.drawLine(0+cellPxSize, 0, 0+cellPxSize, 0+cellPxSize);
        }
    };
    public MazeGame(){
        frame.setSize(800, 600);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);

    }
}
