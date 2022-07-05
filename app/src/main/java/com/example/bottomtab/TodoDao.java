package com.example.bottomtab;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TodoDao {

    /*
        SELECT *   : 모든 항목 조회
        FROM to-do : to-do 테이블에서
        (WHERE 조건식) : 조건에 맞는 레코드만.
        ORDER BY id DESC : id 내림차순으로 (ASC : 오름차순)
     */
    @Query("SELECT * FROM todo ORDER BY id DESC")
    List<Todo> select();

    // "INSERT INTO todo (id, content) VALUES(1, '밥 먹기')
    @Query("INSERT INTO todo (content,completed) VALUES(:content,:completed)")
    void insert( String content, Boolean completed);

    @Query("SELECT * FROM todo WHERE date = :date")
    List<Todo> selectdate(String date);


    @Query( "SELECT id FROM todo ORDER BY ROWID DESC LIMIT 1;")
    int lastItem();


    @Insert
    void insert(Todo todo);

    @Query("DELETE FROM todo WHERE id = :id")
    void delete(int id);

    @Delete
    void delete(Todo todo);

    @Query("UPDATE todo SET completed = 1 WHERE id = :id")
    void complete(int id);
}