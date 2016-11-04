package donkeykong;

import it.randomtower.engine.entity.Entity;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Entity for Donkey Kong
 * @author John Filipowicz
 */
public class DK extends Entity{
    private Image kong;
    private SpriteSheet dkSS;
    private int time;
    
    /**
     * Initialize Donkey Kong entity
     * @param x
     * @param y
     * @throws SlickException 
     */
    public DK(float x, float y) throws SlickException {
        super(x, y);
        scale = 4.0f;
        dkSS = new SpriteSheet("Sprites and Images\\enemiesMod.png", 49, 46);
        kong = dkSS.getSubImage(2, 1).getScaledCopy(scale);
        time = 0;
        
        setGraphic(kong);
        setHitBox(15, 30, kong.getWidth() - 10, kong.getHeight() - 30);
        this.SetAnimations();
        addType("ENEMY");
    }
    
    /**
     * Set Donkey Kong animations
     */
    private void SetAnimations() {
        setGraphic(dkSS);
        
        duration = 500;
        addAnimation("Barrel", true, 1, 2, 0, 3, 4);
        addAnimation("Intro", true, 0, 3, 4, 3, 2);
        
        setGraphic(kong);
    }
    
    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException{
        super.render(gc, g);
    }
    
    /**
     * Updates Donkey Kong's Animations
     * @param gc
     * @param delta
     * @throws SlickException 
     */
    @Override
    public void update(GameContainer gc, int delta) throws SlickException{
        super.update(gc, delta);
        time += delta;
        
        if(time <= 2000){
            currentAnim = "Intro";
        }
        else if (time <= 4000){
            currentAnim = "Barrel";
        }
        else{
            currentAnim = "Intro";
            time = 0;
        }
    }
}
