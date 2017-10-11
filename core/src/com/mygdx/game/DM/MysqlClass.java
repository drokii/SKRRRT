package com.mygdx.game.DM;

import java.sql.*;
/**
 *
 * @author mark
 */
public abstract class MysqlClass {

    private String connstr = "";
    private String username = "dbi322685";
    private String password = "pts3";
    public volatile Statement statement = null;
    public volatile Connection connect = null;
    public volatile PreparedStatement preparedStatement = null;
    public volatile ResultSet resultSet = null;

    public MysqlClass()
    {

    }

    public Connection connect() throws ClassNotFoundException, SQLException
    {
        System.out.println("success");

        Class.forName("com.mysql.jdbc.Driver");

        connect = DriverManager.getConnection("jdbc:mysql://studmysql01.fhict.local/dbi322685", username, password);

        if(connect != null)
        {
            return connect;
        }
        else
        {
            throw new SQLException();
        }
    }
}