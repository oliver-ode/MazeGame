public class Grid {
    //Variable declarations
    private int width;
    private int height;
    Cell[][] grid;

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
}