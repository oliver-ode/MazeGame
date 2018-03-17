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

    //Index getter
    public int[] getIndex(Cell c){
        int[] index = new int[2];
        for(int i = 0; i < height; i++){
            for(int k = 0; k < width; k++){
                if(c.equals(grid[i][k])){
                    index[0] = i;
                    index[1] = k;
                    return(index);
                }
            }
        }
        System.out.println("Invalid object!");
        System.exit(-1);
        return(index);
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

    //Hardcode setup
    /**
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
     **/
}