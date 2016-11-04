package donkeykong;

import it.randomtower.engine.entity.Entity;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Entity for the ladders
 * @author John Filipowicz
 */
public class Ladder extends Entity{
    private SpriteSheet ladderSS;
    private Image ladder;
    
    /**
     * Initializes Ladder according to specified size needed
     * @param x
     * @param y
     * @param height
     * @throws SlickException 
     */
    public Ladder(float x, float y, char height) throws SlickException{
        super(x, y);
        
        switch (height) {
            case 'S':
                ladderSS = new SpriteSheet("Sprites and Images\\DK_LadderSS.png", 13, 29);
                ladder = ladderSS.getSubImage(0, 0);
                ladder = ladder.getScaledCopy(3.5f);
                setHitBox(16, 5, ladder.getWidth() - 29, ladder.getHeight() - 45);
                break;
            case 'L':
                ladder = new Image("Sprites and Images\\DK_LadderExtended.png");
                ladder = ladder.getScaledCopy(3.5f);
                setHitBox(16, 6, ladder.getWidth() - 29, ladder.getHeight() - 40);
                break;
            case 'G':
                ladder = new Image("Sprites and Images\\DK_LadderGreg++.png");
                ladder = ladder.getScaledCopy(3.5f);
                setHitBox(16, 10, ladder.getWidth() - 29, 5);
                break;
            case 'g':
                ladder = new Image("Sprites and Images\\DK_LadderGreg.png");
                ladder = ladder.getScaledCopy(3.5f);
                setHitBox(16, 10, ladder.getWidth() - 29, ladder.getHeight() - 40);
                break;
        }

        setGraphic(ladder);
        
        addType("ROLL_DOWN");
        addType("LADDER");
    }
}
