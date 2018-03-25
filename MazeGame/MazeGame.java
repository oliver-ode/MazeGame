import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MazeGame {
    //Variables that can scale game
    //It is recommended that width/cellsX == height/cellsY
    int width = 1280;
    int height = 800;
    int cellsX = 80;
    int cellsY = 50;

    //Non changeable
    final int xBuffer = 1;
    final int yBuffer = 23;
    int cellPxSizeX;
    int cellPxSizeY;
    Player player = new Player();
    MazeGeneration mazeGeneration = new MazeGeneration();
    Ball ball = new Ball();

    //Random maze generation
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
            if (e.getKeyChar() == KeyEvent.VK_W && !grid.getGrid()[player.getY()][player.getX()].getWallUp()) {
                player.setY(player.getY()-1);
            }
            if (e.getKeyChar() == KeyEvent.VK_D && !grid.getGrid()[player.getY()][player.getX()].getWallRight()) {
                player.setX(player.getX()+1);
            }
            if (e.getKeyChar() == KeyEvent.VK_S && !grid.getGrid()[player.getY()][player.getX()].getWallDown()) {
                player.setY(player.getY()+1);
            }
            if (e.getKeyChar() == KeyEvent.VK_A && !grid.getGrid()[player.getY()][player.getX()].getWallLeft()) {
                player.setX(player.getX()-1);
            }
            //TODO OVERRIDE CONTROLS
            if (e.getKeyChar() == KeyEvent.VK_I){
                player.setY(player.getY()-1);
            }
            if (e.getKeyChar() == KeyEvent.VK_L){
                player.setX(player.getX()+1);
            }
            if (e.getKeyChar() == KeyEvent.VK_K){
                player.setY(player.getY()+1);
            }
            if (e.getKeyChar() == KeyEvent.VK_J){
                player.setX(player.getX()-1);
            }
            if (e.getKeyChar() == KeyEvent.VK_SPACE){
                System.out.println("Is a wall up? " + grid.getGrid()[player.getY()][player.getX()].getWallUp());
                System.out.println("Is a wall down? " + grid.getGrid()[player.getY()][player.getX()].getWallDown());
                System.out.println("Is a wall left? " + grid.getGrid()[player.getY()][player.getX()].getWallLeft());
                System.out.println("Is a wall right? " + grid.getGrid()[player.getY()][player.getX()].getWallRight());
            }
            panel.repaint();
            if(player.getX() == grid.getWidth()-1 && player.getY() == grid.getHeight()-1){
                System.out.println("You win!");
                System.exit(0);
            }
        }
        @Override public void keyPressed(KeyEvent e) {} @Override public void keyReleased(KeyEvent e) {}
    };

    public MazeGame() {
        //Grid and cell setup
        frame.setSize(width+xBuffer, height+yBuffer);
        cellPxSizeX = width/cellsX;
        cellPxSizeY = height/cellsY;

        //Randomized grid
        grid = mazeGeneration.prims(new Grid(cellsX, cellsY));
        panel.repaint();

        //Graphics setup
        frame.add(panel);
        frame.addKeyListener(kl);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}