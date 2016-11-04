package donkeykong;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.lwjgl.input.Mouse;

/**
 * Game state for the main menu
 * @author John Filipowicz
 */
public class MainMenu extends BasicGameState {
    private final int STATE_ID;
    private Image menu;
    
    private Image[] dkFrames = new Image[3];
    private SpriteSheet enemies;
    private Animation dk;
    
    private int mouseX;
    private int mouseY;
    private String mouseLoc;
    
    /**
     * Set the id of the game state to the parameter
     * @param state 
     */
    public MainMenu (int state) {
        STATE_ID = state;
    }
    
    /**
     * Initializes the game state
     * @param gc
     * @param sbg
     * @throws SlickException 
     */
    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        menu = new Image("Sprites and Images\\DK_Menu.png");
        enemies = new SpriteSheet("Sprites and Images\\enemies.png",49,45);
        
        dkFrames[0] = enemies.getSubImage(1,1).getScaledCopy(3.2f);
        dkFrames[1] = enemies.getSubImage(2,1).getScaledCopy(3.2f);
        dkFrames[2] = enemies.getSubImage(3,1).getScaledCopy(3.2f);
        
        dk = new Animation(dkFrames,200);
        dk.setPingPong(true);
        dk.setSpeed(0.75f);
        
        mouseX = 0;
        mouseY = 0;
        mouseLoc = "No Input Yet";
    }
    
    /**
     * Renders the specified Strings and Images
     * @param gc
     * @param sbg
     * @param g
     * @throws SlickException 
     */
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        menu.draw(-25, -50);
        dk.draw(520, 500);
        g.drawString("Press [Enter] to play.", 500, 850);   
    }
    
    /**
     * Changes game state based on user input
     * @param gc
     * @param sbg
     * @param delta
     * @throws SlickException 
     */
    @Override
    public void update (GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        Input input = gc.getInput();
        
        mouseX = Mouse.getX();
        mouseY = Mouse.getY();
        mouseLoc = "X Position: " + mouseX + "\nY Position " + mouseY;
        
        if(input.isKeyPressed(Input.KEY_ENTER)){
            sbg.enterState(1);
        }
    }
    
    @Override
    public int getID() {
        return STATE_ID;
    }
}
