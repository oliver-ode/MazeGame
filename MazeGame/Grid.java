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
}