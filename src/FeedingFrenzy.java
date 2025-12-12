import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Image;
import edu.macalester.graphics.GraphicsText;
// 1. begining page, "start" button
// 2. "restart" page
// 3. a bar that is showing the fishes that we are able to eat right now.
// the reappear thing from the right
// rectangle holding the fish displayed
// hitbox size of middle fish and player fish
import edu.macalester.graphics.Rectangle;

/**
 * The main class that runs the Feeding Frenzy game.
 * It sets up the game window, initializes the player and NPC fish,
 * and maintains the animation loop.
 */
public class FeedingFrenzy {
    public static final int CANVAS_WIDTH = 878;
    public static final int CANVAS_HEIGHT = 912;
    private static final int topbarHeight = 80;
    private final CanvasWindow canvas;
    private Fish player;
    private List<FishType> npcFishTypes;
    private Random rand = new Random();
    private List<Fish> npcFish = new ArrayList<>();
    private Image bg = new Image("seabedBg.jpg");
    private Image barBg = new Image("barbackground.png");
    private Image clickToStartImg = new Image(0,450,"clickToStart.png");
    private Image clickToRestartImg = new Image(0,750,"clickToResatrt.png");
    private int npcFishNum = 20;
    private int minSmallFishNum = 8;
    private GraphicsGroup hud;
    private double minYBoundForAllFishes = 100.0;
    private double maxXBoundForAllFishes = CANVAS_WIDTH + 150;
    private boolean gameRunning;
    
    /**
     * Constructs a new FeedingFrenzy game, setting up the canvas,
     * initializes the player fish and NPC fishes, conneects mouse movement to the player fish,
     * and starts the animation loop.
     */
    public FeedingFrenzy(){
        bg.setScale(2);
        canvas = new CanvasWindow("FeedingFrenzy!", CANVAS_WIDTH, CANVAS_HEIGHT);

        canvas.add(bg);
        canvas.add(barBg);
        hud = new GraphicsGroup();
        canvas.add(hud);
        // GraphicsText starttext = new GraphicsText("Click to play");
        // starttext.setFontSize(90);
        // starttext.setPosition(CANVAS_WIDTH / 2-250, CANVAS_HEIGHT / 2);
        canvas.add(clickToStartImg);

        npcFishTypes = List.of(
            new FishType("bluefish.png", 0.2),
            new FishType("middlefish.png", 0.2),
            new FishType("tuna.png", 0.4),
            new FishType("shark.png", 0.7)
        );

        canvas.onMouseMove(event -> {
            if(gameRunning){
                player.setCenter(
                    event.getPosition().getX(),
                    Math.max(
                        event.getPosition().getY(),
                        minYBoundForAllFishes
                    )
                );
            }
        });
        
        canvas.onClick(event -> {
            if(!gameRunning){
                startGame();
            }
        });

        animate();
    }

    private void startGame(){
        gameRunning = true;
        canvas.removeAll();
        npcFish.clear();
        canvas.add(bg);
        canvas.add(barBg);
        canvas.add(hud);

        player = new Fish(300, 380, "ClownFish.png", 0.25);
        
        // for(int i = 0; i < npcFishNum; i++) {
        //     addRandomFish();
        // }
        addRandomFish();
        showFish();
        showRandomFish();
    }

    /** creates a NPC fish of a random type
     * and adds it to the npcFish list
     * places it off the right edge of the screen.
     */

    ///ask Paul look at this, I want to make sure the fish can grow big enough to eat the larger fishes
    private void addRandomFish(){
        for(int i = 0; i < minSmallFishNum; i ++){
            FishType fishType = npcFishTypes.get(rand.nextInt(2)); // this means to get the first two kinds of fish, which are the blue fish and the middle fish, our player fish can eat at the beginning
            Fish newFish = new Fish(CANVAS_WIDTH, rand.nextInt(topbarHeight, CANVAS_HEIGHT), fishType.getImagePath(), fishType.getScale());
            npcFish.add(newFish);
        }
        for (int i = 0; i < (npcFishNum - minSmallFishNum); i++){
        FishType fishType = npcFishTypes.get(rand.nextInt(npcFishTypes.size()));
        Fish newFish = new Fish(CANVAS_WIDTH, rand.nextInt(topbarHeight, CANVAS_HEIGHT), fishType.getImagePath(), fishType.getScale());
        npcFish.add(newFish);
        }
    }

    /** 
     * adds the player fish and its hitbox to the canvas.
    */
    private void showFish() {
        canvas.add(player.getGraphics());
        canvas.add(player.getHitbox().getHitBoxShape());
    }

    /** 
     * adds all NPC fishes and their hitboxes to the canvas.
    */
    private void showRandomFish(){
        for (Fish npc : npcFish) {
            canvas.add(npc.getGraphics());
            canvas.add(npc.getHitbox().getHitBoxShape());
        }
    }

    /** 
     * Starts the animation loop that updates the positions of all NPC fishes,
     * checks for collisions with walls, handles interactions between fishes,
     * and handles game state changes.
    */
    private void animate(){
        canvas.animate(dt -> {
            dt = Math.min(dt, 0.1);
            for(Fish npc : npcFish){
                npc.updatePosition(dt); 
                ifHit(npc);
            }
            if(gameRunning){
                handleFishInteraction();
                checkSmallerAndShowGraph();
                player.animateGrow();
            }
        });
    }

    /** 
     * Handles NPC fish interactions with the vertical edges and left edge of the canvas.
     * 
     * Checks if a NPC fish has hit the vertical bounds or the left edge,
     * and vertical edges cause the fish to invert its vertical velocity,
     * while the left edge causes the fish to reappear on the right.
    */
    public void ifHit(Fish npcFish){
        if (
            npcFish.getCenterY() < 100 && npcFish.isGoingUp()
            || npcFish.getCenterY() > CANVAS_HEIGHT && !npcFish.isGoingUp()
        ) {
            npcFish.reset_dy_ForVerticalHit();
        } else if(npcFish.getGraphics().getBoundsInParent().getMaxX() < 0){
            npcFish.reset_X_ForHorizontalHit(maxXBoundForAllFishes);
            npcFish.reset_dx_ForHorizontalHit();
            npcFish.reset_dy_ForHorizontalHit();
        }
    }

    /** 
     * Remove all images and Displays the "win" screen 
     * after player fish successfully eats all NPC fish.
    */
    public void win(){
        canvas.removeAll();
        Image winImg = new Image("winfish.png");
        winImg.setScale(0.8);
        winImg.setCenter(CANVAS_WIDTH/2, CANVAS_HEIGHT/2);
        canvas.add(bg);
        canvas.add(winImg);
        gameRunning = false ;
        canvas.add(clickToRestartImg);
    }

    /** 
     * Handles all collisions and eating interactions between the player fish and all NPC fishes.
     * 
     * When two fishes overlap, the larger fish eats the smaller fish.
     * If the player fish's scale reaches zero, remove all and "lose" screen is shown.
     * Also removes any NPC fish that have been eaten from the canvas and the npcFish list.
    */
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

    /** 
     * Remove all images and Displays the "lose" screen 
     * after player fish's scale reaches zero.
    */
    public void loseGameOver(){
        gameRunning = false;
        canvas.remove(player.getGraphics());

        Image loseImg = new Image("losefish.png");
        loseImg.setScale(0.3);
        loseImg.setCenter(CANVAS_WIDTH/2, CANVAS_HEIGHT/2-50);
        canvas.add(loseImg);

        // Rectangle button = new Rectangle(CANVAS_WIDTH/2-100, CANVAS_HEIGHT/2+300, 200,50);
        // button.setFillColor(Color.GRAY);
        // GraphicsText label = new GraphicsText("Play Again");
        // label.setFontSize(22);
        // label.setPosition(CANVAS_WIDTH / 2-50, CANVAS_HEIGHT / 2 +333);
        // canvas.add(button);
        canvas.add(clickToRestartImg);
    }

    /** 
     * Updates the HUD to show which NPC fish types the player fish can currently eat,
     * based on its current scale.
    */
    private void checkSmallerAndShowGraph(){
        hud.removeAll();
        double fishIndicatorHeight = 80;
        if (player.getScale() > 0.2){
            Image blueFishShow = new Image("bluefish.png"); 
            blueFishShow.setMaxHeight(fishIndicatorHeight);
            blueFishShow.setCenter(400,50);
            
            Image middleFishShow = new Image ("middlefish.png");
            middleFishShow.setMaxHeight(fishIndicatorHeight);
            middleFishShow.setCenter(520,50);
            hud.add(blueFishShow);
            hud.add(middleFishShow);
        }
        if (player.getScale() > 0.4){
            Image tunaShow = new Image("tuna.png");
            tunaShow.setMaxHeight(fishIndicatorHeight);
            tunaShow.setCenter(640,50);
            hud.add(tunaShow);
        }
        if (player.getScale() > 0.7){
            Image sharkShow = new Image("shark.png");
            sharkShow.setMaxHeight(fishIndicatorHeight);
            sharkShow.setCenter(760,50);
            hud.add(sharkShow);
        }
    }

    /** 
     * starts the FeedingFrenzy game.
    */
    public static void main(String[] args){
        new FeedingFrenzy();
    }
}
