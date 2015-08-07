/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameAI.mode;

import gameAI.algorithm.MediumAlgorithm;
import game.model.Board;
import game.model.Player;

/**
 *
 * @author Nadheesh
 */
public class MediumMode extends Mode {

    public MediumMode(Board gameBoard) {
        super(gameBoard);
        algorithm = new MediumAlgorithm(gameBoard);
    }

    @Override
    public int[] nextMove(Player cpu) {
        int ans = -10;

        int m = -1;
        int n = -1;
        boolean losePosition = false;
        //try to win the game if any chances exists
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                if (gameBoard.checkCellAvailability(i, j)) {
                    ans = algorithm.decideNext(cpu.nextStep(), i, j);
                    
                    if (ans == 10) {
                        int[] ret = {i, j};
                        return ret;
                    }else if(ans == -10){
                        losePosition = true;        
                    }else if (ans == 0) {
                        m = i;
                        n = j;
                    }
                }
            }
        }
        
        if (m >= 0 && n >= 0 && losePosition) {
            
            int[] ret = {m, n};
            return ret;

        }

        //else try a random spot in the game board
        return getRandomSpot();
    }

}
