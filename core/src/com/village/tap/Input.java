package com.village.tap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;

public class Input implements InputProcessor {
    float _scrollSpeed;
    public Input(float scrollSpeed){
        _scrollSpeed = scrollSpeed;
    }
    public boolean keyDown (int keycode) {
        return false;
    }

    public boolean keyUp (int keycode) {
        return false;
    }

    public boolean keyTyped (char character) {
        return false;
    }

    public boolean touchDown (int x, int y, int pointer, int button) {

        return false;
    }

    public boolean touchUp (int x, int y, int pointer, int button) {
        Vector3 erna = Global.getCurrentCamera().unproject(new Vector3(x,y,0f));
        for(Building s : Plot.getBuildings()) {
            if(erna.x < (s.getPosition().getHeight() + s.getPosition().getX()) && erna.x > s.getPosition().getX() && erna.y < (s.getPosition().getWidth() + s.getPosition().getY()) && erna.y > s.getPosition().getY()){
                s.showBuildingInfoDialog();
            }

        }
        return false;
    }

    public boolean touchDragged (int x, int y, int pointer) {
        float a = Gdx.input.getDeltaX() * _scrollSpeed;
        float b = Gdx.input.getDeltaY() * _scrollSpeed;
        a = a * -1;
        Global.getCurrentCamera().position.add(a, b, 0);
        Global.getCurrentCamera().update();
        return false;
    }

    public boolean mouseMoved (int x, int y) {
        return false;
    }

    public boolean scrolled (int amount) {
        return false;
    }
}
