import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MazeGame {
    //Variables that can scale game
    int width = 1440;
    int height = 900;
    int cellsX = 8;
    int cellsY = 5;

    //Non changeable
    final int xBuffer = 1;
    final int yBuffer = 23;
    int cellPxSizeX;
    int cellPxSizeY;
    Player player = new Player();
    Grid grid = new Grid(cellsX, cellsY);
    private JFrame frame = new JFrame();
    private JPanel panel = new JPanel(){
        public void paint(Graphics g){
            //Setup
            Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            //Drawing grid
            g2.setColor(Color.BLACK);
            for(int y = 0; y < grid.getHeight(); y++){
                for(int x = 0; x < grid.getWidth(); x++){
                    //Upper line
                    if(grid.getGrid()[y][x].getWallUp()){
                        g2.drawLine(x*cellPxSizeX, y*cellPxSizeY, x*cellPxSizeX+cellPxSizeX, y*cellPxSizeY);
                    }
                    //Bottom line
                    if(grid.getGrid()[y][x].getWallDown()){
                        g2.drawLine(x*cellPxSizeX, y*cellPxSizeY+cellPxSizeY, x*cellPxSizeX+cellPxSizeX, y*cellPxSizeY+cellPxSizeY);
                    }
                    //Left line
                    if(grid.getGrid()[y][x].getWallLeft()){
                        g2.drawLine(x*cellPxSizeX, y*cellPxSizeY, x*cellPxSizeX, y*cellPxSizeY+cellPxSizeY);
                    }
                    //Right line
                    if(grid.getGrid()[y][x].getWallRight()){
                        g2.drawLine(x*cellPxSizeX+cellPxSizeX, y*cellPxSizeY, x*cellPxSizeX+cellPxSizeX, y*cellPxSizeY+cellPxSizeY);
                    }
                }
            }
            //Drawing player
            g2.setColor(player.getColour());
            g2.fillRect(player.getX()*cellPxSizeX, player.getY()*cellPxSizeY, cellPxSizeX, cellPxSizeY);
        }
    };
    private KeyListener kl = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {
            if (e.getKeyChar() == KeyEvent.VK_W) {
                player.setY(player.getY()-1);
            }
            else if (e.getKeyChar() == KeyEvent.VK_D) {
                player.setX(player.getX()+1);
            }
            if (e.getKeyChar() == KeyEvent.VK_S) {
                player.setY(player.getY()+1);
            }
            if (e.getKeyChar() == KeyEvent.VK_A) {
                player.setX(player.getX()-1);
            }

            panel.repaint();
        }
        @Override
        public void keyPressed(KeyEvent e) {
            // TODO Auto-generated method stub
        }
        @Override
        public void keyReleased(KeyEvent e) {
            // TODO Auto-generated method stub
        }


    };

    public MazeGame(){
        //Grid and cell setup
        frame.setSize(width+xBuffer, height+yBuffer);
        cellPxSizeX = width/cellsX;
        cellPxSizeY = height/cellsY;

        //Graphics setup
        frame.add(panel);
        frame.addKeyListener(kl);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}