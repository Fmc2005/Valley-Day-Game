package de.tum.cit.aet.valleyday.screen;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import de.tum.cit.aet.valleyday.ValleyDayGame;
import de.tum.cit.aet.valleyday.map.Animal;
import de.tum.cit.aet.valleyday.map.Balrog;
import de.tum.cit.aet.valleyday.map.Bull;
import de.tum.cit.aet.valleyday.map.Chicken;
import de.tum.cit.aet.valleyday.map.Crop;
import de.tum.cit.aet.valleyday.map.Debris;
import de.tum.cit.aet.valleyday.map.Dragon;
import de.tum.cit.aet.valleyday.map.Entrance;
import de.tum.cit.aet.valleyday.map.Fence;
import de.tum.cit.aet.valleyday.texture.Animations;
import de.tum.cit.aet.valleyday.texture.Drawable;
import de.tum.cit.aet.valleyday.texture.Textures;
import de.tum.cit.aet.valleyday.map.Player;
import de.tum.cit.aet.valleyday.map.PlayerDirection;
import de.tum.cit.aet.valleyday.map.Soil;
import de.tum.cit.aet.valleyday.map.Tool;



/**
 * The GameScreen class is responsible for rendering the gameplay screen.
 * It handles the game logic and rendering of the game elements.
 */
public class GameScreen implements Screen {
    
    /**
     * The size of a grid cell in pixels.
     * This allows us to think of coordinates in terms of square grid tiles
     * (e.g. x=1, y=1 is the bottom left corner of the map)
     * rather than absolute pixel coordinates.
     */
    public static final int TILE_SIZE_PX = 16;
    

   //for Balrog:
    private float fireStateTime = 0f;
   
    /**
     * The scale of the game.
     * This is used to make everything in the game look bigger or smaller.
     */
    public static final int SCALE = 4;
    public static final int SMALLSCALE = 2;
    public static final int SUPERSMALLSCALE = 10;

    private final ValleyDayGame game;
    private final SpriteBatch spriteBatch;
    private final Hud hud;
    private final OrthographicCamera mapCamera;

    /**
     * Constructor for GameScreen. Sets up the camera and font.
     *
     * @param game The main game class, used to access global resources and methods.
     */
    public GameScreen(ValleyDayGame game) {
        this.game = game;
        this.spriteBatch = game.getSpriteBatch();
        this.hud = new Hud(spriteBatch, game.getSkin().getFont("font"), game.getMap());
        // Create and configure the camera for the game view
        this.mapCamera = new OrthographicCamera();
        this.mapCamera.setToOrtho(false);
    }
    
    /**
     * The render method is called every frame to render the game.
     * @param deltaTime The time in seconds since the last render.
     */
    @Override
    public void render(float deltaTime) {
        //Balrog:
        fireStateTime += deltaTime;

        // Check for escape key press to go back to the menu
        if(game.getMap().lost == false && game.getMap().won == false){
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.goToMenu();
        }}
        if(game.getMap().lost ==true && game.getMap().won ==false){
            game.goToMenu();
        }
        if(game.getMap().lost ==false && game.getMap().won ==true){
            game.goToMenu();
        }

        
        // Clear the previous frame from the screen, or else the picture smears
        ScreenUtils.clear(Color.BLACK);
        
        // Cap frame time to 250ms to prevent spiral of death
        float frameTime = Math.min(deltaTime, 0.250f);
        
        // Update the map state
        game.getMap().tick(frameTime);
        
        // Update the camera
        updateCamera();
        
        // Render the map on the screen
        renderMap();
        
        // Render the HUD on the screen
        hud.render();
    }
    
    /**
     * Updates the camera to match the current state of the game.
     * Felix: Camera moves with player
     */
    private void updateCamera() {
        mapCamera.setToOrtho(false);
        Vector3 position = mapCamera.position;
        position.x = game.getMap().getPlayer().getX() * TILE_SIZE_PX * SCALE;
        position.y = game.getMap().getPlayer().getY() * TILE_SIZE_PX * SCALE;
        mapCamera.position.set(position);
        mapCamera.update();// This is necessary to apply the changes
    }
    public void drawSoil (Soil soil){
        if (soil.getCrop() == Crop.ROTTEN_CROPS){drawRottenCrops(spriteBatch, soil); return;}
        if (soil.getCrop() == Crop.EMPTY_SOIL){return;}
            if(soil.getCrop() == Crop.MATURE_CROPS || soil.getCrop() == Crop.SPROUTS){
            drawSmall(spriteBatch, soil); return;}
        draw(spriteBatch, soil);
    }
    
    private void renderMap() {
        List<Soil> soils2 = new ArrayList<>();
        // This configures the spriteBatch to use the camera's perspective when rendering
        spriteBatch.setProjectionMatrix(mapCamera.combined);
        
        
        // Start drawing
        spriteBatch.begin();
        
        for(int i = 0; i<game.getMap().MAP_WIDTH; i++){
            for (int j = 0; j<game.getMap().MAP_HEIGHT; j++){
                spriteBatch.draw(Textures.SOIL_EMPTY, i*TILE_SIZE_PX*SCALE, j*TILE_SIZE_PX*SCALE, Textures.SOIL_EMPTY.getRegionWidth()*SCALE, Textures.SOIL_EMPTY.getRegionHeight()*SCALE);
            }
        }
       
        // Render everything in the map here, in order from lowest to highest (later things appear on top)
        // You may want to add a method to GameMap to return all the drawables in the correct order
        
        for (Soil soil : game.getMap().getSoils())  {
            if (soil.getCrop() == Crop.SOIL_PATH)
                draw(spriteBatch, soil);
            else soils2.add(soil);
        }
        //Felix: draw Fences horizontal
        for (Fence fence : game.getMap().getFences()) {
    
            draw(spriteBatch, fence);
        }
        for (Debris debris : game.getMap().getDebris()){ 
            drawDebris(spriteBatch, debris);
        }
        for (Tool tool : game.getMap().getTools()) {
            draw(spriteBatch, tool);
        }
        
         draw(spriteBatch, game.getMap().getExit());
          drawEntrance(spriteBatch, game.getMap().getEntrance());

        spriteBatch.draw(Textures.TILE_HIGHLIGHT, TILE_SIZE_PX, SCALE);
        drawFacingTile(spriteBatch);
        drawStaffFacingTile(spriteBatch);
        if (game.getMap().getPlayer().isInvisible())
        spriteBatch.setColor(1, 1, 1, 0.5f);
        drawPlayer(spriteBatch, game.getMap().player);
        spriteBatch.setColor(1, 1, 1, 1);
        
         for (Soil soil : soils2) {
            drawSoil(soil);          
        }
        

        for(int i = 0; i<game.getMap().MAP_WIDTH; i++){
            for (int j = 0; j<game.getMap().MAP_HEIGHT; j++){
               if (game.getMap().isOnFire[i][j]) {
    TextureRegion frame = Animations.DRAGON_FIRE.getKeyFrame(fireStateTime, true);

    spriteBatch.draw(frame,
        i * TILE_SIZE_PX * SCALE-100 ,
        j * TILE_SIZE_PX * SCALE-30,
        frame.getRegionWidth() * SCALE * 0.5f/SMALLSCALE,
        frame.getRegionHeight() * SCALE * 0.5f/(SMALLSCALE*2)
    );
}
            }}
        for(int i = 0; i<game.getMap().MAP_WIDTH; i++){
            for (int j = 0; j<game.getMap().MAP_HEIGHT; j++){
               if (game.getMap().isOnFire2[i][j]) {
    TextureRegion frame = Animations.BALROG_FIRE.getKeyFrame(fireStateTime, true);

    spriteBatch.draw(frame,
        i * TILE_SIZE_PX * SCALE - 15,
        j * TILE_SIZE_PX * SCALE,
        frame.getRegionWidth() * SCALE * 0.5f,
        frame.getRegionHeight() * SCALE * 0.5f
    );
}
            }}

            for(int i = 0; i<game.getMap().MAP_WIDTH; i++){
            for (int j = 0; j<game.getMap().MAP_HEIGHT; j++){
               if (game.getMap().isOnLightning[i][j]) {
                
    TextureRegion frame = Animations.LIGHTNING.getKeyFrame(fireStateTime, true);

    spriteBatch.draw(frame,
        i * TILE_SIZE_PX * SCALE+10,
        j * TILE_SIZE_PX * SCALE+10,
        frame.getRegionWidth() * SCALE * 0.5f/(SUPERSMALLSCALE*1/2),
        frame.getRegionHeight() * SCALE * 0.5f/(SUPERSMALLSCALE*1/2)
    );
}
            }}

          

    

        for (Animal animal : game.getMap().getAnimals()) {
            if (animal instanceof Chicken theChicken)
            drawChicken(spriteBatch, theChicken);
            else if(animal instanceof Dragon theDragon){
                drawDragon(spriteBatch, theDragon);
            }
            else if(animal instanceof Balrog theBalrog){
                drawBalrog(spriteBatch, theBalrog);
            }
            else if(animal instanceof Bull theBull){
                drawBull(spriteBatch, theBull);
            }
            else draw(spriteBatch, animal);
        }

    

        
        //we should paint some of the object by tile and not by object type....

        
        

        // Finish drawing, i.e. send the drawn items to the graphics card
        spriteBatch.end();
    }

    /**
     * Draws this object on the screen.
     * The texture will be scaled by the game scale and the tile size.
     * This should only be called between spriteBatch.begin() and spriteBatch.end(), e.g. in the renderMap() method.
     * @param spriteBatch The SpriteBatch to draw with.
     */
    private static void draw(SpriteBatch spriteBatch, Drawable drawable) {
        TextureRegion texture = drawable.getCurrentAppearance();
        // Drawable coordinates are in tiles, so we need to scale them to pixels
        float x = drawable.getX() * TILE_SIZE_PX * SCALE;
        float y = drawable.getY() * TILE_SIZE_PX * SCALE;
        // Additionally scale everything by the game scale
        float width = texture.getRegionWidth() * SCALE;
        float height = texture.getRegionHeight() * SCALE;
        spriteBatch.draw(texture, x, y, width, height);
    }

    private static void drawPlayer(SpriteBatch spriteBatch, Player player) {
        TextureRegion texture = player.getCurrentAppearance();
        float x = player.getX() * TILE_SIZE_PX * SCALE;
        float y = player.getY() * TILE_SIZE_PX * SCALE;
        if (player.getDirection() == PlayerDirection.LEFT && player.isDorSpressed()){
            x -= 40;}
        if (player.getDirection() == PlayerDirection.DOWN && player.isDorSpressed()){
            x -= 30;
            y-=10;}
        if (player.getDirection() == PlayerDirection.RIGHT && player.isDorSpressed()){
            x -=18;}
        if (player.getDirection() == PlayerDirection.UP && player.isDorSpressed()){
            x -=35;
            y+= 7;}
        // Additionally scale everything by the game scale
        float width = texture.getRegionWidth() * SCALE;
        float height = texture.getRegionHeight() * SCALE;
        spriteBatch.draw(texture, x, y, width, height);
    }



        private static void drawDebris(SpriteBatch spriteBatch, Debris debris) {
        TextureRegion texture = debris.getCurrentAppearance();
        // Drawable coordinates are in tiles, so we need to scale them to pixels
        float x = debris.getX() * TILE_SIZE_PX * SCALE;
        float y = debris.getY() * TILE_SIZE_PX * SCALE;
        // Additionally scale everything by the game scale
        float width = texture.getRegionWidth() * SMALLSCALE;
        float height = texture.getRegionHeight() * SMALLSCALE;
        if (!debris.isBroken() && debris.getTextureTimer() ==0){
        spriteBatch.draw(texture, x, y, width, height);return;}    
    
        if (!debris.isBroken()) spriteBatch.draw(texture, x, y, width, height);  
        else spriteBatch.draw(texture, x, y, width, height);    
    }   
        
        private static void drawChicken(SpriteBatch spriteBatch, Chicken chicken) {
        TextureRegion texture = chicken.getCurrentAppearance();
        // Drawable coordinates are in tiles, so we need to scale them to pixels
        float x = chicken.getX() * TILE_SIZE_PX * SCALE -25;
        float y = chicken.getY() * TILE_SIZE_PX * SCALE +15;
        // Additionally scale everything by the game scale
        float width = texture.getRegionWidth() * SMALLSCALE*1.3f;
        float height = texture.getRegionHeight() * SMALLSCALE*1.3f;
        spriteBatch.draw(texture, x, y, width, height);
    }    
     

    private static void drawSmall(SpriteBatch spriteBatch, Drawable drawable) {
        TextureRegion texture = drawable.getCurrentAppearance();
        // Drawable coordinates are in tiles, so we need to scale them to pixels
        float x = drawable.getX() * TILE_SIZE_PX * SCALE;
        float y = drawable.getY() * TILE_SIZE_PX * SCALE;
        // Additionally scale everything by the game scale
        float width = texture.getRegionWidth() * SMALLSCALE*1.5f;
        float height = texture.getRegionHeight() * SMALLSCALE*1.5f;
        spriteBatch.draw(texture, x-(TILE_SIZE_PX*SCALE/4) , y+(TILE_SIZE_PX*SCALE/4), width, height);
    }

    private void drawFacingTile(SpriteBatch spriteBatch) {
        TextureRegion texture = Textures.TILE_HIGHLIGHT;
        
        // Drawable coordinates are in tiles, so we need to scale them to pixels
        float x = (float)(game.getMap().getFacingTile().getX()) * TILE_SIZE_PX * SCALE;
        float y = (float)(game.getMap().getFacingTile().getY()) * TILE_SIZE_PX * SCALE;
        // Additionally scale everything by the game scale
        float width = texture.getRegionWidth() * SCALE;
        float height = texture.getRegionHeight() * SCALE;
        spriteBatch.draw(texture, x, y, width, height);
    }
    private void drawStaffFacingTile(SpriteBatch spriteBatch) {
       
        TextureRegion texture = Textures.TILE_HIGHLIGHT_STAFF;
        if(game.getMap().getPlayer().getHasStaff()== true){
        // Drawable coordinates are in tiles, so we need to scale them to pixels
        float x = (float)(game.getMap().getStaffFacingTile().getX()) * TILE_SIZE_PX * SCALE;
        float y = (float)(game.getMap().getStaffFacingTile().getY()) * TILE_SIZE_PX * SCALE;
        // Additionally scale everything by the game scale
        float width = texture.getRegionWidth() * SCALE;
        float height = texture.getRegionHeight() * SCALE;
        spriteBatch.draw(texture, x, y, width, height);
    }}

    //Felix: draw Entrance
     private static void drawEntrance(SpriteBatch spriteBatch, Entrance entrance) {
        TextureRegion texture = entrance.getCurrentAppearance();
        // Drawable coordinates are in tiles, so we need to scale them to pixels
        float x = entrance.getX() * TILE_SIZE_PX * SCALE;
        float y = entrance.getY() * TILE_SIZE_PX * SCALE;
        // Additionally scale everything by the game scale
        float width = texture.getRegionWidth() * SCALE;
        float height = texture.getRegionHeight() * SCALE;
        spriteBatch.draw(texture, x, y, width, height);
    }
    
    //Felix: draw dragon
    private static void drawDragon(SpriteBatch spriteBatch, Dragon dragon) {
        TextureRegion texture = dragon.getCurrentAppearance();
        // Drawable coordinates are in tiles, so we need to scale them to pixels
        float x = dragon.getX() * TILE_SIZE_PX * SCALE;
        float y = dragon.getY() * TILE_SIZE_PX * SCALE;
        // Additionally scale everything by the game scale
        float width = texture.getRegionWidth() * SCALE*3/2/SUPERSMALLSCALE;
        float height = texture.getRegionHeight() * SCALE*3/2/SUPERSMALLSCALE;
        spriteBatch.draw(texture, x-11, y-10, width, height);
    }

    private static void drawRottenCrops(SpriteBatch spriteBatch, Soil soil) {
        TextureRegion texture = soil.getCurrentAppearance();
        // Drawable coordinates are in tiles, so we need to scale them to pixels
        float x = soil.getX() * TILE_SIZE_PX * SCALE;
        float y = soil.getY() * TILE_SIZE_PX * SCALE;
        // Additionally scale everything by the game scale
        float width = texture.getRegionWidth() * SCALE/SUPERSMALLSCALE/4;
        float height = texture.getRegionHeight() * SCALE/SUPERSMALLSCALE/3;
        spriteBatch.draw(texture, x-10, y, width, height);
    }

     private static void drawBalrog(SpriteBatch spriteBatch, Balrog balrog) {
        TextureRegion texture = balrog.getCurrentAppearance();
        // Drawable coordinates are in tiles, so we need to scale them to pixels
        float x = balrog.getX() * TILE_SIZE_PX * SCALE;
        float y = balrog.getY() * TILE_SIZE_PX * SCALE;
        // Additionally scale everything by the game scale
        float width = texture.getRegionWidth() * SCALE*3/2/SUPERSMALLSCALE;
        float height = texture.getRegionHeight() * SCALE*3/2/SUPERSMALLSCALE;
        spriteBatch.draw(texture, x-20, y, width, height);
    } 

    private static void drawBull(SpriteBatch spriteBatch, Bull bull) {
        TextureRegion texture = bull.getCurrentAppearance();
        // Drawable coordinates are in tiles, so we need to scale them to pixels
        float x = bull.getX() * TILE_SIZE_PX * SCALE;
        float y = bull.getY() * TILE_SIZE_PX * SCALE;
        // Additionally scale everything by the game scale
        float width = texture.getRegionWidth() * SCALE/(SUPERSMALLSCALE*5/4);
        float height = texture.getRegionHeight() * SCALE/(SUPERSMALLSCALE*5/4);
        spriteBatch.draw(texture, x-11, y-10, width, height);
    }

    
    /**
     * Called when the window is resized.
     * This is where the camera is updated to match the new window size.
     * @param width The new window width.
     * @param height The new window height.
     */
    @Override
    public void resize(int width, int height) {
        mapCamera.setToOrtho(false);
        hud.resize(width, height);
    }

    // Unused methods from the Screen interface
    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }

}
