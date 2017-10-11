package com.mygdx.game.DAL;

public class Highscore implements IrepoObject{
    public int id;
    public String mapName;
    public int AchievedTime;
    public int PlayerID;
    public String PlayerName;

    public Highscore(int id, String mapName, int achievedTime, int playerID)
    {
        this.id = id;
        this.mapName = mapName;
        this.AchievedTime = achievedTime;
        this.PlayerID = playerID;
    }

    public Highscore(int id, String mapName, int achievedTime, int playerID, String playername)
    {
        this.id = id;
        this.mapName = mapName;
        this.AchievedTime = achievedTime;
        this.PlayerID = playerID;
        this.PlayerName = playername;
    }

    public Highscore(String mapName, int AchievedTime, int PlayerID)
    {
        this.mapName = mapName;
        this.AchievedTime = AchievedTime;
        this.PlayerID = PlayerID;
    }

    public void setPlayerName(String playername)
    {
        this.PlayerName = playername;
    }

    @Override
    public String toString()
    {
        return (this.id + "  " + this.mapName + "  " + this.AchievedTime + "  " + this.PlayerID);
    }
}
