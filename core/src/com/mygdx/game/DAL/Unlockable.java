package com.mygdx.game.DAL;

public class Unlockable implements IrepoObject{

    private int ID;
    private double price;
    private String name;
    private String imagelocation;

    public Unlockable(int id, double price, String name, String imagelocation)
    {
        this.ID = id;
        this.price = price;
        this.name = name;
        this.imagelocation = imagelocation;
    }

    public Unlockable(double price, String name, String imagelocation)
    {
        this.price = price;
        this.name = name;
        this.imagelocation = imagelocation;
    }

    public int getID()
    {
        return this.ID;
    }

    public double getPrice()
    {
        return this.price;
    }

    public String getName()
    {
        return this.name;
    }

    public String getImagelocation(){
        return this.imagelocation;
    }


}