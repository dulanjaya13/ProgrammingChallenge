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
public class MediumAlgorithm extends Algorithm{

    public MediumAlgorithm(Board gameBoard) {
        super(gameBoard);
    }

    @Override
    public int decideNext(Symbol symbol, int row, int column) {
         
        if (gameBoard.checkCellAvailability(row, column)) {

                if (moveLevelOne(symbol, row, column) == 10) {
                    return 10;

                } else { // if player doesn't win buid the game to see posibilities of winning
                    
                    gameBoard.setSymbol(symbol, row, column);
                    int ans =  moveLevelTwo(symbol, row, column);
                    gameBoard.setEmpty(row, column);
                    
                    return ans;
                }
    }else
            return 100;
    }
    
}
