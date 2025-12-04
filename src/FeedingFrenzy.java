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
    private Map<String , Double> fishSizeMap = new HashMap<>();
    private Random rand = new Random();
    private List<RandomFish> randomFishs = new ArrayList<>();
    private Image bg = new Image("seabedBg.jpg");
    
    public FeedingFrenzy(){
        bg.setScale(1);;
        canvas = new CanvasWindow("FeedingFrenzy!", bg.getImageWidth(), bg.getImageHeight());
        fishSizeMap.put("bluefish.png",0.2);
        fishSizeMap.put("clownFish.png", 0.1);
        fishSizeMap.put("middlefish.png", 0.1);
        for(int i =0; i <20; i++){
            addRandomFish();
        }
        canvas.add(bg);
        canvas.onMouseMove(event ->{
            fishBall.setCenter(event.getPosition().getX(), event.getPosition().getY());
        });
        showFish();
        showRandomFish();
        animate();
        
        
    }

    private void addRandomFish(){
        List<String> fishNames = new ArrayList<>(fishSizeMap.keySet());
        String fishName = fishNames.get(rand.nextInt(fishNames.size()));
        double fishSize = fishSizeMap.get(fishName);
        RandomFish newFish = new RandomFish(600, rand.nextInt(0, 600), fishName, fishSize);
        randomFishs.add(newFish);
    }

    private void showFish() {
        canvas.add(fishBall.getFishGraphics());
    }

    private void showRandomFish(){
        for (RandomFish npcFish:randomFishs) {
            canvas.add(npcFish.getRandomFishGraphics());
        }
    }

    private void animate(){
        canvas.animate(dt->{
            dt = Math.min(dt, 0.1);
            for(RandomFish npcFish :randomFishs){
                npcFish.updatePosition(dt); 
                ifHit(npcFish);
            }
        });
    }

    public void ifHit(RandomFish npcFish){
       if (npcFish.getCenterY()<0 || npcFish.getCenterY()>CANVAS_HEIGHT){
            npcFish.reset_dy_ForVerticalHit();
        } else if(npcFish.getCenterX()<-100){
            npcFish.reset_X_ForHorizontalHit();
            npcFish.reset_dx_ForHorizontalHit();
            npcFish.reset_dy_ForHorizontalHit();
        }
    }

    public static void main(String[] args){
        new FeedingFrenzy();
    }
}
