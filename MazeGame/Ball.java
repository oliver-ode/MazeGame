import java.awt.*;

public class Ball {
    private Color c;
    private int x;
    private int y;
    private int xVel;
    private int yVel;

    //Default
    public Ball(){
        c = Color.BLUE;
        x = 200;
        y = 400;
        xVel = 5;
        yVel = 5;
    }

    //Customizable
    public Ball(Color color, int xPos, int yPos, int xVelocity, int yVelocity){
        c = color;
        x = xPos;
        y = yPos;
        xVel = xVelocity;
        yVel = yVelocity;
    }

    //Does not check to see if the ball will be outside of the screen
    public void updateBallPos(int screenWidth, int screenHeight, int ballXSize, int ballYSize){
        //Regular bounds
        if(x > screenWidth-ballXSize) xVel *= -1;
        if(x < 0) xVel *= -1;
        if(y > screenHeight-ballYSize) yVel *= -1;
        if(y < 0) yVel *= -1;
        x += xVel;
        y += yVel;
    }

    //Getters
    public Color getColour(){
        return(c);
    }
    public int getX(){
        return(x);
    }
    public int getY(){
        return(y);
    }
    public int getXVel(){
        return(xVel);
    }
    public int getYVel(){
        return(yVel);
    }
    //Setters
    public void setXVel(int newXVel){
        xVel = newXVel;
    }
    public void setYVel(int newYVel){
        yVel = newYVel;
    }
    public void setX(int newX){
        x = newX;
    }
    public void setY(int newY){
        y = newY;
    }
}
