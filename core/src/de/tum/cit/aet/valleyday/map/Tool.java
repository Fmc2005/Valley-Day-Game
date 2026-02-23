package de.tum.cit.aet.valleyday.map;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import de.tum.cit.aet.valleyday.audio.SoundFX;


public abstract class Tool extends BreakableObstacle{

    public Tool (World world, int x, int y, GameMap map){
        super(world, x ,y, map);
    }

    public boolean isExposed(){
    return (map.debris[(int) getX()][(int) getY()]== null || map.debris[(int) getX()][(int) getY()].isBroken());
    }
    
    
    public void createHitbox(World world) {
    BodyDef bodyDef = new BodyDef();
    bodyDef.type = BodyDef.BodyType.StaticBody;
    bodyDef.position.set(getX(), getY());

    body = world.createBody(bodyDef);

    PolygonShape box = new PolygonShape();
    box.setAsBox(0.7f, 0.7f);

    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = box;
    fixtureDef.isSensor = true;   // walk-through
    fixtureDef.density = 1.0f;

    body.createFixture(fixtureDef);
    box.dispose();

    body.setUserData(this);
}
    public void use(){
        doBreak();
        applyToolEffect();
    }

    public void doBreak(){
        setBroken(true);
        SoundFX.POWERUPSFX.play();
}
    
    
    @Override
    public abstract TextureRegion getCurrentAppearance();

    abstract void applyToolEffect();


    
}
