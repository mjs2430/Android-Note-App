package com.example.journal;

import java.io.Serializable;

public class Note implements Serializable {
    private int id;
    private String timestamp;
    private String text;

    public Note(String timestamp, String text) {
        this.id = id;
        this.timestamp = timestamp;
        this.text = text;
    }

    // Add getter and setter for id
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTimestamp() {
        return timestamp;
    }

    public String getText() {
        return text;
    }
}
