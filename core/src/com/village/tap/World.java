package com.village.tap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class World {

    private static final int WORLD_WIDTH = 50;
    private static final int WORLD_HEIGHT = 50;

    public static Texture[] sprites;

    private static Tile[][] world;


    public static float getWorldWidth() {
        return WORLD_WIDTH;
    }

    public static float getWorldHeight() {
        return WORLD_HEIGHT;
    }

    static void GenerateWorld() {
        world = new Tile[WORLD_WIDTH][WORLD_HEIGHT];
        for (int i = 0; i < WORLD_WIDTH; i++) {
            for (int j = 0; j < WORLD_HEIGHT; j++) {
                world[i][j] = new Tile(i, j);
            }
        }

        GenerateRiver();

    }

    static Tile getTile(int x, int y) throws Exception {

        if (y > WORLD_HEIGHT || x > WORLD_WIDTH) {
            return null;
        }


        return world[x][y];
    }

    static void GenerateRiver() {
        Random r = new Random();
        int a = r.nextInt((WORLD_WIDTH - 5) + 1) + 3;
        for (int j = 0; j < WORLD_HEIGHT; j++) {
            world[a - 1][j].UpdateType(Tile.TileType.Water);
            world[a][j].UpdateType(Tile.TileType.Water);
            world[a + 1][j].UpdateType(Tile.TileType.Water);

            double chance = 0.30;
            if (r.nextDouble() < chance) {
                if (r.nextBoolean()) {
                    a -= 1;
                } else {
                    a += 1;
                }
            }
        }
    }

    static void load() {
        sprites = new Texture[]{(new Texture("grass.png")), (new Texture("water.png"))};
        GenerateWorld();

    }

    public static void draw(SpriteBatch batch) {
        for (int i = 0; i < WORLD_WIDTH; i++) {
            for (int j = 0; j < WORLD_HEIGHT; j++) {
                Tile tile = world[i][j];
                batch.draw(sprites[tile.getType().ordinal()], i * Global.getTileSize(), j * Global.getTileSize());
            }
        }
    }
}
