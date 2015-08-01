/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

import model.Board;
import model.Symbol;

/**
 *
 * @author Nadheesh
 */
public class HardAlgorithm extends Algorithm {

    
    public HardAlgorithm(Board gameBoard) {
        super(gameBoard);

    }

    @Override
    public int decideNext(Symbol symbol, int row, int column) {
        if (!gameBoard.isFull()) { // check the game has finished with condition of all the boxes are filled
            
            if (gameBoard.checkCellAvailability(row, column)) {

                if (moveLevelOne(symbol, row, column) == 10) {
                    return 10;

                } else { // if player doesn't win buid the game to see posibilities of winning

                    gameBoard.setSymbol(symbol, row, column);

                    int ans =  moveLevelThree(symbol, row, column);
                    gameBoard.setEmpty(row, column);
                    return ans;
                }
            }else {
                return 100;
            }
        }
        return  0 ;
    }

}
