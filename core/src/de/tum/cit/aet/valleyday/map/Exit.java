package de.tum.cit.aet.valleyday.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.physics.box2d.World;

import de.tum.cit.aet.valleyday.texture.Textures;

public class Exit extends BreakableObstacle {
    private boolean isExposed;

    
    //if an exit "isBroken" then it is open, aka the player can exit the map

    public Exit(World world, int x, int y, GameMap map) {
        super(world, x, y, map);
        isExposed = false;
    }

    public void Expose(){
        isExposed = true;
    }

    @Override
    public void doBreak() {
        isBroken = true;
    }

    @Override
    public TextureRegion getCurrentAppearance() {
        if (isExposed == true) return Textures.EXIT;
        return Textures.TRANSPARENT;
    }

    @Override
    public void createHitbox(World world) {
  
}



    public boolean isExposed() {
        return isExposed;
    }


}