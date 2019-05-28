package com.village.tap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.List;

public class PlaceMode {
    private static BuildingPlan currentPlan;
    private static Texture selectedTexture;
    private static boolean isPlacing = false;
    private static boolean canBuild = false;

    public static void initialize(BuildingPlan plan) {
        currentPlan = plan;
        if (selectedTexture == null) {
            selectedTexture = new Texture("selected.png");
        }
        isPlacing = true;
    }

    public static void renderSelection(SpriteBatch batch) {
        if (!isPlacing)
            return;

        canBuild = true;
        ArrayList<Tile> tiles = getSelectedTiles();
        for (Tile t : tiles)
            if (!t.isBuildable()) {
                batch.setColor(Color.RED);
                canBuild = false;
            }

        for (Tile t : tiles) {
            batch.draw(selectedTexture, t.getX() * 32, t.getY() * 32);
        }
        batch.setColor(Color.WHITE);

    }

    public static void ConfirmPurchase() {
        if (canBuild() && Game.spendGold(currentPlan.getCost())) {
            List<Tile> selectedTiles = getSelectedTiles();
            Tile t = selectedTiles.get(0);
            Plot.buyBuilding(t.getX() * 32, t.getY() * 32, currentPlan);
            for(Tile tile : selectedTiles)
                tile.setHasBuilding(true);

            GUI.exitPlaceMode();
        }
    }

    public static ArrayList<Tile> getSelectedTiles() {
        ArrayList<Tile> selectedTiles = new ArrayList<Tile>();
        int newX = (currentPlan.getTileWidth() / 2) * Global.getTileSize();
        int newY = (currentPlan.getTileHeight() / 2) * Global.getTileSize();
        Vector3 result = Global.getCurrentCamera().unproject(new Vector3(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0f));
        for (int i = 0; i < currentPlan.getTileWidth(); i++) {
            for (int j = 0; j < currentPlan.getTileHeight(); j++) {
                try {
                    selectedTiles.add(World.getTile((roundUp(result.x - newX) + (i * Global.getTileSize())) / 32, (roundUp(result.y - newY) + (j * Global.getTileSize())) / 32));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return selectedTiles;
    }

    public static boolean canBuild() {
        return canBuild;
    }

    static int roundUp(float num) {
        return (int) (Math.ceil(num / 32d) * 32);
    }

    public static void exitPlaceMode() {
        currentPlan = null;
        isPlacing = false;
    }
}
