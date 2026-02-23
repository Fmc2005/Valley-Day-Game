package de.tum.cit.aet.valleyday.map;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import de.tum.cit.aet.valleyday.texture.Animations;

//bulls will pathfind towards the player if they are within a 5 tile radius

public class Bull extends Animal{
    
    public Bull(World world, float x, float y, GameMap map) {
        super(world, x, y, map);
        setHarmful(true);
        
    }

     @Override
    public TextureRegion getCurrentAppearance() {
        // Get the frame of the walk down animation that corresponds to the current time.
        if (getDirection() == PlayerDirection.UP)
        return Animations.BULL_WALKUP.getKeyFrame(getElapsedTime(), true);
        if (getDirection() == PlayerDirection.LEFT)
        return Animations.BULL_WALKLEFT.getKeyFrame(getElapsedTime(), true);
        if (getDirection() == PlayerDirection.RIGHT)
        return Animations.BULL_WALK_RIGHT.getKeyFrame(getElapsedTime(), true);
        return Animations.BULL_WALKDOWN.getKeyFrame(getElapsedTime(), true);
        
    }  

    @Override
    public void attack(){
        getHitbox().setLinearVelocity(new Vector2(getMap().getPlayer().getX()-getX(),getMap().getPlayer().getY()-getY()).setLength2(speed*6));
    }

    public boolean isAggro(){
        if (getMap().player.isInvisible()) return false;
        PlayerDirection direction = PlayerDirection.UP;
        double dx = getMap().player.getX()-getX();
        double dy = getMap().player.getY()-getY();
        
        boolean xBiggerABS = (Math.abs(dx)>Math.abs(dy));
        if (xBiggerABS) {
            direction = (dx>0) ? PlayerDirection.RIGHT : PlayerDirection.LEFT;
        }
        if (!xBiggerABS) {
            direction = (dy>0) ? PlayerDirection.UP : PlayerDirection.DOWN;
        }
        setDirection(direction);
        return (Math.sqrt(dx*dx+dy*dy)<=5);

    }



    //when bull ticks, if bull changes tile, it should run pathfinding

} 