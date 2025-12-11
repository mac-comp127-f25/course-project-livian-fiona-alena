
import edu.macalester.graphics.Ellipse;

/**
 * Represents an elliptical hitbox for collision detection.
 * The hitbox can be visualized for testing. 
 * The hitbox is not visible in the final game.
 */
public class HitBox {
    private static final boolean DEBUG_HIT_BOXES = false;
    private double x;
    private double y;
    private double radiusX;
    private double radiusY;
    private Ellipse hitBox;

    /**
     * Creates a HitBox with specified center coordinates and radii.
     * 
     * @param centerX The x-coordinate of the hitbox center.
     * @param centerY The y-coordinate of the hitbox center.
     * @param radiusX The horizontal radius of the hitbox.
     * @param radiusY The vertical radius of the hitbox.
    */
    public HitBox ( double centerX,double centerY, double radiusX, double radiusY){
        this.x = centerX;
        this.y = centerY;
        this.radiusX = radiusX;
        this.radiusY = radiusY;
        
        hitBox = new Ellipse(x-radiusX, y-radiusY, radiusX*2, radiusY*2);
        hitBox.setStroked(DEBUG_HIT_BOXES);
        hitBox.setFilled(false);
    }

    /**
     * updates the radii of the hitbox when the fish grows.
     */
    public void changeScale(double new_X_Radius, double new_Y_Radius){
        this.radiusX = new_X_Radius;
        this.radiusY = new_Y_Radius;
        hitBox.setPosition(x-radiusX, y-radiusY);
        hitBox.setSize(2*radiusX, 2*radiusY);
    }

    /**
     * Sets the center of the hitbox to new coordinates.
     * @param newX The new x-coordinate of the hitbox center.
     * @param newY The new y-coordinate of the hitbox center.
     */
    public void setCenter(double newX, double newY){
        this.x = newX;
        this.y = newY;
        hitBox.setCenter(newX,newY);
    }

    /**
     * @return The Ellipse object representing the hitbox.
    */
    public Ellipse getHitBoxShape(){
        return hitBox;
    }

}
