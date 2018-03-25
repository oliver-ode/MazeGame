import java.awt.*;

public class Ball {
    Color c;
    int x;
    int y;
    int xVel;
    int yVel;

    //Default
    public Ball(){
        c = Color.BLUE;
        x = 400;
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
}
