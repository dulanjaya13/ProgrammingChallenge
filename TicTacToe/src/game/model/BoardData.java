/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.model;

import java.util.Date;

/**
 *
 * @author Nadheesh
 */
public class BoardData {
    private String boardString;
    private String playerName;
    private int boardNo;
    private int mode;
    private Date savedDate;
    
    public BoardData(String boardString, String playerName, int boardNo, int mode) {
        this.boardString = boardString;
        this.playerName = playerName;
        this.boardNo = boardNo;
        this.mode = mode;
    }

    public BoardData(String boardString, String playerName, int mode, Date date) {
        this.boardString = boardString;
        this.playerName = playerName;
        this.mode = mode;
        this.savedDate = date;
    }

    public String getBoardString() {
        return boardString;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getBoardNo() {
        return boardNo;
    }

    public int getMode() {
        return mode;
    }

    public Date getSavedDate() {
        return savedDate;
    }
    
    
    
}
