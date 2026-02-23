package de.tum.cit.aet.valleyday.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.physics.box2d.World;


import de.tum.cit.aet.valleyday.texture.Textures;



public class Fertilizer extends Tool {
    public Fertilizer (World world, int x, int y, GameMap map){
        super(world, x ,y, map);     
    }

    
 
    
    @Override
    public TextureRegion getCurrentAppearance(){
        if (isBroken() || !isExposed()) return Textures.TRANSPARENT;
        return Textures.FERTILIZER;
    }

    public void applyToolEffect(){
        for (int i= 0; i<map.MAP_WIDTH; i++){
            for (int j = 0; j<map.MAP_HEIGHT; j++){
                if (map.mapAsMatrix[i][j] instanceof Soil theSoil) theSoil.grow();
            }
        }
    }



 
    
}
