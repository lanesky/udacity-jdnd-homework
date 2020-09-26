package com.udacity.jwdnd.c1.cloudstorage.mapper;

import com.udacity.jwdnd.c1.cloudstorage.model.Credential;
import com.udacity.jwdnd.c1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM FILES WHERE filename = #{fileName}")
    File getFile(String fileName);

    @Select("SELECT * FROM FILES WHERE filename = #{fileName} AND userid = #{userId}")
    File getFileByFileNameAndOwner(String fileName, Integer userId);

    @Select("SELECT * FROM FILES WHERE userid=#{userId} order by filename")
    List<File> getFiles(Integer userId) ;

    @Select("SELECT * FROM FILES WHERE fileId=#{fileId}")
    File getFileById(Integer fileId);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int addFile(File file);

    @Update("UPDATE FILES SET filename=#{fileName}, contenttype=#{contentType}, filesize=#{fileSize} , filedata=#{fileData}  WHERE fileId=#{fileId}")
    int editFile(File file);

    @Delete("DELETE FILES WHERE fileId=#{fileId}")
    int deleteFile(Integer fileId);

}
