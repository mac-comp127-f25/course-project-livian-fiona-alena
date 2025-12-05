import java.util.Random;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.Image;

public class Fish {
    private GraphicsObject shape;
    private double x;
    private double y;
    private Random rand = new Random();
    private double dx=rand.nextDouble(-100,-40);;
    private double dy= rand.nextDouble(-50,50);

    public Fish(
        double centerX,
        double centerY,
        String pathname,
        double fishSize
    ){
        shape = new Image(pathname);
        shape.setScale(fishSize);
        this.x = centerX;
        this.y = centerY;
    }

    public double getCenterX(){
        return x;
    }

    public double getCenterY(){
        return y;
    }

    public double reset_X_ForHorizontalHit(){
        return x = FeedingFrenzy.CANVAS_WIDTH;
    }

    public double reset_dx_ForHorizontalHit(){
        return dx = rand.nextDouble(-100,-40);
    }

    public double reset_dy_ForHorizontalHit(){
        return dy = rand.nextDouble(-50,50);
    }

    public double reset_dy_ForVerticalHit(){
        return this.dy = -dy;
    }
    
    public void updatePosition(double dt) {
        x += dx * dt;
        y += dy * dt;
        shape.setCenter(x, y);
    }

    public void setCenter(double newX, double newY ){
        shape.setCenter(newX,newY );
    }

    public GraphicsObject getGraphics() {
        return shape;
    }

}
