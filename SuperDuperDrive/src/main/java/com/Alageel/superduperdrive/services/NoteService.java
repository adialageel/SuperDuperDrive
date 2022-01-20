package com.Alageel.superduperdrive.services;

import com.Alageel.superduperdrive.mapper.NotesMapper;
import com.Alageel.superduperdrive.model.Notes;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NotesMapper notesMapper;

    public NoteService(NotesMapper notesMapper) {
        this.notesMapper = notesMapper;
    }

    public List<Notes> getAllNotes(int userId) {
        return notesMapper.getAllNotesByUserId(userId);
    }

    public void addNote(Notes notes) {
        notesMapper.insert(notes);
    }

    public int deleteNote(int noteId) {
        return notesMapper.delete(noteId);
    }

    public void updateNotes(Notes notes) {
        notesMapper.update(notes);
    }
}
