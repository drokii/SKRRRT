package com.mygdx.game.DM;

import com.mygdx.game.DAL.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author mark
 */
public interface IUnlockableDM{

    List<Unlockable> getUnlockables();
    List<Unlockable> getUnlockablesForPlayer(int playerid);
    boolean addUnlockable(Unlockable u);
    boolean editUnlockable(Unlockable u);
    void removeUnlockable(int id);

    Connection connect() throws SQLException, ClassNotFoundException;
}