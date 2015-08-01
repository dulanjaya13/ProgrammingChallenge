/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import model.GameConstants.Status;

/**
 *
 * @author Nadheesh
 */
public class Player {
    
    private String playerName;
    private int playerMarks;
    private Status state;
    
    
    public Player(String name, Status  state){
        this.playerName = name;
        this.state = state;
    }
    
    public Symbol nextStep(){
        return new Symbol(state);
    }
}

