package com.mygdx.game.DAL;

import com.mygdx.game.DM.*;
import java.util.List;


/**
 *
 * @author mark
 */
public class Repository implements Irepository{

    IUnlockableDM unlockableDM;
    PlayerDM playerDM;
    HighscoreDM highscoreDM;

    public Repository(){
        unlockableDM = new UnlockableDM();
        playerDM = new PlayerDM();
        highscoreDM = new HighscoreDM();
    }

    @Override
    public List<Highscore> getHighscores() {
        return highscoreDM.getHighscores();
    }

    @Override
    public List<Highscore> getHighscoresForPlayer(int playerid) {
        return highscoreDM.getHighscores(playerid);
    }

    @Override
    public boolean addHighscore(Highscore h) {
        return highscoreDM.addHighscore(h);
    }

    @Override
    public boolean editHighscore(Highscore h) {
        return highscoreDM.editHighscore(h);
    }

    @Override
    public void removeHighscore(int id) {
        highscoreDM.removeHighscore(id);
    }

    @Override
    public List<Unlockable> getUnlockables() {
        return unlockableDM.getUnlockables();
    }

    @Override
    public List<Unlockable> getUnlockablesForPlayer(int playerid) {
        return unlockableDM.getUnlockablesForPlayer(playerid);
    }

    @Override
    public boolean addUnlockable(Unlockable u) {
        return unlockableDM.addUnlockable(u);
    }

    @Override
    public boolean editUnlockable(Unlockable u) {
        return unlockableDM.editUnlockable(u);
    }

    @Override
    public void removeUnlockable(int id) {
        unlockableDM.removeUnlockable(id);
    }

    @Override
    public List<Player> getPlayers() {
        return playerDM.getPlayers();
    }

    @Override
    public Player getPlayer(int playerid) {
        return playerDM.getPlayer(playerid);
    }

    @Override
    public boolean addPlayer(Player p) {
        return playerDM.addPlayer(p);
    }

    @Override
    public boolean editPlayer(Player p) {
        return playerDM.editPlayer(p);
    }

    @Override
    public void removePlayer(int id) {
        playerDM.removePlayer(id);
    }

    @Override
    public void removeHighscores(int id) {
        playerDM.removeHighscores(id);
    }

    @Override
    public void removeOwnedUnlockables(int id) {
        playerDM.removeOwnedUnlockables(id);
    }

    @Override
    public List<Highscore> getHighscoresForMap(String MapName) {
        return highscoreDM.getHighscores(MapName);
    }

    @Override
    public Player logIn(String username, String password) {
        return playerDM.logIn(username, password);
    }

    @Override
    public void removePlayerHighscores(int playerid) {
        highscoreDM.removePlayerHighscores(playerid);
    }



}
