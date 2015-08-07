/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import game.model.Board;
import game.model.Symbol;
import game.model.GameConstants.Status;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Nadheesh
 */
public class Converter {
    
    public static String boardToString(Board broad){
        
        String stringBoard = "";
        
        for (int i = 0 ; i < 3 ; i++){
            for (int j = 0 ; j < 3; j ++){
                Symbol symbol = broad.getSymbol(i, j);
                if (symbol.getStatus() == Status.CROSS){
                    stringBoard +="C";
                }
                else if (symbol.getStatus() == Status.RING){
                    stringBoard +="R";
                }
                else {
                    stringBoard +="E";
                }
            }
        }
        return  stringBoard;
    }
    
    public static Board stringToBoard(String stBoard){
        Board board = Board.createGameBoard();
        
        for (int i = 0; i < 9; i++) {
            if (stBoard.charAt(i) == 'C'){
               board.setSymbol(new Symbol(Status.CROSS), i/3, i%3);
            }
            else if (stBoard.charAt(i) == 'R'){
               board.setSymbol(new Symbol(Status.RING), i/3, i%3);
            }
            else if (stBoard.charAt(i) == 'E'){
               board.setSymbol(new Symbol(Status.EMPTY), i/3, i%3);
            }
            
               
        }
        return board;
    }
    
    
    public static Date getTody() {
        String date = getStringDate((Calendar.getInstance().getTime()));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            return sdf.parse(date);
        } catch (ParseException ex) {
            return null;
        }
        
    }

    public static String getStringDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
    
    public static String getStringDetails(String name , int mode, Date date){
        String strMode = "";
        
        switch(mode){
            
            case 0 :
                strMode = "Hard";
                break;
            case 1 :
                strMode = "Medium";
                break;
            case 2 :    
                strMode = "Easy";    
        }
        
        return name + "  " + strMode + "  " + getStringDate(date);
    }
}
