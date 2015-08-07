/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.controller;

import database.connector.DBConnection;
import database.handler.DBHandler;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import game.model.Player;

/**
 *
 * @author Nadheesh
 */
public class PlayerController {
    
    public static ArrayList<Player> getAllPlayers() throws SQLException {

        Connection connection = DBConnection.getConnectionToDB();
        String query = "SELECT player_name ,player_score FROM scores" ;

        ResultSet resultSet = DBHandler.getData(connection, query);

        ArrayList<Player> players = new ArrayList();
        while (resultSet.next()) {
            Player player = new Player(resultSet.getString("player_name"), 
                                       resultSet.getInt("player_score"));
   
            players.add(player);
        }
        return players;
    }
    
    public static boolean addPlayer(String playerName) throws SQLException{
        
        Connection connection = DBConnection.getConnectionToDB();

        String query = "INSERT INTO scores (player_name) VALUES (?)"; 
        int Added = -1 ;
        Object [] objs = {
            playerName
        };
        Added = DBHandler.setData(connection, query, objs);
        return Added==1;        
    }
   
    
    public static boolean updatePlayerMarks(Player player) throws SQLException{
        
        Connection connection = DBConnection.getConnectionToDB();

        String query = "UPDATE scores SET player_score = ? WHERE player_name = ? ";
  
        int Added = -1 ;
        Object [] objs = {
            player.getPlayerScore(),
            player.getPlayerName()
        };
        Added = DBHandler.setData(connection, query, objs);
        return Added==1;        
    }
    
}
