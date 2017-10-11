package com.mygdx.game.DM;

import com.mygdx.game.DAL.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author mark
 */
public interface IHighscoreDM {
    Connection connect() throws SQLException, ClassNotFoundException;
    List<Highscore> getHighscores();
    List<Highscore> getHighscores(int playerid);
    List<Highscore> getHighscores(String MapName);

    boolean addHighscore(Highscore h);
    boolean editHighscore(Highscore h);
    void removePlayerHighscores(int playerid);
    void removeHighscore(int id);
}