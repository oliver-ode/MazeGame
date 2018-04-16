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
import java.util.ArrayList;
import java.util.Random;

public class MazeGame {
    //Screen dimenesions
    int width = 1280;
    int height = 800;
    //Number of cells
    int cellsX = 8;
    int cellsY = 5;
    //If the game has ended (will freeze game if true)
    boolean gameEnded = true;
    //Screen buffer
    final int xBuffer = 1;
    final int yBuffer = 23;
    //Variables that store the px size for the cells
    int cellPxSizeX;
    int cellPxSizeY;
    //Game level
    int level;
    //Player object
    Player player = new Player(0, 0, Color.RED);
    //Maze Generation class object
    MazeGeneration mazeGeneration = new MazeGeneration();
    //Random object
    Random rand = new Random();
    //Array list of balls
    ArrayList<Ball> balls = new ArrayList<>();
    //Radio x and y position
    int radX;
    int radY;
    //If the radio has been picked up
    boolean radioPickedUp = false;
    //What stage in the startup the program is in
    //1 --> Instructions
    //2 --> Game
    //3 --> Game over
    int startStage = 1;
    //Clip system for audio playing
    Clip clip = AudioSystem.getClip();
    //2D grid
    Grid grid = new Grid(cellsX, cellsY);
    //Frame
    private JFrame frame = new JFrame();
    //Panel
    private JPanel panel = new JPanel(){
        public void paint(Graphics g){
            //Setup
            Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            //Instruction stage
            if(startStage == 1 && level == 1){
                //Welcome
                g2.drawString("Welcome to my Maze Game", width/2, height/2-100);
                //Basic overview
                g2.drawString("The goal of the game is to get from the top left to the bottom right", width/2, height/2-50);
                g2.drawString("If you can do that you progress to the next level (which is harder)", width/2, height/2);
                g2.drawString("You also have to pick up a radio on the way which is yellow", width/2, height/2+50);
                g2.drawString("Make sure not to get hit by the balls though!", width/2, height/2+100);
                g2.drawString("Click enter to start", width/2, height/2+150);
                //Ending
                g2.drawString("Have fun!!", width/2, height/2+200);
            }
            //End stage
            if(startStage == 3){
                g2.drawString("Game over", width/2, height/2-25);
                g2.drawString("You got to level: " + level, width/2, height/2+25);
            }
            //Game stage
            if(startStage == 2){
                g2.setColor(Color.LIGHT_GRAY);
                g2.fillRect(0, 0, width, height);

                //Drawing player
                g2.setColor(player.getColour());
                g2.fillRect(player.getX()*cellPxSizeX, player.getY()*cellPxSizeY, cellPxSizeX, cellPxSizeY);

                //Drawing radio
                if(!radioPickedUp){
                    g2.setColor(Color.yellow);
                    g2.fillRect(radX*cellPxSizeX, radY*cellPxSizeY, cellPxSizeX, cellPxSizeY);
                }

                //Drawing ball
                for(int i = 0; i < balls.size(); i++){
                    g2.setColor(balls.get(i).getColour());
                    g2.fillOval(balls.get(i).getX(), balls.get(i).getY(), cellPxSizeX, cellPxSizeY);
                }

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
        }
    };
    //Volume code
    public void setVolume() {
        //FloatControl for setting volume
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        //Base volume is 100%
        double gain = 1;
        //If the player has not picked up the radio
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
            //The volume can never go lower than 10%
            gain /= 10;
            gain *= 9;
            gain += 0.1;
        }
        //Set the volume
        float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
        gainControl.setValue(dB);
    }
    private KeyListener kl = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {
            //Listen for the start key (enter)
            if (e.getKeyChar() == KeyEvent.VK_ENTER && startStage == 1){
                gameEnded = false;
                startStage = 2;
            }
            //Up movement
            if (e.getKeyChar() == KeyEvent.VK_W && !grid.getGrid()[player.getY()][player.getX()].getWallUp()) {
                player.setY(player.getY()-1);
            }
            //Right movement
            if (e.getKeyChar() == KeyEvent.VK_D && !grid.getGrid()[player.getY()][player.getX()].getWallRight()) {
                player.setX(player.getX()+1);
            }
            //Down movement
            if (e.getKeyChar() == KeyEvent.VK_S && !grid.getGrid()[player.getY()][player.getX()].getWallDown()) {
                player.setY(player.getY()+1);
            }
            //Left movement
            if (e.getKeyChar() == KeyEvent.VK_A && !grid.getGrid()[player.getY()][player.getX()].getWallLeft()) {
                player.setX(player.getX()-1);
            }
            //Radio movement
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
            //Runs volume code after every key press
            setVolume();
            //Redraw
            panel.repaint();
            //Player winning --> new maze
            if(player.getX() == grid.getWidth()-1 && player.getY() == grid.getHeight()-1 && radioPickedUp){
                //Player won
                try{
                    gameEnded = true;
                    for(int i = 0; i < level; i++){
                        balls.get(i).setY(-10000);
                    }
                    player.setX(0);
                    level += 1;
                    clip.stop();
                    MazeGame mg = new MazeGame(1280, 800, 8*level, 5*level, level);
                }
                catch (Exception f){
                    //Something really bad happened
                    System.exit(-1);
                }
            }
            //Player picked up radio
            if(player.getX() == radX && player.getY() == radY){
                radioPickedUp = true;
            }
        }
        @Override public void keyPressed(KeyEvent e) {} @Override public void keyReleased(KeyEvent e) {}
    };

    ActionListener ballUpdate = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            //Update balls if game has not ended
            if(gameEnded == false){
                for(int i = 0; i < balls.size(); i++){
                    balls.get(i).updateBallPos(width, height, cellPxSizeX, cellPxSizeY);
                }
            }
            //Check for collisions
            for(int i = 0; i < balls.size(); i++){
                if(player.collideBall(balls.get(i), cellPxSizeX, cellPxSizeY, level)){
                    startStage = 3;
                    gameEnded = true;
                }
            }
            //Repaint the panel
            panel.repaint();
        }
    };

    public MazeGame(int w, int h, int cX, int cY, int lvScalar) throws LineUnavailableException{
        //Game dimensions
        width = w;
        height = h;
        cellsX = cX;
        cellsY = cY;
        //Level
        level = lvScalar;
        //Bypass instructions if the player is not on level 1
        if(level != 1){
            startStage = 2;
            gameEnded = false;
        }
        //Generate balls
        for(int i = 0; i < level; i++){
            int baseBallSpeed = rand.nextInt(3)+1;
            balls.add(new Ball(Color.MAGENTA, 600, 400, baseBallSpeed, baseBallSpeed));
            balls.get(i).setXVel(baseBallSpeed*level);
            balls.get(i).setYVel(baseBallSpeed*level);
            balls.get(i).setX(100+rand.nextInt(width-200));
            balls.get(i).setY(100+rand.nextInt(height-200));
            int chance = rand.nextInt(101);
            if(chance % 2 == 1)balls.get(i).setXVel(balls.get(i).getXVel()*-1);
            chance = rand.nextInt(101);
            if(chance % 2 == 1)balls.get(i).setYVel(balls.get(i).getYVel()*-1);
        }
        do{
            radX = rand.nextInt(cellsX);
            radY = rand.nextInt(cellsY);
        } while((radX==0&&radY==0)||(radX==cellsX-1&&radY==cellsY-1));

        //Grid and cell setup
        frame.setSize(width+xBuffer, height+yBuffer);
        cellPxSizeX = width/cellsX;
        cellPxSizeY = height/cellsY;

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