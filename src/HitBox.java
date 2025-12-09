
import edu.macalester.graphics.Ellipse;

public class HitBox {
    private static final boolean DEBUG_HIT_BOXES = true;
    private double x;
    private double y;
    private double radiusX;
    private double radiusY;
    private Ellipse hitBox;

    public HitBox ( double centerX,double centerY, double radiusX, double radiusY){
        this.x = centerX;
        this.y = centerY;
        this.radiusX = radiusX;
        this.radiusY = radiusY;
        
        hitBox = new Ellipse(x-radiusX, y-radiusY, radiusX*2, radiusY*2);
        hitBox.setStroked(DEBUG_HIT_BOXES);
        hitBox.setFilled(false);
    }

    public void changeScale(double new_X_Radius, double new_Y_Radius){
        this.radiusX = new_X_Radius;
        this.radiusY = new_Y_Radius;
        hitBox.setPosition(x-radiusX, y-radiusY);
        hitBox.setSize(2*radiusX, 2*radiusY);
    }

    public void setCenter(double newX, double newY){
        this.x = newX;
        this.y = newY;
        hitBox.setCenter(newX,newY);
    }

    public Ellipse getHitBoxShape(){
        return hitBox;
    }

}
