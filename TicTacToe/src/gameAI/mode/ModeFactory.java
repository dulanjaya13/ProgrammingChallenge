/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameAI.mode;

import game.model.Board;

/**
 *
 * @author Nadheesh
 */
public class ModeFactory {

    
    private ModeFactory() {

    }
    
    public static Mode createMode(int modeNo , Board board){
        
        switch (modeNo) {
            case (0):
                return new HardMode(board);
                
            case (1):
                return new MediumMode(board);

            case (2):
                return new EasyMode(board);
                
            default:
                return null;
        }
    }
}
