import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.Image;

public class FeedingFrenzy {
    public static final int CANVAS_WIDTH = 600;
    public static final int CANVAS_HEIGHT = 600;
    private final CanvasWindow canvas;
    private FishGraphic fishBall = new FishGraphic(300, 380,CANVAS_WIDTH, CANVAS_HEIGHT, -200, 100);
    private List<String> smallFishNameList = List.of("bluefish.png","clownFish.png","middlefish.png");
   

    Image bg = new Image("seabedBg.jpg");
    
    public FeedingFrenzy(){
        bg.setScale(1);;
        canvas = new CanvasWindow("FeedingFrenzy!", bg.getImageWidth(), bg.getImageHeight());
        System.out.println(bg.getImageHeight());
        Map<String , Double> fishSizeMap = new HashMap<>();
        fishSizeMap.put("bluefish.png",200.0);
        String fishName;
        


        Random rand = new Random();
        List<RandomFish> randomFishs;
        fishName = smallFishNameList.get(rand.nextInt(smallFishNameList.size()));
        RandomFish others = new RandomFish(600, rand.nextInt(0,600), 100,fishName,fishSizeMap.get(fishName));

        
        canvas.add(bg);
        canvas.onMouseMove(event ->{
            fishBall.setCenter(event.getPosition().getX(), event.getPosition().getY());
        });
        showFish();
        showRandomFish();
        animate();
        
        
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
    private void populateFish(){

    }






    public static void main(String[] args){
        new FeedingFrenzy();
    }
}
