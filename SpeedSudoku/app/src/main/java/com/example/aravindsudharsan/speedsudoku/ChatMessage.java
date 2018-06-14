package com.example.aravindsudharsan.speedsudoku;

/**
 * Created by reale on 18/11/2016.
 */

public class ChatMessage {
    private String UserName;
    private String Email;
    private int Progress;
    private int puzzle_solved;
    private int ready;
    private int user_active;
    private int puzzle_id;


    public ChatMessage(String u, String e, int p, int s, int r, int ua,int pi) {
        this.UserName = u;
        this.Email=e;
        this.Progress=p;
        this.puzzle_solved=s;
        this.ready=r;
        this.user_active=ua;
        this.puzzle_id=pi;
    }

    public ChatMessage() {
    }

    public String getUserName() {
        return UserName;
    }

    public String getEmail() {
        return Email;
    }

    public int getPuzzle_solved(){return puzzle_solved;}

    public int getReady(){return ready;}

    public int getUser_active() {
        return user_active;
    }

    public int getPuzzle_id(){ return puzzle_id;}

    public void setUserName(String messageText) {
        this.UserName = messageText;
    }



    public int getProgress() {
        return Progress;
    }

    public void setProgress(int messageTime) {
        this.Progress = messageTime;
    }
}
