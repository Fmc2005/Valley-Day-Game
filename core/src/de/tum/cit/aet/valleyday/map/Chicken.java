package de.tum.cit.aet.valleyday.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;

import de.tum.cit.aet.valleyday.texture.Animations;



//pig is the most generic animal. random movement, no habilities
public class Chicken extends Animal{

    public Chicken(World world, float x, float y, GameMap map) {
        super(world, x, y, map);
        setHarmful(true);
    }
   

    
    

    @Override
    public TextureRegion getCurrentAppearance() {
        // Get the frame of the walk down animation that corresponds to the current time.
        if (getDirection() == PlayerDirection.UP)
        return Animations.CHICKEN_WALKUP.getKeyFrame(getElapsedTime(), true);
        if (getDirection() == PlayerDirection.LEFT)
        return Animations.CHICKEN_WALKLEFT.getKeyFrame(getElapsedTime(), true);
        if (getDirection() == PlayerDirection.RIGHT)
        return Animations.CHICKEN_WALK_RIGHT.getKeyFrame(getElapsedTime(), true);
        return Animations.CHICKEN_WALKDOWN.getKeyFrame(getElapsedTime(), true);
        
    }   


    @Override public void attack(){}

    public boolean isAggro(){return false;}
}



