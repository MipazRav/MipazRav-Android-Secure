package com.mipazrav.mipazrav;

/**
 * Created by joshuaegoldmeier on 9/5/2016.
 */


public class Shiur {
    private final String name, description, recID;

    public Shiur(String name, String description, String recID) {
        this.name = name;
        this.description = description;
        this.recID = recID;

    }

    public String getRecID() {
        return recID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}
