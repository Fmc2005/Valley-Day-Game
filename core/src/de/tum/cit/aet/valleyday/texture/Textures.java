package de.tum.cit.aet.valleyday.texture;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.Gdx;

/**
 * Contains all texture constants used in the game.
 * It is good practice to keep all textures and animations in constants to avoid loading them multiple times.
 * These can be referenced anywhere they are needed.
 */
public class Textures {
    
    public static final TextureRegion FLOWERS = SpriteSheet.BASIC_TILES.at(2, 5);
    public static final TextureRegion FACING_TILE = SpriteSheet.BASIC_TILES.at(2, 2);
    public static final TextureRegion CHEST = SpriteSheet.BASIC_TILES.at(5, 5);

     //exit:
    public static final TextureRegion EXIT = SpriteSheet.BASIC_TILES.at(8, 2); 
    
    

    //Felix: Fence graphics, just an example the row and column are  wrong
    public static final TextureRegion FENCE_VERTICAL  = SpriteSheet.FARMTHINGS.at(7, 1);
    public static final TextureRegion FENCE_HORIZONTAL  = SpriteSheet.FARMTHINGS.at(8, 2);
    public static final TextureRegion FENCE_TOPLEFTCORNER  = SpriteSheet.FARMTHINGS.at(6, 1);
    public static final TextureRegion FENCE_TOPRIGHTCORNER  = SpriteSheet.FARMTHINGS.at(6, 3);
    public static final TextureRegion FENCE_BOTTOMLEFTCORNER  = SpriteSheet.FARMTHINGS.at(8, 1);
    public static final TextureRegion FENCE_BOTTOMRIGHTCORNER  = SpriteSheet.FARMTHINGS.at(8, 3);
    public static final TextureRegion FENCE_INNER = SpriteSheet.BASIC_TILES.at(5, 8);
     //Chicken textures
    public static final TextureRegion CHICKEN  = SpriteSheet.FARMTHINGS.at(1, 1);
    
   
    
    //PP: object
   // public static final Texture FINILAY = new Texture(Gdx.files.internal("texture/finishedlayout.png"));
  //  public static final TextureRegion OBJECT_DEBRIS1 = new TextureRegion(FINILAY,512-386,512-477 ,28,28);
  //  public static final TextureRegion OBJECT_DEBRIS2 = new TextureRegion(FINILAY,512-480,512-505 ,24,26);
  //  public static final TextureRegion OBJECT_DEBRIS3 = new TextureRegion(FINILAY,512-416,512-505 ,24,26);
    public static final TextureRegion OBJECT_DEBRIS1  = SpriteSheet.FLOORTILES.at(1, 1);
    public static final TextureRegion OBJECT_DEBRIS2  = SpriteSheet.FLOORTILES.at(1, 3);
 //  public static final TextureRegion OBJECT_DEBRIS3  = SpriteSheet.FLOORTILES.at(1, 3);

   
    public static final TextureRegion FIRE  = SpriteSheet.FARMTHINGS.at(6, 7);


    //soils
     public static final TextureRegion SOIL_EMPTY = SpriteSheet.BASIC_TILES.at(9, 2);
   // public static final TextureRegion SOIL_SEEDS = SpriteSheet.FINISHEDLAYOUT.at(4, 1);
   // public static final TextureRegion SOIL_SPROUTS = SpriteSheet.CROPS.at(2, 4);
    // public static final TextureRegion SOIL_MATURE_CROPS = SpriteSheet.CROPS.at(2, 4);
    
    public static final TextureRegion PATH = SpriteSheet.BASIC_TILES.at(10, 2); 

    //red fire
   /**  public static final Texture EXPLOSION = new Texture(Gdx.files.internal("texture/explosion.png"));
    public static final TextureRegion RED_FIRE = new TextureRegion(EXPLOSION,192-96,0 ,32,32);*/
    public static final TextureRegion RED_FIRE  = SpriteSheet.EXPLOSION.at(1, 4);

    //Menus:
    //normal
    public static final Texture RUNNINGSCREEN =
            new Texture(Gdx.files.internal("texture/housecave.png"));
    public static final Texture WINNINGSCREEN =
            new Texture(Gdx.files.internal("texture/minastirith.png"));
    public static final Texture LOSINGSCREEN =
            new Texture(Gdx.files.internal("texture/mordor.png"));

    static {
        RUNNINGSCREEN.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        WINNINGSCREEN.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        LOSINGSCREEN.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
    }

    //entrance:
  //   public static final Texture ENTRANCEFILE = new Texture(Gdx.files.internal("texture/entrance.png"));
    //public static final TextureRegion ENTRANCE = new TextureRegion(ENTRANCEFILE,1536-1025,1024-779 ,505,539);
    public static final TextureRegion ENTRANCE  = SpriteSheet.BASIC_TILES.at(7, 3);
    //Soils
    public static final Texture SOIL_SEED_T = new Texture(Gdx.files.internal("texture/crops.png"));
    public static final TextureRegion SOIL_SEEDS = new TextureRegion(SOIL_SEED_T,518,363 ,20,20);
    public static final TextureRegion SOIL_SPROUTS = new TextureRegion(SOIL_SEED_T,575, 79 ,32,48);
    public static final TextureRegion SOIL_MATURE_CROPS = new TextureRegion(SOIL_SEED_T,607,216 ,33,39);
    public static final Texture SOIL_ROTTENa = new Texture(Gdx.files.internal("texture/Rottencrops.png"));
    public static final TextureRegion SOIL_ROTTEN = new TextureRegion(SOIL_ROTTENa, 1024-932,1536-1454,844,1222);
    //Watering Can
    public static final Texture BASICS = new Texture(Gdx.files.internal("texture/basics.png"));
    public static final TextureRegion WATERING_CAN = new TextureRegion(BASICS,303-113,132-109 ,20,16);

    //Fertilizer
    public static final TextureRegion FERTILIZER  = SpriteSheet.HARVEST.at(1, 6);

    //Shovel:
    public static final TextureRegion SHOVEL = new TextureRegion(BASICS,303-206,132-95,14,20);

    //Hearts:
    public static final TextureRegion HEART = SpriteSheet.OBJECTS.at(1, 5);

    public static final TextureRegion TILE_HIGHLIGHT = createHighlight();
    public static final TextureRegion TILE_HIGHLIGHT_STAFF = createHighlightforstaff();

    //Potion
     public static final TextureRegion FIRE_POTION = SpriteSheet.ROGUEITEMS.at(5, 12);

     //Ring:
    public static final TextureRegion RING = SpriteSheet.ROGUEITEMS.at(3, 7);
   
    //Staff
    public static final TextureRegion STAFF = SpriteSheet.ROGUEITEMS.at(12, 5);
    //Dragonfire
     public static final TextureRegion DRAGONFIRE = SpriteSheet.BLUEFIRE.at(1, 1);
    private static TextureRegion createHighlight() {
        int size = 16;
        Pixmap pm = new Pixmap(size, size, Pixmap.Format.RGBA8888);

        pm.setColor(1, 1, 1, 0.8f);
        pm.drawRectangle(0, 0, size, size);

        Texture texture = new Texture(pm);
        pm.dispose();

        return new TextureRegion(texture);
    }
    private static TextureRegion createHighlightforstaff() {
        int size = 16;
        Pixmap pm = new Pixmap(size, size, Pixmap.Format.RGBA8888);

        pm.setColor(0.6f, 0f, 0.6f, 0.8f);
        pm.drawRectangle(0, 0, size, size);

        Texture texture = new Texture(pm);
        pm.dispose();

        return new TextureRegion(texture);
    }

    public static final TextureRegion TILE_GRID = createGrid();

    private static TextureRegion createGrid(){
        int size = 16;
        Pixmap pm = new Pixmap(size, size, Pixmap.Format.RGBA8888);
        pm.setColor(0, 0, 0, 0.2f);
        pm.drawRectangle(0, 0, size, size);

        Texture texture = new Texture(pm);
        pm.dispose();

        return new TextureRegion(texture);
    }

    public static TextureRegion createTransparent(){
        int size = 16;
        Pixmap pm = new Pixmap(size, size, Pixmap.Format.RGBA8888);
        Texture texture = new Texture(pm);
        pm.dispose();

        return new TextureRegion(texture); 
    }

    public static final TextureRegion TRANSPARENT = createTransparent();
}

