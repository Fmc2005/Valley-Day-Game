package de.tum.cit.aet.valleyday.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;

import de.tum.cit.aet.valleyday.texture.Textures;

public class FireResistancePotion extends Tool {


    
    public FireResistancePotion(World world, int x, int y, GameMap map) {
        super(world, x, y, map);
    }

    @Override
    public TextureRegion getCurrentAppearance() {
        if (isBroken || !isExposed()) return Textures.TRANSPARENT;
        return Textures.FIRE_POTION;
    }

    @Override
    void applyToolEffect() {
        map.getPlayer().getFireResistance();
    }
    

}
