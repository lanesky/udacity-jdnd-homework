package com.udacity.jwdnd.c1.cloudstorage.services;

import com.udacity.jwdnd.c1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.c1.cloudstorage.model.Credential;
import com.udacity.jwdnd.c1.cloudstorage.model.Note;
import com.udacity.jwdnd.c1.cloudstorage.model.NoteForm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private NoteMapper noteMapper;

    public  NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public boolean noteExists(Integer noteId, Integer userId) {
        boolean exist = false;
        List<Note> notes =  this.noteMapper.getNotes(userId);
        for (Note c: notes
        ) {
            if (c.getNoteId() == noteId) {
                exist = true; break;
            }
        }
        return exist;
    }

    public  Note getNoteById(Integer noteId) {
        return this.noteMapper.getNoteById(noteId);
    }

    public List<Note> getNotes(Integer userId) {
        return this.noteMapper.getNotes(userId);
    }

    public int addNote(NoteForm noteForm) {
        return this.noteMapper.addNote(new Note(null, noteForm.getNoteTitle(), noteForm.getNoteDescription(), noteForm.getUserId()));
    }

    public  int updateNote(NoteForm noteForm) {
        return this.noteMapper.updateNote(new Note(noteForm.getNoteId(), noteForm.getNoteTitle(), noteForm.getNoteDescription(), noteForm.getUserId()));
    }

    public int deleteNote(Integer noteId) {
        return this.noteMapper.deleteNote(noteId);
    }
}
