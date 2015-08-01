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
public class Board {

    private static Board gameBoard = new Board();
    private static Symbol[][] symbols;
    private boolean isEmpty;
    private int lenght;

    //singleton class. private constructor
    private Board() {
        symbols = new Symbol[3][3];
        isEmpty = true;
        lenght = 0;
    }

    // create an single object of the gameBoard
    public static Board createGameBoard() {

        if (gameBoard == null) {

            synchronized (Board.class) {

                if (gameBoard == null) {
                    return new Board();
                }

                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        symbols[i][j] = new Symbol();
                    }
                }
            }
        } else {
            synchronized (Board.class) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        symbols[i][j] = new Symbol();
                    }
                }
            }
        }
        return gameBoard;
    }

    public Board getGameBoard() {
        return gameBoard;
    }

    public Symbol getSymbol(int row, int column) {
        return symbols[column][row];
    }

    public void setEmpty(int row, int column) {
        if (symbols[column][row].getStatus() != Status.EMPTY) {
            symbols[column][row] = new Symbol(Status.EMPTY);
            lenght--;
        }
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public int length() {
        return lenght;
    }

    public boolean isFull() {
        return lenght == 9;
    }

    public boolean checkCellAvailability(int row, int column) {
        return (Status.EMPTY == symbols[column][row].getStatus());
    }

    /**
     * set the symbol in the game board at the given position. Return true if
     * the player set the symbol wins. Otherwise return false
     *
     * @param symbol the symbol object contains the status of the given position
     * @param row row number of the position start from 0 - 2
     * @param column column number of the position start from 0 - 2
     * @return player wins or not
     */
    public boolean setSymbol(Symbol symbol, int row, int column) {

        if (symbol.getStatus() != Status.EMPTY && symbols[column][row].getStatus() == Status.EMPTY) {
            symbols[column][row] = symbol;
            lenght++;
            isEmpty = false;
        }

    //--------------------------------------------------------------------------
//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < 3; j++) {
//                System.out.print(getSymbol(i, j).getStatus() + "  ");
//            }
//            System.out.println("");
//        }
//        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
    //-------------------------------------------------------------------------
        boolean returnValue = false;

        for (Symbol s : symbols[column]) {

            if (s.getStatus() != symbol.getStatus()) {
                returnValue = false;
                break;
            }
            returnValue = true;
        }

        if (returnValue) {
            return true;
        }

        for (Symbol[] s : symbols) {

            if (s[row].getStatus() != symbol.getStatus()) {
                returnValue = false;
                break;
            }
            returnValue = true;
        }

        if (returnValue) {
            return true;
        }

        if (symbols[1][1].getStatus() == symbol.getStatus() && (symbols[0][0]).getStatus() == symbol.getStatus() && symbols[2][2].getStatus() == symbol.getStatus()) {
            return true;
            
        } else if (symbols[1][1].getStatus() == symbol.getStatus() && symbols[2][0].getStatus() == symbol.getStatus() && symbols[0][2].getStatus() == symbol.getStatus()) {
            return true;

        }
        return returnValue;
    }

}
