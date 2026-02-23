package de.tum.cit.aet.valleyday.texture;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Contains all animation constants used in the game.
 * It is good practice to keep all textures and animations in constants to avoid loading them multiple times.
 * These can be referenced anywhere they are needed.
 */
public class Animations {
    
    /**
     * The animation for the character walking down.
     */
        public static final Animation<TextureRegion> CHARACTER_WALK_UP = new Animation<>(0.1f,
            SpriteSheet.CHARACTER.at(3, 1),
            SpriteSheet.CHARACTER.at(3, 2),
            SpriteSheet.CHARACTER.at(3, 3),
            SpriteSheet.CHARACTER.at(3, 4));
        public static final Animation<TextureRegion> CHARACTER_WALK_DOWN = new Animation<>(0.1f,
            SpriteSheet.CHARACTER.at(1, 1),
            SpriteSheet.CHARACTER.at(1, 2),
            SpriteSheet.CHARACTER.at(1, 3),
            SpriteSheet.CHARACTER.at(1, 4));
        public static final Animation<TextureRegion> CHARACTER_WALK_LEFT = new Animation<>(0.1f,
                SpriteSheet.CHARACTER.at(4, 1),
            SpriteSheet.CHARACTER.at(4, 2),
            SpriteSheet.CHARACTER.at(4, 3),
            SpriteSheet.CHARACTER.at(4, 4));
        public static final Animation<TextureRegion> CHARACTER_WALK_RIGHT = new Animation<>(0.1f,
            SpriteSheet.CHARACTER.at(2, 1),
            SpriteSheet.CHARACTER.at(2, 2),
            SpriteSheet.CHARACTER.at(2, 3),
            SpriteSheet.CHARACTER.at(2, 4));


            public static final Animation<TextureRegion> CHICKEN_WALKUP = new Animation<>(0.1f,
            SpriteSheet.CHICKEN.at(1, 1),
            SpriteSheet.CHICKEN.at(1, 2),
            SpriteSheet.CHICKEN.at(1, 3));
        public static final Animation<TextureRegion> CHICKEN_WALKDOWN = new Animation<>(0.1f,
            SpriteSheet.CHICKEN.at(3, 1),
            SpriteSheet.CHICKEN.at(3, 2),
            SpriteSheet.CHICKEN.at(3, 3));
        public static final Animation<TextureRegion> CHICKEN_WALKLEFT = new Animation<>(0.1f,
            SpriteSheet.CHICKEN.at(4, 1),
            SpriteSheet.CHICKEN.at(4, 2),
            SpriteSheet.CHICKEN.at(4, 3));
        public static final Animation<TextureRegion> CHICKEN_WALK_RIGHT = new Animation<>(0.1f,
            SpriteSheet.CHICKEN.at(2, 1),
            SpriteSheet.CHICKEN.at(2, 2),
            SpriteSheet.CHICKEN.at(2, 3));
        
        //Dragon:
            public static final Texture DRAGON = new Texture(Gdx.files.internal("texture/dragon.png"));
          public static final Animation<TextureRegion> DRAGON_WALKUP = new Animation<>(0.1f,
            new TextureRegion(DRAGON, 1024-1000, 1024-1002, 214, 214),
            new TextureRegion(DRAGON, 1024-746, 1024-999, 214, 214),
            new TextureRegion(DRAGON, 1024-232, 1024-999, 214, 214));
        public static final Animation<TextureRegion> DRAGON_WALKDOWN = new Animation<>(0.1f,
            new TextureRegion(DRAGON, 1024-991, 1024-740, 214, 214),
            new TextureRegion(DRAGON, 1024-748,  1024-740, 214, 214),
            new TextureRegion(DRAGON, 1024-477,  1024-740, 214, 214));
        public static final Animation<TextureRegion> DRAGON_WALKLEFT = new Animation<>(0.1f,
             new TextureRegion(DRAGON, 1024-1024, 1024-467, 208, 181),
            new TextureRegion(DRAGON, 1024-765, 1024-467, 208, 181),
            new TextureRegion(DRAGON, 1024-510, 1024-467, 208, 181));
        public static final Animation<TextureRegion> DRAGON_WALK_RIGHT = new Animation<>(0.1f,
             new TextureRegion(DRAGON, 1024-990, 1024-223, 208, 181),
            new TextureRegion(DRAGON, 1024-731, 1024-223, 208, 181),
            new TextureRegion(DRAGON, 1024-466, 1024-223, 208, 181));

            //Bull:
        public static final Texture BULL = new Texture(Gdx.files.internal("texture/bull.png"));
          public static final Animation<TextureRegion> BULL_WALKUP = new Animation<>(0.1f,
            new TextureRegion(BULL, 1024-931, 1536-1472,  231, 255),
            new TextureRegion(BULL, 1024-635, 1536-1472, 231, 255),
            new TextureRegion(BULL, 1024-311, 1536-1472, 231, 255));
        public static final Animation<TextureRegion> BULL_WALKDOWN = new Animation<>(0.1f,
            new TextureRegion(BULL, 1024-937,  1536-1187, 221, 245),
            new TextureRegion(BULL, 1024-642,  1536-1187, 221, 245),
            new TextureRegion(BULL, 1024-339,  1536-1187, 221, 245));
        public static final Animation<TextureRegion> BULL_WALKLEFT = new Animation<>(0.1f,
             new TextureRegion(BULL,1024-1000,  1536-329, 313, 245),
            new TextureRegion(BULL, 1024-677,  1536-329, 313, 245),
            new TextureRegion(BULL, 1024-368,  1536-329, 313, 245));
        public static final Animation<TextureRegion> BULL_WALK_RIGHT = new Animation<>(0.1f,
             new TextureRegion(BULL, 1024-968, 1536-604, 316, 209),
            new TextureRegion(BULL, 1024-652,  1536-604, 290, 209),
            new TextureRegion(BULL, 1024-342,  1536-604, 317, 209));


            //Balrog:
            public static final Texture BALROG = new Texture(Gdx.files.internal("texture/balrog.png"));
          public static final Animation<TextureRegion> BALROG_WALKUP = new Animation<>(0.1f,
            new TextureRegion(BALROG, 1024-956,1536-768,  213, 208),
            new TextureRegion(BALROG, 1024-277, 1536-768, 218, 208),
            new TextureRegion(BALROG, 1024-507, 1536-768, 218, 208));
        public static final Animation<TextureRegion> BALROG_WALKDOWN = new Animation<>(0.1f,
            new TextureRegion(BALROG, 1024-953,  1536-1285, 220, 218),
            new TextureRegion(BALROG, 1024-279,  1536-1285, 220, 218),
            new TextureRegion(BALROG, 1024-508,  1536-1285, 220, 218));
        public static final Animation<TextureRegion> BALROG_WALKLEFT = new Animation<>(0.1f,
             new TextureRegion(BALROG,1024-708,1536-1035, 207,218 ),
            new TextureRegion(BALROG, 1024-272,1536-1035, 207,218 ),
            new TextureRegion(BALROG, 1024-943,1536-1035, 207,218 ));
        public static final Animation<TextureRegion> BALROG_WALK_RIGHT = new Animation<>(0.1f,
             new TextureRegion(BALROG,1024-741, 1536-529, 225, 211),
            new TextureRegion( BALROG, 1024-279, 1536-529, 233, 211),
            new TextureRegion( BALROG, 1024-952, 1536-529, 212, 211));

            //Balrog_fire:
            public static final Animation<TextureRegion> BALROG_FIRE = new Animation<>(0.1f,
            SpriteSheet.EXPLOSION.at(1, 3),
            SpriteSheet.EXPLOSION.at(1, 4),
            SpriteSheet.EXPLOSION.at(1, 5),
            SpriteSheet.EXPLOSION.at(1, 6));

            //Dragon_fire:
            public static final Animation<TextureRegion> DRAGON_FIRE = new Animation<>(0.1f,
            SpriteSheet.BLUEFIRE.at(1, 1),
            SpriteSheet.BLUEFIRE.at(1, 2),
            SpriteSheet.BLUEFIRE.at(1, 3),
            SpriteSheet.BLUEFIRE.at(2, 1),
            SpriteSheet.BLUEFIRE.at(2, 2),
            SpriteSheet.BLUEFIRE.at(2, 3),
            SpriteSheet.BLUEFIRE.at(3, 1),
            SpriteSheet.BLUEFIRE.at(3, 2),
            SpriteSheet.BLUEFIRE.at(3, 3));

            //Player Hitting animation
             public static final Animation<TextureRegion> CHARACTER_HIT_UP = new Animation<>(0.1f,
            SpriteSheet.CHARACTER_HIT.at(2, 1),
            SpriteSheet.CHARACTER_HIT.at(2, 2),
            SpriteSheet.CHARACTER_HIT.at(2, 3),
            SpriteSheet.CHARACTER_HIT.at(2, 4));
        public static final Animation<TextureRegion> CHARACTER_HIT_DOWN = new Animation<>(0.1f,
            SpriteSheet.CHARACTER_HIT.at(1, 1),
            SpriteSheet.CHARACTER_HIT.at(1, 2),
            SpriteSheet.CHARACTER_HIT.at(1, 3),
            SpriteSheet.CHARACTER_HIT.at(1, 4));
        public static final Animation<TextureRegion> CHARACTER_HIT_LEFT = new Animation<>(0.1f,
            SpriteSheet.CHARACTER_HIT.at(4, 1),
            SpriteSheet.CHARACTER_HIT.at(4, 2),
            SpriteSheet.CHARACTER_HIT.at(4, 3),
            SpriteSheet.CHARACTER_HIT.at(4, 4));
        public static final Animation<TextureRegion> CHARACTER_HIT_RIGHT = new Animation<>(0.1f,
            SpriteSheet.CHARACTER_HIT.at(3, 1),
            SpriteSheet.CHARACTER_HIT.at(3, 2),
            SpriteSheet.CHARACTER_HIT.at(3, 3),
            SpriteSheet.CHARACTER_HIT.at(3, 4));

            //Lightning
            public static final Animation<TextureRegion> LIGHTNING = new Animation<>(0.1f,
            SpriteSheet.LIGHTNING_FILE.at(1, 1),
            SpriteSheet.LIGHTNING_FILE.at(1, 2),
            SpriteSheet.LIGHTNING_FILE.at(1, 3));
}
