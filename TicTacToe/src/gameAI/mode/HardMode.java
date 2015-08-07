/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameAI.mode;

import gameAI.algorithm.HardAlgorithm;
import java.util.ArrayList;
import java.util.Random;
import game.model.Board;
import game.model.Player;

/**
 *
 * @author Nadheesh
 */
public class HardMode extends Mode{
    
    public HardMode(Board board) {
        super(board);
        this.algorithm = new HardAlgorithm(board);
    }

    @Override
    public int[] nextMove(Player cpu) {

        int ans= -10;
        ArrayList < int[]> winList= new ArrayList<>();
        ArrayList < int[]> tieList= new ArrayList<>();
        int ans2 = 0;
        
        //try to win the game if any chances exists
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                
                if (gameBoard.checkCellAvailability(i, j)) {
                    
                    ans2 =algorithm.moveLevelOne(cpu.nextStep(), i, j);
                    if (ans2 == 10){
                        int[] winPosition= {i, j};
                        return winPosition;
                    }
                    
                    ans= algorithm.decideNext(cpu.nextStep(), i, j);
                    if (ans == 10) {
                        int[] winPosition= {i, j};
                        winList.add(winPosition);
                    }
                    if (ans == 0) {
                        int[] tiePosition= {i, j};
                        tieList.add(tiePosition);
                    }
                }
            }
        }
        Random random = new Random();
        
        if (!winList.isEmpty()){
            int randomNum = random.nextInt(winList.size());
            return winList.get(randomNum);
        }
        if (!tieList.isEmpty()){
            int randomNum = random.nextInt(tieList.size());
            return tieList.get(randomNum);
        }
        //else try a random spot in the game board
        return getRandomSpot();
    }

    }
