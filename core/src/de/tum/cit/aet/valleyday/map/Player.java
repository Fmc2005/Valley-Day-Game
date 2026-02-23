package de.tum.cit.aet.valleyday.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;


import de.tum.cit.aet.valleyday.audio.SoundFX;
import de.tum.cit.aet.valleyday.texture.Animations;
import de.tum.cit.aet.valleyday.texture.Drawable;

/**
 * Represents the player character in the game.
 * The player has a hitbox, so it can collide with other objects in the game.
 */
public class Player implements Drawable {
    private PlayerDirection direction;
    private Tile standingTile;
    private Tile facingTile;
    private GameMap map;
    private float breakingTimer = 0;
    private float cropcooldown;
    private int victorypoints = 0;
    private boolean hasShovel;
    private int playerhp;
    private float hpcooldown;
    private static float FIREPOTION_LENGTH = 15;
    private float firePotionTimer;
    private static float INVISIBILITY_LENGTH = 15;
    private float invisibleTimer;
    public boolean isSpressed;
    private boolean hasStaff;
    private int staffatkcounter;
    private Tile StafffacingTile;
  
    
    
    
    public boolean isInvisible() {
        return invisibleTimer<INVISIBILITY_LENGTH;
    }

    public Body getHitbox() {
        return hitbox;
    }

    public void setHasShovel(boolean hasShovel) {
        this.hasShovel = hasShovel;
    }

    public float getElapsedTime() {
		return elapsedTime;
	}

	public Tile getFacingTile() {
		return facingTile;
	}

    
	public Tile getStafffacingTile() {
        return StafffacingTile;
    }


    /** Total time elapsed since the game started. We use this for calculating the player movement and animating it. */
    private float elapsedTime;
    
    /** The Box2D hitbox of the player, used for position and collision detection. */
    private final Body hitbox;
    
    public Player(World world, float x, float y, GameMap map) {
        this.hitbox = createHitbox(world, x, y);
        direction = PlayerDirection.LEFT;
        this.map = map;
        facingTile = new Tile(Math.round(x-1), Math.round(y), map);
        StafffacingTile = new Tile(Math.round(x-2), Math.round(y), map);
        standingTile = new Tile(Math.round(x), Math.round(y), map);
        cropcooldown =5;
        playerhp =3;
        if (map.getGame().difficulty == 0) playerhp = 5;
        if (map.getGame().difficulty == 2) playerhp = 1;
        hpcooldown = 0;
        victorypoints =0;
        firePotionTimer = 15;
        invisibleTimer = 12;
        staffatkcounter = 1;

    }
    
    /**
     * Creates a Box2D body for the player.
     * This is what the physics engine uses to move the player around and detect collisions with other bodies.
     * @param world The Box2D world to add the body to.
     * @param startX The initial X position.
     * @param startY The initial Y position.
     * @return The created body.
     */
    private Body createHitbox(World world, float startX, float startY) {
        // BodyDef is like a blueprint for the movement properties of the body.
        BodyDef bodyDef = new BodyDef();
        // Dynamic bodies are affected by forces and collisions.
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        // Set the initial position of the body.
        bodyDef.position.set(startX, startY);
        // Create the body in the world using the body definition.
        Body body = world.createBody(bodyDef);
        // Now we need to give the body a shape so the physics engine knows how to collide with it.
        // We'll use a circle shape for the player.
        CircleShape circle = new CircleShape();
        // Give the circle a radius of 0.3 tiles (the player is 0.6 tiles wide).
        circle.setRadius(0.3f);
        // Attach the shape to the body as a fixture.
        // Bodies can have multiple fixtures, but we only need one for the player.
        body.createFixture(circle, 1.0f);
        // We're done with the shape, so we should dispose of it to free up memory.
        circle.dispose();
        // Set the player as the user data of the body so we can look up the player from the body later.
        body.setUserData(this);
        return body;
    }
    
    /**
     * Move the player around in a circle by updating the linear velocity of its hitbox every frame.
     * This doesn't actually move the player, but it tells the physics engine how the player should move next frame.
     * @param frameTime the time since the last frame.
     */
    public void tick(float frameTime) {
        firePotionTimer+= frameTime;
        invisibleTimer += frameTime;
        this.elapsedTime += frameTime;
        cropcooldown += frameTime;
        hpcooldown += frameTime;
        boolean isLeftPressed = Gdx.input.isKeyPressed(Input.Keys.LEFT);
        boolean isRightPressed = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        boolean isUpPressed = Gdx.input.isKeyPressed(Input.Keys.UP);
        boolean isDownPressed = Gdx.input.isKeyPressed(Input.Keys.DOWN);
        boolean isApressed = Gdx.input.isKeyPressed(Input.Keys.A);
        boolean isSpressed = Gdx.input.isKeyPressed(Input.Keys.S);
        playerdead();
        float xVelocity = 0;
                if (isLeftPressed) {
                    xVelocity += -5;
                    direction = PlayerDirection.LEFT;
                }
                if (isRightPressed){
                    xVelocity += +5;
                    direction = PlayerDirection.RIGHT;
                }
        float yVelocity = 0;
                if (isUpPressed){ 
                    yVelocity += 5;
                    direction = PlayerDirection.UP;
                }
                if (isDownPressed){
                    yVelocity += -5;
                    direction = PlayerDirection.DOWN;}
                // make the player walk
        this.hitbox.setLinearVelocity(xVelocity, yVelocity);

        // bug fixing --> no more moonwalking (Franz)
        if (yVelocity == 0){
            if (xVelocity ==5)
            direction = PlayerDirection.RIGHT;
            if (xVelocity == -5)
            direction = PlayerDirection.LEFT;
        }
      
        switch (direction) {
            case LEFT: 
            facingTile.setX(Math.round(getX())-1);
            facingTile.setY(Math.round(getY()));
            StafffacingTile.setX(Math.round(getX())-2);
            StafffacingTile.setY(Math.round(getY()));
                break;
            case RIGHT: 
            facingTile.setX(Math.round(getX())+1);
            facingTile.setY(Math.round(getY()));
            StafffacingTile.setX(Math.round(getX())+2);
            StafffacingTile.setY(Math.round(getY()));
                break;
            case UP: 
            facingTile.setX(Math.round(getX()));
            facingTile.setY(Math.round(getY())+1); 
            StafffacingTile.setX(Math.round(getX()));
            StafffacingTile.setY(Math.round(getY())+2);
                break;
            case DOWN: 
            facingTile.setX(Math.round(getX()));
            facingTile.setY(Math.round(getY())-1);
            StafffacingTile.setX(Math.round(getX()));
            StafffacingTile.setY(Math.round(getY())-2);
                break;       
        }
       
        facingTile.setFacingObject(map.mapAsMatrix[facingTile.getX()][facingTile.getY()]);

      int xstaff = (StafffacingTile.getX()<0) ? (0): Math.min(map.MAP_WIDTH-1, StafffacingTile.getX());
        int ystaff = (StafffacingTile.getY()<0) ? (0): Math.min(map.MAP_HEIGHT-1, StafffacingTile.getY());
        StafffacingTile.setX(xstaff);
        StafffacingTile.setY(ystaff);
        StafffacingTile.setFacingObject(map.mapAsMatrix[StafffacingTile.getX()][StafffacingTile.getY()]);
        standingTile.setFacingObject(map.mapAsMatrix[Math.round(getX())][Math.round(getY())]);
        standingTile.setX(Math.round(getX()));
        standingTile.setY(Math.round(getY()));
        
    //Franz: Debris breaking
    if (facingTile.getFacingObject() instanceof Debris theDebris && !theDebris.isBroken()) {
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            breakingTimer += frameTime;
            float neededtime = 1;
            if (hasShovel) neededtime = 0.5f;
            theDebris.setTextureTimer(breakingTimer);// accumulate over frames
            if (breakingTimer >= neededtime) {
                theDebris.doBreak();
                breakingTimer = 0f;
                theDebris.setTextureTimer(breakingTimer);
                if (theDebris.getX() == map.exit.getX() && theDebris.getY() == map.exit.getY())map.exit.Expose(); // reset after breaking
            }
        } else {
            breakingTimer = 0f;
            theDebris.setTextureTimer(breakingTimer); // reset if key released
        }
    }

       if (facingTile.getFacingObject() instanceof Animal theAnimal  && !theAnimal.isDead()){   
        if (Gdx.input.isKeyPressed(Input.Keys.S)){SoundFX.BLOOD.play(); theAnimal.setDead();}}
     

        if (StafffacingTile.getFacingObject() instanceof Animal theAnimal
        && hasStaff
        && staffatkcounter > 0
        && !theAnimal.isDead()) {

    if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {

        int x = StafffacingTile.getX();
        int y = StafffacingTile.getY();

        map.isOnLightning[x][y] = true;
        map.lightningTimer[x][y] = 0f;

        SoundFX.BLOOD.play();
        theAnimal.setDead();

        staffatkcounter--;
        setHasStaff(false);
    }
}

        

    //fertlizer pickup if standing on top of it

    if (standingTile.getFacingObject() instanceof Tool theTool && !theTool.isBroken()){
        theTool.use();
    }
    //Felix: planting crops
    if (isApressed && facingTile.getFacingObject() instanceof Soil theSoil && getCropcooldown() > 5 ) {
        theSoil.plantSeed();
        if(theSoil.getCrop() != Crop.SOIL_PATH) {
        setCropcooldown(0);}
        }

    //harvesting crops: Franzl
    if (isApressed && facingTile.getFacingObject() instanceof Soil theSoil && theSoil.getCrop()==Crop.MATURE_CROPS){
        theSoil.doBreak();
        //increase the crop count
        victorypoints++;
    }

   

    if ((map.isOnFire[standingTile.getX()][standingTile.getY()] == true ||map.isOnFire2[standingTile.getX()][standingTile.getY()] == true) && firePotionTimer>=15){
        receiveDamage();
    }

    
}   

    public PlayerDirection getDirection() {
        return direction;
    }

    public void receiveDamage(){
        if(getHpcooldown()>3){
            SoundFX.PAIN.play();
            playerhp--;
            setHpcooldown(0);
        }
    }

    //Implement HP System for the player
    public boolean playerdead() {
        if(playerhp > 0) {
            return false;
        }
        else return true;
    }
    
    @Override
public TextureRegion getCurrentAppearance() {

    if (direction == PlayerDirection.UP) {
        if (isDorSpressed()){
            return Animations.CHARACTER_HIT_UP.getKeyFrame(elapsedTime, true);}
        return Animations.CHARACTER_WALK_UP.getKeyFrame(elapsedTime, true);
    }

    if (direction == PlayerDirection.LEFT) {
        if (isDorSpressed()){
            return Animations.CHARACTER_HIT_LEFT.getKeyFrame(elapsedTime, true);}
        return Animations.CHARACTER_WALK_LEFT.getKeyFrame(elapsedTime, true);
    }

    if (direction == PlayerDirection.RIGHT) {
        if (isDorSpressed()){
            return Animations.CHARACTER_HIT_RIGHT.getKeyFrame(elapsedTime, true);}
        return Animations.CHARACTER_WALK_RIGHT.getKeyFrame(elapsedTime, true);
    }

    if (isDorSpressed()){
        return Animations.CHARACTER_HIT_DOWN.getKeyFrame(elapsedTime, true);}
    return Animations.CHARACTER_WALK_DOWN.getKeyFrame(elapsedTime, true);
}


public boolean isDorSpressed(){
    if(( Gdx.input.isKeyPressed(Input.Keys.D) && facingTile.getFacingObject() instanceof Debris theDebris && !theDebris.isBroken)) SoundFX.PICKAXE.play(); if (Gdx.input.isKeyPressed(Input.Keys.S)) SoundFX.SWOOSH.play();
    return (( Gdx.input.isKeyPressed(Input.Keys.D) && facingTile.getFacingObject() instanceof Debris theDebris && !theDebris.isBroken)|| Gdx.input.isKeyPressed(Input.Keys.S));
}

public boolean isQpressed(){
    return Gdx.input.isKeyPressed(Input.Keys.Q);
}
    
    @Override
    public float getX() {
        // The x-coordinate of the player is the x-coordinate of the hitbox (this can change every frame).
        return hitbox.getPosition().x;
    }
    
    @Override
    public float getY() {
        // The y-coordinate of the player is the y-coordinate of the hitbox (this can change every frame).
        return hitbox.getPosition().y;
    }

    public Tile getStandingTile() {
        return standingTile;
    }

    public float getCropcooldown() {
        return cropcooldown;
    }

    public void setCropcooldown(float cropcooldown) {
        this.cropcooldown = cropcooldown;
    }

    public int getVictorypoints() {
        return victorypoints;
    }

    public void setVictorypoints(int victorypoints) {
        this.victorypoints = victorypoints;
    }

    public int getPlayerhp() {
        return playerhp;
    }

    public void setPlayerhp(int playerhp) {
        this.playerhp = playerhp;
    }

    public float getHpcooldown() {
        return hpcooldown;
    }

    public void setHpcooldown(float hpcooldown) {
        this.hpcooldown = hpcooldown;
    }    

    public void getFireResistance(){
        firePotionTimer = 0;
    }

    public float getFirePotionTimer() {
        return firePotionTimer;
    }

    public void getInvisibility(){
        invisibleTimer = 0;
    }

    public float getInvisibleTimer() {
        return invisibleTimer;
    }

    public void setInvisibleTimer(float invisibleTimer) {
        this.invisibleTimer = invisibleTimer;
    }

    public static float getFIREPOTION_LENGTH() {
        return FIREPOTION_LENGTH;
    }

    public static void setFIREPOTION_LENGTH(float fIREPOTION_LENGTH) {
        FIREPOTION_LENGTH = fIREPOTION_LENGTH;
    }

    public static float getINVISIBILITY_LENGTH() {
        return INVISIBILITY_LENGTH;
    }

    public static void setINVISIBILITY_LENGTH(float iNVISIBILITY_LENGTH) {
        INVISIBILITY_LENGTH = iNVISIBILITY_LENGTH;
    }

    
    public void getstaff() {
        hasStaff = true;
    }

    public boolean getHasStaff() {
        return hasStaff;
    }

    public void setHasStaff(boolean hasStaff) {
        this.hasStaff = hasStaff;
    }

    public int getStaffatkcounter() {
        return staffatkcounter;
    }

    public void setStaffatkcounter(int staffatkcounter) {
        this.staffatkcounter = staffatkcounter;
    }
    
    
}
