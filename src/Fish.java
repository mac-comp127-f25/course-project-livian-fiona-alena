import java.util.Random;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.Image;
import edu.macalester.graphics.Point;

/**
 * Constructs a Fish object with a visible image(GraphicsObject), a hitbox, 
 * indenpendent movement with randomized velocity and size parameters.
 * 
 * Both NPC fish and player fish are represented using this class.
 */
public class Fish {
    private GraphicsObject shape;
    private double x;
    private double y;
    private Random rand = new Random();
    private double dx=rand.nextDouble(-120,-40);;
    private double dy= rand.nextDouble(-50,50);

    private double scale;
    private double targetScale;
    private double radiusX;
    private double radiusY;

    private HitBox hitBox;
    
    /**
     * Constructs a Fish object with a given center position, image path, and size scale.
     * 
     * @param centerX   The initial x-coordinate of the fish's center.
     * @param centerY   The initial y-coordinate of the fish's center.
     * @param pathname  The file path to the fish's image.
     * @param fishSize  The initial scale factor for the fish's and hitbox size.
     */
    public Fish(
        double centerX,
        double centerY,
        String pathname,
        double fishSize
    ) {
        shape = new Image(pathname);
        shape.setScale(fishSize);
        this.x = centerX;
        this.y = centerY;
        shape.setScale(fishSize);

        double base = 140;
        radiusX = base * fishSize;
        radiusY = base * fishSize * 0.55;
        hitBox = new HitBox(x, y, radiusX, radiusY);
        
        scale = fishSize;
        targetScale = fishSize;
    }
    
    /**
     * @return The current x-coordinate of the fish's center.
     */
    public double getCenterX() {
        return x;
    }


    /**
     * @return The current y-coordinate of the fish's center.
     */
    public double getCenterY() {
        return y;
    }

    /**
     * Resets the fish's x-coordinate to a specified value after a horizontal hit.
     * @param reappearX The x-coordinate to reset the fish's position to.
     * The updaate x-coordinate of the fish.
    */
    public void reset_X_ForHorizontalHit(double reappearX) {
        x = reappearX;
    }

    /**
     * Randomly resets the fish's horizontal velocity after a horizontal hit.
     * The updated horizontal velocity of the fish.
    */
    public void reset_dx_ForHorizontalHit() {
        dx = rand.nextDouble(-100,-40);
    }

    /**
     * Randomly resets the fish's vertical velocity after a horizontal hit.
     * The updated vertical velocity of the fish.
    */
    public void reset_dy_ForHorizontalHit() {
        dy = rand.nextDouble(-50,50);
    }

    /**
     * Inverts the fish's vertical velocity after a vertical hit.
     * @return The updated vertical velocity of the fish.
    */
    public void reset_dy_ForVerticalHit() {
        this.dy = -dy;
    }
    
    /**
     * Updates the fish's position based on its velocity and the elapsed time.
     * Alsp updates the position of the fish's hitbox.
     * 
     * @param dt The time step since the last update.
    */
    public void updatePosition(double dt) {
        x += dx * dt;
        y += dy * dt;
        shape.setCenter(x, y);
        hitBox.setCenter(x, y);
    }

    /**
     * Sets the fish's center position and its hitbox to new coordinates.
     * 
     * @param newX The new x-coordinate for the fish's center.
     * @param newY The new y-coordinate for the fish's center.
    */
    public void setCenter(double newX, double newY) {
        this.x = newX;
        this.y = newY;
        shape.setCenter(newX, newY);
        hitBox.setCenter(newX, newY);

    }

    /**
     * @return The current center point of the fish.
    */
    public Point getCenter() {
        return shape.getCenter();
    }

    /**
     * @return The GraphicsObject used for the visual representation of this fish.
    */
    public GraphicsObject getGraphics() {
        return shape;
    }
    
    /**
     * @return The HitBox object associated with this fish.
    */
    public HitBox getHitbox() {
        return hitBox;
    }

    /**
     * Determines if this fish overlaps with another fish based on their hitboxes.
     * 
     * @param other The other fish to check for overlap.
     * @return true if the two hitboxes overlap, false otherwise.
    */
    private boolean overlapAmount(Fish other) {
        double dx = this.getCenterX() - other.getCenterX();
        double dy = this.getCenterY() - other.getCenterY();

        double rx = this.radiusX + other.radiusX;
        double ry = this.radiusY + other.radiusY;

        double normalizeX = dx/rx;
        double normalizeY = dy/ry;

        double overlapValue = normalizeX * normalizeX + normalizeY * normalizeY;
        if (overlapValue <= 1.0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Handles the interaction between this fish and another fish.
     * 
     * If the fish overlap and one fish is larger, the larger fish grows
     * by an amount based on the smaller fish's scale, and the smaller fish "dies"
     * (its scale is set to zero).
     * 
     * @param other The other fish to interact with.
    */
    public void interactWith(Fish other) {
        if (this.scale == 0 || other.scale == 0) {
            return;
        }
        if(!this.overlapAmount(other)) {
            return;
        }
        if (this.scale > other.scale) {
            this.grow(other.getScale());
            goDie(other);

        } else if (this.scale < other.scale) {
            other.grow(this.getScale());
            goDie(this);
        }
    }

    /**
     * Sets the fish's scale to zero, indicating that it has "died".
     * Also sets the fish's visual representation scale to zero.
     * 
     * @param f The fish that should "die".
     */
    public void goDie(Fish f) {
        f.scale = 0;
        f.shape.setScale(0);
    }

    /**
     * @return The current scale of the fish.
    */
    public double getScale() {
        return this.scale;
    }

    /**
     * Increases the target scale of the fish based on the eaten fish's size.
     * 
     * @param amount The amount fo the eaten fish's scale.
    */
    public void grow(double amount) {
        targetScale += amount - 0.18;
    }

    /**
     * Gradually increases the fish's current scale towards its target scale.
     * Also updates the fish's hitbox size accordingly.
    */
    public void animateGrow() {
        if (targetScale > scale) {
            scale += 0.01;
            shape.setScale(this.scale);
            double base = 135;
            this.radiusX = base * this.scale;
            this.radiusY = base * this.scale * 0.3;
            hitBox.changeScale(radiusX, radiusY);
        }
    }

    /**
     * @return true if the fish is moving upwards.
    */
    public boolean isGoingUp() {
       return dy < 0;
    }

}
