package com.example.noteswidget.model;

import com.example.noteswidget.model.Note;

import java.util.List;

public interface NoteSource {
    List<Note> getData();
}