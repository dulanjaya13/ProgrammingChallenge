/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameAI;

import algorithm.Algorithm;
import java.util.Random;
import model.Board;
import model.Player;

/**
 *
 * @author Nadheesh
 */
public abstract class Mode {
    
    protected  Algorithm algorithm;
    protected  Board gameBoard;

    public Mode(Board gameBoard) {
        this.gameBoard = gameBoard;
    }
    
    
    public abstract int[] nextMove(Player cpu);
    
    
    public int[] getRandomSpot(){
        
        boolean[] emptyCells = new boolean[9];
        int count = 0;
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                
                if (gameBoard.checkCellAvailability(i, j)) {
                    int index = i*3 + j;
                    emptyCells[index] = true; 
                    count ++;
                }
            }
        }
        
        Random random = new Random();
        int ranPosition = random.nextInt(count)+1;
        
        int pos = 0;
        for (int i = 0 ; i < 9 ; i ++){
            if (emptyCells[i]){
                ranPosition--;
            }
            if (ranPosition== 0){
                pos= i;
                
            }
        }
        int[] ret = {pos/3,pos%3};
        return ret;
    }
    
    
    
}
