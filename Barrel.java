package donkeykong;

import it.randomtower.engine.entity.Entity;
import java.util.Random;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Entity for the rolling barrels
 * @author John Filipowicz
 */
public class Barrel extends Entity{
    private Image barrel;
    private SpriteSheet barrelSS;
    private boolean climbing = false, clear = false, over = false;
    private boolean cleared = false, changed = false;
    private int xVelocity, time;
    private String tempStr;
    
    /**
     * Initializes the barrels
     * @param x
     * @param y
     * @throws SlickException 
     */
    public Barrel(float x, float y) throws SlickException {
        super(x, y);
        
        scale = 3.5f;
        barrelSS = new SpriteSheet("Sprites and Images\\DK_Barrel.png", 21, 13);
        barrel = barrelSS.getSubImage(1, 0);
        setGraphic(barrel);
        setHitBox(25, 18, (barrel.getWidth() * 3) - 26, (barrel.getHeight() * 3) - 11);
        this.SetAnimations();
        
        addType("BARREL");
        xVelocity = 5;
        time = 0;
        tempStr = "";
    }
    
    /**
     * Sets / creates the barrel animations
     */
    private void SetAnimations() {
        setGraphic(barrelSS);
        
        duration = 200;
        addAnimation("RIGHTROLL", true, 0, 1, 2, 3, 4);
        addAnimation("LEFTROLL", true, 0, 4, 3, 2, 1);
        addAnimation("CLIMBING", true, 0, 0, 5);
        currentAnim = "RIGHTROLL";
        
        setGraphic(barrel);
    }
    
    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException{
        super.render(gc, g);
    }
    
    /**
     * Updates the barrel movement and animations
     * @param gc
     * @param delta
     * @throws SlickException 
     */
    @Override
    public void update(GameContainer gc, int delta) throws SlickException{
        super.update(gc, delta);
        
        this.VerticalMovement();
        this.HorizontalMovement();
        this.MarioCollision(delta);
        
        if(collide("OIL", x - 5, y) != null)
            super.destroy();

    }
    
    /**
     * Handle movement on the horizontal plane
     */
    private void HorizontalMovement() {
        if(!climbing && collide("SOLID", x + xVelocity, y - 5) != null && collide("SOLID", x + xVelocity, y) != null){
            xVelocity = xVelocity * -1;
            if(currentAnim.equals("RIGHTROLL"))
                currentAnim = "LEFTROLL";
            if(currentAnim.equals("LEFTROLL"))
                currentAnim = "RIGHTROLL";
        }
        if(!climbing)
            x += xVelocity;
    }
    
    /**
     * Handle movement from gravity and descending ladders
     */
    private void VerticalMovement() {
        //Gravity
        if(!climbing && collide("SOLID", x, y + 5) == null){
            y += 5;
        }
        
        //Ladder Stuff
        Random rand = new Random();
        int i = rand.nextInt(9);
        
        if(collide("LADDER", x, y + 30) != null && i == 2){
            climbing = true;
            if(!changed){
                tempStr = currentAnim;
                changed = true;
            }
            currentAnim = "CLIMBING";
        }
        if(climbing){
            y += 5;
            if(collide("SOLID", x, y) == null){
                cleared = true;
            }
            if(cleared && collide("SOLID", x, y + 5) != null){
                climbing = false;
                xVelocity = xVelocity * -1;
                if(tempStr.equals("RIGHTROLL"))
                    currentAnim = "LEFTROLL";
                if(tempStr.equals("LEFTROLL"))
                    currentAnim = "RIGHTROLL";
                cleared = false;
            }
        }
        
    }
    
    /**
     * Checks for Mario jumping over the barrel and handles it accordingly
     */
    private void MarioCollision(int delta) {
        if(collide("PLAYER", x, y - 20) != null && collide("PLAYER", x, y) == null){
            over = true;
        }
        if(over){
            time += delta;
        }
        if(time > 200 && ((collide("PLAYER", x - 30, y) != null || collide("PLAYER", x + 30, y) != null) || (collide("PLAYER", x - 60, y) != null || collide("PLAYER", x + 60, y) != null))){
            clear = true;
        }
        else if(time > 200 && ((collide("PLAYER", x - 90, y) != null || collide("PLAYER", x + 90, y) != null) || (collide("PLAYER", x - 120, y) != null || collide("PLAYER", x + 120, y ) != null))){
            clear = true;
        }
        if(collide("PLAYER", x, y) != null){
            clear = false;
            over = false;
            time = 0;
        }
        if(clear && over){
            super.destroy();
        }
    }
}
