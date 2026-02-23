package de.tum.cit.aet.valleyday.map;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;

import de.tum.cit.aet.valleyday.texture.Drawable;

/**
 * Represents the player character in the game.
 * The player has a hitbox, so it can collide with other objects in the game.
 */
public abstract class Animal implements Drawable {    
    



    public float getElapsedTime() {
		return elapsedTime;
	}
    private GameMap map;
    private Tile standingTile;
    public float elapsedTime;
    private PlayerDirection direction;
    public static float movementConstantFREQUENCY;
    public static float movementConstantLENGTH;
    public static float speed;
    private boolean harmful;
    private boolean isDead;
    public boolean isMoving;

    
    
    /** The Box2D hitbox of the player, used for position and collision detection. */
    protected final Body hitbox;
    
    public Animal(World world, float x, float y, GameMap map) {
        this.hitbox = createHitbox(world, x, y);
        this.map = map;
        standingTile = new Tile(Math.round(x), Math.round(y), map);
        direction = PlayerDirection.UP;  
        movementConstantFREQUENCY = 1;
        movementConstantLENGTH =1;
        speed =1;
        harmful = false;
        isDead = false;
        isMoving = true;
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
        
        circle.setRadius(0.4f);
        // Attach the shape to the body as a fixture.
        // Bodies can have multiple fixtures, but we only need one for the player.
        body.createFixture(circle, 1.0f);
        // We're done with the shape, so we should dispose of it to free up memory.
        circle.dispose();
        // Set the player as the user data of the body so we can look up the player from the body later.
        body.setUserData(this);
        return body;
    }
    
    public abstract void attack();

    public void setDead(){
        isDead = true;
        getMap().getWorld().destroyBody(hitbox);
    }
    
    public boolean isDead() {
        return isDead;
    }

    public void tick(float frameTime){
        elapsedTime += frameTime; 
        if (isDead){
            return;
        }
        standingTile.setX(Math.round(getX()));
        standingTile.setY(Math.round(getY()));
        if (isAggro()){attack();}
        else { 
            if (elapsedTime >=movementConstantFREQUENCY){
            elapsedTime = 0;
            direction = normalPathfind(); 
        }
        move();

        Soil theSoil = map.soils[Math.round(getX())][Math.round(getY())];
            if (theSoil.getCrop() == Crop.SEEDS || theSoil.getCrop() == Crop.SPROUTS || theSoil.getCrop() == Crop.MATURE_CROPS || theSoil.getCrop() == Crop.ROTTEN_CROPS){
                theSoil.setCrop(Crop.EMPTY_SOIL);
            }
        }
    } 

    public void move(){
        if (isMoving) {        
        if (direction == PlayerDirection.RIGHT) this.hitbox.setLinearVelocity(+speed, 0);
        if (direction == PlayerDirection.LEFT) this.hitbox.setLinearVelocity(-speed, 0);
        if (direction == PlayerDirection.UP) this.hitbox.setLinearVelocity(0, +speed);
        if (direction == PlayerDirection.DOWN) this.hitbox.setLinearVelocity(0, -speed);
        
    }
else this.hitbox.setLinearVelocity(0, 0);
}
    
    @Override
    public abstract TextureRegion getCurrentAppearance();


    
    
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

    public GameMap getMap() {
        return map;
    }

    public Tile getStandingTile() {
        return standingTile;
    }

    public void setMap(GameMap map) {
        this.map = map;
    }

    public void setStandingTile(Tile standingTile) {
        this.standingTile = standingTile;
    }

    public void setElapsedTime(float elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

        public PlayerDirection normalPathfind(){
    int i = (int) (Math.random()*4);
            if (i ==0) return PlayerDirection.LEFT;
            if (i == 1) return PlayerDirection.RIGHT;
            if (i== 2) return PlayerDirection.UP;
            return PlayerDirection.DOWN;
}


    public PlayerDirection getDirection() {
        return direction;
    }

    public abstract boolean isAggro();

    public void setDirection(PlayerDirection direction) {
        this.direction = direction;
    }

    public Body getHitbox() {
        return hitbox;
    }

    public boolean isHarmful() {
        return harmful;
    }

    public void setHarmful(boolean harmful) {
        this.harmful = harmful;
    }
    
}
