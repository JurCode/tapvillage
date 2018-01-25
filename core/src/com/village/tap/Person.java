package com.village.tap;

import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.Random;

public class Person {
    static Random r;
    public enum Gender{
        MALE, FEMALE, OTHER
    }

    public static HashMap<Person.Gender, Texture> genderTextures;

    public static Texture getGenderTexture(Person.Gender id){
        return genderTextures.get(id);
    }

    private String firstName;
    private String lastName;
    private Gender gender;
    private int age;

    static String[] firstNames = {"Jack","Mohamed","Gerard","Maryjane","Lisa","Alicia","Bram","John","Nicole","Dean"};
    static String[] lastNames = {"Smith","Johnson","Wilson","Martinez","Lee","Lopez","Keys","Davis","Clark","Harris"};

    public Person(){
        r = new Random();
        firstName = firstNames[r.nextInt(firstNames.length)];
        lastName = lastNames[r.nextInt(lastNames.length)];
        gender = Gender.values()[r.nextInt(Gender.values().length)];
        age = r.nextInt(70);
    }

    public int getAge(){
        return age;
    }
    public String getName(){
        return firstName+ " " + lastName;
    }

    public Gender getGender(){
        return gender;
    }

    public String toString(){
        return firstName + " " + lastName + " Gender: " + gender.toString() + " Age: " + age;
    }

}
