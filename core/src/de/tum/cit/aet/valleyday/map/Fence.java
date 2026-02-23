package de.tum.cit.aet.valleyday.map;
//Felix: I created that :D 

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import de.tum.cit.aet.valleyday.texture.Textures;

public class Fence extends Obstacle{

    private FenceType type;

    public Fence(World world, int x, int y, FenceType type) {
        super(x, y);
        this.type = type;
        createHitbox(world);
    }

    public void createHitbox(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(getX(), getY());

        Body body = world.createBody(bodyDef);

        PolygonShape box = new PolygonShape();
        box.setAsBox(0.5f, 0.5f);
        body.createFixture(box, 1.0f);
        box.dispose();

        body.setUserData(this);
    }

    //Felix: tempered with it, need to fix it.
    @Override
    public TextureRegion getCurrentAppearance() {
        return switch (type) {

            case HORIZONTAL     -> Textures.FENCE_HORIZONTAL;
            case VERTICAL       -> Textures.FENCE_VERTICAL;
            case TOP_LEFT       -> Textures.FENCE_TOPLEFTCORNER;
            case TOP_RIGHT      -> Textures.FENCE_TOPRIGHTCORNER;
            case BOTTOM_LEFT    -> Textures.FENCE_BOTTOMLEFTCORNER;
            case BOTTOM_RIGHT   -> Textures.FENCE_BOTTOMRIGHTCORNER;
            case INNERFENCE     -> Textures.FENCE_INNER;
        }; }

    public FenceType getType() {
        return type;
    }

    public void setType(FenceType type) {
        this.type = type;
    }  


}