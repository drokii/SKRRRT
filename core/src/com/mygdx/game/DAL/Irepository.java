package com.mygdx.game.DAL;

import java.util.List;

public interface Irepository {

    List<Highscore> getHighscores();
    List<Highscore> getHighscoresForPlayer(int playerid);
    List<Highscore> getHighscoresForMap(String MapName);

    boolean addHighscore(Highscore h);
    boolean editHighscore(Highscore h);
    void removeHighscore(int id);
    void removePlayerHighscores(int playerid);

    List<Unlockable> getUnlockables();
    List<Unlockable> getUnlockablesForPlayer(int playerid);
    boolean addUnlockable(Unlockable u);
    boolean editUnlockable(Unlockable u);
    void removeUnlockable(int id);

    List<Player> getPlayers();
    Player getPlayer(int playerid);
    Player logIn(String username, String password);
    boolean addPlayer(Player p);
    boolean editPlayer(Player p);
    void removePlayer(int id);
    void removeHighscores(int id);
    void removeOwnedUnlockables(int id);



}
