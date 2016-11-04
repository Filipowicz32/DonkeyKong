package donkeykong;

import it.randomtower.engine.entity.Entity;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Entity class for Mario
 * @author John Filipowicz
 */
public class Mario extends Entity implements Serializable{
    private SpriteSheet marioSS;
    private Image mario;
    
    private int xSpeed, time, timeBarrel;
    private boolean climbing, jumping, falling;
    private boolean over, clear;
    private boolean reset, levelCompleted, rip;
    private char jumpDirection;
    private String defaultAnim;
    
    private int lives, highScore, currentScore;
    
    /**
     * Initializes all fields and movements of Mario
     * @param x
     * @param y
     * @throws SlickException 
     */
    public Mario (float x, float y) throws SlickException{
        super(x, y);
        scale = 3.0f;
        
        marioSS = new SpriteSheet("Sprites and Images\\mario.png", 40, 36);
        mario = marioSS.getSubImage(4, 0).getScaledCopy(scale);
        setGraphic(mario);
        
        define("RIGHT", Input.KEY_RIGHT);
        define("LEFT", Input.KEY_LEFT);
        define("UP", Input.KEY_UP);
        define("DOWN", Input.KEY_DOWN);
        define("SPACE", Input.KEY_SPACE);
        
        this.SetAnimations();
        
        setHitBox(10, 0, mario.getWidth() - 90, mario.getHeight() - 60);
        addType("PLAYER");
        
        lives = 3;
        xSpeed = 4;
        climbing = false;
        jumping = false;
        reset = false;
        levelCompleted = false;
        over = false;
        clear = false;
        rip = false;
        time = 0;
        timeBarrel = 0;
        jumpDirection = 'F';
        defaultAnim = "Still";
        
        this.LoadFields();
    }
    
    /**
     * Creates Mario's animations
     */
    private void SetAnimations() {
        setGraphic(marioSS);
        
        duration = 225;
        this.addAnimation("Still", false, 0, 4);
        this.addAnimation("RevStill", false, 0, 3);
        this.addAnimation("MoveRight", true, 0, 4, 5);
        this.addAnimation("MoveLeft", true, 0, 3, 2);
        this.addAnimation("Climb", true, 1, 4, 0, 3);
        
        setGraphic(mario);
    }
    
    /**
     * Returns reset field
     * @return is a reset necessary
     */
    public boolean isReset(){
        return reset;
    }
    
    /**
     * Returns level completed field
     * @return is the level completed necessary
     */
    public boolean isLevelCompleted(){
        return levelCompleted;
    }
    
    /**
     * Returns level completed field
     * @return is the level completed necessary
     */
    public boolean isRIP(){
        return rip;
    }
    
    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException{
        super.render(gc, g);
        
        g.drawString("Score: " + currentScore, 875, 50);
        g.drawString("Highscore: " + highScore, 875, 80);
        g.drawString("Lives: " + lives, 875, 110);
    }
    
    /**
     * Updates the logic interactions, movement, and animations for Mario, along with handling the HUD
     * @param gc
     * @param delta
     * @throws SlickException 
     */
    @Override
    public void update(GameContainer gc, int delta) throws SlickException{
        super.update(gc, delta);
        
        this.UpDownMovement();
        this.LeftRightMovement();
        
        this.BarrelPoints(delta);
        
        if(collide("END", x - xSpeed, y) != null){
            currentScore += 1000;
            if(currentScore > highScore)
                highScore = currentScore;
            this.SaveFields(lives, currentScore, highScore);
            levelCompleted = true;
            reset = true;
        }
        
        if(currentScore > highScore)
            highScore = currentScore;
        
        if(jumping)
            this.SmoothJump();
        
        if( !(jumping || check("RIGHT") || check("LEFT") || check("UP") || check("DOWN") || check("SPACE")))
            currentAnim = defaultAnim;
        
        if(collide("BARREL", x, y) != null){
            if(lives > 1)
                lives -= 1;
            else{
                rip = true;
                lives = 3;
                currentScore = 0;
            }
            this.SaveFields(lives, currentScore, highScore);
            reset = true;
        }
    }
    
    /**
     * Handle the left and right movement of Mario
     */
    private void LeftRightMovement(){
        if(!climbing && !jumping && check("RIGHT")){
            defaultAnim = "Still";
            if(collide("SOLID", x + xSpeed, y) == null){
                x += xSpeed;
                currentAnim = "MoveRight";
            }
            else if(collide("SOLID" , x + xSpeed, y) != null && collide("SOLID", x + xSpeed, y - 3) == null){
                x += xSpeed;
                y -= 3;
                currentAnim = "MoveRight";
            }
        }
        if(!climbing && !jumping && check("LEFT")){
            defaultAnim = "RevStill";
            if(collide("SOLID", x - xSpeed, y) == null){
                x -= xSpeed;
                currentAnim = "MoveLeft";
            }
            else if (collide("SOLID" , x - xSpeed, y) != null && collide("SOLID", x - xSpeed, y - 3) == null){
                x -= xSpeed;
                y -= 3;
                currentAnim = "MoveLeft";
            }
        }
    }
    
    /**
     * Handle the upward and downward movement of Mario
     */
    private void UpDownMovement() {
        //Gravity
        if(!climbing && collide("SOLID", x, y + 5) == null && !jumping){
            y += 5;
            falling = true;
        }
        else
            falling = false;
        
        //Up/Down
        if(check("UP") && collide("LADDER", x, y) != null){
            climbing = true;
            y -= 2;
            currentAnim = "Climb";
        }
        /*
        else if(check("DOWN") && collide("LADDER", x, y + 20) != null){
            if(collide("SOLID", x, y + 2) == null && collide("SOLID", x, y - 2) != null){
                climbing = true;
                currentAnim = "Climb";
                y += 2;
            }
        }*/
        else if(!check("UP") && !check("DOWN"))
            climbing = false;
        
        if(!falling && !climbing && check("SPACE") && collide("SOLID", x, y - 10) == null){
            jumping = true;
            if(check("LEFT"))
                jumpDirection = 'L';
            else if(check("RIGHT"))
                jumpDirection = 'R';
        }
    }
    
    /**
     * Attempts to make Mario's jump smoother
     */
    private void SmoothJump() {
        time++;
        
        if(time < 50 / 2 && collide("SOLID", x, y - 2) == null){
            y -= 2;
            if(jumpDirection == 'R' && collide("SOLID", x + 3, y - 2) == null)
                x += 3;
            else if(jumpDirection == 'L' && collide("SOLID", x - 3, y - 2) == null)
                x -= 3;
        }
        else if(time < 50 && collide("SOLID", x, y + 2) == null){
            y += 2;
            if(jumpDirection == 'R' && collide("SOLID", x + 3, y) == null)
                x += 3;
            else if(jumpDirection == 'L' && collide("SOLID", x - 3, y - 2) == null)
                x -= 3;
        }
        else{
            time = 0;
            jumping = false;
            jumpDirection = 'F';
        }
    }
    
    /**
     * Detect if Mario jumps over a barrel completely and add score accordingly
     * @param delta 
     */
    private void BarrelPoints(int delta) {
        if((collide("BARREL", x, y + 20) != null || collide("BARREL", x, y + 40) != null) && collide("BARREL", x, y) == null){
            over = true;
        }
        if(over){
            timeBarrel += delta;
        }
        if(((collide("BARREL", x - 30, y) != null || collide("BARREL", x + 30, y) != null) || (collide("BARREL", x - 60, y) != null || collide("BARREL", x + 60, y) != null))){
            clear = true;
        }
        else if(((collide("BARREL", x - 90, y) != null || collide("BARREL", x + 90, y) != null) || (collide("BARREL", x - 120, y) != null || collide("BARREL", x + 120, y ) != null))){
            clear = true;
        }
        if(collide("BARREL", x, y) != null){
            clear = false;
            over = false;
            timeBarrel = 0;
        }
        if(!climbing && clear && over && timeBarrel > 300){
            currentScore += 100;
            clear = false;
            over = false;
            timeBarrel = 0;
        }
    }
    
    /**
     * Load scores/lives file into the respective fields
     */
    private void LoadFields () {
        try{
            Scanner scan = new Scanner(new File("HUD.txt"));
            
            lives = Integer.parseInt(scan.nextLine());
            currentScore = Integer.parseInt(scan.nextLine());
            highScore = Integer.parseInt(scan.nextLine());
            
            scan.close();
        } catch(FileNotFoundException e){
            lives = 3;
            currentScore = 0;
            highScore = 0;
            CreateFile();
        }
    }
    
    /**
     * If the scores/lives file doesn't exist, create that file w/initialized values
     */
    private void CreateFile () {
        try{
            PrintWriter writer = new PrintWriter("HUD.txt", "UTF-8");
            writer.println("" + lives);
            writer.println("" + currentScore);
            writer.println("" + highScore);
            
            writer.flush();
            writer.close();
        }catch(FileNotFoundException | UnsupportedEncodingException e){}
    }
    
    /**
     * Save the three parameter fields to a specific file
     * @param currentLives
     * @param score
     * @param topScore 
     */
    private void SaveFields (int currentLives, int score, int topScore) {
        this.LoadFields();
        
        try{
            PrintWriter writer = new PrintWriter("HUD.txt", "UTF-8");
            
            writer.println("" + currentLives);
            writer.println("" + score);
            writer.println("" + topScore);
            
            writer.flush();
            writer.close();
        } catch(IOException e){   
        }
        
    }
}
