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
public class UnlockableDM extends MysqlClass implements IUnlockableDM{

    public UnlockableDM()
    {
        super();
    }


    public List<Unlockable> getUnlockables() {
        try {
            this.connect();
            preparedStatement = connect.prepareStatement("Select * from Unlockable");
            resultSet = preparedStatement.executeQuery();
            List<Unlockable> returnable = new ArrayList<Unlockable>();
            while(resultSet.next())
            {
                int id = resultSet.getInt("ID");
                double price = resultSet.getDouble("price");
                String name = resultSet.getString("Name");
                String image = resultSet.getString("Image");
                Unlockable u = new Unlockable(id, price, name, image);
                returnable.add(u);
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
                Logger.getLogger(UnlockableDM.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }


    public List<Unlockable> getUnlockablesForPlayer(int playerid) {
        try {
            this.connect();
            preparedStatement = connect.prepareStatement("select u.* from unlockable u, player_unlocked pu where u.ID = pu.UnlockableID and pu.PlayerID = ?");
            preparedStatement.setInt(1, playerid);
            resultSet = preparedStatement.executeQuery();
            List<Unlockable> returnable = new ArrayList<Unlockable>();
            while(resultSet.next())
            {
                int id = resultSet.getInt("ID");
                double price = resultSet.getDouble("price");
                String name = resultSet.getString("Name");
                String image = resultSet.getString("Image");
                Unlockable u = new Unlockable(id, price, name, image);
                returnable.add(u);
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
                Logger.getLogger(UnlockableDM.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }


    public boolean addUnlockable(Unlockable u) {
        try {
            this.connect();
            preparedStatement = connect.prepareStatement("insert into unlockable (price, Name, Image) values (?,?,?)");
            preparedStatement.setDouble(1, u.getPrice());
            preparedStatement.setString(2, u.getName());
            preparedStatement.setString(3, u.getImagelocation());
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
                Logger.getLogger(UnlockableDM.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }


    public boolean editUnlockable(Unlockable u) {
        try {
            this.connect();
            preparedStatement = connect.prepareStatement("update unlockable set price = ?, Name = ?, Image = ? where ID = ?");
            preparedStatement.setDouble(1, u.getPrice());
            preparedStatement.setString(2, u.getName());
            preparedStatement.setString(3, u.getImagelocation());
            preparedStatement.setInt(4, u.getID());
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
                Logger.getLogger(UnlockableDM.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }


    public void removeUnlockable(int id) {
        try {
            removePlayerUnlockable(id); //remove dependencies in database.
            this.connect();
            preparedStatement = connect.prepareStatement("delete from unlockable where ID = ?");
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
                Logger.getLogger(UnlockableDM.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void removePlayerUnlockable(int unlockableID)
    {
        try {
            this.connect();
            preparedStatement = connect.prepareStatement("delete from player_unlocked where UnlockableID = ?");
            preparedStatement.setInt(1, unlockableID);
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
                Logger.getLogger(UnlockableDM.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


    }



}
