package donkeykong;

import it.randomtower.engine.entity.Entity;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Invisible solid wall entities
 * @author John Filipowicz
 */
public class Boundries extends Entity{
    private SpriteSheet boundriesSS;
    private Image boundries;
    
    /**
     * Initializes the invisible wall
     * @param x
     * @param y
     * @throws SlickException 
     */
    public Boundries(float x, float y) throws SlickException {
        super(x, y);
        
        boundriesSS = new SpriteSheet("Sprites and Images\\enemies.png", 2, 2);
        boundries = boundriesSS.getSubImage(0, 0);
        
        setHitBox(0, 0, 10, 200);
        addType("Boundries");
        addType("SOLID");
        setGraphic(boundries);
    }
}
