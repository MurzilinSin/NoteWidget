package com.example.noteswidget.observe;

import com.example.noteswidget.model.Note;
import com.example.noteswidget.model.NoteSourceImpl;

public interface Observer {
    void updateNote(Note note);
}
