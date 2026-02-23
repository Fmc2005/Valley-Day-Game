package de.tum.cit.aet.valleyday.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;


import de.tum.cit.aet.valleyday.audio.SoundFX;
import de.tum.cit.aet.valleyday.texture.Textures;

public class Debris extends BreakableObstacle {

    public float textureTimer;


    public Debris (World world, int x, int y, GameMap map){
        super(world, x ,y, map);
        textureTimer = 0;
    }
    
    public void tick(float frameTime){
        if (map.player.getFacingTile().getFacingObject() != this){textureTimer = 0;
        }

    }
    
    
   // private DebrisBreakingLevel level = DebrisBreakingLevel.LEVEL0;

    public float getTextureTimer() {
        return textureTimer;
    }



    public void createHitbox(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(getX(), getY());

        body = world.createBody(bodyDef);

        PolygonShape box = new PolygonShape();
        box.setAsBox(0.5f, 0.5f);
        body.createFixture(box, 1.0f);
        box.dispose();

        body.setUserData(this);
    }    

    public void doBreak(){
        if (isBroken()) return;
        setBroken(true);
        
        SoundFX.POP.play();
        for (Fixture fixture : getBody().getFixtureList()) {
            getBody().destroyFixture(fixture);
}
    }
    //test --> need to implement texture /breaking animation
    @Override
    public TextureRegion getCurrentAppearance(){
        if (isBroken()) return Textures.TRANSPARENT;
        if (textureTimer ==0) return Textures.OBJECT_DEBRIS1;
        return Textures.OBJECT_DEBRIS2;
        
        
    
    }

    public void setTextureTimer(float textureTimer) {
        this.textureTimer = textureTimer;
    }
    
}
