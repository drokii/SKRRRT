package com.mygdx.game.DM;

import com.mygdx.game.DAL.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author mark
 */
public interface IPlayerDM {
    List<Player> getPlayers();
    Player getPlayer(int playerid);
    Player logIn(String username, String password);
    boolean addPlayer(Player p);
    boolean editPlayer(Player p);
    void removePlayer(int id);
    void removeHighscores(int playerid);
    void removeOwnedUnlockables(int playerid);

    Connection connect() throws SQLException, ClassNotFoundException;
}