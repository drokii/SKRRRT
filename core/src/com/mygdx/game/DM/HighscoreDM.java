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
public class HighscoreDM extends MysqlClass implements IHighscoreDM{

    public HighscoreDM()
    {
        super();
    }

    @Override
    public List<Highscore> getHighscores() {
        try {
            this.connect();
            preparedStatement = connect.prepareStatement("select * from highscore h, player p where p.ID = h.PlayerID order by MapName");
            resultSet = preparedStatement.executeQuery();
            List<Highscore> returnable = new ArrayList<Highscore>();
            while(resultSet.next())
            {
                int id = resultSet.getInt("h.ID");
                String mapname = resultSet.getString("MapName");
                int time = resultSet.getInt("AchievedTime");
                int playerid = resultSet.getInt("PlayerID");
                String playername = resultSet.getString("DisplayName");
                Highscore h = new Highscore(id, mapname, time, playerid, playername);
                returnable.add(h);
            }
            return returnable;

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HighscoreDM.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch(SQLException ex)
        {
            Logger.getLogger(HighscoreDM.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        finally
        {
            try {
                connect.close();
            } catch (SQLException ex) {
                Logger.getLogger(HighscoreDM.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public List<Highscore> getHighscores(int playerid) {
        try {
            this.connect();
            preparedStatement = connect.prepareStatement("select * from highscore h, player p where p.ID = h.PlayerID and p.ID = ? order by MapName");
            preparedStatement.setInt(1, playerid);
            resultSet = preparedStatement.executeQuery();
            List<Highscore> returnable = new ArrayList<Highscore>();
            while(resultSet.next())
            {
                int id = resultSet.getInt("h.ID");
                String mapname = resultSet.getString("MapName");
                int time = resultSet.getInt("AchievedTime");
                int pid = resultSet.getInt("PlayerID");
                String playername = resultSet.getString("DisplayName");
                Highscore h = new Highscore(id, mapname, time, pid, playername);
                returnable.add(h);
            }
            return returnable;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HighscoreDM.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch(SQLException ex)
        {
            Logger.getLogger(HighscoreDM.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        finally{
            try {
                connect.close();
            } catch (SQLException ex) {
                Logger.getLogger(HighscoreDM.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public List<Highscore> getHighscores(String MapName) {
        try {
            this.connect();
            preparedStatement = connect.prepareStatement("select * from highscore h, player p where p.ID = h.PlayerID and h.MapName = ? order by MapName");
            preparedStatement.setString(1, MapName);
            resultSet = preparedStatement.executeQuery();
            List<Highscore> returnable = new ArrayList<Highscore>();
            while(resultSet.next())
            {
                int id = resultSet.getInt("h.ID");
                String mapname = resultSet.getString("MapName");
                int time = resultSet.getInt("AchievedTime");
                int pid = resultSet.getInt("PlayerID");
                String playername = resultSet.getString("DisplayName");
                Highscore h = new Highscore(id, mapname, time, pid, playername);
                returnable.add(h);
            }
            return returnable;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HighscoreDM.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch(SQLException ex)
        {
            Logger.getLogger(HighscoreDM.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        finally{
            try {
                connect.close();
            } catch (SQLException ex) {
                Logger.getLogger(HighscoreDM.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public boolean addHighscore(Highscore h) {
        try {
            this.connect();
            preparedStatement = connect.prepareStatement("Insert into highscore (MapName, AchievedTime, PlayerID)VALUES (?,?,?)");
            preparedStatement.setString(1, h.mapName);
            preparedStatement.setInt(2, h.AchievedTime);
            preparedStatement.setInt(3, h.PlayerID);
            preparedStatement.executeUpdate();
            return true;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HighscoreDM.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch(SQLException ex)
        {
            Logger.getLogger(HighscoreDM.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        finally{
            try {
                connect.close();
            } catch (SQLException ex) {
                Logger.getLogger(HighscoreDM.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public boolean editHighscore(Highscore h) {
        try {
            if(h.id > 0){
                this.connect();
                preparedStatement = connect.prepareStatement("Update Highscore set MapName = ?, AchievedTime = ?, PlayerID = ? where ID = ?");
                preparedStatement.setString(1, h.mapName);
                preparedStatement.setInt(2, h.AchievedTime);
                preparedStatement.setInt(3, h.PlayerID);
                preparedStatement.setInt(4, h.id);
                preparedStatement.executeUpdate();

                return true;
            }else{
                return false;
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HighscoreDM.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch(SQLException ex)
        {
            Logger.getLogger(HighscoreDM.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        finally{
            try {
                connect.close();
            } catch (SQLException ex) {
                Logger.getLogger(HighscoreDM.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void removePlayerHighscores(int playerid) {
        try {
            this.connect();
            preparedStatement = connect.prepareStatement("delete from Highscore where PlayerID = ?");
            preparedStatement.setInt(1, playerid);
            preparedStatement.executeUpdate();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HighscoreDM.class.getName()).log(Level.SEVERE, null, ex);
        } catch(SQLException ex)
        {
            Logger.getLogger(HighscoreDM.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            try {
                connect.close();
            } catch (SQLException ex) {
                Logger.getLogger(HighscoreDM.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void removeHighscore(int id) {
        try {
            this.connect();
            preparedStatement = connect.prepareStatement("Delete from Highscore where ID = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HighscoreDM.class.getName()).log(Level.SEVERE, null, ex);
        } catch(SQLException ex)
        {
            Logger.getLogger(HighscoreDM.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            try {
                connect.close();
            } catch (SQLException ex) {
                Logger.getLogger(HighscoreDM.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}