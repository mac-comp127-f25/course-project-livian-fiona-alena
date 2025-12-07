import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Image;

public class FeedingFrenzy {
    public static final int CANVAS_WIDTH = 878;
    public static final int CANVAS_HEIGHT = 912;
    private final CanvasWindow canvas;
    private Fish player = new Fish(300, 380, "ClownFish.png", 0.25);
    private List<FishType> npcFishTypes;
    private Random rand = new Random();
    private List<Fish> npcFish = new ArrayList<>();
    private Image bg = new Image("seabedBg.jpg");
    
    public FeedingFrenzy(){
        bg.setScale(2);
        canvas = new CanvasWindow("FeedingFrenzy!", CANVAS_WIDTH, CANVAS_HEIGHT);
        npcFishTypes = List.of(
            new FishType("bluefish.png", 0.2),
            new FishType("tuna.png", 0.4),
            new FishType("middlefish.png", 0.2),
            new FishType("shark.png", 0.6)
        );
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
        FishType fishType = npcFishTypes.get(rand.nextInt(npcFishTypes.size()));
        Fish newFish = new Fish(CANVAS_WIDTH, rand.nextInt(0, CANVAS_HEIGHT), fishType.getImagePath(), fishType.getScale());
        npcFish.add(newFish);
    }

    private void showFish() {
        canvas.add(player.getGraphics());
        canvas.add(player.getHitbox());
    }

    private void showRandomFish(){
        for (Fish npc : npcFish) {
            canvas.add(npc.getGraphics());
            canvas.add(npc.getHitbox());
        }
    }

    private void animate(){
        canvas.animate(dt->{
            dt = Math.min(dt, 0.1);
            for(Fish npc : npcFish){
                npc.updatePosition(dt); 
                ifHit(npc);
            }
            handleFishInteraction();
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

    private void handleFishInteraction(){
        for (int i = 0; i < npcFish.size(); i++) {
        Fish npc = npcFish.get(i);
        player.interactWith(npc);
        }
    

        for (int i = npcFish.size()-1; i>=0; i--){
        Fish f = npcFish.get(i);
        if (f.getScale()==0){
            canvas.remove(f.getGraphics());
            npcFish.remove(i);
            }
        }
    }


    public static void main(String[] args){
        new FeedingFrenzy();
    }
}
