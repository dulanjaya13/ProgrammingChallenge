/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

import model.Board;
import model.GameConstants;
import model.Symbol;

/**
 *
 * @author Nadheesh
 */
public class MiniMax {

    private Board gameBoard;
    private final int[][] values = new int[3][3];

    public MiniMax(Board gameBoard) {
        this.gameBoard = gameBoard;

        for (int m = 0; m < 3; m++) {
            for (int n = 0; n < 3; n++) {
                values[n][m] = -10;
            }
        }
    }

    public int next(Symbol symbol, int row, int column) {

        int ans = 0;
        int win = 0;
        int tie = 0;

        if (!gameBoard.isFull()) { // check the game has finished with condition of all the boxes are filled

            if (gameBoard.checkCellAvailability(row, column)) {// assure that the box is empty

                if (gameBoard.setSymbol(symbol, row, column)) { //set the symbol at given position of the board and check whether player wins
                    gameBoard.setEmpty(row, column);
                    return 10; // if player wins return 10;

                } else { // if player doesn't win buid the game to see posibilities of winning

                    if (symbol.getStatus() == GameConstants.Status.CROSS) {

                        for (int m = 0; m < 3; m++) {
                            for (int n = 0; n < 3; n++) {

                                if (gameBoard.checkCellAvailability(m, n)) {

                                    if (gameBoard.setSymbol(new Symbol(GameConstants.Status.RING), m, n)) { // check wether other player wins at his move
                                        gameBoard.setEmpty(row, column);
                                        gameBoard.setEmpty(m, n);
                                        return -10;

                                    } else {
                                        
                                        int temp = -10;

                                        for (int i = 0; i < 3; i++) {
                                            for (int j = 0; j < 3; j++) {

                                                temp = next(new Symbol(GameConstants.Status.CROSS), i, j);
                                                if (temp == 10) {
                                                    win = 10;
                                                }
                                                if (temp == 0){
                                                    tie = 0;
                                                }
                                                ans += temp;
                                            }
                                        }
                                    }
                                    gameBoard.setEmpty(m, n);
                                }
                            }
                        }

                    } else {

                        for (int m = 0; m < 3; m++) {
                            for (int n = 0; n < 3; n++) {
                                if (gameBoard.checkCellAvailability(m, n)) {
                                    if (gameBoard.setSymbol(new Symbol(GameConstants.Status.CROSS), m, n)) {
                                        gameBoard.setEmpty(row, column);
                                        gameBoard.setEmpty(m, n);
                                        return -10;
                                    } else {
                                        int temp = 0;
                                        for (int i = 0; i < 3; i++) {
                                            for (int j = 0; j < 3; j++) {
                                                temp = next(new Symbol(GameConstants.Status.RING), i, j);
                                                if (temp == 10) {
                                                    win = 10;
                                                }
                                                if (temp == 0){
                                                    tie = 0;
                                                }
                                                ans += temp;
                                            }

                                        }
                                    }
                                    gameBoard.setEmpty(m, n);
                                }
                            }
                        }

                    }
                }
                gameBoard.setEmpty(row, column);
                if (win == 10) {
                    return 10;
                }
                if (tie == 0){
                    return 0 ;
                }
            }
            return ans;
        }
        return 0;
    }
}
