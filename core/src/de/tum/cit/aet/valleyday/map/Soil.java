package de.tum.cit.aet.valleyday.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.physics.box2d.World;

import de.tum.cit.aet.valleyday.audio.SoundFX;
import de.tum.cit.aet.valleyday.texture.Textures;

public class Soil extends BreakableObstacle {
    private Crop crop;
    private float seedTimer;
    public Soil(World world, int x, int y, GameMap map, Crop crop) {
        super(world, x, y, map);
        double i = Math.random();
        if (i<0.2){
        this.crop = Crop.EMPTY_SOIL;
    }
        else this.crop = Crop.SOIL_PATH;
        isBroken = true;
    }

    public void plantSeed(){
        if (crop != Crop.EMPTY_SOIL) return;
        SoundFX.PLANTING.play();
        crop = Crop.SEEDS;
        isBroken = false;
        seedTimer = 0;
    }

    public void doWatering(){
        if (crop == Crop.ROTTEN_CROPS){
            crop = Crop.MATURE_CROPS;
            seedTimer =0;
        }
    }


    //harvesting 
    @Override 
    public void doBreak(){
        SoundFX.HARVESTING.play();
        isBroken =true;
        crop = Crop.COOLDOWN;  
        seedTimer = 0;
    }

    
    @Override
    public void createHitbox(World world) {

    }

    public TextureRegion getCurrentAppearance() {
        if (crop == Crop.SOIL_PATH) return Textures.PATH;
        if (crop == Crop.EMPTY_SOIL)return Textures.TRANSPARENT;
        if (crop == Crop.SEEDS) return Textures.SOIL_SEEDS;
        if (crop == Crop.SPROUTS) return Textures.SOIL_SPROUTS;
        if (crop == Crop.MATURE_CROPS) return Textures.SOIL_MATURE_CROPS;
        if (crop == Crop.COOLDOWN) return Textures.TRANSPARENT;
        else return Textures.SOIL_ROTTEN;
    }

    //Felix: plant Crops
   

    public void tick(float frameTime){      
        seedTimer += frameTime;
        
        if (crop == Crop.COOLDOWN){
            if (seedTimer >= 5){
                seedTimer = 0;
                crop = Crop.EMPTY_SOIL;
                return;
            }
        }
        if (crop == Crop.SEEDS){
            if (seedTimer >= 10){
                seedTimer = 0;
                crop = Crop.SPROUTS;
                return;
            }
        }
        if (crop == Crop.SPROUTS){
            if (seedTimer >=10){
                seedTimer = 0;
                crop = Crop.MATURE_CROPS;
                return;
            }
        }
        if (crop == Crop.MATURE_CROPS){
            if (seedTimer >55){
                double i = Math.random();
                if (i<0.0227||seedTimer >65) //Franz: make sure it breaks at 65 s max, but most will break somewhere in the window (99%) assuming 20fps
                crop = Crop.ROTTEN_CROPS;
                return;
            }
        }

    }

    public Crop getCrop() {
        return crop;
    }

    public void setCrop(Crop crop) {
        this.crop = crop;
    }
    
    public void grow() {
        if (crop == Crop.SEEDS){
                seedTimer = 0;
                crop = Crop.SPROUTS;
                return;
            }
        
        if (crop == Crop.SPROUTS){
            seedTimer = 0;
                crop = Crop.MATURE_CROPS;
                return;
            }
        }

    }




    


