/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import database.connector.DBConnection;
import database.handler.DBHandler;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import util.definitions.SQLConstants;

/**
 *
 * @author Nadheesh
 */
public class GameSettingUp {

    public static boolean checkDataBase() throws SQLException {

        Connection connection = DBConnection.getConnectionToDB();
        ResultSet resultSet = connection.getMetaData().getCatalogs();

        while (resultSet.next()) {
            String databaseName = resultSet.getString(1);
            if (databaseName.equals(SQLConstants.DATABASE)) {
                resultSet.close();
                return true;

            }

        }
        resultSet.close();
        return false;
    }

    public static void createDataBase() {

        Statement stmt = null;
        Connection connection = null;
        try {

            Class.forName("com.mysql.jdbc.Driver");

            connection = DBConnection.getConnectionToDB();

            stmt = connection.createStatement();

            String sql = "CREATE DATABASE " + SQLConstants.DATABASE;
            stmt.executeUpdate(sql);

        } catch (SQLException se) {
            JOptionPane.showMessageDialog(null, "DataBase Error. Please Check if you have installed MYSQL");
        } catch (Exception e) {

        } finally {

            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do

        }
    }

    public static void createTables() throws SQLException {

        Connection connection = DBConnection.getConnectionToDB();

        String createTable1 = "CREATE TABLE scores (player_name VARCHAR(15) NOT NULL, player_score INT NOT NULL DEFAULT '0', PRIMARY KEY (player_name)";
        String createTable2 = "CREATE TABLE savedGame(\n"
                + "board_no INT AUTO_INCREMENT ,\n"
                + "player_name VARCHAR(15) NOT NULL,\n"
                + "board VARCHAR(9) NOT NULL,\n"
                + "game_mode  INT NOT NULL,\n"
                + "saved_date DATE NOT NULL,\n"
                + "CONSTRAINT PRIMARY KEY (board_no),\n"
                + "CONSTRAINT foreign KEY (player_name) REFERENCES scores(player_name) ON DELETE CASCADE ON UPDATE CASCADE  \n"
                + "\n"
                + ")";
        
        DBHandler.setData(connection, createTable1);
        DBHandler.setData(connection, createTable2);

    }

}
