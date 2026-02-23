package de.tum.cit.aet.valleyday.map;



import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.physics.box2d.World;

import de.tum.cit.aet.valleyday.audio.SoundFX;
import de.tum.cit.aet.valleyday.texture.Animations;


public class Balrog extends Animal {


   public void setFireTimer1(float fireTimer1) {
        this.fireTimer1 = fireTimer1;
    }



    private float attacktimer = 0;
    private float fireTimer1;
    public Balrog(World world, float x, float y, GameMap map) {
        super(world, x, y, map);
        movementConstantFREQUENCY = 4;
        movementConstantLENGTH = 2;
        speed = 0.5f;
        fireTimer1 = 0;
    }




@Override
public void attack() {
        setFireTimer1(0);

        int x = getStandingTile().getX();
        int y = getStandingTile().getY();

        for (int i = -2; i <= 2; i++) {
            for (int j = -2; j <= 2; j++) {
                int fx = x + i;
                int fy = y + j;
                if ((fx<getMap().MAP_WIDTH-1) && (fx >0)&& (fy<getMap().MAP_HEIGHT-1) && (fy >0) && !(getMap().mapAsMatrix[fx][fy] instanceof Debris))
                getMap().isOnFire2[fx][fy] = true;
            }
        
    } 
        SoundFX.DRAGONFIRE.play();
    
}



@Override
public void tick(float frameTime) {
    getStandingTile().setX(Math.round(getX()));
    getStandingTile().setY(Math.round(getY()));
    attacktimer += frameTime;
    fireTimer1 += frameTime;
    if (fireTimer1>=2) {
        
        getMap().isOnFire2 = new boolean[getMap().MAP_WIDTH][getMap().MAP_HEIGHT];}

        if (attacktimer >= 3) {
            attacktimer = 0;
            attack();
        }

        elapsedTime += frameTime;

        if (isMoving && elapsedTime >= movementConstantLENGTH) {
            elapsedTime = 0;
            isMoving = false;
        } else if (!isMoving && elapsedTime >= movementConstantFREQUENCY) {
            elapsedTime = 0;
            isMoving = true;
            setDirection(normalPathfind());
        }
        Soil theSoil = getMap().soils[Math.round(getX())][Math.round(getY())];
            if (theSoil.getCrop() == Crop.SEEDS || theSoil.getCrop() == Crop.SPROUTS || theSoil.getCrop() == Crop.MATURE_CROPS || theSoil.getCrop() == Crop.ROTTEN_CROPS){
                theSoil.setCrop(Crop.EMPTY_SOIL);
            }
        move();
    }



    @Override
    public TextureRegion getCurrentAppearance() {
        // Get the frame of the walk down animation that corresponds to the current time.
        if (getDirection() == PlayerDirection.UP)
        return Animations.BALROG_WALKUP.getKeyFrame(getElapsedTime(), true);
        if (getDirection() == PlayerDirection.LEFT)
        return Animations.BALROG_WALKLEFT.getKeyFrame(getElapsedTime(), true);
        if (getDirection() == PlayerDirection.RIGHT)
        return Animations.BALROG_WALK_RIGHT.getKeyFrame(getElapsedTime(), true);
        return Animations.BALROG_WALKDOWN.getKeyFrame(getElapsedTime(), true);
        
    }   

    @Override
    public boolean isAggro() {
        return true;
    }

    @Override
        public void setDead(){
        super.setDead();
        getMap().isOnFire2 = new boolean[getMap().MAP_WIDTH][getMap().MAP_HEIGHT];
    }
}

