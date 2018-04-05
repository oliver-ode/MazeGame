import java.awt.*;

public class Player {
    //Variable declarations
    private int x;
    private int y;
    private Color colour;

    //Default constructor
    public Player(){
        x = 0;
        y = 0;
        colour = Color.RED;
    }
    //Customizable constructor
    public Player(int xPos, int yPos, Color c){
        x = xPos;
        y = yPos;
        colour = c;
    }

    //Colliders
    public void collideBall(Ball b, int pxX, int pxY){
        if((x*pxX<b.getX()+pxX) && (x*pxX+pxX>b.getX()) && (y*pxY<b.getY()+pxY) && (y*pxY+pxY>b.getY())){
            System.out.println("You lose");
            System.exit(0);
        }
    }

    //Getters
    public int getX(){
        return(x);
    }
    public int getY(){
        return(y);
    }
    public Color getColour(){
        return(colour);
    }
    //Setters
    public void setX(int xPos){
        x = xPos;
    }
    public void setY(int yPos){
        y = yPos;
    }
}
