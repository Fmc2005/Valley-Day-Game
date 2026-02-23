package de.tum.cit.aet.valleyday.map;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import de.tum.cit.aet.valleyday.ValleyDayGame;

import java.nio.file.WatchEvent;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;

//For Fences implementation
import java.util.Objects;
//Felix: implement Fertilizer

/**
 * Represents the game map.
 * Holds all the objects and entities in the game.
 */
public class GameMap implements ContactListener {
    public boolean[][] isOnFire;
    public boolean[][] isOnFire2;
    public boolean[][] isOnLightning;

    //Felix: map layout matrix
    public Soil[][] soils;
    public Object[][] mapAsMatrix;
    
   public float[][] lightningTimer;
   


    //Felix: countdown variable
    public static final float StartingTime = 200.0f;
    public static float RemainingTime; //in seconds
    public  boolean lost = false;
    public  boolean won = false;

    //Felix: fix map size 
    public int MAP_WIDTH = 20;
    public int MAP_HEIGHT =10;
     //public int neededVictoryPoints =  (MAP_HEIGHT * MAP_WIDTH)/20;
    public int neededVictoryPoints = 2;


    public ValleyDayGame getGame() {
        return game;
    }
    // A static block is executed once when the class is referenced for the first time.
    static {
        // Initialize the Box2D physics engine.
        com.badlogic.gdx.physics.box2d.Box2D.init();
    }
    
    // Box2D physics simulation parameters (you can experiment with these if you want, but they work well as they are)
    /**
     * The time step for the physics simulation.
     * This is the amount of time that the physics simulation advances by in each frame.
     * It is set to 1/refreshRate, where refreshRate is the refresh rate of the monitor, e.g., 1/60 for 60 Hz.
     */
    private static final float TIME_STEP = 1f / Gdx.graphics.getDisplayMode().refreshRate;
    /** The number of velocity iterations for the physics simulation. */
    private static final int VELOCITY_ITERATIONS = 6;
    /** The number of position iterations for the physics simulation. */
    private static final int POSITION_ITERATIONS = 2;
    /**
     * The accumulated time since the last physics step.
     * We use this to keep the physics simulation at a constant rate even if the frame rate is variable.
     */
    public float physicsTime = 0;

    
    /** The game, in case the map needs to access it. */
    private final ValleyDayGame game;
    /** The Box2D world for physics simulation. */
    private final World world;
    private Entrance entrance;
    
    
    // Game objects

    public World getWorld() {
        return world;
    }
    public final Player player;
    public final Animal[][] animals;
    public List<Animal> toKill = new ArrayList<>();

    //Felix: implement Fence
    public Fence[][] fences;

    //Franz: implement Debris
    public Debris[][] debris;
    public Tool[][] tools;
    public Exit exit;

        
    public GameMap(ValleyDayGame game) {
        int[][] map = loadFromFile(Gdx.files.internal("maps/map-1.properties"));
         MAP_WIDTH = map.length;
    MAP_HEIGHT= map[0].length;
    RemainingTime = MAP_WIDTH*MAP_HEIGHT;
    if (game.difficulty == 1) RemainingTime = RemainingTime/(3/2);
    if (game.difficulty == 2) RemainingTime = RemainingTime/(3/2);

    neededVictoryPoints = Math.max(1,MAP_WIDTH * MAP_HEIGHT/80);
    if (game.difficulty == 1) neededVictoryPoints = (int)(neededVictoryPoints*1.5f);
    if (game.difficulty == 2) neededVictoryPoints = neededVictoryPoints*2;

    
     lightningTimer = new float[MAP_WIDTH][MAP_HEIGHT];

    this.game = game;
        world = new World(Vector2.Zero, true);
        world.setContactListener(this);
        // Create a player with initial position (1, MAP_HEIGHT/3)
        //PP: initialize animal (just one for now)
        animals = new Animal[MAP_WIDTH][MAP_HEIGHT];
        // Create a chest in the middle of the map
        // Create flowers in a MAP_WIDTH x MAP_HEIGHT grid
        soils = new Soil[MAP_WIDTH][MAP_HEIGHT];

        mapAsMatrix = new Object[MAP_WIDTH][MAP_HEIGHT];

        for (int i = 0; i < soils.length; i++) {
            for (int j = 0; j < soils[i].length; j++) {
                this.soils[i][j] = new Soil(world, i, j, this, Crop.EMPTY_SOIL);
            }
        }
        fences = new Fence[MAP_WIDTH][MAP_HEIGHT];

        


    isOnFire = new boolean[MAP_WIDTH][MAP_HEIGHT];
    isOnFire2 = new boolean[MAP_WIDTH][MAP_HEIGHT];
    isOnLightning = new boolean[MAP_WIDTH][MAP_HEIGHT];

    //test debris (Franz)
    debris = new Debris[MAP_WIDTH][MAP_HEIGHT];

    //Felix: 
    tools = new Tool[MAP_WIDTH][MAP_HEIGHT];

    int counter = 0;

    for (int i = 0; i<MAP_WIDTH; i++){
        for (int j = 0; j<MAP_HEIGHT; j++){
            double a = Math.random();
            if (map[i][j] == 0) {
                fences[i][j] = new Fence(world, i, j, FenceType.INNERFENCE);}
            if (map[i][j] == 1) {
                debris[i][j] = new Debris(world, i, j, this);
                if (a<=0.05) tools[i][j] = new FireResistancePotion(world, i, j, this);
                if (a>=0.95) tools [i][j] = new InvisibilyRing(world, i, j, this);
                if (a>0.15 && a<0.2) tools[i][j] = new Staff(world, i, j, this);
            }
            if (map[i][j] == 2) entrance = new Entrance(world, i, j);
            if (map[i][j] == 3) animals[i][j] = createRandomAnimal(i, j);
            if (map[i][j] == 4) {
                counter ++;
                debris[i][j] = new Debris(world, i, j, this);
                this.exit = new Exit(world, i, j, this);
            }
            if (map[i][j] == 5) {
                debris[i][j] = new Debris(world, i, j, this);
                tools[i][j] = new Fertilizer(world, i, j, this);}
            
            if (map[i][j] == 6){
                tools[i][j] = new WateringCan(world, i, j, this);
                debris[i][j] = new Debris(world, i, j, this);
            }
            if (map[i][j] == 7){
                tools [i][j] = new Shovel(world, i, j, this);
                debris[i][j] = new Debris(world, i, j, this);
            }

            
    

    }

}
//Felix: bottom and top borders
for (int x = 1; x < MAP_WIDTH -1 ; x++) {
    fences[x][0].setType(FenceType.HORIZONTAL);
    fences[x][MAP_HEIGHT - 1].setType(FenceType.HORIZONTAL);
}
//Felix: left and right borders
for (int y = 1; y < MAP_HEIGHT -1; y++) {
    fences[0][y].setType(FenceType.VERTICAL);
    fences[MAP_WIDTH - 1][y].setType(FenceType.VERTICAL);
    }
//Felix: corners
    fences[0][MAP_HEIGHT-1].setType(FenceType.TOP_LEFT);
    fences[MAP_WIDTH -1][MAP_HEIGHT-1].setType(FenceType.TOP_RIGHT);
    fences[0][0].setType(FenceType.BOTTOM_LEFT);
    fences[MAP_WIDTH-1][0].setType(FenceType.BOTTOM_RIGHT);
   
//needs improvements...
            if (counter ==0){
                int i = (int)((Math.random()*MAP_WIDTH-3));
                int j = (int)((Math.random()*MAP_HEIGHT-3));
                while (map[i+1][j+1] == 0 ){
                i = (int)((Math.random()*MAP_WIDTH-3));
                j = (int)((Math.random()*MAP_HEIGHT-3));  
                if(i <0 || j <0){  
                i =0;
                j=0;
                }}
                debris[i+1][j+1] = new Debris(world, i+1, j+1, this);           
                exit = new Exit(world, i+1, j+1, this);
                
                }

            player = new Player(world, entrance.getX(), entrance.getY(), this);
            
}
    /**
     * Updates the game state. This is called once per frame.
     * Every dynamic object in the game should update its state here.
     * @param frameTime the time that has passed since the last update
     */
public void tick(float frameTime) {
    this.player.tick(frameTime);

    for(Animal animal : getAnimals()){
        animal.tick(frameTime);}

    for (Soil soil : getSoils())
        soil.tick(frameTime);

    for (Debris debris : getDebris())
        debris.tick(frameTime);

    if (debris[(int) exit.getX()][(int) exit.getY()] == null || debris[(int) exit.getX()][(int) exit.getY()].isBroken) {
        debris[(int) exit.getX()][(int) exit.getY()] = null;
        exit.Expose();

    }
    
    

    WINorLOSEstate();

    updateMapAsMatrix();
    
    
        
//timer updater (HUD)
 RemainingTime-=frameTime;
 if(RemainingTime <= 0) {
    RemainingTime =0;
 }

 for (int x = 0; x < MAP_WIDTH; x++) {
    for (int y = 0; y < MAP_HEIGHT; y++) {

        if (isOnLightning[x][y]) {
            lightningTimer[x][y] += frameTime;

            if (lightningTimer[x][y] >= 1f) {
                isOnLightning[x][y] = false;
                lightningTimer[x][y] = 0f;
            }
        }

    }
}

 doPhysicsStep(frameTime);

}
      public void updateMapAsMatrix() {
        for (int i = 0; i < MAP_WIDTH; i++) {
        for (int j = 0; j < MAP_HEIGHT; j++) {
            mapAsMatrix[i][j] = soils[i][j];}}

            mapAsMatrix[(int) exit.getX()][(int) exit.getY()] = exit;
    for (int i = 0; i < MAP_WIDTH; i++) {
        for (int j = 0; j < MAP_HEIGHT; j++) {
            if (fences[i][j] != null)
                mapAsMatrix[i][j] = fences[i][j];

            if (tools[i][j] != null && !tools[i][j].isBroken())
                mapAsMatrix[i][j] = tools[i][j];

            if (debris[i][j] != null && !debris[i][j].isBroken())
                mapAsMatrix[i][j] = debris[i][j];

            //Felix:for player getting dmg by animals
            for( Animal animal : getAnimals()){
                mapAsMatrix[animal.getStandingTile().getX()][animal.getStandingTile().getY()] = animal;
            }
}
}
}




    /**
     * Performs as many physics steps as necessary to catch up to the given frame time.
     * This will update the Box2D world by the given time step.
     * @param frameTime Time since last frame in seconds
     */
    private void doPhysicsStep(float frameTime) {
        this.physicsTime += frameTime;
        while (this.physicsTime >= TIME_STEP) {
            this.world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
            this.physicsTime -= TIME_STEP;
        }
    }
    
    /** Returns the player on the map. */
    public Player getPlayer() {
        return player;
    }

    public Exit getExit(){
        return exit;
    }

    //PP: Get animal
    public List<Animal> getAnimals() {
          return  Arrays.stream(animals).flatMap(Arrays::stream).filter(Objects::nonNull).filter(n->!n.isDead()).toList();
    }
    public List<Animal> getDragons() {
        return Arrays.stream(animals).flatMap(Arrays::stream).filter(Objects::nonNull).filter(n->!n.isDead()).filter(n -> n instanceof Dragon).toList();
    }
    public List<Animal> getBalrogs() {
        return Arrays.stream(animals).flatMap(Arrays::stream).filter(Objects::nonNull).filter(n->!n.isDead()).filter(n -> n instanceof Balrog).toList();
    }
    

public List<Soil> getSoils() {
    return Arrays.stream(soils)
            .flatMap(Arrays::stream)
            .sorted((a, b) -> {
                int c = Float.compare(b.getY(), a.getY());
                if (c != 0) return c;
                return Float.compare(b.getX(), a.getX());
            })
            .toList();
}


   //Felix: Getter for Fence
   public List<Fence> getFences() {
    return Arrays.stream(fences).flatMap(Arrays::stream).filter(Objects::nonNull).toList();
   }

   public List<Debris> getDebris() {
    return Arrays.stream(debris).flatMap(Arrays::stream).filter(Objects::nonNull).toList();
   }

   //Felix: get fertilizers
   public List<Tool> getTools() {
    return Arrays.stream(tools).flatMap(Arrays::stream).filter(Objects::nonNull).toList();
   }
   public Tile getFacingTile(){
    return player.getFacingTile();
   }
   public Tile getStaffFacingTile(){
    return player.getStafffacingTile();
   }

    //getters for map width and length
     public int getMapWidth() {
        return MAP_WIDTH;
    }

    public int getMapHeight() {
        return MAP_HEIGHT;
    }
 public static int[][] loadFromFile(FileHandle file) {

    String content = file.readString();
    String[] lines = content.split("\r?\n");

    // 1. First pass: determine matrix size
    int maxX = 0;
    int maxY = 0;

    for (String line : lines) {
        if (line.isBlank() || line.startsWith("#")) continue;

        String[] parts = line.split("=");
        if (parts.length != 2) continue;

        String[] coords = parts[0].split(",");
        int x = Integer.parseInt(coords[0].trim());
        int y = Integer.parseInt(coords[1].trim());

        maxX = Math.max(maxX, x);
        maxY = Math.max(maxY, y);
    }

    // 2. Create matrix ( +1 because coordinates start at 0 )
    int[][] mapLayout = new int[maxX + 1][maxY + 1];

    // Optional: fill with default value (e.g. -1 = empty)
    for (int x = 0; x <= maxX; x++) {
        for (int y = 0; y <= maxY; y++) {
            mapLayout[x][y] = -1;
        }
    }

    // 3. Second pass: fill matrix
    for (String line : lines) {
        if (line.isBlank() || line.startsWith("#")) continue;

        String[] parts = line.split("=");
        if (parts.length != 2) continue;

        String[] coords = parts[0].split(",");
        int x = Integer.parseInt(coords[0].trim());
        int y = Integer.parseInt(coords[1].trim());

        int typenumber = Integer.parseInt(parts[1].trim());

        mapLayout[x][y] = typenumber;
        
    }

    return mapLayout;
    
}
        public Entrance getEntrance() {
    return entrance;
}

public GameMap (ValleyDayGame game, FileHandle file){
    int[][] map = loadFromFile(file);
    MAP_WIDTH = map.length;
    MAP_HEIGHT= map[0].length;
    RemainingTime = MAP_WIDTH*MAP_HEIGHT;
    if (game.difficulty == 1) RemainingTime = RemainingTime/(3/2);
    if (game.difficulty == 2) RemainingTime = RemainingTime/(3/2);

    neededVictoryPoints = Math.max(1,MAP_WIDTH * MAP_HEIGHT/80);
    if (game.difficulty == 1) neededVictoryPoints = (int)(neededVictoryPoints*1.5f);
    if (game.difficulty == 2) neededVictoryPoints = neededVictoryPoints*2;


    
     lightningTimer = new float[MAP_WIDTH][MAP_HEIGHT];

    this.game = game;
        world = new World(Vector2.Zero, true);
        world.setContactListener(this);
        // Create a player with initial position (1, MAP_HEIGHT/3)
        //PP: initialize animal (just one for now)
        animals = new Animal[MAP_WIDTH][MAP_HEIGHT];
        // Create a chest in the middle of the map
        // Create flowers in a MAP_WIDTH x MAP_HEIGHT grid
        soils = new Soil[MAP_WIDTH][MAP_HEIGHT];

        mapAsMatrix = new Object[MAP_WIDTH][MAP_HEIGHT];

        for (int i = 0; i < soils.length; i++) {
            for (int j = 0; j < soils[i].length; j++) {
                this.soils[i][j] = new Soil(world, i, j, this, Crop.EMPTY_SOIL);
            }
        }
        fences = new Fence[MAP_WIDTH][MAP_HEIGHT];

        


    isOnFire = new boolean[MAP_WIDTH][MAP_HEIGHT];
    isOnFire2 = new boolean[MAP_WIDTH][MAP_HEIGHT];
    isOnLightning = new boolean[MAP_WIDTH][MAP_HEIGHT];

    //test debris (Franz)
    debris = new Debris[MAP_WIDTH][MAP_HEIGHT];

    //Felix: 
    tools = new Tool[MAP_WIDTH][MAP_HEIGHT];

    int counter = 0;

    for (int i = 0; i<MAP_WIDTH; i++){
        for (int j = 0; j<MAP_HEIGHT; j++){
            double a = Math.random();
            if (map[i][j] == 0) {
                fences[i][j] = new Fence(world, i, j, FenceType.INNERFENCE);}
            if (map[i][j] == 1) {
                debris[i][j] = new Debris(world, i, j, this);
                if (a<=0.05) tools[i][j] = new FireResistancePotion(world, i, j, this);
                if (a>=0.95) tools [i][j] = new InvisibilyRing(world, i, j, this);
                if (a>0.15 && a<0.2) tools[i][j] = new Staff(world, i, j, this);
            }
            if (map[i][j] == 2) entrance = new Entrance(world, i, j);
            if (map[i][j] == 3) animals[i][j] = createRandomAnimal(i, j);
            if (map[i][j] == 4) {
                counter ++;
                debris[i][j] = new Debris(world, i, j, this);
                this.exit = new Exit(world, i, j, this);
            }
            if (map[i][j] == 5) {
                debris[i][j] = new Debris(world, i, j, this);
                tools[i][j] = new Fertilizer(world, i, j, this);}
            
            if (map[i][j] == 6){
                tools[i][j] = new WateringCan(world, i, j, this);
                
                debris[i][j] = new Debris(world, i, j, this);
            }
            if (map[i][j] == 7){
                tools [i][j] = new Shovel(world, i, j, this);
                debris[i][j] = new Debris(world, i, j, this);
            }

            
    

    }

}
//Felix: bottom and top borders
for (int x = 1; x < MAP_WIDTH -1 ; x++) {
    fences[x][0].setType(FenceType.HORIZONTAL);
    fences[x][MAP_HEIGHT - 1].setType(FenceType.HORIZONTAL);
}
//Felix: left and right borders
for (int y = 1; y < MAP_HEIGHT -1; y++) {
    fences[0][y].setType(FenceType.VERTICAL);
    fences[MAP_WIDTH - 1][y].setType(FenceType.VERTICAL);
    }
//Felix: corners
    fences[0][MAP_HEIGHT-1].setType(FenceType.TOP_LEFT);
    fences[MAP_WIDTH -1][MAP_HEIGHT-1].setType(FenceType.TOP_RIGHT);
    fences[0][0].setType(FenceType.BOTTOM_LEFT);
    fences[MAP_WIDTH-1][0].setType(FenceType.BOTTOM_RIGHT);
   
//needs improvements...
            if (counter ==0){
                int i = (int)((Math.random()*MAP_WIDTH-3));
                int j = (int)((Math.random()*MAP_HEIGHT-3));
                while (map[i+1][j+1] == 0 ){
                i = (int)((Math.random()*MAP_WIDTH-3));
                j = (int)((Math.random()*MAP_HEIGHT-3));  
                if(i <0 || j <0){  
                i =0;
                j=0;
                }}
                if (debris[i+1][j+1]== null) 
                debris[i+1][j+1] = new Debris(world, i+1, j+1, this);           
                exit = new Exit(world, i+1, j+1, this);
                if (fences[i+1][j+1] != null){
                    for (Fixture fixture : fences[i+1][j+1].getBody().getFixtureList()) {
            fences[i+1][j+1].getBody().destroyFixture(fixture);
                }
                
                }}

            player = new Player(world, entrance.getX(), entrance.getY(), this);
            
}


        public Animal createRandomAnimal(int i, int j){
            double p = Math.random();
            boolean hasDragon = (!getDragons().isEmpty());
            boolean hasBalrog = (!getBalrogs().isEmpty());
            if (game.difficulty ==2){
            
                if (!hasDragon && p<0.25){
                    return new Dragon(world, i, j, this);}
             
                if(!hasBalrog && p >0.75) {
                    return new Balrog(world, i, j, this);}
                if (p>0.7) return new Bull(world, i, j, this);
                return new Chicken(world, i, j, this);
            }
            if (game.difficulty == 1){
                
                if (!hasDragon && p<0.25 && !hasBalrog){
                    return new Dragon(world, i, j, this);}
             
                if(!hasBalrog && p >0.75 &&!hasDragon) {
                    return new Balrog(world, i, j, this);}
                if (p>0.5) return new Bull(world, i, j, this);
                return new Chicken(world, i, j, this);
            }
                if (p>0.7) return new Bull(world, i, j, this);
                return new Chicken(world, i, j, this);
            }
        

            
        

        public void playergettingdmg() {
                         if (player.getHpcooldown() >= 3) {
                        player.setPlayerhp(player.getPlayerhp() - 1);
                        player.setHpcooldown(0);
                    }
        }
      

    public boolean enoughVP() {
        if(player.getVictorypoints() >= neededVictoryPoints) {
            exit.doBreak();
            return true;
        }
        return false;
    }

    public void WINorLOSEstate() {
        if(RemainingTime == 0 || player.playerdead() == true) {
            lost = true;
        }
        if(enoughVP() == true && player.getStandingTile().getX() == exit.getX() && player.getStandingTile().getY() == exit.getY()) {
            won = true;
        }
        
    }
    @Override
public void beginContact(Contact contact) {
    Object a = contact.getFixtureA().getBody().getUserData();
    Object b = contact.getFixtureB().getBody().getUserData();

    if (a instanceof Player thePlayer && b instanceof Animal theAnimal && theAnimal.isHarmful()) {
        thePlayer.receiveDamage();
    }
    else if (b instanceof Player thePlayer2 && a instanceof Animal theAnimal2 && theAnimal2.isHarmful()) {
        thePlayer2.receiveDamage();
    }
}

@Override public void endContact(Contact contact) {}
@Override public void preSolve(Contact contact, Manifold oldManifold) {}
@Override public void postSolve(Contact contact, ContactImpulse impulse) {}

}
        


    
