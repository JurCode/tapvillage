package com.village.tap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Plot {
    private static List<Building> buildings;

    public static void load() {
        buildings = new ArrayList<Building>();
        BuildingPlan.load();
        Person.genderTextures = new HashMap<Person.Gender, Texture>();
        Person.genderTextures.put(Person.Gender.MALE, new Texture("male.png"));
        Person.genderTextures.put(Person.Gender.FEMALE, new Texture("female.png"));
        Person.genderTextures.put(Person.Gender.OTHER, new Texture("other.png"));

    }

    public static void buyBuilding(float x, float y, BuildingPlan b) {
        Rectangle newPos = new Rectangle(x, y, b.getWidth(), b.getHeight());
        Building newBuilding = new Building(b, newPos);
        buildings.add(newBuilding);

    }

    public static void renderPlots(SpriteBatch batch) {
        for (Building s : buildings) {
            batch.draw(s.getPlan().getSprite(), s.getPosition().getX(), s.getPosition().getY());
        }


    }

    static List<Building> getBuildings() {
        return buildings;
    }

    private static List<Building> getBuildingsNotFull() {
        List<Building> buildingsWithSpace = new ArrayList<Building>();
        for (Building b : buildings) {
            if ((b.getFreeCapacity() > 0)) {
                buildingsWithSpace.add(b);
            }
        }
        return buildingsWithSpace;
    }

    static void generateVillagers() {
        Random rand = new Random();
        double chance = 0.15; // 15% kans voor villager spawn
        if (rand.nextDouble() < chance) {
            List<Building> currentBuildings = getBuildingsNotFull();
            if (currentBuildings.isEmpty())
                return;
            if (currentBuildings.size() > 1) {
                Building mostFree = currentBuildings.get(0);
                for (Building plot : currentBuildings) {
                    if (plot.getFreeCapacity() > mostFree.getFreeCapacity()) {
                        mostFree = plot;
                    }

                }
                mostFree.addPerson();
            } else {
                currentBuildings.get(0).addPerson();
            }
        }


    }
}
