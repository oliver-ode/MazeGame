import java.util.ArrayList;

public class Grid {
    private ArrayList<Cell> grid;
    private int width;
    private int height;
    public Grid(int w, int h){
        width = w;
        height = h;
        ArrayList<Cell> grid = new ArrayList<Cell>(width*height);

    }
    public Cell valueAt(int x, int y){
        return(grid.get(y*height+width));
    }
    public void setValue(Cell c, int x, int y){
        grid.set(y*height+width, c);
    }
}
