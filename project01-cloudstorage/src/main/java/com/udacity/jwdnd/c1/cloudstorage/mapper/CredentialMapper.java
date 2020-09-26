package com.udacity.jwdnd.c1.cloudstorage.mapper;

import com.udacity.jwdnd.c1.cloudstorage.model.Credential;
import com.udacity.jwdnd.c1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface CredentialMapper {

    @Select("SELECT * FROM CREDENTIALS WHERE username = #{userName}")
    Credential getUser(String userName);

    @Select("SELECT * FROM CREDENTIALS WHERE username = #{userName}  AND userid = #{userId}")
    Credential getCredentialByUserAndOwner(String userName, Integer userId);

    @Select("SELECT * FROM CREDENTIALS WHERE userid=#{userId} order by url")
    List<Credential> getCredentials(Integer userId) ;

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid=#{credentialId}")
    Credential getCredentialById(Integer credentialId);

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES(#{url}, #{userName}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int addCredential(Credential credential);

    @Update("UPDATE CREDENTIALS SET url=#{url}, username=#{userName}, key=#{key}, password=#{password}  WHERE credentialid=#{credentialId}")
    int updateCredential(Credential credential);

    @Delete("DELETE CREDENTIALS WHERE credentialid=#{credentialId}")
    int deleteCredential(Integer credentialId);

}
