package com.mygdx.game.DAL;

public class Player implements IrepoObject{

    private int id;
    private String displayName;
    private String username;
    private String password;

    public Player(int id, String displayname, String username, String password){

        this.id = id;
        this.displayName = displayname;
        this.username = username;
        this.password = password;
    }

    public Player(int id, String displayName)
    {
        this.id = id;
        this.displayName = displayName;
    }

    public Player(String displayname, String username, String password)
    {
        this.displayName = displayname;
        this.username = username;
        this.password = password;
    }

    public int getID(){
        return this.id;
    }

    public String getDisplayname(){
        return this.displayName;
    }

    public String getUsername(){
        return this.username;
    }

    public String getPassword()
    {
        return this.password;
    }

    @Override
    public String toString()
    {
        return (this.displayName + "  " + this.username + "  " + this.password + "  " + this.id);
    }
}
