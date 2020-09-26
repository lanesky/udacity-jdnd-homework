package com.udacity.jwdnd.c1.cloudstorage.services;

import com.udacity.jwdnd.c1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.c1.cloudstorage.model.File;
import com.udacity.jwdnd.c1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {
    private FileMapper fileMapper;

    public  FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }


    public boolean existSameFileName(String fileName, Integer userId) {
        return fileMapper.getFileByFileNameAndOwner(fileName, userId) == null;
    }

    public boolean fileExists(Integer fileId, Integer userId) {
        boolean exist = false;
        List<File> files =  this.fileMapper.getFiles(userId);
        for (File c: files
        ) {
            if (c.getFileId() == fileId) {
                exist = true; break;
            }
        }
        return exist;
    }

    public File getFileById(Integer fileId) {
        return this.fileMapper.getFileById(fileId);
    }

    public List<File> getFiles(Integer userId) {
        return this.fileMapper.getFiles(userId);
    }

    public int addFile(File file) {
        return this.fileMapper.addFile(file);
    }

    public int updateFile(File file) {
        return this.fileMapper.editFile(file);
    }

    public int deleteFile(Integer fileId) {
        return  this.fileMapper.deleteFile(fileId);
    }

}
