/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.engine;

import gameAI.mode.Mode;
import gameAI.mode.ModeFactory;
import game.model.Board;
import game.model.GameConstants;
import game.model.Player;


/**
 *
 * @author Dulanjaya Tennekoon
 */
public class GameEngine {

    private Mode mode;
    private Player player;
    private Player cpu;
    private  Board board;
    private boolean firstHuman = false;
    private boolean playerWin = false;
    private boolean cpuWin = false;
    private boolean isTie = true;
    private int modeInt ;

    int[] tem = {0, 0};

    public GameEngine() {

    }

    public GameEngine(int mode, String playerName, boolean firstHuman) {
        board = Board.createGameBoard();
        player = new Player(playerName, GameConstants.Status.CROSS);
        cpu = new Player("cpu", GameConstants.Status.RING);
        this.mode = ModeFactory.createMode(mode, board);
        this.modeInt =mode;
        this.firstHuman = firstHuman;
    }

    public int play() {
        if (!board.isFull()) {
            if (firstHuman) {
                firstHuman = false;
                //playerWin = board.setSymbol(player.nextStep(), row, column);
                

            } else if (!playerWin) {
                int[] position = mode.nextMove(cpu);
                tem = position;
                cpuWin = board.setSymbol(cpu.nextStep(), position[0], position[1]);
                firstHuman = true;

                

            } else {
                System.out.println("Player Won");
                isTie = false;
                return 1;
            }
            if (cpuWin) {
                System.out.println("CPU Won");
                isTie = false;
                return 2;
            }
        } else {
            return 3;
        }
        return 0;
    }

    public int play(int row, int column) {
        if (!board.isFull()) {
            if (firstHuman) {
                firstHuman = false;
                playerWin = board.setSymbol(player.nextStep(), row, column);
                
            } else if (!playerWin) {
                int[] position = mode.nextMove(cpu);
                tem = position;
                cpuWin = board.setSymbol(cpu.nextStep(), position[0], position[1]);
                firstHuman = true;
                if (board.isFull()) {
                    return 3;
                }
                

            } else {
                System.out.println("Player Won");
                isTie = false;
                return 1;
            }
            if (cpuWin) {
                System.out.println("CPU Won");
                isTie = false;
                return 2;
            }
            return 0;
        } else {
            return 3;
        }
    }

    public int getCPUMove() {
        return 3 * tem[0] + tem[1];
    }
    
    public boolean isADraw() {
        return board.isFull();
    }

    public Player getPlayer() {
        return player;
    }

    public Board getBoard() {
        return board;
    }

    public int getMode() {
        return modeInt;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
    
    public void setHumanFirst(){
        this.firstHuman = true;
    }

}
