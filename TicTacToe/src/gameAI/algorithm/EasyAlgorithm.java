/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameAI.algorithm;

import game.model.Board;
import game.model.Symbol;

/**
 *
 * @author Nadheesh
 */
public class EasyAlgorithm  extends Algorithm{

    public EasyAlgorithm(Board gameBoard) {
        super(gameBoard);
    }

    @Override
    public int decideNext(Symbol symbol, int row, int column) {        
        if (gameBoard.checkCellAvailability(row, column)){
            return moveLevelOne(symbol, row, column); 
        }else{
            return 100;
        }
    }
    
}
