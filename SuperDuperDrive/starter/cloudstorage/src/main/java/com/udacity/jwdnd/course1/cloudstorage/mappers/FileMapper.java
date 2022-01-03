package com.udacity.rw.course1.cloudstorage.mappers;

import com.udacity.rw.course1.cloudstorage.models.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

  @Select("SELECT * FROM FILES WHERE filename = #{filename}")
  File getFileByFileName(String filename);

  @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
  File getFile(int fileId);

  @Select("SELECT * FROM FILES WHERE userid = #{userId}")
  List<File> getAllFiles(int userId);

  @Insert("INSERT INTO FILES(filename,contenttype,filesize,userid,filedata) " +
          " VALUES(#{filename},#{contentType},#{fileSize},#{userId},#{fileData})")
  @Options(useGeneratedKeys = true,keyProperty = "fileId")
  int insertFile(File file);

  @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
  void deleteFile(int fileId);

}
