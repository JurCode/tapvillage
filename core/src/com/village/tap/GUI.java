package com.village.tap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class GUI {
    private static ShapeRenderer shapeRenderer;

    private static Stage main;
    private static Label statLabel;
    private static SpriteBatch batch;
    private static Music bgMusic;


    static void loadGui(SpriteBatch batch) {

        GUI.batch = batch;
        shapeRenderer = new ShapeRenderer();
        // Main stage
        main = new Stage();
        BitmapFont font = Global.getSkin().getFont("default");
        font.getData().scale(-0.3f);
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.BLACK);
        statLabel = new Label("Gold: " + Game.getGoldCount() + "  Villagers: " + Game.getCurrentVillagerCount() + " / " + Game.getMaxVillagerCount(), labelStyle);
        statLabel.setX(20);
        statLabel.setY(Gdx.graphics.getHeight() - 54);
        TextButton buyBuildingButton = new TextButton("Buy\nbuilding", Global.getSkin());
        buyBuildingButton.setWidth(Gdx.graphics.getWidth() * 0.23f);
        buyBuildingButton.setX(Gdx.graphics.getWidth() * 0.01f);
        buyBuildingButton.setY(Gdx.graphics.getWidth() * 0.03f);
        buyBuildingButton.setHeight(Gdx.graphics.getHeight() * 0.12f);
        buyBuildingButton.getLabel().setColor(Color.WHITE);
        buyBuildingButton.setColor(Color.LIGHT_GRAY);
        //clickButton.getLabel().setFontScale(1);
        buyBuildingButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                BuildingPlan.showBuildingPlanDialog();
            }
        });
        buyBuildingButton.setName("buybuildingbutton");

        TextButton acceptButton = new TextButton("Buy", Global.getSkin());
        acceptButton.setWidth(Gdx.graphics.getWidth() * 0.40f);
        acceptButton.setX(Gdx.graphics.getWidth() * 0.05f);
        acceptButton.setY(Gdx.graphics.getWidth() * 0.05f);
        acceptButton.setHeight(Gdx.graphics.getHeight() * 0.09f);
        acceptButton.getLabel().setFontScale(1);
        acceptButton.getLabel().setColor(Color.WHITE);
        acceptButton.setColor(Color.LIGHT_GRAY);
        acceptButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                PlaceMode.ConfirmPurchase();
            }
        });
        acceptButton.setVisible(false);
        acceptButton.setName("buybutton");

        TextButton cancelButton = new TextButton("Cancel", Global.getSkin());
        cancelButton.setWidth(Gdx.graphics.getWidth() * 0.40f);
        cancelButton.setX(Gdx.graphics.getWidth() * 0.55f);
        cancelButton.setY(Gdx.graphics.getWidth() * 0.05f);
        cancelButton.setHeight(Gdx.graphics.getHeight() * 0.09f);
        cancelButton.getLabel().setFontScale(1);
        cancelButton.getLabel().setColor(Color.WHITE);
        cancelButton.setColor(Color.LIGHT_GRAY);
        cancelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                exitPlaceMode();
            }
        });
        cancelButton.setVisible(false);
        cancelButton.setName("cancelbutton");

        main.addActor(buyBuildingButton);
        //main.addActor(clickButton);
        main.addActor(acceptButton);
        main.addActor(cancelButton);
        main.addActor(statLabel);

        //Set startup stage
        Global.setCurrentStage(main);

        bgMusic = Gdx.audio.newMusic(Gdx.files.internal("bg.mp3"));
        bgMusic.play();
    }

    static void updateStatLabel() {
        statLabel.setText(
                "Gold: " + Game.getGoldCount() +
                "  Villagers: " + Game.getCurrentVillagerCount() + "/" + Game.getMaxVillagerCount() +
                "  Gold/s: " + Game.getGoldPerSec());
    }

    static void renderGui() {
        updateStatLabel();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(0, Gdx.graphics.getHeight() - 55, Gdx.graphics.getWidth(), 55);
        shapeRenderer.end();
        batch.begin();

        Global.getCurrentStage().act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));

        updateStatLabel();
        Global.getCurrentStage().draw();
        batch.end();

    }

    private static void clickButtonClick() {
        Game.increaseGold(1);
    }

    static void switchToPlaceMode(int buildingId) {
        BuildingPlan buildingPlan = BuildingPlan.getBuildingPlan(buildingId);
        for (Actor a : Global.getCurrentStage().getActors()) {
            if (a.getName() != null) {
                if (a.getName().equals("clickbutton") || a.getName().equals("buybuildingbutton")) {
                    a.setVisible(false);
                } else if (a.getName().equals("buybutton") || a.getName().equals("cancelbutton")) {
                    a.setVisible(true);
                }
            }
        }
        TextButton b = Global.getCurrentStage().getRoot().findActor("buybutton");
        b.getLabel().setText("Buy\n" + buildingPlan.getCost() + Global.getCurrency());
    }

    static void exitPlaceMode() {
        for (Actor a : Global.getCurrentStage().getActors()) {
            if (a.getName() != null) {
                if (a.getName().equals("clickbutton") || a.getName().equals("buybuildingbutton")) {
                    a.setVisible(true);
                } else if (a.getName().equals("buybutton") || a.getName().equals("cancelbutton")) {
                    a.setVisible(false);
                }
            }
        }
        PlaceMode.exitPlaceMode();
    }
}
