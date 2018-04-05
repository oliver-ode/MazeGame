import javax.sound.sampled.LineUnavailableException;

public class MazeGameRunner {
    public static void main(String[] args)throws LineUnavailableException{
        //It is recommended that width/cellsX == height/cellsY
        //1280, 800 - 8, 5
        final int baseLevel = 1;
        MazeGame mg = new MazeGame(1280, 800, 8*baseLevel, 5*baseLevel, baseLevel);
    }
}