package com.village.tap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Global {

    private static Stage currentStage;

    private static OrthographicCamera currentCamera;

    private static Skin skin;

    private static int tileSize = 32;

    public static OrthographicCamera getCurrentCamera() {
        return currentCamera;
    }

    public static void setCamera(OrthographicCamera camera) {
        currentCamera = camera;
    }

    public static Skin getSkin() {
        return skin;
    }

    public static void load() {
        skin = new Skin(Gdx.files.internal("data/skin/skin.json"), new TextureAtlas("data/skin/skin.atlas"));
    }

    public static Stage getCurrentStage() {
        return currentStage;
    }

    public static void setCurrentStage(Stage stage) {

        currentStage = stage;
    }

    public static String getCurrency() {
        return "g";
    }

    public static int getTileSize() {
        return tileSize;
    }
}
