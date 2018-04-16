import javax.sound.sampled.LineUnavailableException; //Audio exception

public class MazeGameRunner {
    public static void main(String[] args)throws LineUnavailableException{
        //It is recommended that width/cellsX == height/cellsY
        //1280, 800 - 8, 5
        //Final variable that sets the base level of the game (default 1)
        final int baseLevel = 1;
        //Creates instance of the game
        MazeGame mg = new MazeGame(1280, 800, 8*baseLevel, 5*baseLevel, baseLevel);
    }
}