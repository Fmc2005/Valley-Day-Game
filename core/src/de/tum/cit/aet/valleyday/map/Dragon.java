package de.tum.cit.aet.valleyday.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;

import de.tum.cit.aet.valleyday.audio.SoundFX;
import de.tum.cit.aet.valleyday.texture.Animations;

public class Dragon extends Animal {

    private float attacktimer = 0;
    private static final float AGGRO_LIMIT = 1;
    private float fireTimer = 0;
    private float attackcooldown = 0;

    public Dragon(World world, float x, float y, GameMap map) {
        super(world, x, y, map);
        movementConstantFREQUENCY = 4;
        movementConstantLENGTH = 2;
        speed = 0.5f;
        setHarmful(true);
    }

    @Override
    public void attack() {
        if (attackcooldown >= 3) {
            attackcooldown = 0;
            fireTimer = 0;
            SoundFX.DRAGONFIRE.play();

            int x = getStandingTile().getX();
            int y = getStandingTile().getY();

            if (getDirection() == PlayerDirection.UP) {
                for (int i = y + 1; i <= y + 5; i++) {
                    if (i >= 0 && i < getMap().MAP_HEIGHT) {
                        if (getMap().mapAsMatrix[x][i] instanceof Debris) break;
                        getMap().isOnFire[x][i] = true;
                    }
                }
            }

            if (getDirection() == PlayerDirection.DOWN) {
                for (int i = y - 1; i >= y - 5; i--) {
                    if (i >= 0 && i < getMap().MAP_HEIGHT) {
                        if (getMap().mapAsMatrix[x][i] instanceof Debris) break;
                        getMap().isOnFire[x][i] = true;
                    }
                }
            }

            if (getDirection() == PlayerDirection.RIGHT) {
                for (int i = x + 1; i <= x + 5; i++) {
                    if (i >= 0 && i < getMap().MAP_WIDTH) {
                        if (getMap().mapAsMatrix[i][y] instanceof Debris) break;
                        getMap().isOnFire[i][y] = true;
                    }
                }
            }

            if (getDirection() == PlayerDirection.LEFT) {
                for (int i = x - 1; i >= x - 5; i--) {
                    if (i >= 0 && i < getMap().MAP_WIDTH) {
                        if (getMap().mapAsMatrix[i][y] instanceof Debris) break;
                        getMap().isOnFire[i][y] = true;
                    }
                }
            }
        }
    }

    public void tick(float frameTime) {
        getStandingTile().setX(Math.round(getX()+0.2f));
        getStandingTile().setY((int)(getY()));

        fireTimer += frameTime;
        attackcooldown += frameTime;

        if (fireTimer >= 1f)
            getMap().isOnFire = new boolean[getMap().MAP_WIDTH][getMap().MAP_HEIGHT];

        // =====================
        // ATTACK STATE
        // =====================
        if (isAggro()) {
            attacktimer += frameTime;

            isMoving = false;

            // HARD STOP physics body
            hitbox.setLinearVelocity(0, 0);

            if (attacktimer >= AGGRO_LIMIT) {
                attacktimer = 0;
                attack();
            }

            return; // <<< DO NOT PROCESS MOVEMENT LOGIC
        }

        // =====================
        // WALKING STATE
        // =====================
        elapsedTime += frameTime;
        attacktimer = 0;

        if (isMoving && elapsedTime >= movementConstantLENGTH) {
            elapsedTime = 0;
            isMoving = false;
        } else if (!isMoving && elapsedTime >= movementConstantFREQUENCY) {
            elapsedTime = 0;
            isMoving = true;
            setDirection(normalPathfind());
        }

        Soil theSoil = getMap().soils[Math.round(getX())][Math.round(getY())];
        if (theSoil.getCrop() == Crop.SEEDS || theSoil.getCrop() == Crop.SPROUTS
                || theSoil.getCrop() == Crop.MATURE_CROPS || theSoil.getCrop() == Crop.ROTTEN_CROPS) {
            theSoil.setCrop(Crop.EMPTY_SOIL);
        }

        move();
    }

    @Override
    public TextureRegion getCurrentAppearance() {
        if (getDirection() == PlayerDirection.UP)
            return Animations.DRAGON_WALKUP.getKeyFrame(getElapsedTime(), true);
        if (getDirection() == PlayerDirection.LEFT)
            return Animations.DRAGON_WALKLEFT.getKeyFrame(getElapsedTime(), true);
        if (getDirection() == PlayerDirection.RIGHT)
            return Animations.DRAGON_WALK_RIGHT.getKeyFrame(getElapsedTime(), true);
        return Animations.DRAGON_WALKDOWN.getKeyFrame(getElapsedTime(), true);
    }

    @Override
    public boolean isAggro() {
        if (getMap().player.isInvisible()) return false;

        int dx = getMap().getPlayer().getStandingTile().getX() - Math.round(getX());
        int dy = getMap().getPlayer().getStandingTile().getY() - Math.round(getY());

        boolean radius = (Math.abs(dx) + Math.abs(dy) <= 5);

        if (dx == 0 && radius) {
            setDirection(dy >= 0 ? PlayerDirection.UP : PlayerDirection.DOWN);
            return true;
        }

        if (dy == 0 && radius) {
            setDirection(dx >= 0 ? PlayerDirection.RIGHT : PlayerDirection.LEFT);
            return true;
        }

        return false;
    }

    @Override
    public void setDead() {
        super.setDead();
        getMap().isOnFire = new boolean[getMap().MAP_WIDTH][getMap().MAP_HEIGHT];
    }
}
