package com.example.bottomtab;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NoteDao {


    @Query("SELECT * FROM note ORDER BY id DESC")
    List<Note> select();

    // "INSERT INTO todo (id, content) VALUES(1, '밥 먹기')
    @Query("INSERT INTO note (content,completed) VALUES(:content,:completed)")
    void insert(String content,Boolean completed);

    @Insert
    void insert(Note note);

    @Query("DELETE FROM note WHERE id = :id")
    void delete(int id);

    @Delete
    void delete(Note note);

    @Query("UPDATE note SET completed = 1 WHERE id = :id")
    void complete(int id);

    @Query("SELECT completed FROM note WHERE id = :id")
    boolean getcomplete(int id);

    @Query("SELECT content FROM note WHERE date = :date")
    String getcontent(String date);

    @Query("SELECT * FROM note WHERE date = :date")
    List<Note> selectdate(String date);

    @Query("UPDATE note SET completed=:bool WHERE id = :id")
    void setcomplete(boolean bool,int id);

    @Query( "SELECT id FROM note ORDER BY ROWID DESC LIMIT 1;")
    int lastItem();

    @Query("DELETE FROM note;")
    void deleteAll();


}
