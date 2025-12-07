import java.awt.Color;
import java.util.Random;

import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.Image;
import edu.macalester.graphics.Point;

public class Fish {
    private GraphicsObject shape;
    private double x;
    private double y;
    private Random rand = new Random();
    private double dx=rand.nextDouble(-100,-40);;
    private double dy= rand.nextDouble(-50,50);

    private double scale;
    private double radiusX;
    private double radiusY;

    private Ellipse hitBox;

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
        Image img = new Image(pathname);
        img.setScale(fishSize);

        double base =135;
        radiusX = base * fishSize;
        radiusY = base * fishSize * 0.3;

        hitBox = new Ellipse(x-radiusX, y-radiusY, radiusX*2, radiusY*2);
        hitBox.setStrokeColor(Color.RED);
        hitBox.setFilled(false);
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
        hitBox.setCenter(x, y);
    }

    public void setCenter(double newX, double newY ){
        shape.setCenter(newX,newY );
        hitBox.setCenter(newX,newY);
    }

    public Point getCenter(){
        return shape.getCenter();
    }

    public GraphicsObject getGraphics() {
        return shape;
    }
    
    public Ellipse getHitbox(){
        return hitBox;
    }

}
