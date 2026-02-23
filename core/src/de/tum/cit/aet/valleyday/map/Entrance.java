package de.tum.cit.aet.valleyday.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;

import de.tum.cit.aet.valleyday.texture.Textures;

public class Entrance extends Obstacle {

    public Entrance(World world, float x, float y) {
        super(x, y);
        createHitbox(world);
    }

    @Override
    public TextureRegion getCurrentAppearance() {
        return Textures.ENTRANCE;
    }

    @Override
    public void createHitbox(World world) {

    }



    
}
