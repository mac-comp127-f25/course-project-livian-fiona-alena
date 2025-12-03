import java.util.Random;

import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.Image;

public class RandomFish {
    private GraphicsObject otherFishShape;
    private double x;
    private double y;
    private double dx;

    public RandomFish(
        double centerX,
        double centerY,
        double dx,
        String pathname,
        double fishSize
    ){
        otherFishShape = new Image(pathname);
        otherFishShape.setScale(fishSize);
        this.x=centerX;
        this.y= centerY;
        this.dx = dx;
    }
    
    public void updatePosition(double dt) {
        this.x=x - dx * dt;
        if (0 <= x && x <=600){
            otherFishShape.setCenter(x,y);   
        }
    }

    public GraphicsObject getRandomFishGraphics() {
        return otherFishShape;
    }

}
