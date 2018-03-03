public class Cell {
    //Initialization of variables
    private boolean wallUp;
    private boolean wallDown;
    private boolean wallLeft;
    private boolean wallRight;
    private boolean visited;

    //Default constructor
    public Cell(){
        wallUp = true;
        wallDown = true;
        wallLeft = true;
        wallRight = true;
        visited = false;
    }

    //Customizable constructor
    public Cell(boolean wU, boolean wD, boolean wL, boolean wR, boolean v){
        wallUp = wU;
        wallDown = wD;
        wallLeft = wL;
        wallRight = wR;
        visited = v;
    }

    //Getters
    public boolean getVisited(){
        return(visited);
    }
    public boolean getWallUp(){
        return(wallUp);
    }
    public boolean getWallDown(){
        return(wallDown);
    }
    public boolean getWallLeft(){
        return(wallLeft);
    }
    public boolean getWallRight(){
        return(wallRight);
    }

    //Setters (kinda)
    public void makeVisited(){
        visited = true;
    }
    public void wallDownRemove(){
        wallDown = false;
    }
    public void wallUpRemove(){
        wallUp = false;
    }
    public void wallLeftRemove(){
        wallLeft = false;
    }
    public void wallRightRemove(){
        wallRight = false;
    }
}