import java.util.ArrayList;
import java.util.List;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.Image;

public class FeedingFrenzy {
    public static final int CANVAS_WIDTH = 600;
    public static final int CANVAS_HEIGHT = 600;
    private final CanvasWindow canvas;
    private FishGraphic fishBall = new FishGraphic(300, 380,CANVAS_WIDTH, CANVAS_HEIGHT, -200, 100);
    private RandomFish others = new RandomFish(600, 0, 600, 600, 100,"bluefish.png",0.4);
    private List<String>smallFishNameList = new ArrayList<>();


    Image bg = new Image("seabedBg.jpg");
    
    public FeedingFrenzy(){
        bg.setScale(1);;
        canvas = new CanvasWindow("FeedingFrenzy!", bg.getImageWidth(), bg.getImageHeight());
        System.out.println(bg.getImageHeight());
        
        canvas.add(bg);
        canvas.onMouseMove(event ->{
            fishBall.setCenter(event.getPosition().getX(), event.getPosition().getY());
        });
        showFish();
        showRandomFish();
        animate();
        setSmallFishNameList();

    }

    private void showFish() {
        canvas.add(fishBall.getFishGraphics());
    }

    private void showRandomFish(){
        canvas.add(others.getRandomFishGraphics());
    }

    private void animate(){
        canvas.animate((dt)->{
        dt = Math.min(dt, 0.1);
        others.updatePosition(dt);
        });
    }

    public void setSmallFishNameList() {
        smallFishNameList.add("bluefish.png");
        smallFishNameList.add("clownFish.png");
        smallFishNameList.add("middlefih.png");
    }




    public static void main(String[] args){
        new FeedingFrenzy();
    }
}
