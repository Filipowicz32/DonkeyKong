package donkeykong;

import it.randomtower.engine.entity.Entity;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Entity class for multiple smaller (code wise) entities
 * @author John Filipowicz
 */
public class Misc extends Entity{
    private Image misc, blank;
    private SpriteSheet miscSS;
    private String miscType;
    private int time, count;
    
    /**
     * Initializes the fields for the entity specified with the mType parameter
     * @param x
     * @param y
     * @param mType
     * @throws SlickException 
     */
    public Misc(float x, float y, String mType) throws SlickException {
        super(x, y);
        miscType = mType;
        time = 0;
        count = 0;
        switch(miscType){
            case "oil":
                this.InitOil();
                break;
            case "help":
                this.InitHelp();
                break;
            case "stack":
                this.InitStack();
                break;
        }
    }
    
    /**
     * Initializes entity to oil drum if needed
     * @throws SlickException 
     */
    private void InitOil() throws SlickException {
        scale = 2.6f;
        miscSS = new SpriteSheet("Sprites and Images\\enemies.png", 24, 40);
        misc = miscSS.getSubImage(4, 0).getScaledCopy(scale);
        
        setGraphic(misc);
        setHitBox(0, 20, misc.getWidth() - 10, misc.getHeight() + 20);
        
        setGraphic(miscSS);
        duration = 200;
        addAnimation("FIRE", true, 0, 4, 5);
        setGraphic(misc);
        
        
        addType("OIL");
        addType("SOLID");
    }
    
    /**
     * Initializes entity to help text bubble if needed
     * @throws SlickException 
     */
    private void InitHelp() throws SlickException {
        blank = new Image("Sprites and Images\\Blank.png");
        misc = new Image("Sprites and Images\\DK_Help.png");
        setGraphic(misc);
    }
    
    /**
     * Initializes entity to blurry as hell barrel stack if needed
     * @throws SlickException 
     */
    private void InitStack() throws SlickException {
        scale = 2.1f;
        misc = new Image("Sprites and Images\\DK_Stack.png");
        misc = misc.getScaledCopy(scale);
        setGraphic(misc);
    }
    
    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException{
        super.render(gc, g);
    }
    
    /**
     * Update the corresponding entity's animation and general logic
     * @param gc
     * @param delta
     * @throws SlickException 
     */
    @Override
    public void update(GameContainer gc, int delta) throws SlickException{
        super.update(gc, delta);
        
        if(miscType.equals("help")){
            time += delta;
            if(time > 500){
                if(count == 0 || count == 2 || count == 4)
                    setGraphic(blank);
                else if(count == 1 || count == 3)
                    setGraphic(misc);
                else
                    super.destroy();
                count++;
                time = 0;
            }
        }
    }
}
