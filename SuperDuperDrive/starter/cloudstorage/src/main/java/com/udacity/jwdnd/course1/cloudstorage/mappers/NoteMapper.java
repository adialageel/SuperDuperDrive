package com.udacity.rw.course1.cloudstorage.mappers;

import com.udacity.rw.course1.cloudstorage.models.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

  @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
  List<Note> getAllNotes(int userId);

  @Insert("INSERT INTO NOTES(notetitle,notedescription,userid) " +
          " VALUES(#{noteTitle},#{noteDescription},#{userId})")
  @Options(useGeneratedKeys = true, keyProperty = "noteId")
  int insertNote(Note note);

  @Update("UPDATE NOTES " +
          " SET notetitle = #{noteTitle}, notedescription = #{noteDescription} " +
          " WHERE noteid = #{noteId}")
  int updateNote(Note note);

  @Delete("DELETE FROM NOTES WHERE noteid = #{noteId}")
  void deleteNote(int noteId);
}
