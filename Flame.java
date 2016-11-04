package donkeykong;

import it.randomtower.engine.entity.Entity;
import java.util.Random;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Entity for little flame guy that spawns out of barrel (he stays on first level)
 * @author John Filipowicz
 */
public class Flame extends Entity{
    private Image flame;
    private SpriteSheet flameSS;
    private int xVelocity, num;
    private Random rand;
    private boolean startTime;

    /**
     * Initializes stuff for little flame guy
     * @param x
     * @param y
     * @throws SlickException 
     */
    public Flame(float x, float y) throws SlickException {
        super(x, y);
        
       scale = 2.5f;
       flameSS = new SpriteSheet("Sprites and Images\\enemiesMod.png", 24, 21);
       flame = flameSS.getSubImage(2, 1);
       
       setGraphic(flame);
       setHitBox(20, 0, (flame.getWidth() * 3) - 35, (flame.getHeight() * 3) - 15);
       this.SetAnimations();
       
       addType("BARREL");
       rand = new Random();
       xVelocity = 3;
       startTime = false;
    }
    
    /*
    Sets flame guy's animations
    */
    private void SetAnimations() {
        setGraphic(flameSS);
        
        duration = 200;
        addAnimation("RIGHT", true, 1, 2, 3);
        addAnimation("LEFT", true, 0, 2, 3);
        currentAnim = "RIGHT";

        setGraphic(flame);
    }
    
    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException{
        super.render(gc, g);
    }
    
    /**
     * Updates flame guy's animation and movement
     * @param gc
     * @param delta
     * @throws SlickException 
     */
    @Override
    public void update(GameContainer gc, int delta) throws SlickException{
        super.update(gc, delta);
        
        //Gravity
        if(collide("SOLID", x, y + 5) == null){
            y += 5;
        }
        
        //Horizontal Movement
        if(collide("LADDER", x + xVelocity, y) == null)
            startTime = false;
        
        if(collide("LADDER", x, y) != null && !startTime){
            num = rand.nextInt(12);
            startTime = true;
            if(num == 2){
                xVelocity = xVelocity * -1;
                if(currentAnim.equals("RIGHT"))
                    currentAnim = "LEFT";
                else if(currentAnim.equals("LEFT"))
                    currentAnim = "RIGHT";
            }
        }
        if(collide("SOLID", x + xVelocity, y - 10) != null){
            xVelocity = xVelocity * -1;
            if(currentAnim.equals("RIGHT"))
                currentAnim = "LEFT";
            else if(currentAnim.equals("LEFT"))
                currentAnim = "RIGHT";
        }
        else if (collide("SOLID", x + xVelocity, y) != null && collide("SOLID", x + xVelocity, y - 3) == null){
            y -= 3;
        }
        if(collide("SOLID", x + xVelocity, y) == null)
            x += xVelocity;
    }
}
