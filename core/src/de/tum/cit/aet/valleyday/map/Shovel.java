package de.tum.cit.aet.valleyday.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;

import de.tum.cit.aet.valleyday.texture.Textures;

public class Shovel extends Tool {

    public Shovel(World world, int x, int y, GameMap map) {
        super(world, x, y, map);
    }
    
    @Override 
    public void applyToolEffect(){
    map.getPlayer().setHasShovel(true);
    }

    public TextureRegion getCurrentAppearance(){
        if (isBroken() || !isExposed()) return Textures.TRANSPARENT;
        return Textures.SHOVEL;
    }
}
