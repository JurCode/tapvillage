package com.village.tap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Gestures implements GestureDetector.GestureListener {
    private float initialZoom;

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        initialZoom = Global.getCurrentCamera().zoom;
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        Vector3 erna = Global.getCurrentCamera().unproject(new Vector3(x,y,0f));
        for(Building s : Plot.getBuildings()) {
            if(erna.x < (s.getPosition().getWidth() + s.getPosition().getX()) && erna.x > s.getPosition().getX() && erna.y < (s.getPosition().getHeight() + s.getPosition().getY()) && erna.y > s.getPosition().getY()){
                s.showBuildingInfoDialog();
            }

        }
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {

        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {

        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        float a = Gdx.input.getDeltaX() * 0.35f;
        float b = Gdx.input.getDeltaY() * 0.35f;
        a = a * -1;
        Global.getCurrentCamera().position.add(a, b, 0);
        Global.getCurrentCamera().update();
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {

        return false;
    }

    @Override
    public boolean zoom(float originalDistance, float currentDistance) {

        float clamp = (float) MathUtils.clamp(initialZoom * (originalDistance / currentDistance), 0.5, 4);
        Global.getCurrentCamera().zoom = clamp;
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialFirstPointer, Vector2 initialSecondPointer, Vector2 firstPointer, Vector2 secondPointer) {

        return false;
    }

    @Override
    public void pinchStop() {
    }
}
