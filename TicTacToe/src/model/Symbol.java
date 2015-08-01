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
public class Symbol {
    
    private Status status ;
    
    
    public Symbol() {
        status = Status.EMPTY;
    }
    
    public Symbol(Status state) {
        status = state;
    }
    
    public Status getStatus(){
        return status;
    }
    
    public void setStatus (Status status){
        this.status = status ;
    }
    
}
