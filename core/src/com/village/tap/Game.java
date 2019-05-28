package com.village.tap;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;

import java.util.ArrayList;
import java.util.List;

public class Game extends ApplicationAdapter {
    private static int goldCount = 1200;
    private static List<Upgrade> upgrades;
    SpriteBatch batch;
    private int gameTick = 0;

    static int getGoldCount() {
        return goldCount;
    }

    static void increaseGold(int toAdd) {
        goldCount += toAdd;
    }


    static boolean spendGold(int price) {
        if (goldCount >= price) {
            goldCount -= price;
            return true;
        }
        return false;
    }

    static void buyUpgrade(int index){
        upgrades.add(getAvailableUpgrades().get(index));
    }

    static int getCurrentVillagerCount() {
        int count = 0;
        for (Building l : Plot.getBuildings()) {
            count += l.getCurrentCapacity();
        }
        return count;

    }


    public static int getGoldPerSec() {
        int goldPerVillagerSec = 0;
        for(Upgrade upgrade : getUpgrades()){
            if(upgrade instanceof TaxUpgrade){
                TaxUpgrade taxUpgrade = (TaxUpgrade) upgrade;

                goldPerVillagerSec += taxUpgrade.getGoldPerSec();
            }
        }
        int goldPerSec = getCurrentVillagerCount() * goldPerVillagerSec;

        return goldPerSec;
    }


    static int getMaxVillagerCount() {
        int count = 0;
        for (Building l : Plot.getBuildings()) {
            count += l.getPlan().getMaxCapacity();
        }
        return count;
    }

    static List<Upgrade> getUpgrades(){
        return upgrades;
    }

    static List<Upgrade> getAvailableUpgrades(){
        List<Upgrade> allUpgrades = new ArrayList<Upgrade>(Upgrade.AllUpgrades);
        allUpgrades.removeAll(upgrades);
        return allUpgrades;
    }

    private void initCamera() {
        OrthographicCamera camera = new OrthographicCamera(432, 768);
        camera.position.set(World.getWorldWidth() / 2, World.getWorldHeight() / 2, 0);
        camera.zoom += 0.6f;
        Global.setCamera(camera);
    }

    @Override
    public void create() {

        batch = new SpriteBatch();
        initCamera();
        Global.load();
        World.load();
        Plot.load();
        GUI.loadGui(batch);

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(Global.getCurrentStage());
        GestureDetector detector = new GestureDetector(new Gestures());
        inputMultiplexer.addProcessor(detector);
        Upgrade.load();
        upgrades = new ArrayList<Upgrade>();

        Gdx.input.setInputProcessor(inputMultiplexer);

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Global.getCurrentCamera().update();
        batch.setProjectionMatrix(Global.getCurrentCamera().combined);
        batch.begin();
        World.draw(batch);
        PlaceMode.renderSelection(batch);
        Plot.renderPlots(batch);
        batch.end();
        GUI.renderGui();
        update();
    }

    void update() {
        gameTick++;
        if (gameTick > Gdx.graphics.getFramesPerSecond()) { //Elke 120 Gameticks villagers generaten.
            Plot.generateVillagers();
            Game.goldCount += getGoldPerSec();
            gameTick = 0;
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

}
