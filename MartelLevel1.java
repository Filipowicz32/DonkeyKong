package donkeykong;

import it.randomtower.engine.ME;
import it.randomtower.engine.World;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.StateBasedGame;

/**
 * The game state for the first level
 * @author John Filipowicz
 */
public class MartelLevel1 extends World {
    private Mario mario;
    private int mouseX, mouseY;
    private int time;
    private boolean flameOn;
    
    /**
     * Sets id to the parameter
     * @param id 
     */
    public MartelLevel1(int id) {
        super(id);
    }
    
    /**
     * Initializes the game state
     * @param gc
     * @param game
     * @throws SlickException 
     */
    @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException {
        super.init(gc, game);
        mario = new Mario(225, 915);
        mouseX = 0;
        mouseY = 0;
        time = 0;
        flameOn = false;

        this.PlaceLadders();
        this.DrawMap();
        this.PlaceBoundries();
        this.PlaceMisc();
        
        add(new Pauline(515, 147));
        add(new DK(150, 135));
        add(mario);
    }
    
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{
        super.render(gc,sbg, g);
        //g.drawString("X: " + mouseX + "\nY: " + mouseY, 30, 30);
    }
    
    /**
     * Updates entities to add after the start, and clearing / finishing the level
     * @param gc
     * @param sbg
     * @param delta
     * @throws SlickException 
     */
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException{
        super.update(container, sbg, delta);
        Input input = gc.getInput();
        mouseX = input.getMouseX();
        mouseY = input.getMouseY();
        
        if(mario.isReset()){
            super.clear();
            if(mario.isLevelCompleted())
                sbg.enterState(2);
            if(mario.isRIP())
                sbg.enterState(3);
            this.init(gc, sbg);
        }
        
        time += delta;
        if(time >= 3600){
            add(new Barrel(323, 253));
            time = -400;
        }
        if(!flameOn && time < 0 && time > -200){
            add(new Flame(65, 925));
            flameOn = true;
        }
    }
    
    /**
     * Adds all full and partial ladders to the map
     * @throws SlickException 
     */
    private void PlaceLadders () throws SlickException {
        //@Floors 2 and 3
        add(new Ladder(960, 840, 'L'));
        add(new Ladder(460, 845, 'g'));
        add(new Ladder(460, 940, 'g'));
        add(new Ladder(210, 715, 'L'));
        add(new Ladder(510, 710, 'L'));
        
        //@Floors 4 and 5
        add(new Ladder(310, 590, 'g'));
        add(new Ladder(310, 690, 'g'));
        add(new Ladder(960, 585, 'L'));
        add(new Ladder(660, 575, 'L'));
        add(new Ladder(910, 460, 'g'));
        add(new Ladder(910, 560, 'g'));
        add(new Ladder(210, 445, 'L'));
        add(new Ladder(360, 440, 'L'));
        
        //@Top Floor
        add(new Ladder(510, 310, 'g'));
        add(new Ladder(510, 390, 'G'));
        add(new Ladder(960, 305, 'L'));
        add(new Ladder(660, 210, 'S'));
        
        //Extended Ladder on Top Floor
        add(new Ladder(460, 165, 'L'));
        add(new Ladder(410, 165, 'L'));
        add(new Ladder(460, 55, 'L'));
        add(new Ladder(410, 55, 'L'));
        add(new Ladder(460, -16, 'L'));
        add(new Ladder(410, -16, 'L'));
        
    }
    
    /**
     * Adds the tiles to the game state
     * @throws SlickException 
     */
    private void DrawMap () throws SlickException {
        //Base Floor
        int tempY = 0;
        for(int i = 0; i < 1200; i = i + 50){
            if(i > 300 && i % 100 == 0)
                tempY += 2;
            add(new Floor(i, 960 - tempY));
        }
        
        //Second Level Floor
        tempY = 0;
        for(int i = 1000; i >= 0; i = i - 50){
            add(new Floor(i, 840 + tempY));
            if(i % 100 == 0)
                tempY -= 2;
        }
        
        //Third
        tempY = 0;
        for(int i = 150; i < 1200; i = i + 50){
            add(new Floor(i, 710 - tempY));
            if(i % 100 == 0)
                tempY += 2;
        }
        
        //Fourth
        tempY = 0;
        for(int i = 1000; i >= 0; i = i - 50){
            add(new Floor(i, 580 + tempY));
            if(i % 100 == 0)
                tempY -= 2;
        }
        
        //Fifth
        tempY = 0;
        for(int i = 150; i < 1200; i = i + 50){
            add(new Floor(i, 440 - tempY));
            if(i % 100 == 0)
                tempY += 2;
        }
        
        //Top
        tempY = 0;
        for(int i = 1000; i >=0; i = i - 50){
            add(new Floor(i, 300 + tempY));
            if(i % 100 == 0 && i > 300)
                tempY -= 2;
        }
        
        //Pauline Platform
        for(int i = 500; i < 700; i = i + 50){
            add(new Floor(i, 200));
        }
        add(new Floor(450, 225));
        add(new Floor(400, 225));
    }
    
    /**
     * Adds the invisible boundaries to the game state
     * @throws SlickException 
     */
    private void PlaceBoundries() throws SlickException {
        //Left and right side
        for(int i = 0; i <= 1000; i = i + 200){
            add(new Boundries(0, i));
            add(new Boundries(1190, i));
        }
        //Top floor divider
        add(new Boundries(500, 0));
    }
    
    /**
     * Add miscellaneous entities
     * @throws SlickException 
     */
    private void PlaceMisc() throws SlickException {
        add(new Misc(25, 873, "oil"));
        add(new Misc(570, 150, "help"));
        add(new Misc(50, 162, "stack"));
    }
}
