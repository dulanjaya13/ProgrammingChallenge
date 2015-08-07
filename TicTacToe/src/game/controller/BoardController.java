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
import game.model.BoardData;

/**
 *
 * @author Nadheesh
 */
public class BoardController {

    public static ArrayList<BoardData> getAllBoards() throws SQLException {

        Connection connection = DBConnection.getConnectionToDB();

        String query = "SELECT player_name, game_mode,  board , saved_date FROM savedGame";

        ResultSet resultSet = DBHandler.getData(connection, query);

        ArrayList<BoardData> boards = new ArrayList<>();
        while (resultSet.next()) {

            boards.add(new BoardData(resultSet.getString("board"),
                    resultSet.getString("player_name") ,
                    resultSet.getInt("game_mode"),
                    resultSet.getDate("saved_date")
            ));
        }

        return boards;
    }

    public static boolean addBoard( BoardData boardData) throws SQLException {

        Connection connection = DBConnection.getConnectionToDB();

        int added = -1;

        Object[] obj = {
            boardData.getBoardString(),
            boardData.getMode(),
            boardData.getPlayerName(),
            boardData.getSavedDate()
        };



            String query = "INSERT INTO savedGame ( board ,game_mode, player_name, saved_date ) VALUES ( ? ,? ,?, ?)";
            added = DBHandler.setData(connection, query, obj);

        return added == 1;

    }
    
    public static boolean deleteSavedBoard(BoardData boardData) throws SQLException{
        
        Connection connection = DBConnection.getConnectionToDB();

        int added = -1;

        Object[] obj = {
            boardData.getBoardString(),
            boardData.getMode(),
            boardData.getPlayerName(),
            boardData.getSavedDate()
        };

            String query = "DELETE FROM savedGame WHERE board=? AND game_mode=? AND player_name=? AND saved_date=? ";
            
            added = DBHandler.setData(connection, query, obj);

        return added == 1;

    }
}
