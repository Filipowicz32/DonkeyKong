package donkeykong;

import it.randomtower.engine.ME;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

/**
 * The class containing the main method
 * @author John Filipowicz
 */

public class DonkeyKong extends StateBasedGame{

    public static final String gamename = "Donkey Kong Project5";
    public static final int mainMenu = 0;
    public static final int martelLevel1 = 1;
    public static final int continueScreen = 2;
    public static final int gameOver = 3;
    
    /**
     * Creates the game states and adds them to the game
     * @param gamename 
     */
    public DonkeyKong (String gamename) {
        super(gamename);
        this.addState(new MainMenu(mainMenu));
        this.addState(new MartelLevel1(martelLevel1));
        this.addState(new ContinueScreen(continueScreen));
        this.addState(new GameOver(gameOver));
    }
    
    /**
     * Initializes the game states and enters main menu state
     * @param gc
     * @throws SlickException 
     */
    @Override
    public void initStatesList(GameContainer gc) throws SlickException {
        this.getState(mainMenu).init(gc, this);
        this.getState(martelLevel1).init(gc, this);
        //this.getState(continueScreen).init(gc, this);
        //this.getState(gameOver).init(gc, this);
        this.enterState(mainMenu);
        //this.enterState(martelLevel1);
        //this.enterState(continueScreen);
    }
    
    /**
     * Sets basic game properties and calls the constructor
     * @param args 
     */
    public static void main(String[] args) {
        AppGameContainer appGC;
        ME.keyToggleDebug = Input.KEY_1;
        
        try {
            appGC = new AppGameContainer(new DonkeyKong(gamename));
            appGC.setDisplayMode(1200,1000, false);
            Display.setLocation(-1, -2);
            appGC.setTargetFrameRate(60);
            appGC.setMaximumLogicUpdateInterval(60);
            appGC.setAlwaysRender(true);
            appGC.start();
        } catch(SlickException e) {
        }
        
    }
    
}