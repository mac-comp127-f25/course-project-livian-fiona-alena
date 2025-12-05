import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.Image;

public class FeedingFrenzy {
    public static final int CANVAS_WIDTH = 878;
    public static final int CANVAS_HEIGHT = 912;
    private final CanvasWindow canvas;
    private Fish player = new Fish(300, 380, "ClownFish.png", 0.1);
    private Map<String, Double> fishSizeMap = new HashMap<>();
    private Random rand = new Random();
    private List<Fish> npcFish = new ArrayList<>();
    private Image bg = new Image("seabedBg.jpg");
    
    public FeedingFrenzy(){
        bg.setScale(2);
        canvas = new CanvasWindow("FeedingFrenzy!", CANVAS_WIDTH, CANVAS_HEIGHT);
        fishSizeMap.put("bluefish.png",0.2);
        fishSizeMap.put("tuna.png", 0.3);
        fishSizeMap.put("middlefish.png", 0.2);
        fishSizeMap.put("shark.png", 0.5);
        for(int i =0; i <20; i++){
            addRandomFish();
        }
        canvas.add(bg);
        canvas.onMouseMove(event ->{
            player.setCenter(event.getPosition().getX(), event.getPosition().getY());
        });
        showFish();
        showRandomFish();
        animate();
        
        
    }

    private void addRandomFish(){
        List<String> fishNames = new ArrayList<>(fishSizeMap.keySet());
        String fishName = fishNames.get(rand.nextInt(fishNames.size()));
        double fishSize = fishSizeMap.get(fishName);
        Fish newFish = new Fish(CANVAS_WIDTH, rand.nextInt(0, CANVAS_HEIGHT), fishName, fishSize);
        npcFish.add(newFish);
    }

    private void showFish() {
        canvas.add(player.getGraphics());
    }

    private void showRandomFish(){
        for (Fish npc : npcFish) {
            canvas.add(npc.getGraphics());
        }
    }

    private void animate(){
        canvas.animate(dt->{
            dt = Math.min(dt, 0.1);
            for(Fish npc : npcFish){
                npc.updatePosition(dt); 
                ifHit(npc);
            }
        });
    }

    public void ifHit(Fish npcFish){
        // Consider adding this to RandomFish as getRightX():
        // npcFish.getRandomFishGraphics().getBoundsInParent().getMaxX()   // <- to compute the right edge of a graphics object

        // Consider renaming RandomFish -> NPCFish

        if (npcFish.getCenterY()<0 || npcFish.getCenterY()>CANVAS_HEIGHT){
            npcFish.reset_dy_ForVerticalHit();
        } else if(npcFish.getCenterX()<-10){
            npcFish.reset_X_ForHorizontalHit();
            npcFish.reset_dx_ForHorizontalHit();
            npcFish.reset_dy_ForHorizontalHit();
        }
    }

    public static void main(String[] args){
        new FeedingFrenzy();
    }
}
