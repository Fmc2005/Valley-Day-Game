package de.tum.cit.aet.valleyday.map;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


import de.tum.cit.aet.valleyday.texture.Textures;

public class Tile {
    private int x;
    private int y;
    private Object facingObject;
    GameMap map;

        public TextureRegion getCurrentAppearance(){
        return Textures.FACING_TILE;
    }   
        

        public Tile(int x, int y, GameMap map) {
            this.x = x;
            this.y = y;
            this.map = map;
            facingObject = null;
        }


        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }


        public Object getFacingObject() {
            return facingObject;
        }


        public void setFacingObject(Object facingObject) {
            this.facingObject = facingObject;
        }



        
        
        

        

    
    

}
