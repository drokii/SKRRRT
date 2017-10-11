package com.mygdx.game.DM;

import com.mygdx.game.DAL.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mark
 */
public class PlayerDM extends MysqlClass implements IPlayerDM{

    public PlayerDM()
    {
        super();
    }

    @Override
    public List<Player> getPlayers() {
        try {
            this.connect();
            preparedStatement = connect.prepareStatement("select * from player");
            resultSet = preparedStatement.executeQuery();
            List<Player> returnable = new ArrayList<Player>();
            while(resultSet.next())
            {
                int id = resultSet.getInt("ID");
                String displayname = resultSet.getString("DisplayName");
                String email = resultSet.getString("Email");
                String password = resultSet.getString("Password");
                Player p = new Player(id, displayname, email, password);
                returnable.add(p);
            }
            return returnable;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HighscoreDM.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch(SQLException ex)
        {
            Logger.getLogger(HighscoreDM.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            try {
                connect.close();
            } catch (SQLException ex) {
                Logger.getLogger(PlayerDM.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public Player getPlayer(int playerid) {
        try {
            this.connect();
            preparedStatement = connect.prepareStatement("select * from player where ID = ?");
            preparedStatement.setInt(1, playerid);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
            {
                return new Player(playerid, resultSet.getString("DisplayName"), resultSet.getString("Email"), resultSet.getString("Password"));
            }
            else return null;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HighscoreDM.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch(SQLException ex)
        {
            Logger.getLogger(HighscoreDM.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            try {
                connect.close();
            } catch (SQLException ex) {
                Logger.getLogger(PlayerDM.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public Player logIn(String username, String password) {
        try {
            this.connect();
            preparedStatement = connect.prepareStatement("select * from player where Email = ? and Password = ?");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
            {
                return new Player(resultSet.getInt("ID"), resultSet.getString("DisplayName"), resultSet.getString("Email"), resultSet.getString("Password"));
            }
            else return null;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HighscoreDM.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch(SQLException ex)
        {
            Logger.getLogger(HighscoreDM.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            try {
                connect.close();
            } catch (SQLException ex) {
                Logger.getLogger(PlayerDM.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public boolean addPlayer(Player p) {
        try {
            this.connect();
            preparedStatement = connect.prepareStatement("insert into player(DisplayName, Email, Password) values (?,?,?)");
            preparedStatement.setString(1, p.getDisplayname());
            preparedStatement.setString(2, p.getUsername());
            preparedStatement.setString(3, p.getPassword());
            preparedStatement.executeUpdate();
            return true;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HighscoreDM.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch(SQLException ex)
        {
            Logger.getLogger(HighscoreDM.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            try {
                connect.close();
            } catch (SQLException ex) {
                Logger.getLogger(PlayerDM.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public boolean editPlayer(Player p) {
        try {
            this.connect();
            preparedStatement = connect.prepareStatement("update player set DisplayName = ?, Email = ?, Password = ? where ID = ?");
            preparedStatement.setString(1, p.getDisplayname());
            preparedStatement.setString(2, p.getUsername());
            preparedStatement.setString(3, p.getPassword());
            preparedStatement.setInt(4, p.getID());
            preparedStatement.executeUpdate();
            return true;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HighscoreDM.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch(SQLException ex)
        {
            Logger.getLogger(HighscoreDM.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            try {
                connect.close();
            } catch (SQLException ex) {
                Logger.getLogger(PlayerDM.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void removePlayer(int id) {

        try {
            removeOwnedUnlockables(id);
            removeHighscores(id);
            this.connect();
            preparedStatement = connect.prepareStatement("delete from player where ID = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HighscoreDM.class.getName()).log(Level.SEVERE, null, ex);
        } catch(SQLException ex)
        {
            Logger.getLogger(HighscoreDM.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connect.close();
            } catch (SQLException ex) {
                Logger.getLogger(PlayerDM.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @Override
    public void removeHighscores(int playerid) {
        try {
            this.connect();
            preparedStatement = connect.prepareStatement("delete from highscore where PlayerID = ?");
            preparedStatement.setInt(1, playerid);
            preparedStatement.executeUpdate();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HighscoreDM.class.getName()).log(Level.SEVERE, null, ex);
        } catch(SQLException ex)
        {
            Logger.getLogger(HighscoreDM.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connect.close();
            } catch (SQLException ex) {
                Logger.getLogger(PlayerDM.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void removeOwnedUnlockables(int playerid) {
        try {
            this.connect();
            preparedStatement = connect.prepareStatement("delete from player_unlocked where PlayerID = ?");
            preparedStatement.setInt(1, playerid);
            preparedStatement.executeUpdate();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HighscoreDM.class.getName()).log(Level.SEVERE, null, ex);
        } catch(SQLException ex)
        {
            Logger.getLogger(HighscoreDM.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connect.close();
            } catch (SQLException ex) {
                Logger.getLogger(PlayerDM.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}