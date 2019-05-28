package com.village.tap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.net.HttpRequestBuilder.json;

public class BuildingPlan {
    private static List<BuildingPlan> buildingPlans;

    private String name;
    private String spriteName;
    private String description;
    private int cost;
    private int maxCapacity;
    private int width;
    private int height;
    private Sprite sprite;

    public BuildingPlan() {
    }

    public static List<BuildingPlan> getBuildingPlans() {
        return buildingPlans;
    }

    public static BuildingPlan getBuildingPlan(int buildingId) {
        return buildingPlans.get(buildingId);
    }

    public static void load() {
        buildingPlans = new ArrayList<BuildingPlan>();
        buildingPlans = json.fromJson(ArrayList.class, BuildingPlan.class,
                Gdx.files.internal("data/buildings.json").readString());

        // TODO: switch to TextureAtlas
        for(BuildingPlan buildingPlan : buildingPlans){
            Texture currentTexture = new Texture(buildingPlan.spriteName);
            buildingPlan.sprite = new Sprite(currentTexture);
        }
    }

    public static void showBuildingPlanDialog() {
        Label label = new Label("Buy new building", Global.getSkin());
        TextButton closeButton = new TextButton("X", Global.getSkin());
        label.setAlignment(Align.center);
        final Dialog dialog = new Dialog("", Global.getSkin(), "dialog");
        closeButton.setPosition(closeButton.getX() - 30, closeButton.getY() - 30);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dialog.hide();
            }
        });
        dialog.getContentTable().add(label).width(Gdx.graphics.getWidth() - 200).height(200);
        dialog.getContentTable().add(closeButton).width(100).height(100).padLeft(-200);
        dialog.getContentTable().row();
        Table table = new Table();
        table.top();
        int index = 0;
        for (BuildingPlan b : BuildingPlan.getBuildingPlans()) {
            final int temp = index++;
            TextButton z = new TextButton("Buy\n" + b.cost + Global.getCurrency(), Global.getSkin());
            z.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    GUI.switchToPlaceMode(temp);
                    PlaceMode.initialize(BuildingPlan.getBuildingPlan(temp));
                    dialog.hide();
                }
            });
            table.add(new Image(b.sprite)).height(150).width(150).padLeft(25).padRight(25).padBottom(30);
            table.add(new Label(b.toString(), Global.getSkin())).prefWidth(550).padRight(25).padBottom(30);
            table.add(z).width(270).height(100).padRight(25).padBottom(30);
            table.row();
        }

        //Maak font sizes proper.
        for (Actor a : table.getChildren()) {
            if (a instanceof Label) {
                Label temp = (Label) a;
                temp.setFontScale(.5f);
            }
            if (a instanceof TextButton) {
                TextButton temp = (TextButton) a;
                temp.getLabel().setFontScale(.5f);
            }
        }

        table.setHeight(Gdx.graphics.getHeight() - 200);
        table.setWidth(Gdx.graphics.getWidth() - 200);
        ScrollPane pane = new ScrollPane(table, Global.getSkin());

        dialog.getContentTable().add(pane).height(1000);

        dialog.invalidateHierarchy();
        dialog.invalidate();
        dialog.layout();
        Color color = dialog.getColor();
        color.a = 0;
        dialog.setColor(color);
        dialog.show(Global.getCurrentStage());
    }

    public String getName() {
        return name;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public int getCost() {
        return cost;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public int getWidth() {
        return width * Global.getTileSize();
    }

    public int getHeight() {
        return height * Global.getTileSize();
    }

    public int getTileWidth() {
        return width;
    }

    public int getTileHeight() {
        return height;
    }

    public String toString() {
        return name + "\nHolds " + maxCapacity + " villagers.";
    }
}
