import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.Image;

public class FeedingFrenzy {
    public static final int CANVAS_WIDTH = 600;
    public static final int CANVAS_HEIGHT = 800;
    private final CanvasWindow canvas;
    private FishGraphic fishBall = new FishGraphic(300, 380,CANVAS_WIDTH, CANVAS_HEIGHT, -200, 100);
    Image bg = new Image("seabedBg.jpg");
    
    public FeedingFrenzy(){
        bg.setScale(1);;
        canvas = new CanvasWindow("FeedingFrenzy!", bg.getImageWidth(), bg.getImageHeight());
        
        canvas.add(bg);
        canvas.onMouseMove(event ->{
            fishBall.setCenter( event.getPosition().getX(), event.getPosition().getY());
        });
        showFish();

    }

    private void showFish() {
        canvas.add(fishBall.getFishGraphics());
    }

    public static void main(String[] args){
        new FeedingFrenzy();
    }
}
