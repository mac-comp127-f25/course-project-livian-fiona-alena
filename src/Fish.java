import java.util.Random;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.Image;
import edu.macalester.graphics.Point;

public class Fish {
    private GraphicsObject shape;
    private double x;
    private double y;
    private Random rand = new Random();
    private double dx=rand.nextDouble(-50,-40);;
    private double dy= rand.nextDouble(-50,50);

    private double scale;
    private double radiusX;
    private double radiusY;

    private HitBox hitBox;
    

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
        shape.setScale(fishSize);

        double base = 135;
        radiusX = base * fishSize;
        radiusY = base * fishSize * 0.3;

        hitBox = new HitBox(x, y, radiusX, radiusY);
        
        scale = fishSize;
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
        this.x = newX;
        this.y = newY;
        shape.setCenter(newX, newY);
        hitBox.setCenter(newX, newY);

    }

    public Point getCenter(){
        return shape.getCenter();
    }

    public GraphicsObject getGraphics() {
        return shape;
    }
    
    public HitBox getHitbox(){
        return hitBox;
    }

    private boolean overlapAmount(Fish other){
        //(dx/rx)^2 + (dy/ry)^2 <= 1 means in the ellipse, vice versa, out of ellipse
        double dx = this.getCenterX() - other.getCenterX();
        double dy = this.getCenterY() - other.getCenterY();

        double rx = this.radiusX + other.radiusX;
        double ry = this.radiusY + other.radiusY;

        double normalizeX = dx/rx;
        double normalizeY = dy/ry;
        double overlapValue = normalizeX * normalizeX + normalizeY * normalizeY;
        if (overlapValue <= 1.0){
            return true;
        } else{
            return false;
        }
    }

    public void interactWith(Fish other){
        if (this.scale == 0 || other.scale == 0){
            return;
        }
        if(!this.overlapAmount(other)){
            return;
        }
        if (this.scale > other.scale){
            this.grow(other.getScale());
            goDie(other);

        }else if (this.scale < other.scale){
            other.grow(this.getScale());
            goDie(this);
        }
    }

    public void goDie(Fish f){
        f.scale = 0;
        f.shape.setScale(0);
    }

    public double getScale(){
        return this.scale;
    }

    public void grow(double amount){
        this.scale += amount - 0.18;
        shape.setScale(this.scale);
        double base = 135;
        this.radiusX = base * this.scale;
        this.radiusY = base * this.scale * 0.3;
        hitBox.changeScale(radiusX, radiusY);

    }

}
