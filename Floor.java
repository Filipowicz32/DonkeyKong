package donkeykong;

import it.randomtower.engine.entity.Entity;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Entity for the red tiles
 * @author John Filipowicz
 */
public class Floor extends Entity{
    private Image tile;
    private float scale = 3.2f;
    
    /**
     * Initializes the red tiles
     * @param x
     * @param y
     * @throws SlickException 
     */
    public Floor(float x, float y) throws SlickException {
        super(x, y);
        
        tile = new Image("Sprites and Images\\DK_RedTile.png");
        tile = tile.getScaledCopy(scale);
        setGraphic(tile);
        
        setHitBox(0, 15, tile.getWidth() - 9, tile.getHeight() - 37);
        addType("SOLID");
    }
}
