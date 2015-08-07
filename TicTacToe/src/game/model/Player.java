/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.model;

import game.model.GameConstants.Status;

/**
 *
 * @author Nadheesh
 */
public class Player {
    private int playerNo;
    private String playerName;
    private int score;
    private Status state;

    public Player( String playerName, int score) {
        
        this.playerName = playerName;
        this.score = score;
    }
    public Player (String name){
        this.playerName=name;
    }
    
    public Player(String name, Status  state){
        this.playerName = name;
        this.state = state;
    }
   
    
    public Symbol nextStep(){
        return new Symbol(state);
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getPlayerScore() {
        return score;
    }

    public void setPlayerScore(int playerMarks) {
        this.score = playerMarks;
    }

    @Override
    public String toString() {
        return playerName;
    }
    
    
    
}

