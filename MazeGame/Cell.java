public class Cell {
    private boolean wallUp = true;
    private boolean wallDown = true;
    private boolean wallLeft = true;
    private boolean wallRight = true;

    public Cell(){

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
}
