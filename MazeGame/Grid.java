public class Grid {
    //Variable declarations
    private int width;
    private int height;
    private Cell[][] grid;

    //Constructor with width and height (how many cells in x and y)
    public Grid(int w, int h){
        width = w;
        height = h;
        grid = new Cell[height][width];
        for(int i = 0; i < height; i++){
            for(int k = 0; k < width; k++){
                grid[i][k] = new Cell();
            }
        }
    }

    //Generate the maze randomly
    public void primRandomize(){
        MazeGeneration mg = new MazeGeneration();
        grid = mg.prims(grid);
    }

    //Getters
    public int getWidth(){
        return(width);
    }
    public int getHeight(){
        return(height);
    }
    public Cell[][] getGrid(){
        return(grid);
    }

    //Setters (kinda)
    public void removeRightWall(int x, int y){
        grid[y][x].wallRightRemove();
        grid[y][x+1].wallLeftRemove();
    }
    public void removeLeftWall(int x, int y){
        grid[y][x].wallLeftRemove();
        grid[y][x-1].wallRightRemove();
    }
    public void removeTopWall(int x, int y){
        grid[y][x].wallUpRemove();
        grid[y-1][x].wallDownRemove();
    }
    public void removeBottomWall(int x, int y){
        grid[y][x].wallDownRemove();
        grid[y+1][x].wallUpRemove();
    }

    //Hardcode setup
    public void setup(){
        //Sample maze
        //Never do this ***NEVER***
        removeBottomWall(0, 0);
        removeBottomWall(1, 0);
        removeRightWall(0, 0);
        removeBottomWall(2, 0);
        removeRightWall(1, 1);
        removeBottomWall(1, 1);
        removeLeftWall(1, 2);
        removeBottomWall(2, 1);
        removeBottomWall(2, 2);
        removeBottomWall(2, 3);
        removeLeftWall(2, 3);
        removeLeftWall(1, 3);
        removeBottomWall(0, 3);
        removeRightWall(0, 4);
        removeLeftWall(3, 3);
        removeRightWall(3, 3);
        removeTopWall(4, 3);
        removeTopWall(4, 2);
        removeRightWall(4, 1);
        removeRightWall(5, 1);
        removeBottomWall(6, 1);
        removeBottomWall(6, 2);
        removeRightWall(6, 3);
        removeBottomWall(7, 3);
        removeRightWall(2, 0);
        removeRightWall(3, 0);
        removeBottomWall(3, 0);
        removeBottomWall(3, 1);
        removeBottomWall(5, 0);
        removeRightWall(5, 0);
        removeRightWall(6, 0);
        removeBottomWall(7, 0);
        removeBottomWall(7, 1);
        removeLeftWall(5, 2);
        removeBottomWall(5, 2);
        removeBottomWall(5, 3);
        removeRightWall(5, 4);
        removeLeftWall(4, 4);
        removeLeftWall(3, 4);
    }
}