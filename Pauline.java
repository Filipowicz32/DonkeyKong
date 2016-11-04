package donkeykong;

import it.randomtower.engine.entity.Entity;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Entity for Pauline
 * @author John Filipowicz
 */
public class Pauline extends Entity{
    private Image pauline;
    private SpriteSheet paulineSS;
    
    /**
     * Initializes Pauline entity
     * @param x
     * @param y
     * @throws SlickException 
     */
    public Pauline(float x, float y) throws SlickException {
        super(x, y);
        scale = .5f;
        paulineSS = new SpriteSheet("Sprites and Images\\pauline.png", 100, 135);
        pauline = paulineSS.getSubImage(0, 0);
        setGraphic(pauline);
        setHitBox(0, 10, pauline.getWidth() - 20, pauline.getHeight() - 70);
        
        this.SetAnimations();
        
        addType("END");
    }
    
    /**
     * Sets Pauline's animation
     */
    private void SetAnimations() {
        setGraphic(paulineSS);
        
        duration = 300;
        addAnimation("LEVEL1", true, 0, 2, 3);
        
        setGraphic(pauline);
    }
}
