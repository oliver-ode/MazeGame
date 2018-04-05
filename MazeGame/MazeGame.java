import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Random;

public class MazeGame {
    int width = 1280;
    int height = 800;
    int cellsX = 800;
    int cellsY = 500;

    final int baseBallSpeed = 2;
    boolean gameEnded = false;

    //Non changeable
    final int xBuffer = 1;
    final int yBuffer = 23;
    int cellPxSizeX;
    int cellPxSizeY;
    int level;
    Player player = new Player(0, 0, Color.RED);
    MazeGeneration mazeGeneration = new MazeGeneration();
    Random rand = new Random();
    Ball ball = new Ball(Color.MAGENTA, 600, 400, baseBallSpeed, baseBallSpeed);

    int radX;
    int radY;
    boolean radioPickedUp = false;

    Clip clip = AudioSystem.getClip();

    //Random maze generation
    Grid grid = new Grid(cellsX, cellsY);
    private JFrame frame = new JFrame();
    private JPanel panel = new JPanel(){
        public void paint(Graphics g){
            //Setup
            Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(Color.LIGHT_GRAY);
            g2.fillRect(0, 0, width, height);

            //Drawing player
            g2.setColor(player.getColour());
            g2.fillRect(player.getX()*cellPxSizeX, player.getY()*cellPxSizeY, cellPxSizeX, cellPxSizeY);

            //Drawing radio
            if(!radioPickedUp){
                g2.setColor(Color.ORANGE);
                g2.fillRect(radX*cellPxSizeX, radY*cellPxSizeY, cellPxSizeX, cellPxSizeY);
            }

            //Drawing ball
            g2.setColor(ball.getColour());
            g2.fillOval(ball.getX(), ball.getY(), cellPxSizeX, cellPxSizeY);

            //Drawing grid
            g2.setColor(Color.BLUE);
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
        }
    };
    //Volume code
    public void setVolume() {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

        double gain = 1;
        if(!radioPickedUp){
            int totalXDistance = (cellsX-1)*(cellsX-1);
            int totalYDistance = (cellsY-1)*(cellsY-1);
            double totalDistance = Math.sqrt(totalXDistance+totalYDistance);
            int xDistance = Math.abs(player.getX()-radX);
            int yDistance = Math.abs(player.getY()-radY);
            xDistance *= xDistance;
            yDistance *= yDistance;
            double partDistance = Math.sqrt(xDistance + yDistance);
            gain = partDistance/totalDistance;
            gain = 1-gain;
            //The volume can never go lower than 10%%
            gain /= 10;
            gain *= 9;
            gain += 0.1;
        }

        float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
        gainControl.setValue(dB);
    }
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
            //Radio
            if (e.getKeyChar() == KeyEvent.VK_I && !grid.getGrid()[radY][radX].getWallUp()){
                radY -= 1;
            }
            if (e.getKeyChar() == KeyEvent.VK_L && !grid.getGrid()[radY][radX].getWallRight()){
                radX += 1;
            }
            if (e.getKeyChar() == KeyEvent.VK_K && !grid.getGrid()[radY][radX].getWallDown()){
                radY += 1;
            }
            if (e.getKeyChar() == KeyEvent.VK_J && !grid.getGrid()[radY][radX].getWallLeft()){
                radX -= 1;
            }
            if (e.getKeyChar() == KeyEvent.VK_SPACE){
                //
            }
            setVolume();
            panel.repaint();
            if(player.getX() == grid.getWidth()-1 && player.getY() == grid.getHeight()-1 && radioPickedUp){
                //Player won
                try{
                    gameEnded = true;
                    ball.setY(-10000);
                    player.setX(0);
                    level += 1;
                    clip.stop();
                    MazeGame mg = new MazeGame(1280, 800, 8*level, 5*level, level);
                }
                catch (Exception f){
                    System.exit(-1);
                }
            }
            if(player.getX() == radX && player.getY() == radY){
                radioPickedUp = true;
            }
        }
        @Override public void keyPressed(KeyEvent e) {} @Override public void keyReleased(KeyEvent e) {}
    };

    ActionListener ballUpdate = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(gameEnded == false) ball.updateBallPos(width, height, cellPxSizeX, cellPxSizeY);
            player.collideBall(ball, cellPxSizeX, cellPxSizeY);
            panel.repaint();
        }
    };

    public MazeGame(int w, int h, int cX, int cY, int lvScalar) throws LineUnavailableException{
        width = w;
        height = h;
        cellsX = cX;
        cellsY = cY;
        level = lvScalar;
        ball.setXVel(baseBallSpeed*level);
        ball.setYVel(baseBallSpeed*level);

        do{
            radX = rand.nextInt(cellsX);
            radY = rand.nextInt(cellsY);
        } while((radX==0&&radY==0)||(radX==cellsX-1&&radY==cellsY-1));

        //Grid and cell setup
        frame.setSize(width+xBuffer, height+yBuffer);
        cellPxSizeX = width/cellsX;
        cellPxSizeY = height/cellsY;

        ball.setX(cellPxSizeX+rand.nextInt(width-(cellPxSizeX*2)));
        ball.setY(cellPxSizeY+rand.nextInt(height-(cellPxSizeY*2)));

        int chance = rand.nextInt(101);
        if(chance % 2 == 1)ball.setXVel(ball.getXVel()*-1);
        chance = rand.nextInt(101);
        if(chance % 2 == 1)ball.setYVel(ball.getYVel()*-1);

        //Randomized grid
        grid = mazeGeneration.prims(new Grid(cellsX, cellsY));
        panel.repaint();

        //Timer for updating ball
        Timer t = new Timer(10, ballUpdate);
        t.start();

        //Music
        try {
            File file = new File("/Users/oliver.odendaal/Documents/GitHub/ICS4U_Project2/MazeGame/tetris.wav");
            clip.open(AudioSystem.getAudioInputStream(file));
            clip.loop(Integer.MAX_VALUE);
            clip.start();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        setVolume();

        //Graphics setup
        frame.add(panel);
        frame.addKeyListener(kl);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}