package com.village.tap;

/**
 * Created by Henkie on 17-Jan-18.
 */

public class Tile {

    private int x;
    private int y;
    private TileType type;

    public Tile(int x, int y) {
        this.y = y;
        this.x = x;
        type = TileType.Grass;
    }

    public enum TileType{
        Grass, Water
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void UpdateType(TileType newType){
        type = newType;
    }

    public TileType getType() {
        return type;
    }

    public boolean isBuildable(){
        return type == TileType.Grass;
    }
}
