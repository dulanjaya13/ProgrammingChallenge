/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkEngine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dulanjaya Tennekoon
 */
public class MultiplayerGameEngine {

    Player currentPlayer;

    Player board[] = {null, null, null, null, null, null, null, null, null};

    class Player extends Thread {
        char mark;
        Player opponent;
        Socket socket;
        BufferedReader input;
        PrintWriter output;
        private String op_name;
        private boolean nameSendFlag = true;

        public Player(Socket socket, char mark) {
            this.socket = socket;
            this.mark = mark;
            try {
                input = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                output = new PrintWriter(socket.getOutputStream(), true);
                output.println("WELCOME " + mark);
                output.println("MESSAGE Waiting for Opponent to Connect");
            } catch (IOException e) {
                System.out.println("Player Died: " + e);
            }
        }

        public void setOpponent(Player opponent) {
            this.opponent = opponent;
        }

        public void otherPlayerMoved(int location) {
            output.println("OPPONENT_MOVED " + location);
            output.println(
                    hasWinner() ? "DEFEAT" : boardFilledUp() ? "TIE" : "");
        }

        public String getOp_Name() {
            return op_name;
        }

        @Override
        public void run(){
            try {
                // Starts When All Players Connected to the Server
                output.println("MESSAGE All players connected");

                // Messege for the First Player
                if (mark == 'X') {
                    output.println("MESSAGE Your Move");
                }
                String tem = input.readLine();
                if (tem.startsWith("MY_NAME")) {
                    System.out.println("got my name Here");
                    op_name = tem.substring(10);
                }

                while (true) {
                    if (opponent.op_name != null && nameSendFlag) {
                        output.println("OPPONENT_NAME : " + opponent.op_name);
                        nameSendFlag = false;
                        break;
                    }
                }
                // Server Repeatedly Wating for the Client
                while (true) {
                    if(opponent.socket.isClosed()) {
                        output.println("DIED");
                    }
                    String command = input.readLine();

                    if (command.startsWith("MOVE")) {
                        int location = Integer.parseInt(command.substring(5));
                        if (legalMove(location, this)) {
                            output.println("VALID_MOVE");
                            output.println(hasWinner() ? "VICTORY"
                                    : boardFilledUp() ? "TIE"
                                            : "");
                        } else {
                            output.println("MESSAGE Wait for the Opponent's Move");
                        }
                    } else if (command.startsWith("QUIT")) {
                        return;
                    }
                }
            } 
            catch (IOException ex) {
                
            }            
            finally {
                try {
                    socket.close();
                    
                } catch (IOException ex) {
                    
                }
            }
        }
    }

    public synchronized boolean legalMove(int location, Player player) {
        if (player == currentPlayer && board[location] == null) {
            board[location] = currentPlayer;
            currentPlayer = currentPlayer.opponent;
            currentPlayer.otherPlayerMoved(location);
            return true;
        }
        return false;
    }

    public boolean hasWinner() {
        return (board[0] != null && board[0] == board[1] && board[0] == board[2])
                || (board[3] != null && board[3] == board[4] && board[3] == board[5])
                || (board[6] != null && board[6] == board[7] && board[6] == board[8])
                || (board[0] != null && board[0] == board[3] && board[0] == board[6])
                || (board[1] != null && board[1] == board[4] && board[1] == board[7])
                || (board[2] != null && board[2] == board[5] && board[2] == board[8])
                || (board[0] != null && board[0] == board[4] && board[0] == board[8])
                || (board[2] != null && board[2] == board[4] && board[2] == board[6]);
    }

    public boolean boardFilledUp() {
        for (int i = 0; i < board.length; i++) {
            if (board[i] == null) {
                return false;
            }
        }
        return true;
    }

}
