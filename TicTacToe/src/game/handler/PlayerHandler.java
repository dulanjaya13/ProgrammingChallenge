/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.handler;

import game.controller.PlayerController;
import game.model.Player;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Nadheesh
 */
public class PlayerHandler {
    
    
    
    private ArrayList<Player> players ;
    
    
    public PlayerHandler (){
        loadPlayers();
    }
    private void loadPlayers() {
        try {
            players = PlayerController.getAllPlayers();
        } catch (SQLException ex) {
            Logger.getLogger(PlayerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public boolean setNewPlayer(String name) throws SQLException{
        for (Player p : players){
           if (p.getPlayerName().equals(name) ){
               JOptionPane.showMessageDialog(null,name+ " is already exists.\nPlease Enter new name.");
               return false;
           } 
        }
        if (PlayerController.addPlayer(name)){
            players.add(new Player(name));
            return true;
        }
        return false;
    }
    
    public boolean updateScores(String playerName,int score) throws SQLException{
        
        for (Player p  : players){
            if (p.getPlayerName().equals(playerName)){
                int sc = p.getPlayerScore()+ score;
                p.setPlayerScore(sc);
                PlayerController.updatePlayerMarks(p);
                
            }
        }
        return false;
    }
    
    
    public ArrayList<Player> getPlayers() {
        return players;
    }
}
