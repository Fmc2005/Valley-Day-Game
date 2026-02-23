package de.tum.cit.aet.valleyday.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import de.tum.cit.aet.valleyday.texture.Drawable;


//Group work
public abstract class Obstacle implements Drawable  {

// We would normally get the position from the hitbox, but since we don't need to move the chest, we can store the position directly.
    private final float x;
    private final float y;
    protected Body body;
    /**
     * Create a chest at the given position.
     * @param world The Box2D world to add the chest's hitbox to.
     * @param x The X position.
     * @param y The Y position.
     */
    public Obstacle(float x, float y) {
        this.x = x;
        this.y = y;
        // Since the hitbox never moves, and we never need to change it, we don't need to store a reference to it.
    }
    



    public abstract TextureRegion getCurrentAppearance();
    public abstract void createHitbox(World world);
    public Body getBody() {
        return body;
    }
    @Override
    public float getX() {
        return x;
    }
    
    @Override
    public float getY() {
        return y;
    }
}
