package donkeykong;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * State to divide level attempts. Prompts whether to reset level or not
 * @author John Filipowicz
 */
public class ContinueScreen extends BasicGameState {
    private final int STATE_ID;
    private Image menu, gameOver;
    
    /**
     * Sets the id of the game state to the parameter
     * @param state 
     */
    public ContinueScreen (int state) {
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
        menu = new Image("Sprites and Images\\DK_MenuBlank.png");
        gameOver = new Image("Sprites and Images\\GameOver.png");
        gameOver = gameOver.getScaledCopy(0.25f);
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
        gameOver.draw(175, 200);
        g.drawString("Press [Tab] to continue.", 400, 835);
        g.drawString("Press [Enter] to go to the main menu.", 400, 865);
    }
    
    /**
     * Updates to another game state based on the input
     * @param gc
     * @param sbg
     * @param delta
     * @throws SlickException 
     */
    @Override
    public void update (GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        Input input = gc.getInput();
        if(input.isKeyPressed(Input.KEY_ENTER)){
            sbg.enterState(0);
        }
        if(input.isKeyPressed(Input.KEY_TAB)){
            sbg.enterState(1);
        }
    }
    
    @Override
    public int getID() {
        return STATE_ID;
    }   
}
