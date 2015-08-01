/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import gameAI.EasyMode;
import gameAI.HardMode;
import gameAI.MediumMode;
import gameAI.Mode;
import model.Board;
import model.GameConstants;
import model.Player;
import static tttgame.TTTGame.printBoard;

/**
 *
 * @author Dulanjaya Tennekoon
 */
public class GameEngine {

    Mode mode;
    Player player;
    Player cpu;
    Board board;
    private boolean firstHuman = false;
    private boolean playerWin = false;
    private boolean cpuWin = false;
    private boolean isTie = true;

    int[] tem = {0, 0};

    public GameEngine() {

    }

    public GameEngine(int mode, String playerName, boolean firstHuman) {
        board = Board.createGameBoard();
        player = new Player(playerName, GameConstants.Status.CROSS);
        cpu = new Player("cpu", GameConstants.Status.RING);
        switch (mode) {
            case (0):
                this.mode = new HardMode(board);
                break;
            case (1):
                this.mode = new MediumMode(board);
                break;
            case (2):
                this.mode = new EasyMode(board);
                break;
            default:
                System.exit(0);
        }
        this.firstHuman = firstHuman;
    }

    public int play() {
        if (!board.isFull()) {
            if (firstHuman) {
                firstHuman = false;
                //playerWin = board.setSymbol(player.nextStep(), row, column);
                printBoard(board);

            } else if (!playerWin) {
                int[] position = mode.nextMove(cpu);
                tem = position;
                cpuWin = board.setSymbol(cpu.nextStep(), position[0], position[1]);
                firstHuman = true;

                printBoard(board);

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
                printBoard(board);

            } else if (!playerWin) {
                int[] position = mode.nextMove(cpu);
                tem = position;
                cpuWin = board.setSymbol(cpu.nextStep(), position[0], position[1]);
                firstHuman = true;
                if (board.isFull()) {
                    return 3;
                }
                printBoard(board);

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
    

}
