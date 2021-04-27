package com.example.noteswidget.model;

public interface NoteSource {
    NoteSource init(NoteSourceResponse noteSourceResponse);
    Note getNote(int position);
    void deleteNote(int position);
    void updateNote(int position, Note note);
    void addNote(Note note);
    void clearNotes();
    int size();
}