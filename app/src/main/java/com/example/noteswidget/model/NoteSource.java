package com.example.noteswidget.model;

import com.example.noteswidget.model.Note;

import java.util.List;

public interface NoteSource {
    Note getNote(int position);
    void deleteNote(int position);
    void updateNote(int position, Note note);
    void addNote(Note note);
    void clearNotes();
}