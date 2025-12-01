import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.Image;
import edu.macalester.graphics.Point;

public class FishGraphic {
    public static final double RADIUS = 5;
    private double x;
    private double y;
    private GraphicsObject fishShape;
    private double dx;
    private double dy;

    public FishGraphic(
        double centerX,
        double centerY,
        double maxX,
        double maxY,
        double dx,
        double dy
    ){
        fishShape = new Image("ClownFish.png");
        fishShape.setScale(0.1);
        this.x=centerX;
        this.y=centerY;
        this.dx = dx;
        this.dy = dy;
    }

    public GraphicsObject getFishGraphics() {
        return fishShape;
    }

    public double getCenterX(){
        return this.x;
    }

    public double getCenterY(){
        return this.y;
    }

    public void updatePosition(double dt) {
        this.x=x + dx * dt;
        this.y=y + dy * dt;
        if (0 <= x && x <=600 && 0 <= y && y <= 800){
            fishShape.setCenter(x,y);
            
        }
    }

    public void setCenter(double newX, double newY ){
        fishShape.setCenter(newX,newY );
    }

}
