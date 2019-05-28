package com.village.tap;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.net.HttpRequestBuilder.json;

abstract class Upgrade {

    public static List<Upgrade> AllUpgrades;


    private String title;

    private String description;

    private int cost;

    public Upgrade() {
    }

    public static void load(){
        AllUpgrades = json.fromJson(ArrayList.class, TaxUpgrade.class,
                Gdx.files.internal("data/upgrades.json").readString());

    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCost() {
        return cost;
    }
}

class TaxUpgrade extends Upgrade {
    private int goldPerSec;

    public TaxUpgrade() {
    }

    public int getGoldPerSec() {
        return goldPerSec;
    }
}
