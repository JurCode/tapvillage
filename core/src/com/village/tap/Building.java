package com.village.tap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
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

public class Building {
    private List<Person> persons;
    private BuildingPlan plan;
    private Rectangle position;

    public Building(BuildingPlan plan, Rectangle position) {
        persons = new ArrayList<Person>();
        this.plan = plan;
        this.position = position;
    }

    public void addPerson() {
        persons.add(new Person());
    }

    public int getCurrentCapacity() {
        return persons.size();
    }

    public int getFreeCapacity() {
        return getPlan().getMaxCapacity() - getCurrentCapacity();
    }

    public BuildingPlan getPlan() {
        return plan;
    }

    public Rectangle getPosition() {
        return position;
    }

    public void showBuildingInfoDialog() {
        Label label = new Label(plan.getName(), Global.getSkin());
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
        for (Person p : persons) {
            table.add(new Label(p.getName(), Global.getSkin())).prefWidth(300).padRight(25).padBottom(30).left();
            Texture genderTexture = Person.getGenderTexture(p.getGender());
            table.add(new Image(genderTexture)).height(30).width(30).padLeft(25).padRight(25).padBottom(30);
            table.add(new Label("Age: " + p.getAge(), Global.getSkin())).prefWidth(400).padRight(25).padBottom(30).right();
            table.row();

        }
        table.setHeight(Gdx.graphics.getHeight() - 200);
        table.setWidth(Gdx.graphics.getWidth() - 200);
        ScrollPane pane = new ScrollPane(table, Global.getSkin());


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
        dialog.getContentTable().add(pane).height(1000);
        dialog.invalidateHierarchy();
        dialog.invalidate();
        dialog.layout();
        Color color = dialog.getColor();
        color.a = 0;
        dialog.setColor(color);
        dialog.show(Global.getCurrentStage());
    }
}
