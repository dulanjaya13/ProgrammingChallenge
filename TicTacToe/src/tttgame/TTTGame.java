/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tttgame;

import gameAI.HardMode;
import gameAI.Mode;
import java.util.Scanner;
import model.Board;
import model.GameConstants;
import model.Player;

/**
 *
 * @author Nadheesh
 */
public class TTTGame {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //use this code as gameluancher
        
        Board board = Board.createGameBoard();
        
        Mode mode = new HardMode(board);

        Player player = new Player("jnj", GameConstants.Status.CROSS);
        Player cpu = new Player("cpu", GameConstants.Status.RING);
        
        
        Scanner scanner = new Scanner(System.in);
        
        boolean firstHuman = false; 
        boolean playerWin =false;
        boolean cpuWin = false;
        boolean isTie= true;
        while (!board.isFull()) {
            if (firstHuman) {
                int row = scanner.nextInt();
                int column = scanner.nextInt();
                firstHuman= false;
                playerWin = board.setSymbol(player.nextStep(), row, column);
                printBoard(board);

            }else if (!playerWin) {
                int[] position = mode.nextMove(cpu);
                cpuWin =board.setSymbol(cpu.nextStep(), position[0], position[1]);
                firstHuman= true;
                printBoard(board);
            } else {
                System.out.println("Player Won");
                isTie =false;
                break;
            }
            if (cpuWin){
                System.out.println("CPU Won");
                isTie =false;
                break;
            }
            
            
        }
                System.out.println("It is a Tie");
                
        

     
    }

    public static void printBoard(Board board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(board.getSymbol(i, j).getStatus() + "  ");
            }
            System.out.println("");
            
        }
        
            System.out.println("");
            System.out.println("xxxxxxxxxxxxxxxxxxx");
            System.out.println("");
    }
}
