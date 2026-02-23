package de.tum.cit.aet.valleyday.map;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;


public abstract class BreakableObstacle extends Obstacle  {
    protected boolean isBroken;
    protected GameMap map;

    public BreakableObstacle (World world, int x, int y, GameMap map){
        super(x, y);
        this.map = map;
        createHitbox(world);
        isBroken = false;
    }

    public abstract void doBreak(); //breaks objects

    

    public boolean isBroken() {
        return isBroken;
    }

    public void setBroken(boolean isBroken) {
        this.isBroken = isBroken;
    }

    public GameMap getMap() {
        return map;
    }
    

    
}
