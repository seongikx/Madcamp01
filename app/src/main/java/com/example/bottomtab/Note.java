package com.example.bottomtab;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Note {
    @PrimaryKey(autoGenerate = true)
    int id;


    String content;
    boolean completed;
    private String date ;


    public Note(String date, String content, boolean completed) {
        this.date = date;
        this.content = content;
        this.completed = completed;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }


}