import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Image;
import edu.macalester.graphics.Rectangle;
// 1. begining page, "start" button
// 2. "restart" page
// 3. a bar that is showing the fishes that we are able to eat right now.
// the reappear thing from the right
// rectangle holding the fish displayed
// hitbox size of middle fish and player fish
public class FeedingFrenzy {
    public static final int CANVAS_WIDTH = 878;
    public static final int CANVAS_HEIGHT = 912;
    private Rectangle topbar;
    private static final double topbarHeight = 80;
    private final CanvasWindow canvas;
    private Fish player = new Fish(300, 380, "ClownFish.png", 0.25);
    private List<FishType> npcFishTypes;
    private Random rand = new Random();
    private List<Fish> npcFish = new ArrayList<>();
    private Image bg = new Image("seabedBg.jpg");
    private GraphicsGroup hud;
    
    public FeedingFrenzy(){
        bg.setScale(2);
        canvas = new CanvasWindow("FeedingFrenzy!", CANVAS_WIDTH, CANVAS_HEIGHT);

        canvas.add(bg);

        topbar = new Rectangle(0,0,CANVAS_WIDTH,topbarHeight);
        topbar.setFillColor(Color.GREEN);
        canvas.add(topbar);

        hud = new GraphicsGroup();
        canvas.add(hud);

        npcFishTypes = List.of(
            new FishType("bluefish.png", 0.2),
            new FishType("tuna.png", 0.4),
            new FishType("middlefish.png", 0.2),
            new FishType("shark.png", 0.7)
        );
        for(int i = 0; i < 20; i++) {
            addRandomFish();
        }

        canvas.onMouseMove(event -> {
            player.setCenter(event.getPosition().getX(), event.getPosition().getY());
        });

        showFish();
        showRandomFish();
        animate();
    }

    private void addRandomFish(){
        FishType fishType = npcFishTypes.get(rand.nextInt(npcFishTypes.size()));
        Fish newFish = new Fish(CANVAS_WIDTH, rand.nextInt(0, 100), fishType.getImagePath(), fishType.getScale());
        npcFish.add(newFish);
    }

    private void showFish() {
        canvas.add(player.getGraphics());
        canvas.add(player.getHitbox().getHitBoxShape());
    }

    private void showRandomFish(){
        for (Fish npc : npcFish) {
            canvas.add(npc.getGraphics());
            canvas.add(npc.getHitbox().getHitBoxShape());
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
            checkSmallerAndShowGraph();
            player.animateGrow();
        });
    }

    public void ifHit(Fish npcFish){

        if (npcFish.getCenterY() < 100 && npcFish.isGoingUp() || npcFish.getCenterY() > CANVAS_HEIGHT && !npcFish.isGoingUp()){
            npcFish.reset_dy_ForVerticalHit();
        } else if(npcFish.getGraphics().getBoundsInParent().getMaxX() < 0){
            npcFish.reset_X_ForHorizontalHit();
            npcFish.reset_dx_ForHorizontalHit();
            npcFish.reset_dy_ForHorizontalHit();
        }
    }

    public void win(){
        canvas.removeAll();
        Image winImg = new Image("winfish.png");
        winImg.setScale(0.8);
        winImg.setCenter(CANVAS_WIDTH/2, CANVAS_HEIGHT/2);
        canvas.add(bg);
        canvas.add(winImg);
    }

    private void handleFishInteraction() {
        for (int i = 0; i < npcFish.size(); i++) {
            Fish npc = npcFish.get(i);
            player.interactWith(npc);
        }
        
        if (player.getScale() == 0) {
            loseGameOver();
        }

        for (int i = npcFish.size()-1; i>=0; i--){
            Fish f = npcFish.get(i);
            if (f.getScale()==0){
                canvas.remove(f.getGraphics());
                npcFish.remove(i);
            }
            if (npcFish.size()==0){
                win();
            }
        }

    }

    public void loseGameOver(){
        canvas.removeAll();
        Image loseImg = new Image("losefish.png");
        loseImg.setScale(0.3);
        loseImg.setCenter(CANVAS_WIDTH/2, CANVAS_HEIGHT/2);
        canvas.add(bg);
        canvas.add(loseImg);
    }
   
    private void checkSmallerAndShowGraph(){
        hud.removeAll();
        double fishIndicatorHeight = 80;
        if (player.getScale() > 0.2){
            Image blueFishShow = new Image("bluefish.png"); 
            blueFishShow.setMaxHeight(fishIndicatorHeight);
            blueFishShow.setCenter(50,50);
            
            Image middleFishShow = new Image ("middlefish.png");
            middleFishShow.setMaxHeight(fishIndicatorHeight);
            middleFishShow.setCenter(200,50);
            hud.add(blueFishShow);
            hud.add(middleFishShow);
        }
        if (player.getScale() > 0.4){
            Image tunaShow = new Image("tuna.png");
            tunaShow.setMaxHeight(fishIndicatorHeight);
            tunaShow.setCenter(350,50);
            hud.add(tunaShow);
        }
        if (player.getScale() > 0.7){
            Image sharkShow = new Image("shark.png");
            sharkShow.setMaxHeight(fishIndicatorHeight);
            sharkShow.setCenter(500,50);
            hud.add(sharkShow);
        }
    }

    public static void main(String[] args){
        new FeedingFrenzy();
    }
}
