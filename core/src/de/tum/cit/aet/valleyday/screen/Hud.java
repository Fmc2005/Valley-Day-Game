package de.tum.cit.aet.valleyday.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import de.tum.cit.aet.valleyday.map.FireResistancePotion;
import de.tum.cit.aet.valleyday.map.GameMap;
import de.tum.cit.aet.valleyday.map.InvisibilyRing;
import de.tum.cit.aet.valleyday.map.Player;
import de.tum.cit.aet.valleyday.map.Shovel;
import de.tum.cit.aet.valleyday.map.Staff;
import de.tum.cit.aet.valleyday.map.Tool;
import de.tum.cit.aet.valleyday.texture.Textures;
/**
 * A Heads-Up Display (HUD) that displays information on the screen.
 * It uses a separate camera so that it is always fixed on the screen.
 */
public class Hud {
    
    /** The SpriteBatch used to draw the HUD. This is the same as the one used in the GameScreen. */
    private final SpriteBatch spriteBatch;
    /** The font used to draw text on the screen. */
    private final BitmapFont font;
    /** The camera used to render the HUD. */
    private final OrthographicCamera camera;


    private GameMap map;
    /**
     * Constructor of HUD
     * @param spriteBatch
     * @param font
     * @param game
     */
    public Hud(SpriteBatch spriteBatch, BitmapFont font, GameMap map) {
        this.spriteBatch = spriteBatch;
        this.font = font;
        this.camera = new OrthographicCamera();
  
        this.map = map;
    }
    
    
    /**
     * Renders the HUD on the screen.
     * This uses a different OrthographicCamera so that the HUD is always fixed on the screen.
     */
    public void render() {
        // Render from the camera's perspective
        spriteBatch.setProjectionMatrix(camera.combined);

        //STC
        // Draw semi-transparent background boxes using ShapeRenderer
        ShapeRenderer shapeRenderer = new ShapeRenderer();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(121f / 255f, 121f / 255f, 121f / 255f, 0.8f);


        // left box
        shapeRenderer.rect(50, Gdx.graphics.getHeight() - 220, 400, 210);


        shapeRenderer.end();

        // Start drawing
        spriteBatch.begin();
        // Draw the HUD elements
        for (int i = 0; i <map.getPlayer().getPlayerhp(); i++){
            spriteBatch.draw(Textures.HEART, 60 + i*25, Gdx.graphics.getHeight() - 48, Textures.HEART.getRegionWidth()*2, Textures.HEART.getRegionHeight()*2);
        }

        if (map.enoughVP() == true) {
            font.setColor(Color.GREEN);
            font.draw(spriteBatch, "EXIT!", 60, Gdx.graphics.getHeight() - 260);
            font.setColor(Color.BLACK);
        }

        // special things then timer reaches down to 15 seconds
        if(GameMap.RemainingTime <= 15){
            font.setColor(Color.RED);
        }
        int minutes = (int) (GameMap.RemainingTime / 60);
        int seconds =  (int) (GameMap.RemainingTime % 60);

        font.draw(spriteBatch, String.format("%02d:%02d", minutes, seconds), 60, Gdx.graphics.getHeight() - 180);
        font.setColor(Color.BLACK);
        
        font.draw(spriteBatch, map.getPlayer().getVictorypoints() + " /"+ map.neededVictoryPoints, 140, Gdx.graphics.getHeight() - 100);
        spriteBatch.draw(Textures.SOIL_MATURE_CROPS, 60, Gdx.graphics.getHeight() - 140, Textures.SOIL_MATURE_CROPS.getRegionWidth()*2, Textures.SOIL_MATURE_CROPS.getRegionHeight()*2);
     
        //Items appear in bar when picked up
        for (Tool tool: map.getTools()) {
            if(tool instanceof Shovel theshovel) {
                if(theshovel.isBroken()) {
                    spriteBatch.draw(Textures.SHOVEL, 210, Gdx.graphics.getHeight() - 85, Textures.SHOVEL.getRegionWidth()*2, Textures.SHOVEL.getRegionHeight()*2);
                }
            }
            if(tool instanceof FireResistancePotion thepotion) {
                if(thepotion.isBroken()){
                if(map.getPlayer().getFirePotionTimer() < 15) {
                    
                    spriteBatch.draw(Textures.FIRE_POTION, 230, Gdx.graphics.getHeight() - 125, Textures.FIRE_POTION.getRegionWidth()*2, Textures.FIRE_POTION.getRegionHeight()*2);
                    font.draw(spriteBatch,  (int)(Player.getFIREPOTION_LENGTH()- map.getPlayer().getFirePotionTimer())+ " s left", 270, Gdx.graphics.getHeight() - 100);
                }
            }}
            if(tool instanceof InvisibilyRing thering) {
                if(thering.isBroken()){
                if(map.getPlayer().getInvisibleTimer() < 15) {
                    
                    spriteBatch.draw(Textures.RING, 230, Gdx.graphics.getHeight() - 165, Textures.RING.getRegionWidth()*2, Textures.RING.getRegionHeight()*2);
                    font.draw(spriteBatch,  (int)(Player.getINVISIBILITY_LENGTH() - map.getPlayer().getInvisibleTimer())+ " s left", 270, Gdx.graphics.getHeight() - 140);
                }
            }}
            if(tool instanceof Staff thestaff) {
                if(thestaff.isBroken()){
                if(map.getPlayer().getStaffatkcounter() >0) {
                    
                    spriteBatch.draw(Textures.STAFF, 210, Gdx.graphics.getHeight() - 205, Textures.STAFF.getRegionWidth()*2, Textures.STAFF.getRegionHeight()*2);
                    font.draw(spriteBatch,  + map.getPlayer().getStaffatkcounter() + " kill/s left", 250, Gdx.graphics.getHeight() - 180);
                }
            }}
            
            
        }
        // Finish drawing
        spriteBatch.end();
    }
    
    /**
     * Resizes the HUD when the screen size changes.
     * This is called when the window is resized.
     * @param width The new width of the screen.
     * @param height The new height of the screen.
     */
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
    }
    
}
