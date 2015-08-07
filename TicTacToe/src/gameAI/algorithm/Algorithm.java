/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameAI.algorithm;

import game.model.Board;
import game.model.GameConstants.Status;
import game.model.Symbol;

/**
 *
 * @author Nadheesh
 */
public abstract class Algorithm {

    protected Board gameBoard;

    public Algorithm(Board gameBoard) {
        this.gameBoard = gameBoard;
    }

    public abstract int decideNext(Symbol symbol, int row, int column);

    public int moveLevelOne(Symbol symbol, int row, int column) {

        if (!gameBoard.isFull()) { // check the game has finished with condition of all the boxes are filled
            if (gameBoard.checkCellAvailability(row, column)) {// assure that the box is empty

                if (gameBoard.setSymbol(symbol, row, column)) { //set the symbol at given position of the board and check whether player wins
                    gameBoard.setEmpty(row, column);
                    return 10; // if player wins return 10;
                }
                gameBoard.setEmpty(row, column);
            }
        }
        return 0;
    }

    public int moveLevelTwo(Symbol symbol, int row, int column) {

        if (!gameBoard.isFull()) { // check the game has finished with condition of all the boxes are filled
            Status state = Status.EMPTY;

            if (symbol.getStatus() == Status.CROSS) {
                state = Status.RING;
            } else if (symbol.getStatus() == Status.RING) {
                state = Status.CROSS;
            }
            
            for (int m = 0; m < 3; m++) {
                for (int n = 0; n < 3; n++) {

                    if (gameBoard.checkCellAvailability(m, n)) {

                        if (gameBoard.setSymbol(new Symbol(state), m, n)) { // check wether other player wins at his move
                            gameBoard.setEmpty(row, column);
                            gameBoard.setEmpty(m, n);
                            return -10;
                        }
                        gameBoard.setEmpty(m, n);
                    }
                    
                }
            }

        }

        return 0;
    }

    public int moveLevelThree(Symbol symbol, int row, int column) {

        int ans = 0;
        int win;
        int tie;
        boolean isWin = false;
        boolean isTie = false;

        Status state = Status.EMPTY;

        if (!gameBoard.isFull()) {
            if (symbol.getStatus() == Status.CROSS) {
                state = Status.RING;
            } else if (symbol.getStatus() == Status.RING) {
                state = Status.CROSS;
            }

            for (int m = 0; m < 3; m++) {
                for (int n = 0; n < 3; n++) {

                    if (gameBoard.checkCellAvailability(m, n)) {

                        if (gameBoard.setSymbol(new Symbol(state), m, n)) { // check wether other player wins at his move
                            gameBoard.setEmpty(row, column);
                            gameBoard.setEmpty(m, n);
                            return -10;
                        } else {
                            win = -10;
                            tie = -10;
                            int temp;

                            for (int i = 0; i < 3; i++) {
                                for (int j = 0; j < 3; j++) {

                                    temp = decideNext(new Symbol(symbol.getStatus()), i, j);

                                    if (temp == 10) {
                                        win = 10;
                                        break;
                                    } else if (temp == 0) {
                                        tie = 0;
                                    }
                                    ans += temp;
                                }
                            }
                            if (win < 0 && tie < 0) {
                                gameBoard.setEmpty(m, n);
                                gameBoard.setEmpty(row, column);
                                return -10;
                            } else if (win < 0) {
                                isTie = true;
                            } else {
                                isWin = true;
                            }
                        }
                        gameBoard.setEmpty(m, n);
                    }
                }
            }

            gameBoard.setEmpty(row, column);

            if (isTie) {
                return 0;
            }
            if (isWin) {
                return 10;
            }
        }
        return ans;
    }

}
